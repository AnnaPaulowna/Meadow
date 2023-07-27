package net.satisfyu.meadow.compat.jei;

import dev.architectury.registry.registries.RegistrySupplier;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.util.Identifier;
import net.satisfyu.meadow.util.MeadowIdentifier;

import java.util.Objects;


@JeiPlugin
public class BakeryJEIPlugin implements IModPlugin {

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CookingPotCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new StoveCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BakerStationCategory(registration.getJeiHelpers().getGuiHelper()));
    }


    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<CookingPotRecipe> fridgeRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.COOKING_POT_RECIPE_TYPE.get());
        registration.addRecipes(CookingPotCategory.COOKING_POT, fridgeRecipes);

        List<StoveRecipe> stoveRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.STOVE_RECIPE_TYPE.get());
        registration.addRecipes(StoveCategory.STOVE, stoveRecipes);

        List<BakerStationRecipe> bakerStationRecipes = rm.getAllRecipesFor(RecipeTypeRegistry.BAKER_STATION_RECIPE_TYPE.get());
        registration.addRecipes(BakerStationCategory.BAKER_STATION, bakerStationRecipes);
    }

    @Override
    public Identifier getPluginUid() {
        return new MeadowIdentifier("jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        addCatalyst(registration, ObjectRegistry.SMALL_COOKING_POT, CookingPotCategory.COOKING_POT);
        addCatalyst(registration, ObjectRegistry.BAKER_STATION, BakerStationCategory.BAKER_STATION);

        addCatalyst(registration, ObjectRegistry.BRICK_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.STONE_BRICKS_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.COBBLESTONE_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.DEEPSLATE_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.GRANITE_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.MUD_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.SANDSTONE_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.END_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.RED_NETHER_BRICKS_STOVE, BakerStationCategory.BAKER_STATION);
        addCatalyst(registration, ObjectRegistry.QUARTZ_STOVE, BakerStationCategory.BAKER_STATION);
    }

    private static void addCatalyst(IRecipeCatalystRegistration registration, RegistrySupplier<Block> block, RecipeType<?>... recipeTypes){
        registration.addRecipeCatalyst(block.get().asItem().getDefaultInstance(), recipeTypes);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(new CookingTransferInfo());
        registration.addRecipeTransferHandler(StoveGuiHandler.class, ScreenHandlerTypeRegistry.STOVE_SCREEN_HANDLER.get(), StoveCategory.STOVE, 1, 3, 5, 36);
        registration.addRecipeTransferHandler(BakerStationGuiHandler.class, ScreenHandlerTypeRegistry.BAKER_STATION_SCREEN_HANDLER.get(), BakerStationCategory.BAKER_STATION, 1, 2, 3, 36);
    }

    public static void addSlot(IRecipeLayoutBuilder builder, int x, int y, Ingredient ingredient){
        builder.addSlot(RecipeIngredientRole.INPUT, x, y).addIngredients(ingredient);
    }
}
