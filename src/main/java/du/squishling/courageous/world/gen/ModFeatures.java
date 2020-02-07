package du.squishling.courageous.world.gen;

import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.world.gen.features.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

public class ModFeatures {

    public static final ArrayList<Feature> FEATURES = new ArrayList<Feature>();

    public static final AbstractTreeFeature ALPINE_TREE = new AlpineTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature PALM_TREE = new PalmTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature MAPLE_TREE = new MapleTreeFeature(NoFeatureConfig::deserialize);

    public static final AbstractTreeFeature PEAR_TREE = new PearTreeFeature(NoFeatureConfig::deserialize);
    public static final AbstractTreeFeature ORANGE_TREE = new OrangeTreeFeature(NoFeatureConfig::deserialize);

    public static void registerFeatures() {
        for (Feature feature : FEATURES) ForgeRegistries.FEATURES.register(feature);
    }

    public static void addAlpineTrees(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(ALPINE_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(5, 0.1F, 1)));
    }

    public static void addSparseAlpineTrees(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(ALPINE_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(2, 0.1F, 1)));
    }

    public static void addSparseTrees(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{Feature.BIRCH_TREE, Feature.FANCY_TREE}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.2F, 0.1F}, Feature.NORMAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(1, 0.1F, 1)));
    }

    public static void addFruitForestTrees(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.RANDOM_SELECTOR, new MultipleRandomFeatureConfig(new Feature[]{Feature.BIRCH_TREE, Feature.FANCY_TREE}, new IFeatureConfig[]{IFeatureConfig.NO_FEATURE_CONFIG, IFeatureConfig.NO_FEATURE_CONFIG}, new float[]{0.2F, 0.1F}, Feature.NORMAL_TREE, IFeatureConfig.NO_FEATURE_CONFIG), Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(5, 0.1F, 1)));
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(PEAR_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(3, 0.1F, 1)));
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(ORANGE_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(3, 0.1F, 1)));
    }

    public static void addPalmTrees(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(PALM_TREE, IFeatureConfig.NO_FEATURE_CONFIG, Placement.COUNT_EXTRA_HEIGHTMAP, new AtSurfaceWithExtraConfig(0, 0.1F, 1)));
    }

    public static void addVegetation(Biome biome) {
        DefaultBiomeFeatures.addExtraDefaultFlowers(biome);
        DefaultBiomeFeatures.addDenseGrass(biome);
        DefaultBiomeFeatures.addTaigaLargeFerns(biome);
    }

    public static void addDesertVegetation(Biome biome) {
        biome.addFeature(Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.GRASS, new GrassFeatureConfig(ModBlocks.DESERT_SHRUB.getDefaultState()), Placement.COUNT_HEIGHTMAP_DOUBLE, new FrequencyConfig(1)));
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

    public static void addPillagerOutpost(Biome biome) {
        biome.addStructure(Feature.PILLAGER_OUTPOST, new PillagerOutpostConfig(0.004D));
    }

    public static void addVilage(Biome biome, String type) {
        biome.addStructure(Feature.VILLAGE, new VillageConfig("village/" + type + "/town_centers", 6));
    }

}
