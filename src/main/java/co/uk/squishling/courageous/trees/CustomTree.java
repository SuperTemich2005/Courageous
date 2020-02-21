package co.uk.squishling.courageous.trees;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;

public class CustomTree extends Tree {

    private AbstractTreeFeature tree;

    public CustomTree(AbstractTreeFeature tree) {
        this.tree = tree;
    }

    @Nullable
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random rand) {
        return tree;
    }

}
