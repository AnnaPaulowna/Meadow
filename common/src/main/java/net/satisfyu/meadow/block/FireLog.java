package net.satisfyu.meadow.block;

import de.cristelknight.doapi.common.block.FacingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.satisfyu.meadow.registry.ObjectRegistry;

import java.util.List;

public class FireLog extends FacingBlock {

    public static final IntProperty STAGE = IntProperty.of("stage", 0, 3);

    private static final VoxelShape SHAPE_AXE = Block.createCuboidShape(1, 0, 1, 15, 4, 15);

    private static final VoxelShape SHAPE_SMALL = Block.createCuboidShape(1, 0, 1, 15, 8, 15);
    private static final VoxelShape SHAPE_MID = Block.createCuboidShape(1, 0, 1, 15, 12, 15);
    private static final VoxelShape SHAPE_BIG = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

    public FireLog(Settings setting) {
        super(setting);
        this.setDefaultState(this.getDefaultState().with(STAGE, 1));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        int stage = state.get(STAGE);
        if (stage == 0) return SHAPE_AXE;
        else if (stage == 1) return SHAPE_SMALL;
        else if (stage == 2) return SHAPE_MID;
        else if (stage == 3) return SHAPE_BIG;
        else return SHAPE_AXE;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        int stage = state.get(STAGE);
        ItemStack stack = player.getStackInHand(hand);

        if (player.isSneaking()) {
            if (stack.isEmpty() && stage > 0) {
                stage--;
                player.giveItemStack(new ItemStack(ObjectRegistry.FIRE_LOG.get()));
                world.setBlockState(pos, state.with(STAGE, stage));
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        } else {
            if (stack.isOf(this.asItem())) {
                if (stage < 3 && stage > 0) {
                    stage++;
                    if (!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                }
            } else if (stack.isOf(Items.IRON_AXE) && stage == 1 && stack.getDamage() == 0) {
                stage = 0;
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
            }
        }

        if (stage == state.get(STAGE)) {
            return ActionResult.PASS;
        }

        world.setBlockState(pos, state.with(STAGE, stage));
        world.playSound(null, pos, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.BLOCKS, 1.0f, 1.0f);
        return ActionResult.SUCCESS;
    }

    private void dropItemStack(World world, BlockPos pos) {
        Block.dropStack(world, pos, new ItemStack(ObjectRegistry.FIRE_LOG.get(), 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(STAGE);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, BlockView world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("block.meadow.canbeplaced.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
        tooltip.add(Text.translatable("block.meadow.fuel_item.tooltip").formatted(Formatting.ITALIC, Formatting.GRAY));
    }
}
