package net.satisfyu.meadow.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.satisfyu.meadow.block.WoodenFlowerPotBigBlock;
import net.satisfyu.meadow.entity.WoodenFlowerPotBigBlockEntity;

import static de.cristelknight.doapi.client.ClientUtil.renderBlock;

public class WoodenFlowerPotBigRenderer implements BlockEntityRenderer<WoodenFlowerPotBigBlockEntity> {

    @SuppressWarnings("unused")
    public WoodenFlowerPotBigRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(WoodenFlowerPotBigBlockEntity entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!entity.hasLevel()) {
            return;
        }
        BlockState selfState = entity.getBlockState();
        if (selfState.getBlock() instanceof WoodenFlowerPotBigBlock) {
            Item item = entity.getFlower();
            matrices.pushPose();
            if (item instanceof BlockItem) {
                BlockState state = ((BlockItem) item).getBlock().defaultBlockState();
                matrices.translate(0f, 0.4f, 0f);
                renderBlock(state, matrices, vertexConsumers, entity);
                state = ((BlockItem) item).getBlock().defaultBlockState().setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);
                matrices.translate(0f, 1f, 0f);
                renderBlock(state, matrices, vertexConsumers, entity);
            }
        }
        matrices.popPose();
    }

}