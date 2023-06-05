package net.satisfyu.meadow;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.satisfyu.meadow.block.ModBlocks;
import net.satisfyu.meadow.block.cheeseForm.CheeseFormScreen;
import net.satisfyu.meadow.block.cookingCauldron.CookingCauldronScreen;
import net.satisfyu.meadow.block.cookingPot.CookingPotScreen;
import net.satisfyu.meadow.block.fondueBlock.FondueScreen;
import net.satisfyu.meadow.block.woodCutter.WoodcutterScreen;
import net.satisfyu.meadow.entity.ModEntities;
import net.satisfyu.meadow.entity.custom.bear.brown.BrownBearEntityModel;
import net.satisfyu.meadow.entity.custom.bear.brown.BrownBearEntityRenderer;
import net.satisfyu.meadow.entity.custom.buffalo.water_buffalo.WaterBuffaloEntityModel;
import net.satisfyu.meadow.entity.custom.buffalo.water_buffalo.WaterBuffaloEntityRenderer;
import net.satisfyu.meadow.entity.custom.chair.ChairEntityRenderer;
import net.satisfyu.meadow.entity.custom.chicken.chicken1.Chicken1Renderer;
import net.satisfyu.meadow.entity.custom.chicken.chicken2.Chicken2Renderer;
import net.satisfyu.meadow.entity.custom.chicken.chicken3.Chicken3Renderer;
import net.satisfyu.meadow.entity.custom.cow.albino_cow.AlbinoCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.cookie_cow.CookieCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.cream_cow.CreamCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.dairy_cow.DairyCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.dark_cow.DarkCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.pinto_cow.PintoCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.shearable.WoolyCowModel;
import net.satisfyu.meadow.entity.custom.cow.shearable.highland_cattle.HighlandCattleRenderer;
import net.satisfyu.meadow.entity.custom.cow.shearable.umbra.UmbraCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.shearable.warped.WarpedCowRenderer;
import net.satisfyu.meadow.entity.custom.cow.sunset_cow.SunsetCowRenderer;
import net.satisfyu.meadow.entity.custom.sheep.flecked.FleckedSheepRenderer;
import net.satisfyu.meadow.entity.custom.sheep.fuzzy.FuzzySheepRenderer;
import net.satisfyu.meadow.entity.custom.sheep.horned.HornedSheepModel;
import net.satisfyu.meadow.entity.custom.sheep.horned.HornedSheepRenderer;
import net.satisfyu.meadow.entity.custom.sheep.inky.InkySheepRenderer;
import net.satisfyu.meadow.entity.custom.sheep.long_nosed.LongNosedSheepRenderer;
import net.satisfyu.meadow.entity.custom.sheep.patched.PatchedSheepRenderer;
import net.satisfyu.meadow.entity.custom.sheep.rocky.RockySheepRenderer;
import net.satisfyu.meadow.item.ModItems;
import net.satisfyu.meadow.item.custom.BowModelProvider;
import net.satisfyu.meadow.item.custom.FurArmorItem;
import net.satisfyu.meadow.item.custom.HuntingBowItem;
import net.satisfyu.meadow.particle.ModParticles;
import net.satisfyu.meadow.particle.custom.SplashParticle;
import net.satisfyu.meadow.render.BigFlowerPotBlockEntityRenderer;
import net.satisfyu.meadow.render.CheeseRackBlockEntityRenderer;
import net.satisfyu.meadow.render.FlowerBoxBlockEntityRenderer;
import net.satisfyu.meadow.render.WheelBarrowBlockEntityRenderer;
import net.satisfyu.meadow.screenHandler.ModScreenHandlers;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.satisfyu.meadow.Meadow.MOD_ID;
import static net.satisfyu.meadow.Meadow.PINE_ID;

public class MeadowClient implements ClientModInitializer {

    public static final EntityModelLayer FLECKED_SHEEP_FUR = new EntityModelLayer(new Identifier(MOD_ID, "flecked_sheep"), "fur");
    public static final EntityModelLayer FLECKED_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "flecked_sheep"), "main");

    public static final EntityModelLayer FUZZY_SHEEP_FUR = new EntityModelLayer(new Identifier(MOD_ID, "fuzzy_sheep"), "fur");
    public static final EntityModelLayer FUZZY_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "fuzzy_sheep"), "main");

    public static final EntityModelLayer HORNED_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "horned_sheep"), "main");

    public static final EntityModelLayer BROWN_BEAR_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "brown_bear"), "main");

    public static final EntityModelLayer INKY_SHEEP_FUR = new EntityModelLayer(new Identifier(MOD_ID, "inky_sheep"), "fur");
    public static final EntityModelLayer INKY_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "inky_sheep"), "main");

    public static final EntityModelLayer LONG_NOSED_SHEEP_FUR = new EntityModelLayer(new Identifier(MOD_ID, "long_nosed_sheep"), "fur");
    public static final EntityModelLayer LONG_NOSED_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "long_nosed_sheep"), "main");

    public static final EntityModelLayer PATCHED_SHEEP_FUR = new EntityModelLayer(new Identifier(MOD_ID, "patched_sheep"), "fur");
    public static final EntityModelLayer PATCHED_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "patched_sheep"), "main");

    public static final EntityModelLayer ROCKY_SHEEP_FUR = new EntityModelLayer(new Identifier(MOD_ID, "rocky_sheep"), "fur");
    public static final EntityModelLayer ROCKY_SHEEP_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "rocky_sheep"), "main");

    public static final EntityModelLayer UMBRA_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "umbra_cow"), "head");
    public static final EntityModelLayer WARPED_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "warped_cow"), "head");

    public static final EntityModelLayer HIGHLAND_CATTLE_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "highland_cattle"), "head");

    public static final EntityModelLayer ALBINO_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "albino_cow"), "head");
    public static final EntityModelLayer WATER_BUFFALO_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "water_buffalo"), "head");

    public static final EntityModelLayer COOKIE_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "cookie_cow"), "head");

    public static final EntityModelLayer CREAM_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "cream_cow"), "head");

    public static final EntityModelLayer DAIRY_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "dairy_cow"), "head");

    public static final EntityModelLayer DARK_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "dark_cow"), "head");

    public static final EntityModelLayer PINTO_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "pinto_cow"), "head");

    public static final EntityModelLayer SUNSET_COW_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "sunset_cow"), "head");

    public static final EntityModelLayer CHICKEN1_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "chicken_1"), "main");

    public static final EntityModelLayer CHICKEN2_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "chicken_2"), "main");

    public static final EntityModelLayer CHICKEN3_MODEL_LAYER = new EntityModelLayer(new Identifier(MOD_ID, "chicken_3"), "main");

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.WOODCUTTER, ModBlocks.OAT_CROP, ModBlocks.JUNIPER_CROP, ModBlocks.YARROW_CROP, ModBlocks.MOUNTAIN_LAVENDER_CROP, ModBlocks.ALPINE_GRASS, ModBlocks.ALPINE_GRASS_TALL,
                ModBlocks.PINE_DOOR, ModBlocks.PINE_TRAPDOOR, ModBlocks.DELPHINIUM, ModBlocks.ALPINE_POPPY, ModBlocks.SAXIFRAGE,
                ModBlocks.ENZIAN, ModBlocks.COOKING_CAULDRON, ModBlocks.FRAME, ModBlocks.TABLE, ModBlocks.FIRE_LOG, ModBlocks.ERIOPHORUM,
                ModBlocks.ERIOPHORUM_TALL, ModBlocks.FURNACE_COBBLESTONE, ModBlocks.SMALL_FIR, ModBlocks.PINE_SAPLING, ModBlocks.CHAIR, ModBlocks.OATBLOCK_RUG,
                ModBlocks.POTTED_DELPHINIUM, ModBlocks.POTTED_ALPINE_POPPY, ModBlocks.POTTED_SAXIFRAGE, ModBlocks.POTTED_ENZIAN,
                ModBlocks.POTTED_ERIOPHORUM, ModBlocks.WILD_JUNIPER, ModBlocks.WILD_MOUNTAIN_LAVENDER, ModBlocks.WILD_YARROW,
                ModBlocks.POTTED_ERIOPHORUM, ModBlocks.ERIOPHORUM_TALL, ModBlocks.PINE_SAPLING, ModBlocks.POTTED_PINE_SAPLING, ModBlocks.FIRE_LILY, ModBlocks.POTTED_FIRE_LILY,
                ModBlocks.POTTED_OAK_SAPLING, ModBlocks.POTTED_SPRUCE_SAPLING, ModBlocks.POTTED_BIRCH_SAPLING, ModBlocks.POTTED_JUNGLE_SAPLING, ModBlocks.POTTED_ACACIA_SAPLING,
                ModBlocks.POTTED_DARK_OAK_SAPLING, ModBlocks.POTTED_DANDELION, ModBlocks.POTTED_POPPY, ModBlocks.POTTED_BLUE_ORCHID, ModBlocks.POTTED_ALLIUM, ModBlocks.POTTED_AZURE_BLUET,
                ModBlocks.POTTED_RED_TULIP, ModBlocks.POTTED_ORANGE_TULIP, ModBlocks.POTTED_WHITE_TULIP, ModBlocks.POTTED_PINK_TULIP, ModBlocks.POTTED_OXEYE_DAISY, ModBlocks.POTTED_CORNFLOWER,
                ModBlocks.POTTED_LILY_OF_THE_VALLEY, ModBlocks.POTTED_WITHER_ROSE, ModBlocks.W_POTTED_PINE_SAPLING, ModBlocks.W_POTTED_DELPHINIUM, ModBlocks.W_POTTED_ALPINE_POPPY, ModBlocks.W_POTTED_SAXIFRAGE,
                ModBlocks.W_POTTED_ENZIAN, ModBlocks.W_POTTED_FIRE_LILY, ModBlocks.W_POTTED_ERIOPHORUM, ModBlocks.POTTED_AZALEA, ModBlocks.POTTED_FLOWERING_AZALEA, ModBlocks.FLOWER_POT_BIG, ModBlocks.FONDUE, ModBlocks.OIL_LANTERN, ModBlocks.WHEELBARROW);

        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.WINDOW, ModBlocks.WINDOW_2, ModBlocks.WINDOW_3);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.WINDOW);
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.SIGN_TEXTURE_ID));

        registerClientScreens();
        registerCows();
        registerSheeps();
        registerParticles();
        registerChicken();
        registerOtherEntities();

        BowModelProvider.registerModModels();

        TerraformBoatClientHelper.registerModelLayers(PINE_ID);

        BlockEntityRendererFactories.register(ModEntities.CHEESE_RACK_BLOCK_ENTITY, CheeseRackBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(ModEntities.WHEEL_BARROW_BLOCK_ENTITY, WheelBarrowBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(ModEntities.BIG_FLOWER_POT_BLOCK_ENTITY, BigFlowerPotBlockEntityRenderer::new);

        BlockEntityRendererFactories.register(ModEntities.FLOWER_BOX_BLOCK_ENTITY, FlowerBoxBlockEntityRenderer::new);

    }

    private static void registerClientScreens(){
        HandledScreens.register(ModScreenHandlers.CHEESE_FORM_SCREEN_HANDLER, CheeseFormScreen::new);
        HandledScreens.register(ModScreenHandlers.WOODCUTTOR_SCREEN_HANDLER, WoodcutterScreen::new);
        HandledScreens.register(ModScreenHandlers.COOKING_CAULDRON_SCREEN_HANDLER, CookingCauldronScreen::new);
        HandledScreens.register(ModScreenHandlers.COOKING_POT_SCREEN_HANDLER, CookingPotScreen::new);
        HandledScreens.register(ModScreenHandlers.FONDUE_SCREEN_HANDLER, FondueScreen::new);
    }

    private void registerParticles(){
        ParticleFactoryRegistry.getInstance().register(ModParticles.CHEESE_SPLASH, SplashParticle.Factory::new);
    }

    private static void registerOtherEntities(){
        EntityRendererRegistry.register(ModEntities.BROWN_BEAR, BrownBearEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(BROWN_BEAR_MODEL_LAYER, BrownBearEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.CHAIR, ChairEntityRenderer::new);
    }

    private void registerBowPredicate(HuntingBowItem bowItem) {
        ModelPredicateProviderRegistry.register(bowItem, new Identifier("pull"),
                (itemStack, clientWorld, livingEntity, i) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    }
                    return livingEntity.getActiveItem() != itemStack ? 0.0F
                            : (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / 20.0F;
                });
        ModelPredicateProviderRegistry.register(bowItem, new Identifier("pulling"),
                (itemStack, clientWorld, livingEntity, i) -> {
                    if (livingEntity == null) {
                        return 0.0F;
                    }
                    return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
                });
    }

    private static void registerCows(){
        EntityRendererRegistry.register(ModEntities.UMBRA_COW, UmbraCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(UMBRA_COW_MODEL_LAYER, WoolyCowModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.WARPED_COW, WarpedCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(WARPED_COW_MODEL_LAYER, WoolyCowModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.HIGHLAND_CATTLE, HighlandCattleRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HIGHLAND_CATTLE_MODEL_LAYER, WoolyCowModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.ALBINO_COW, AlbinoCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ALBINO_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.WATER_BUFFALO, WaterBuffaloEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(WATER_BUFFALO_MODEL_LAYER, WaterBuffaloEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.COOKIE_COW, CookieCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(COOKIE_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.CREAM_COW, CreamCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CREAM_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DAIRY_COW, DairyCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(DAIRY_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.DARK_COW, DarkCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(DARK_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PINTO_COW, PintoCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PINTO_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.SUNSET_COW, SunsetCowRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SUNSET_COW_MODEL_LAYER, CowEntityModel::getTexturedModelData);
    }

    private void registerSheeps(){
        EntityRendererRegistry.register(ModEntities.FLECKED_SHEEP, FleckedSheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(FLECKED_SHEEP_MODEL_LAYER, SheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FLECKED_SHEEP_FUR, SheepWoolEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.FUZZY_SHEEP, FuzzySheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(FUZZY_SHEEP_MODEL_LAYER, SheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FUZZY_SHEEP_FUR, SheepWoolEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.HORNED_SHEEP, HornedSheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HORNED_SHEEP_MODEL_LAYER, HornedSheepModel::getTexturedModelData);


        EntityRendererRegistry.register(ModEntities.INKY_SHEEP, InkySheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(INKY_SHEEP_MODEL_LAYER, SheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(INKY_SHEEP_FUR, SheepWoolEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.LONG_NOSED_SHEEP, LongNosedSheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(LONG_NOSED_SHEEP_MODEL_LAYER, SheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LONG_NOSED_SHEEP_FUR, SheepWoolEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.PATCHED_SHEEP, PatchedSheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PATCHED_SHEEP_MODEL_LAYER, SheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(PATCHED_SHEEP_FUR, SheepWoolEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.ROCKY_SHEEP, RockySheepRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ROCKY_SHEEP_MODEL_LAYER, SheepEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ROCKY_SHEEP_FUR, SheepWoolEntityModel::getTexturedModelData);
    }

    private void registerChicken(){
        EntityRendererRegistry.register(ModEntities.CHICKEN1, Chicken1Renderer::new);
        EntityModelLayerRegistry.registerModelLayer(CHICKEN1_MODEL_LAYER, ChickenEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.CHICKEN2, Chicken2Renderer::new);
        EntityModelLayerRegistry.registerModelLayer(CHICKEN2_MODEL_LAYER, ChickenEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(ModEntities.CHICKEN3, Chicken3Renderer::new);
        EntityModelLayerRegistry.registerModelLayer(CHICKEN3_MODEL_LAYER, ChickenEntityModel::getTexturedModelData);
    }

    public static void appendToolTip(@NotNull List<Text> tooltip){
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = player.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        tooltip.add(Text.of(""));
        tooltip.add(Text.of(Formatting.DARK_GREEN + I18n.translate("meadow.tooltip.fur_armor")));
        tooltip.add(Text.of((helmet != null && helmet.getItem() instanceof FurArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ModItems.BEAR_FUR_HELMET.getName().getString() + "]"));
        tooltip.add(Text.of((chestplate != null && chestplate.getItem() instanceof FurArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ModItems.BEAR_FUR_CHESTPLATE.getName().getString() + "]"));
        tooltip.add(Text.of((leggings != null && leggings.getItem() instanceof FurArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ModItems.BEAR_FUR_LEGGINGS.getName().getString() + "]"));
        tooltip.add(Text.of((boots != null && boots.getItem() instanceof FurArmorItem ? Formatting.GREEN.toString() : Formatting.GRAY.toString()) + "- [" + ModItems.BEAR_FUR_BOOTS.getName().getString() + "]"));
        tooltip.add(Text.of(""));
        tooltip.add(Text.of(Formatting.GRAY + I18n.translate("meadow.tooltip.fur_armor2")));
        tooltip.add(Text.of(((helmet != null && helmet.getItem() instanceof FurArmorItem &&
                chestplate != null && chestplate.getItem() instanceof FurArmorItem &&
                leggings != null && leggings.getItem() instanceof FurArmorItem &&
                boots != null && boots.getItem() instanceof FurArmorItem) ? Formatting.DARK_GREEN.toString() : Formatting.GRAY.toString()) + I18n.translate("meadow.tooltip.fur_armor3")));
    }

    public static PlayerEntity getClientPlayer() {
        return MinecraftClient.getInstance().player;
    }
    
}