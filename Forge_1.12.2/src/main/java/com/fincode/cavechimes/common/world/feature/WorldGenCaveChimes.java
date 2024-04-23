package com.fincode.cavechimes.common.world.feature;

import com.fincode.cavechimes.Config;
import com.fincode.cavechimes.common.block.BlockCaveChimes;
import com.fincode.cavechimes.init.CaveChimesBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenCaveChimes implements IWorldGenerator {
    public static final int TRIES_GENERATION = 8;
    public static final int TRIES_HEIGHT = 20;

    public WorldGenCaveChimes() {
    }

    public boolean generateChimes(World world, BlockPos blockPos) {
        if (world.isAirBlock(blockPos) && canHang(world, blockPos)) {
            world.setBlockState(blockPos, CaveChimesBlocks.CAVE_CHIMES.getDefaultState(), 2 | 16); // "https://forums.minecraftforge.net/topic/64271-solvedsetblockstate-flags/" Absolute legend.
            //CaveChimesMod.getLogger().info("THE BITE OF 87???");

            return true;
        }

        return false;
    }

    private static boolean canHang(IBlockAccess world, BlockPos pos) {
        return BlockCaveChimes.canHang(world, pos);
    }

    private boolean xor(boolean l, boolean r) {
        return (l || r) && !(l && r);
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value)
                return true;
        }
        return false;
    }

    private boolean contains(String[] array, String value) {
        for (String i : array) {
            if (i.equals(value))
                return true;
        }
        return false;
    }



    @Override
    public void generate(Random random, int cX, int cZ, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        if (!Config.worldgen.generateChimes) return;
        if (!xor(contains(Config.worldgen.dimensions, world.provider.getDimension()), Config.worldgen.dimBlacklist)) return;

        int heightMin = MathHelper.clamp(Config.worldgen.minChimeHeight, 1, 255);
        int heightMax = MathHelper.clamp(Config.worldgen.maxChimeHeight, 1, 255);
        if (heightMin > heightMax) return;

        random = new Random(world.getSeed());
        long xSeed = random.nextLong() >> 3L;
        long zSeed = random.nextLong() >> 3L;

        random.setSeed(xSeed * cX + zSeed * cZ ^ world.getSeed());

        float atOdds = random.nextFloat();
        if (atOdds > Config.worldgen.chimeFrequency) return;

        BlockPos pos = new BlockPos(cX * 16 + random.nextInt(15), 0, cZ * 16+ random.nextInt(15));

        //CaveChimesMod.getLogger().info(pos);

        ResourceLocation biome = world.provider.getBiomeForCoords(pos).getRegistryName();
        if (biome == null || !xor(contains(Config.worldgen.biomes, biome.getResourcePath()), Config.worldgen.biomeBlacklist)) return;

        //CaveChimesMod.getLogger().info("Was it me?");

        for (int t = 0; t < TRIES_GENERATION; ++t) {
            int tryHeight = random.nextInt(heightMax - heightMin) + heightMin;

            if (world.getHeight(pos.getX(), pos.getY()) <= tryHeight) continue;

            BlockPos tryPos = pos.up(tryHeight);
            if (world.isAirBlock(tryPos)) {
                pos = tryPos;
                break;
            }
        }
        //CaveChimesMod.getLogger().info("Or me?");

        for (int t = 0; t < TRIES_HEIGHT; ++t) {
            if (generateChimes(world, pos)) break;
            pos = pos.up();
        }
    }
}
