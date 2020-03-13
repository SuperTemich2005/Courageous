//package co.uk.squishling.courageous.world.gen.features.trees;
//
//import com.mojang.datafixers.Dynamic;
//import co.uk.squishling.courageous.blocks.ModBlocks;
//import co.uk.squishling.courageous.util.Reference;
//import co.uk.squishling.courageous.world.gen.ModFeatures;
//import net.minecraft.block.BlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.util.math.MutableBoundingBox;
//import net.minecraft.world.gen.IWorldGenerationReader;
//import net.minecraft.world.gen.feature.HugeTreesFeature;
//import net.minecraft.world.gen.feature.NoFeatureConfig;
//import net.minecraft.world.gen.feature.TreeFeatureConfig;
//
//import java.util.Random;
//import java.util.Set;
//import java.util.function.Function;
//
//public class GiantRedwoodTreeFeature extends HugeTreesFeature<TreeFeatureConfig> {
//    private static final BlockState TRUNK = ModBlocks.REDWOOD_LOG.getDefaultState();
//    private static final BlockState LEAF = ModBlocks.REDWOOD_LEAVES.getDefaultState();
//
//    private final boolean useBaseHeight;
//
//    public GiantRedwoodTreeFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
//        super(configFactoryIn, false, 16, 20, TRUNK, LEAF);
//        this.useBaseHeight = true;
//        setSapling((net.minecraftforge.common.IPlantable)ModBlocks.REDWOOD_SAPLING);
//
//        this.setRegistryName(Reference.MOD_ID, "giant_redwood_tree");
//        ModFeatures.FEATURES.add(this);
//    }
//
//    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox boundsIn) {
//        int i = this.getHeight(rand);
//        if (!this.func_203427_a(worldIn, position, i)) {
//            return false;
//        } else {
//            this.func_214596_a(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand, boundsIn, changedBlocks);
//
//            for(int j = 0; j < i; ++j) {
//                if (isAirOrLeaves(worldIn, position.up(j))) {
//                    this.setLogState(changedBlocks, worldIn, position.up(j), this.trunk, boundsIn);
//                }
//
//                if (j < i - 1) {
//                    if (isAirOrLeaves(worldIn, position.add(1, j, 0))) {
//                        this.setLogState(changedBlocks, worldIn, position.add(1, j, 0), this.trunk, boundsIn);
//                    }
//
//                    if (isAirOrLeaves(worldIn, position.add(1, j, 1))) {
//                        this.setLogState(changedBlocks, worldIn, position.add(1, j, 1), this.trunk, boundsIn);
//                    }
//
//                    if (isAirOrLeaves(worldIn, position.add(0, j, 1))) {
//                        this.setLogState(changedBlocks, worldIn, position.add(0, j, 1), this.trunk, boundsIn);
//                    }
//                }
//            }
//
//            return true;
//        }
//    }
//
//    private void func_214596_a(IWorldGenerationReader p_214596_1_, int p_214596_2_, int p_214596_3_, int p_214596_4_, int p_214596_5_, Random p_214596_6_, MutableBoundingBox p_214596_7_, Set<BlockPos> p_214596_8_) {
//        int i = p_214596_6_.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
//        int j = 0;
//
//        for(int k = p_214596_4_ - i; k <= p_214596_4_; ++k) {
//            int l = p_214596_4_ - k;
//            int i1 = p_214596_5_ + MathHelper.floor((float)l / (float)i * 3.5F);
//            this.func_222839_a(p_214596_1_, new BlockPos(p_214596_2_, k, p_214596_3_), i1 + (l > 0 && i1 == j && (k & 1) == 0 ? 1 : 0), p_214596_7_, p_214596_8_);
//            j = i1;
//        }
//
//    }
//
//}
