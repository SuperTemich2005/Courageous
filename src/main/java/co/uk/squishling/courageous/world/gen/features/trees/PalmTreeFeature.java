package co.uk.squishling.courageous.world.gen.features.trees;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.world.gen.ModFeatures;
import com.mojang.datafixers.Dynamic;
import co.uk.squishling.courageous.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class PalmTreeFeature extends AbstractTreeFeature<BaseTreeFeatureConfig> {

    private static final BlockState TRUNK = ModBlocks.PALM_LOG.getDefaultState();
    private static final BlockState LEAF = ModBlocks.PALM_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, 1);

    private static final ArrayList<BlockPos> LEAVES = new ArrayList<BlockPos>();

    public PalmTreeFeature(Function<Dynamic<?>, ? extends BaseTreeFeatureConfig> p_i51443_1_) {
        super(p_i51443_1_);

        LEAVES.add(new BlockPos(-4, 0, 0));
        LEAVES.add(new BlockPos(4, 0, 0));
        LEAVES.add(new BlockPos(0, 0, 4));
        LEAVES.add(new BlockPos(0, 0, -4));

        LEAVES.add(new BlockPos(3, 0, 3));
        LEAVES.add(new BlockPos(-3, 0, -3));
        LEAVES.add(new BlockPos(-3, 0, 3));
        LEAVES.add(new BlockPos(3, 0, -3));


        LEAVES.add(new BlockPos(0, 1, 0));
        LEAVES.add(new BlockPos(1, 1, 0));
        LEAVES.add(new BlockPos(-1, 1, 0));
        LEAVES.add(new BlockPos(0, 1, 1));
        LEAVES.add(new BlockPos(0, 1, -1));

        LEAVES.add(new BlockPos(-1, 1, -1));
        LEAVES.add(new BlockPos(-1, 1, 1));
        LEAVES.add(new BlockPos(1, 1, -1));
        LEAVES.add(new BlockPos(1, 1, 1));

        LEAVES.add(new BlockPos(2, 1, 2));
        LEAVES.add(new BlockPos(2, 1, -2));
        LEAVES.add(new BlockPos(-2, 1, -2));
        LEAVES.add(new BlockPos(-2, 1, 2));

        LEAVES.add(new BlockPos(-2, 1, 0));
        LEAVES.add(new BlockPos(-3, 1, 0));
        LEAVES.add(new BlockPos(2, 1, 0));
        LEAVES.add(new BlockPos(3, 1, 0));
        LEAVES.add(new BlockPos(0, 1, -2));
        LEAVES.add(new BlockPos(0, 1, 2));
        LEAVES.add(new BlockPos(0, 1, 3));
        LEAVES.add(new BlockPos(0, 1, -3));

        this.setRegistryName(Util.MOD_ID, "palm_tree");
        ModFeatures.FEATURES.add(this);
    }

    @Override
    protected boolean func_225557_a_(IWorldGenerationReader worldIn, Random rand, BlockPos position, Set<BlockPos> set, Set<BlockPos> set1, MutableBoundingBox boundsIn, BaseTreeFeatureConfig treeFeatureConfig) {
        int min = 5;
        int max = 9;

        int trunkHeight = rand.nextInt(max - min) + min;
        if (!canCreate(worldIn, position, trunkHeight)) return false;
        buildTree(worldIn, position, trunkHeight);

        return true;
    }

    private void buildTree(IWorldGenerationReader world, BlockPos pos, int height) {
        for (int i = 0; i < height; i++) placeLog(world, pos.up(i));
        placeLeaves(world, pos.up(height));

        // Rest of leaves
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int y = 0;

            for (int j = 0; j < 3; j++) {
                x++;

                switch (i) {
                    case 0:
                        placeLeaves(world, pos.add(x, height + y, 0));
                        if (x < 3) placeLeaves(world, pos.add(x + 1, height + y, 0));
                    case 1:
                        placeLeaves(world, pos.add(-x, height + y, 0));
                        if (x < 3) placeLeaves(world, pos.add(-x - 1, height + y, 0));
                    case 2:
                        placeLeaves(world, pos.add(0, height + y, x));
                        if (x < 3) placeLeaves(world, pos.add(0, height + y, x + 1));
                    case 3:
                        placeLeaves(world, pos.add(0, height + y, -x));
                        if (x < 3) placeLeaves(world, pos.add(0, height + y, -x - 1));
                }

                y--;
            }
        }

    }

    private boolean canCreate(IWorldGenerationReader world, BlockPos position, int height) {
        if (!(world.hasBlockState(position.down(), (p_214586_0_) -> {
            Block block = p_214586_0_.getBlock();
            return block == Blocks.DIRT || block == Blocks.GRASS_BLOCK || block == Blocks.SAND;
        }))) {
            System.out.println("Aborted due to wrong spawn block");
            return false;
        }

        for (int i = 0; i < height + 1; i++) if (!isAirOrLeaves(world, position.up(i)) && !isTallPlants(world, position.up(i))) {
            System.out.println("Aborted due to block blocking path");
            return false;
        }

        return true;
    }

    private void placeLog(IWorldWriter world, BlockPos pos) {
        this.setBlockState(world, pos, TRUNK);
    }

    private void placeLeaves(IWorldGenerationReader world, BlockPos pos) {
        if (isAirOrLeaves(world, pos)) this.setBlockState(world, pos, LEAF);
    }

}
