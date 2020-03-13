package co.uk.squishling.courageous.trees;

import co.uk.squishling.courageous.world.gen.ModFeatures;
import net.minecraft.block.trees.BigTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.*;

import javax.annotation.Nullable;
import java.util.Random;

public class RedwoodTree extends Tree {

//    @Nullable
//    @Override
//    protected ConfiguredFeature<HugeTreeFeatureConfig, ?> getHugeTreeFeature(Random random) {
//        return ModFeatures.GIANT_REDWOOD_TREE.withConfiguration(DefaultBiomeFeatures.MEGA_SPRUCE_TREE_CONFIG);
//    }

    @Nullable
    @Override
    protected ConfiguredFeature<TreeFeatureConfig, ?> getTreeFeature(Random random, boolean b) {
        return ModFeatures.REDWOOD_TREE.withConfiguration(DefaultBiomeFeatures.OAK_TREE_CONFIG);
    }
}
