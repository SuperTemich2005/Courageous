package co.uk.squishling.courageous.world.gen.features.trees;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.world.gen.ModFeatures;
import com.mojang.datafixers.Dynamic;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class PalmTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {

    private static final BlockState TRUNK = ModBlocks.PALM_LOG.getDefaultState();
    private static final BlockState LEAF = ModBlocks.PALM_LEAVES.getDefaultState();

    private static final ArrayList<BlockPos> LEAVES = new ArrayList<BlockPos>();

    public PalmTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51443_1_) {
        super(p_i51443_1_, false);

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

        this.setRegistryName(Reference.MOD_ID, "palm_tree");
        ModFeatures.FEATURES.add(this);
    }

    @Override
    protected boolean place(Set changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
        int min = 5;
        int max = 9;

        int trunkHeight = rand.nextInt(max - min) + min;
        if (!canCreate(worldIn, position, trunkHeight)) return false;
        buildTree(changedBlocks, worldIn, position, boundsIn, trunkHeight);

        return true;
    }

    private void buildTree(Set<BlockPos> blocks, IWorldGenerationReader world, BlockPos pos, MutableBoundingBox bounds, int height) {
        for (int i = 0; i < height; i++) placeLog(world, pos.up(i), bounds, blocks);
        placeLeaves(world, pos.up(height), bounds, blocks);

        // Rest of leaves
        for (int i = 0; i < 4; i++) {
            int x = 0;
            int y = 0;

            for (int j = 0; j < 3; j++) {
                x++;

                switch (i) {
                    case 0:
                        placeLeaves(world, pos.add(x, height + y, 0), bounds, blocks);
                        if (x < 3) placeLeaves(world, pos.add(x + 1, height + y, 0), bounds, blocks);
                    case 1:
                        placeLeaves(world, pos.add(-x, height + y, 0), bounds, blocks);
                        if (x < 3) placeLeaves(world, pos.add(-x - 1, height + y, 0), bounds, blocks);
                    case 2:
                        placeLeaves(world, pos.add(0, height + y, x), bounds, blocks);
                        if (x < 3) placeLeaves(world, pos.add(0, height + y, x + 1), bounds, blocks);
                    case 3:
                        placeLeaves(world, pos.add(0, height + y, -x), bounds, blocks);
                        if (x < 3) placeLeaves(world, pos.add(0, height + y, -x - 1), bounds, blocks);
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

    private void placeLog(IWorldWriter world, BlockPos pos, MutableBoundingBox bounds, Set<BlockPos> changedBlocks) {
        this.setLogState(changedBlocks, world, pos, TRUNK, bounds);
    }

    private void placeLeaves(IWorldGenerationReader world, BlockPos pos, MutableBoundingBox bounds, Set<BlockPos> changedBlocks) {
        if (isAirOrLeaves(world, pos)) this.setLogState(changedBlocks, world, pos, LEAF, bounds);
    }

}
