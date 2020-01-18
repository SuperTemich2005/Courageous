package du.squishling.courageous.trees;

import du.squishling.courageous.world.gen.features.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BigTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

import javax.annotation.Nullable;
import java.util.ArrayList;
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
