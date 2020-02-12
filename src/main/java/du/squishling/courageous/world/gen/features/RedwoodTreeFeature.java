package du.squishling.courageous.world.gen.features;

import com.mojang.datafixers.Dynamic;
import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.world.gen.ModFeatures;
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

public class RedwoodTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {

    private static final BlockState TRUNK = ModBlocks.REDWOOD_LOG.getDefaultState();
    private static final BlockState LEAF = ModBlocks.REDWOOD_LEAVES.getDefaultState();

    private static final ArrayList<ArrayList<BlockPos>> BLOBS_LIST = new ArrayList<ArrayList<BlockPos>>();

    public RedwoodTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config, false);

        BLOBS_LIST.add(new ArrayList<BlockPos>());
        BLOBS_LIST.get(0).add(new BlockPos(0, 0, 0));

        BLOBS_LIST.get(0).add(new BlockPos(1, 0, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 0, 1));
        BLOBS_LIST.get(0).add(new BlockPos(-1, 0, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 0, -1));

        BLOBS_LIST.get(0).add(new BlockPos(1, 1, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 1, 1));
        BLOBS_LIST.get(0).add(new BlockPos(-1, 1, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 1, -1));
        BLOBS_LIST.get(0).add(new BlockPos(0, 1, 0));

        BLOBS_LIST.get(0).add(new BlockPos(1, 2, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 2, 1));
        BLOBS_LIST.get(0).add(new BlockPos(-1, 2, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 2, -1));
        BLOBS_LIST.get(0).add(new BlockPos(0, 2, 0));

        BLOBS_LIST.get(0).add(new BlockPos(0, 3, 0));
        BLOBS_LIST.get(0).add(new BlockPos(0, 4, 0));


        BLOBS_LIST.add(new ArrayList<BlockPos>());
        BLOBS_LIST.get(1).add(new BlockPos(0, -3, 0));

        BLOBS_LIST.get(1).add(new BlockPos(1, 0, 0));
        BLOBS_LIST.get(1).add(new BlockPos(1, 0, 1));
        BLOBS_LIST.get(1).add(new BlockPos(0, 0, 1));
        BLOBS_LIST.get(1).add(new BlockPos(-1, 0, 0));
        BLOBS_LIST.get(1).add(new BlockPos(-1, 0, 1));
        BLOBS_LIST.get(1).add(new BlockPos(1, 0, -1));
        BLOBS_LIST.get(1).add(new BlockPos(-1, 0, -1));
        BLOBS_LIST.get(1).add(new BlockPos(0, 0, -1));

        BLOBS_LIST.get(1).add(new BlockPos(1, 1, 0));
        BLOBS_LIST.get(1).add(new BlockPos(0, 1, 1));
        BLOBS_LIST.get(1).add(new BlockPos(-1, 1, 0));
        BLOBS_LIST.get(1).add(new BlockPos(0, 1, -1));


        BLOBS_LIST.add(new ArrayList<BlockPos>());
        BLOBS_LIST.get(2).add(new BlockPos(0, -7, 0));

        BLOBS_LIST.get(2).add(new BlockPos(1, 0, 0));
        BLOBS_LIST.get(2).add(new BlockPos(0, 0, 1));
        BLOBS_LIST.get(2).add(new BlockPos(0, 0, -1));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 0, 0));

        BLOBS_LIST.get(2).add(new BlockPos(1, 1, 0));
        BLOBS_LIST.get(2).add(new BlockPos(1, 1, 1));
        BLOBS_LIST.get(2).add(new BlockPos(0, 1, 1));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 1 ,0));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 1, 1));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 1, -1));
        BLOBS_LIST.get(2).add(new BlockPos(1, 1, -1));
        BLOBS_LIST.get(2).add(new BlockPos(0, 1, -1));

        BLOBS_LIST.get(2).add(new BlockPos(1, 2, 0));
        BLOBS_LIST.get(2).add(new BlockPos(1, 2, 1));
        BLOBS_LIST.get(2).add(new BlockPos(0, 2, 1));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 2 ,0));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 2, 1));
        BLOBS_LIST.get(2).add(new BlockPos(-1, 2, -1));
        BLOBS_LIST.get(2).add(new BlockPos(1, 2, -1));
        BLOBS_LIST.get(2).add(new BlockPos(0, 2, -1));
        BLOBS_LIST.get(2).add(new BlockPos(2, 2, 0));
        BLOBS_LIST.get(2).add(new BlockPos(0, 2, 2));
        BLOBS_LIST.get(2).add(new BlockPos(-2, 2, 0));
        BLOBS_LIST.get(2).add(new BlockPos(0, 2, -2));


        BLOBS_LIST.add(new ArrayList<BlockPos>());
        BLOBS_LIST.get(3).add(new BlockPos(0, -9, 0));

        BLOBS_LIST.get(3).add(new BlockPos(1, 0, 0));
        BLOBS_LIST.get(3).add(new BlockPos(0, 0, 1));
        BLOBS_LIST.get(3).add(new BlockPos(-1, 0, 0));
        BLOBS_LIST.get(3).add(new BlockPos(0, 0, -1));

        BLOBS_LIST.get(3).add(new BlockPos(1, 1, 0));
        BLOBS_LIST.get(3).add(new BlockPos(2, 1, 0));
        BLOBS_LIST.get(3).add(new BlockPos(-1, 1, 0));
        BLOBS_LIST.get(3).add(new BlockPos(-2, 1, 0));
        BLOBS_LIST.get(3).add(new BlockPos(0, 1, 1));
        BLOBS_LIST.get(3).add(new BlockPos(0, 1, 2));
        BLOBS_LIST.get(3).add(new BlockPos(0, 1, -1));
        BLOBS_LIST.get(3).add(new BlockPos(0, 1, -2));
        BLOBS_LIST.get(3).add(new BlockPos(1, 1, 1));
        BLOBS_LIST.get(3).add(new BlockPos(1, 1, -1));
        BLOBS_LIST.get(3).add(new BlockPos(-1, 1, 1));
        BLOBS_LIST.get(3).add(new BlockPos(-1, 1, -1));
        BLOBS_LIST.get(3).add(new BlockPos(1, 1, 2));
        BLOBS_LIST.get(3).add(new BlockPos(2, 1, 1));
        BLOBS_LIST.get(3).add(new BlockPos(-1, 1, -2));
        BLOBS_LIST.get(3).add(new BlockPos(-2, 1, -1));
        BLOBS_LIST.get(3).add(new BlockPos(1, 1, -2));
        BLOBS_LIST.get(3).add(new BlockPos(2, 1, -1));
        BLOBS_LIST.get(3).add(new BlockPos(-1, 1, 2));
        BLOBS_LIST.get(3).add(new BlockPos(-2, 1, 1));


        BLOBS_LIST.add(new ArrayList<BlockPos>());
        BLOBS_LIST.get(4).add(new BlockPos(0, -14, 0));

        BLOBS_LIST.get(4).add(new BlockPos(1, 0, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 0, 0));
        BLOBS_LIST.get(4).add(new BlockPos(0, 0, 1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 0, -1));

        BLOBS_LIST.get(4).add(new BlockPos(1, 1, 0));
        BLOBS_LIST.get(4).add(new BlockPos(1, 1, 1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 1, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 1, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 1, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 1, -1));
        BLOBS_LIST.get(4).add(new BlockPos(1, 1, -1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 1, -1));

        BLOBS_LIST.get(4).add(new BlockPos(1, 2, 0));
        BLOBS_LIST.get(4).add(new BlockPos(1, 2, 1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 2, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 2, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 2, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 2, -1));
        BLOBS_LIST.get(4).add(new BlockPos(1, 2, -1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 2, -1));
        BLOBS_LIST.get(4).add(new BlockPos(2, 2, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-2, 2, 0));
        BLOBS_LIST.get(4).add(new BlockPos(0, 2, 2));
        BLOBS_LIST.get(4).add(new BlockPos(0, 2, -2));

        BLOBS_LIST.get(4).add(new BlockPos(1, 3, 0));
        BLOBS_LIST.get(4).add(new BlockPos(2, 3, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 3, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-2, 3, 0));
        BLOBS_LIST.get(4).add(new BlockPos(0, 3, 1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 3, 2));
        BLOBS_LIST.get(4).add(new BlockPos(0, 3, -1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 3, -2));
        BLOBS_LIST.get(4).add(new BlockPos(1, 3, 1));
        BLOBS_LIST.get(4).add(new BlockPos(1, 3, -1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 3, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 3, -1));
        BLOBS_LIST.get(4).add(new BlockPos(1, 3, 2));
        BLOBS_LIST.get(4).add(new BlockPos(2, 3, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 3, -2));
        BLOBS_LIST.get(4).add(new BlockPos(-2, 3, -1));
        BLOBS_LIST.get(4).add(new BlockPos(1, 3, -2));
        BLOBS_LIST.get(4).add(new BlockPos(2, 3, -1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 3, 2));
        BLOBS_LIST.get(4).add(new BlockPos(-2, 3, 1));

        BLOBS_LIST.get(4).add(new BlockPos(1, 4, 0));
        BLOBS_LIST.get(4).add(new BlockPos(1, 4, 1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 4, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 4, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 4, 1));
        BLOBS_LIST.get(4).add(new BlockPos(-1, 4, -1));
        BLOBS_LIST.get(4).add(new BlockPos(1, 4, -1));
        BLOBS_LIST.get(4).add(new BlockPos(0, 4, -1));
        BLOBS_LIST.get(4).add(new BlockPos(2, 4, 0));
        BLOBS_LIST.get(4).add(new BlockPos(-2, 4, 0));
        BLOBS_LIST.get(4).add(new BlockPos(0, 4, 2));
        BLOBS_LIST.get(4).add(new BlockPos(0, 4, -2));

        this.setRegistryName("redwood_tree");
        ModFeatures.FEATURES.add(this);
    }

    @Override
    protected boolean place(Set changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
        int trunkHeight = rand.nextInt(13) + 12;
        if (!canCreate(worldIn, position, trunkHeight)) return false;

        buildTree(worldIn, position, trunkHeight, boundsIn, changedBlocks);

        return true;
    }

    private void buildTree(IWorldGenerationReader world, BlockPos pos, int height, MutableBoundingBox bounds, Set blocks) {
        // Place logs
        for (int i = 0; i < height; i++) {
            placeLog(world, pos.up(i), bounds, blocks);
        }

        ArrayList<ArrayList<BlockPos>> BLOBS = (ArrayList<ArrayList<BlockPos>>) BLOBS_LIST.clone();

        // Place leaves
        while(BLOBS.size() > 0) {
            ArrayList<BlockPos> BLOB = BLOBS.get(0);

            if (BLOB.size() == 0) break;
            if (BLOB.get(0).getY() < Math.floor(height * -0.666)) break;

            int y = BLOB.get(0).getY();

            for (int j = 1; j < BLOB.size(); j++) {
                BlockPos pos2 = BLOB.get(j);
                placeLeaves(world, new BlockPos(pos.getX() + pos2.getX(), pos.getY() + y + height + pos2.getY() - 2, pos.getZ() + pos2.getZ()), bounds, blocks);
            }
            BLOBS.remove(0);
        }
    }

    private boolean canCreate(IWorldGenerationReader world, BlockPos position, int height) {
        if (!isDirtOrGrassBlock(world, position.down())) return false;

        for (int i = 0; i < height; i++) {
            if (!isAirOrLeaves(world, position.up(i)) && !isTallPlants(world, position.up(i))) return false;
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
