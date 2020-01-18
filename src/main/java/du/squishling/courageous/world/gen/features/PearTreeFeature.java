package du.squishling.courageous.world.gen.features;

import com.mojang.datafixers.Dynamic;
import du.squishling.courageous.blocks.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class PearTreeFeature extends AbstractTreeFeature<NoFeatureConfig> {

    private static final BlockState TRUNK = Blocks.OAK_LOG.getDefaultState();
    private static final BlockState LEAF = ModBlocks.ALPINE_LEAVES.getDefaultState();

    public PearTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config, false);
    }

    @Override
    protected boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
        return false;
    }
}
