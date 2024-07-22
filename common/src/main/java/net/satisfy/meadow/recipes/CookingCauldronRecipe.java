package net.satisfy.meadow.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.satisfy.meadow.registry.RecipeRegistry;
import net.satisfy.meadow.util.NewGeneralUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public class CookingCauldronRecipe implements Recipe<RecipeInput> {
    final ResourceLocation id;
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;

    public CookingCauldronRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.inputs = inputs;
        this.output = output;
    }

    public CookingCauldronRecipe(Optional<ResourceLocation> resourceLocation, List<Ingredient> ingredients, ItemStack itemStack) {
        this.id = resourceLocation.orElseGet(() -> ResourceLocation.fromNamespaceAndPath("meadow", "cooking_cauldron_recipe"));
        NonNullList<Ingredient> nonNullList = NonNullList.create();
        nonNullList.addAll(ingredients);
        this.inputs = nonNullList;
        this.output = itemStack;
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return NewGeneralUtil.matchesRecipe(recipeInput, inputs, 0, 6);
    }

    public ItemStack assemble() {
        return assemble(null, null);
    }

    @Override
    public @NotNull ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output.copy();
    }

    public ItemStack getResultItem() {
        return getResultItem(null);
    }

    public @NotNull ResourceLocation getId() {
        return id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegistry.COOKING_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeRegistry.COOKING.get();
    }

    @Override
    public @NotNull NonNullList<Ingredient> getIngredients() {
        return this.inputs;
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<CookingCauldronRecipe> {
        public static final MapCodec<CookingCauldronRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        ResourceLocation.CODEC.optionalFieldOf("id").forGetter(recipe -> Optional.of(recipe.getId())),
                        Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(CookingCauldronRecipe::getIngredients),
                        ItemStack.CODEC.fieldOf("output").forGetter(recipe -> recipe.output)
                ).apply(instance, CookingCauldronRecipe::new)
        );

        public static final StreamCodec<RegistryFriendlyByteBuf, CookingCauldronRecipe> STREAM_CODEC = StreamCodec.of(CookingCauldronRecipe.Serializer::toNetwork, CookingCauldronRecipe.Serializer::fromNetwork);

        public static CookingCauldronRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            ResourceLocation id = buf.readResourceLocation();
            NonNullList<Ingredient> ingredients = NonNullList.create();
            var children = buf.readCollection(ArrayList::new, buffer -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
            ingredients.addAll(children);
            final var output = ItemStack.OPTIONAL_STREAM_CODEC.decode(buf);

            return new CookingCauldronRecipe(id, ingredients, output);
        }

        public static void toNetwork(RegistryFriendlyByteBuf buf, CookingCauldronRecipe recipe) {
            buf.writeResourceLocation(recipe.getId());
            buf.writeCollection(recipe.getIngredients(), (b, child) -> Ingredient.CONTENTS_STREAM_CODEC.encode(buf, child));
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buf, recipe.output);
        }

        @Override
        public @NotNull MapCodec<CookingCauldronRecipe> codec() {
            return CODEC;
        }

        @Override
        public @NotNull StreamCodec<RegistryFriendlyByteBuf, CookingCauldronRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}