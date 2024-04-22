package com.fincode.cavechimes.common.block;

import com.fincode.cavechimes.CaveChimesMod;
import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.block.entity.TileEntityCaveChimes;
import com.fincode.cavechimes.init.CaveChimesBlocks;
import com.fincode.cavechimes.init.CaveChimesItems;
import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockCaveChimes extends Block implements ITileEntityProvider {

    boolean initialized = false;
    public static PropertyInteger VOLUME = PropertyInteger.create("volume", 0, 15);

    protected static final AxisAlignedBB CHIMES_AABB = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 1.0, 0.75);

    public BlockCaveChimes() {
        super(Material.IRON, MapColor.GRAY);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.DECORATIONS);
        this.setHardness(1);
        this.setResistance(3);
        this.setLightOpacity(0);
        this.setSoundType(SoundType.METAL);
        this.hasTileEntity = true;
        //this.setHarvestLevel("Iron", 3)

        this.setDefaultState(blockState.getBaseState().withProperty(VOLUME, 0));
    }

    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState p_185496_1_, IBlockAccess p_185496_2_, BlockPos p_185496_3_) {
        return CHIMES_AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState p_180646_1_, IBlockAccess p_180646_2_, BlockPos p_180646_3_) {
        return NULL_AABB;
    }

    @Override
    public ItemStack getItem(World p_185473_1_, BlockPos p_185473_2_, IBlockState p_185473_3_) {
        return new ItemStack(CaveChimesItems.CAVE_CHIMES);
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState p_canPlaceTorchOnTop_1_, IBlockAccess p_canPlaceTorchOnTop_2_, BlockPos p_canPlaceTorchOnTop_3_) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        return super.canPlaceBlockAt(world, pos) && canHang(world, pos);
    }

    public static boolean canHang(IBlockAccess world, BlockPos pos) {
        boolean can = !world.isAirBlock(pos.up()) && world.getBlockState(pos.up()).isSideSolid(world, pos.up(), EnumFacing.DOWN);
        //CaveChimesMod.getLogger().info("Am I hanging? " + can);
        return can;
    }

    public static boolean canHang(IBlockAccess world, BlockPos pos, IBlockState state, Block neighblock, BlockPos neighpos) {
        //CaveChimesMod.getLogger().info("Am I hanging? " + (neighpos == pos.up()));
        return neighpos != pos.up();
    }

    public boolean isOpaqueCube(IBlockState p_149662_1_) {
        return false;
    }

    public boolean isFullCube(IBlockState p_149686_1_) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasCustomBreakingProgress(IBlockState p_190946_1_) {
        return true;
    }

    public EnumBlockRenderType getRenderType(IBlockState p_149645_1_) {
        return CaveChimesMod.isModelSimplified() ? EnumBlockRenderType.MODEL : EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float p_getStateForPlacement_4_, float p_getStateForPlacement_5_, float p_getStateForPlacement_6_, int p_getStateForPlacement_7_, EntityLivingBase p_getStateForPlacement_8_, EnumHand p_getStateForPlacement_9_) {
        return blockState.getBaseState().withProperty(VOLUME, world.isBlockIndirectlyGettingPowered(pos));
    }

    private short getPower(World world, BlockPos pos) {
        int max = 0;
        for (EnumFacing d : EnumFacing.values()) {
            int pow = world.getRedstonePower(pos, d);
            if (pow > max)
                max = pow;
        }
        int strongPow = world.getStrongPower(pos);
        if (strongPow > max)
            max = strongPow;
        return (short)max;
    }

    private short getPower(World world, BlockPos pos, IBlockState state) {
        int max = 0;
        for (EnumFacing d : EnumFacing.values()) {
            int pow = world.getRedstonePower(pos, d);
            if (pow > max)
                max = pow;
        }
        int strongPow = world.getStrongPower(pos);
        if (strongPow > max)
            max = strongPow;
        return (short)max;
    }

    public void update(World world, BlockPos pos, IBlockState state) {
        if (world.isRemote || (!world.isBlockPowered(pos) && state.getValue(VOLUME) == 0)) return;

        //CaveChimesMod.getLogger().warn("POWAAAHHHHH");
        short power = (short)world.isBlockIndirectlyGettingPowered(pos);

        //CaveChimesMod.getLogger().warn("I am a whiny bitch!!! " + power + " != " + state.getValue(VOLUME).shortValue());

        if (state.getValue(VOLUME).shortValue() != power) // I will be furious if this is the issue right here.
            updateVolume(world, pos, state, power);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos posOfNeighbor) {
        super.neighborChanged(state, world, pos, block, posOfNeighbor);
        //CaveChimesMod.getLogger().info("Updating chimes.");
        neighborChangeInternal(world, pos, state, block, posOfNeighbor);
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
        return Config.obtaining.canBlockDrop;
    }

    private boolean requiresSilkTouch() {
        return Config.obtaining.silkTouchRequired;
    }

    private void breakChimes(World world, BlockPos pos, boolean silkTouch) {
        //CaveChimesMod.getLogger().info("Allegedly breaking cave chimes.");
        if (Config.obtaining.canBlockDrop) {
            if (!Config.obtaining.silkTouchRequired || silkTouch) {
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
    public int quantityDropped(IBlockState p_quantityDropped_1_, int p_quantityDropped_2_, Random p_quantityDropped_3_) {
        return requiresSilkTouch() || !canDrop() ? 0 : 1;
    }

    @Override
    public boolean canSilkHarvest(World p_canSilkHarvest_1_, BlockPos p_canSilkHarvest_2_, IBlockState p_canSilkHarvest_3_, EntityPlayer p_canSilkHarvest_4_) {
        return canDrop();
    }

    private void breakChimes(World world, BlockPos pos) { breakChimes(world, pos, false); }

    private void updateVolume(World world, BlockPos pos, IBlockState state, short volume) {
        world.setBlockState(pos, state.withProperty(VOLUME, (int)volume));

        TileEntity tE = world.getTileEntity(pos);
        if (tE instanceof TileEntityCaveChimes) {
            TileEntityCaveChimes chimes = (TileEntityCaveChimes)tE;
            //CaveChimesMod.getLogger().info("Updating chimes!");
            chimes.setVolume(volume);
            return;
        }
        CaveChimesMod.getLogger().error("Cave Chimes at " + pos + " are not using the correct TileEntity! TileEntityCaveChimes expected, " + tE + " present.");
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        //CaveChimesMod.getLogger().info("Creating Tile Entity with createTileEntity!");
        //CaveChimesMod.getLogger().error("I AM TRYING TO WARN YOU!!!");
        return new TileEntityCaveChimes();
    }

    public IBlockState getStateFromMeta(int p_176203_1_) {
        return this.getDefaultState().withProperty(VOLUME, p_176203_1_);
    }

    public int getMetaFromState(IBlockState p_176201_1_) {
        return p_176201_1_.getValue(VOLUME);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{VOLUME});
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        //CaveChimesMod.getLogger().info("Creating Tile Entity with createNewTileEntity!");
        return new TileEntityCaveChimes();
    }
}
