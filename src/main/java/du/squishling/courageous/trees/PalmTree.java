package du.squishling.courageous.trees;

import du.squishling.courageous.world.gen.ModFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class PalmTree extends Tree {

    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return ModFeatures.PALM_TREE;
    }

}
