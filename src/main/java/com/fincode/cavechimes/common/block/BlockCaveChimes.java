package com.fincode.cavechimes.common.block;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.ConfigCommon;
import com.fincode.cavechimes.common.tileentity.TileEntityCaveChimes;
import com.fincode.cavechimes.init.CaveChimesBlocks;
import com.fincode.cavechimes.init.CaveChimesClient;
import com.fincode.cavechimes.init.CaveChimesItems;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCaveChimes extends Block implements ITileEntityProvider {
    public static IntegerProperty VOLUME = IntegerProperty.create("volume", 0, 15);

    protected static final VoxelShape CHIMES_AABB = Block.makeCuboidShape(4, 4, 4, 12, 16, 12);

    public BlockCaveChimes() {
        super(Properties.create(Material.IRON, EnumDyeColor.GRAY)
                .hardnessAndResistance(1, 3)
                .needsRandomTick()
                .sound(SoundType.METAL)
                .doesNotBlockMovement());

        this.setDefaultState(getStateContainer().getBaseState().with(VOLUME, 0));
    }

    public BlockFaceShape getBlockFaceShape(IBlockReader p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getOpacity(IBlockState p_200011_1_, IBlockReader p_200011_2_, BlockPos p_200011_3_) {
        return 0;
    }

    @Override
    public VoxelShape getShape(IBlockState p_185496_1_, IBlockReader p_185496_2_, BlockPos p_185496_3_) {
        return CHIMES_AABB;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(CaveChimesItems.CAVE_CHIMES);
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IWorldReaderBase world, BlockPos pos) {
        return false;
    }

    public static boolean canHang(IBlockReader world, BlockPos pos) {
        return world.getBlockState(pos.up()).getBlock() != Blocks.AIR && faceShapeValid(world.getBlockState(pos.up()).getBlockFaceShape(world, pos.up(), EnumFacing.DOWN));
    }

    private static boolean faceShapeValid(BlockFaceShape shape) {
        return shape == BlockFaceShape.CENTER ||
                shape == BlockFaceShape.CENTER_BIG ||
                shape == BlockFaceShape.SOLID ||
                shape == BlockFaceShape.MIDDLE_POLE ||
                shape == BlockFaceShape.MIDDLE_POLE_THICK;
    }

    public boolean isFullCube(IBlockState p_149686_1_) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState p_190946_1_) {
        return true;
    }

    public EnumBlockRenderType getRenderType(IBlockState p_149645_1_) {
        return CaveChimesClient.isModelSimplified() ? EnumBlockRenderType.MODEL : EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Nullable
    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext ctx) {
        return canHang(ctx.getWorld(), ctx.getPos()) ? getDefaultState().with(VOLUME, ctx.getWorld().getRedstonePowerFromNeighbors(ctx.getPos())) : null;
    }

    public void update(World world, BlockPos pos, IBlockState state) {
        if ((!world.isBlockPowered(pos) && state.get(VOLUME) == 0)) return;

        //CaveChimesMod.getLogger().warn("POWAAAHHHHH");
        short power = (short)world.getRedstonePowerFromNeighbors(pos);

        //CaveChimesMod.getLogger().warn("I am a whiny bitch!!! " + power + " != " + state.getValue(VOLUME).shortValue());

        if (state.get(VOLUME).shortValue() != power || world.isRemote) // I will be furious if this is the issue right here.
            updateVolume(world, pos, state, power);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos posOfNeighbor) {
        super.neighborChanged(state, world, pos, block, posOfNeighbor);
        //CaveChimesMod.getLogger().info("Updating chimes.");
        neighborChangeInternal(world, pos, state, block, posOfNeighbor);
    }

    @Override
    public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock, BlockPos changedBlockPos) {
        update(world, observerPos, observerState);
    }

    private void neighborChangeInternal(World world, BlockPos pos, IBlockState state, Block neighborBlock, BlockPos neighborPos) {
        if (!canHang(world, pos)) {
            breakChimes(world, pos);
            return;
        }

        //CaveChimesMod.getLogger().info(state.getBlock());
        if (state.getBlock() == CaveChimesBlocks.CAVE_CHIMES)
            update(world, pos, state);
    }


    private boolean canDrop() {
        return ConfigCommon.obtaining.canBlockDrop.get();
    }

    private boolean requiresSilkTouch() {
        return ConfigCommon.obtaining.silkTouchRequired.get();
    }

    private void breakChimes(World world, BlockPos pos) {
        //CaveChimesMod.getLogger().info("Allegedly breaking cave chimes.");
        if (ConfigCommon.obtaining.canBlockDrop.get()) {
            if (!ConfigCommon.obtaining.silkTouchRequired.get()) {
                world.destroyBlock(pos, true);

                return;
            }
        }
        world.destroyBlock(pos, false);
    }

    @Override
    public boolean canDropFromExplosion(Explosion p_149659_1_) {
        return super.canDropFromExplosion(p_149659_1_) && canDrop() && !requiresSilkTouch();
    }

    @Override
    public int getItemsToDropCount(IBlockState p_196251_1_, int p_196251_2_, World p_196251_3_, BlockPos p_196251_4_, Random p_196251_5_) {
        return requiresSilkTouch() || !canDrop() ? 0 : 1;
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState p_180643_1_) {
        return canDrop() ? new ItemStack(CaveChimesItems.CAVE_CHIMES, 1) : ItemStack.EMPTY;
    }

    @Override
    public IItemProvider getItemDropped(IBlockState p_199769_1_, World p_199769_2_, BlockPos p_199769_3_, int p_199769_4_) {
        return canDrop() && !requiresSilkTouch() ? CaveChimesItems.CAVE_CHIMES : null;
    }

    @Override
    public boolean canSilkHarvest(IBlockState p_canSilkHarvest_1_, IWorldReader p_canSilkHarvest_2_, BlockPos p_canSilkHarvest_3_, EntityPlayer p_canSilkHarvest_4_) {
        return canDrop();
    }

    private void updateVolume(World world, BlockPos pos, IBlockState state, short volume) {
        world.setBlockState(pos, state.with(VOLUME, (int)volume));

        TileEntity tE = world.getTileEntity(pos);
        if (tE instanceof TileEntityCaveChimes) {
            TileEntityCaveChimes chimes = (TileEntityCaveChimes)tE;
            //CaveChimesMod.getLogger().info("Updating chimes!");
            chimes.setVolume(volume);
            return;
        }
        CaveChimesMod.getLogger().error("Cave Chimes at " + pos + " are not using the correct TileEntity! TileEntityCaveChimes expected, " + tE + " present.");
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new TileEntityCaveChimes();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(VOLUME);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader iBlockReader) {
        return new TileEntityCaveChimes();
    }
}
