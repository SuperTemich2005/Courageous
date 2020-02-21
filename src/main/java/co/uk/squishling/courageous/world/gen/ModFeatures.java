package co.uk.squishling.courageous.world.gen;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.world.gen.features.MudLake;
import co.uk.squishling.courageous.world.gen.features.trees.*;
import co.uk.squishling.courageous.world.gen.features.trees.fruit.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.*;

import java.util.ArrayList;

public class ModFeatures {

    public static final ArrayList<Feature> FEATURES = new ArrayList<Feature>();

    public static final AbstractTreeFeature ALPINE_TREE = new AlpineTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature REDWOOD_TREE = new RedwoodTreeFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature DOUGLAS_FIR_TREE = new DouglasFirTreeFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature GIANT_REDWOOD_TREE = new GiantRedwoodTreeFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature PALM_TREE = new PalmTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature MAPLE_TREE = new MapleTreeFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature PEAR_TREE = new PearTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature ORANGE_TREE = new OrangeTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature LEMON_TREE = new LemonTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature PLUM_TREE = new PlumTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature AVOCADO_TREE = new AvocadoTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature KIWI_FRUIT_TREE = new KiwiFruitTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature APPLE_TREE = new AppleTreeFeature(NoFeatureConfig::deserialize);

    public static final Feature MUD_LAKE = new MudLake(LakesConfig::deserialize);

    public static void addAlpineTrees(Biome biome) {
        addTree(biome, ALPINE_TREE, 5, 0.1f, 1);
    }

    public static void addSparseAlpineTrees(Biome biome) {
        addTree(biome, ALPINE_TREE, 2, 0.1f, 1);
    }

    public static void addSparseTrees(Biome biome) {
        addDefaultTrees(biome, 1, 0.1f, 1);
    }

    public static void addFruitForestTrees(Biome biome) {
        addDefaultTrees(biome, 4, 0.1f, 2);
        addTree(biome, PEAR_TREE, 1, 0.1f, 1);
        addTree(biome, ORANGE_TREE, 1, 0.1f, 1);
        addTree(biome, LEMON_TREE, 1, 0.1f, 1);
        addTree(biome, PLUM_TREE, 1, 0.1f, 1);
        addTree(biome, AVOCADO_TREE, 1, 0.1f, 1);
        addTree(biome, APPLE_TREE, 1, 0.1f, 1);
        addTree(biome, KIWI_FRUIT_TREE, 1, 0.1f, 1);
    }

    public static void addAutumnalTrees(Biome biome) {
        addDefaultTrees(biome, 4, 0.1f, 1);
        addTree(biome, MAPLE_TREE, 4, 0.1f, 1);
    }

    public static void addPalmTrees(Biome biome) {
        addTree(biome, PALM_TREE, 0, 0.1f, 1);
    }

    public static void addVegetation(Biome biome) {
        DefaultBiomeFeatures.addExtraDefaultFlowers(biome);
        DefaultBiomeFeatures.addDenseGrass(biome);
        DefaultBiomeFeatures.addTaigaLargeFerns(biome);
    }

    public static void addDesertVegetation(Biome biome) {
        addPlant(biome, ModBlocks.DESERT_SHRUB.getDefaultState(), 1);
    }

    public static void addFallenLeaves(Biome biome) {
        addPlant(biome, ModBlocks.FALLEN_LEAVES.getDefaultState(), 1);
    }

    public static void addFallenSnow(Biome biome) {
        addPlant(biome, Blocks.SNOW.getDefaultState(), 3);
    }

    public static void addUndergroundFeatures(Biome biome) {
        DefaultBiomeFeatures.addCarvers(biome);
        DefaultBiomeFeatures.addMonsterRooms(biome);
        DefaultBiomeFeatures.addStoneVariants(biome);
        DefaultBiomeFeatures.addOres(biome);
    }

    public static void addDesertStructures(Biome biome) {
        addVilage(biome, "desert");
        biome.addStructure(Feature.DESERT_PYRAMID, IFeatureConfig.NO_FEATURE_CONFIG);
    }

    public static void addSavannaStructures(Biome biome) {
        addVilage(biome, "savanna");
        addPillagerOutpost(biome);
    }

    public static void addSparseSavannaTrees(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{Feature.SAVANNA_TREE}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.8F}, Feature.NORMAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.02F, 1)));
    }

    public static void addPillagerOutpost(Biome biome) {
        biome.addStructure(Feature.PILLAGER_OUTPOST, new PillagerOutpostConfig(0.004D));
    }

    public static void addVilage(Biome biome, String type) {
        biome.addStructure(Feature.VILLAGE, new VillageConfig("village/" + type + "/town_centers", 6));
    }

    public static void addTree(Biome biome, AbstractTreeFeature tree, int perChunk, float extraChance, int extra) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(tree, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(perChunk, extraChance, extra)));
    }

    public static void addDefaultTrees(Biome biome, int perChunk, float extraChance, int extra) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{Feature.BIRCH_TREE, Feature.FANCY_TREE}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.2F, 0.1F}, Feature.NORMAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(perChunk, extraChance, extra)));
    }

    public static void addPlant(Biome biome, BlockState state, int frequency) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.GRASS, new GrassFeatureConfig(state), Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(frequency)));
    }

    public static void addMudLake(Biome biome, int chance) {
        biome.addFeature(Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(MUD_LAKE, new LakesConfig(Blocks.WATER.getDefaultState()), Placement.WATER_LAKE, new LakeChanceConfig(chance)));
    }

    public static void addSwampDecoration(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SEAGRASS, new SeaGrassConfig(64, 0.6D), Placement.TOP_SOLID_HEIGHTMAP, IPlacementConfig.NO_PLACEMENT_CONFIG));
        DefaultBiomeFeatures.addSwampVegetation(biome);
        DefaultBiomeFeatures.addSwampClayDisks(biome);
    }

}
