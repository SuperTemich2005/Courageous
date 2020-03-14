package co.uk.squishling.courageous.world.gen;

import co.uk.squishling.courageous.util.config.ConfigHandler;
import co.uk.squishling.courageous.world.gen.biomes.*;
import co.uk.squishling.courageous.world.gen.surface.SnowyMountainSurfaceBuilder;
import co.uk.squishling.courageous.Courageous;
import co.uk.squishling.courageous.util.Reference;
import co.uk.squishling.courageous.world.gen.surface.ChaparralSurfaceBuilder;
import co.uk.squishling.courageous.world.gen.surface.TundraSurfaceBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;

@EventBusSubscriber(bus= Bus.MOD)
public class BiomeRegistry {

    public static Biome FRUIT_FOREST;
    public static Biome AUTUMNAL_FOREST;

    public static Biome ALPINE_FOREST;
    public static Biome SPARSE_ALPINE_FOREST;
    public static Biome TEMPERATE_RAINFOREST;

    public static Biome REDWOOD_FOREST;
    public static Biome DOUGLAS_FIR_FOREST;

    public static Biome TUNDRA;

    public static Biome LUSH_DESERT;
    public static Biome STEPPE;
    public static Biome CHAPARRAL;

    public static Biome WETLANDS;

    public static Biome SNOWY_MOUNTAIN;

    public static SurfaceBuilder<SurfaceBuilderConfig> TUNDRA_SURFACE_BUILDER = new TundraSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static SurfaceBuilder<SurfaceBuilderConfig> MOUNTAIN_SURFACE_BUILDER = new SnowyMountainSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static SurfaceBuilder<SurfaceBuilderConfig> CHAPARRAL_SURFACE_BUILDER = new ChaparralSurfaceBuilder(SurfaceBuilderConfig::deserialize);

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        FRUIT_FOREST = registerBiome(new BiomeFruitForest(), BiomeType.COOL, "fruitful_forest", 6, Type.FOREST, Type.COLD, Type.DENSE);
        AUTUMNAL_FOREST = registerBiome(new BiomeAutumnalForest(), BiomeType.COOL, "autumnal_forest", 16, Type.FOREST, Type.COLD, Type.DENSE);

        ALPINE_FOREST = registerBiome(new BiomeAlpineForest(), BiomeType.COOL, "alpine_forest", 11, Type.FOREST, Type.COLD, Type.CONIFEROUS, Type.HILLS);
        SPARSE_ALPINE_FOREST = registerBiome(new BiomeSparseAlpineForest(), BiomeType.COOL, "sparse_alpine_forest", 8, Type.FOREST, Type.COLD, Type.CONIFEROUS, Type.HILLS);
        TEMPERATE_RAINFOREST = registerBiome(new BiomeTemperateRainforest(), BiomeType.COOL, "temperate_rainforest", 13, Type.FOREST, Type.CONIFEROUS, Type.HILLS, Type.DENSE);

        REDWOOD_FOREST = registerBiome(new BiomeRedwoodForest(), BiomeType.COOL, "redwood_forest", 11, Type.FOREST, Type.CONIFEROUS, Type.WET, Type.DENSE);
        DOUGLAS_FIR_FOREST = registerBiome(new BiomeDouglasFirForest(), BiomeType.COOL, "douglas_fir_forest", 12, Type.FOREST, Type.CONIFEROUS, Type.DENSE, Type.COLD);

        TUNDRA = registerBiome(new BiomeTundra(), BiomeType.ICY, "tundra", 1, Type.COLD, Type.SPARSE, Type.DRY, Type.PLAINS, Type.SNOWY);

        LUSH_DESERT = registerBiome(new BiomeLushDesert(), BiomeType.DESERT, "lush_desert", 10, Type.LUSH, Type.DRY, Type.HILLS, Type.HOT, Type.SANDY);
        STEPPE = registerBiome(new BiomeSteppe(), BiomeType.DESERT, "steppe", 12, Type.PLAINS, Type.DRY, Type.SPARSE, Type.HOT, Type.SAVANNA, Type.SANDY);
        CHAPARRAL = registerBiome(new BiomeChaparral(), BiomeType.DESERT, "chaparral", 13, Type.HILLS, Type.HOT, Type.SPARSE, Type.SANDY, Type.PLAINS);

        WETLANDS = registerBiome(new BiomeWetlands(), BiomeType.COOL, "wetlands", 15, Type.SPARSE, Type.SWAMP, Type.WET, Type.WATER);

        SNOWY_MOUNTAIN = registerBiome(new BiomeSnowyMountain(), BiomeType.ICY, "snowy_mountain", 22, Type.SPARSE, Type.WET, Type.HILLS, Type.SNOWY, Type.COLD);
    }

    public static Biome registerBiome(Biome biome, BiomeManager.BiomeType biomeType, String name, int weight, BiomeDictionary.Type... types) {
        biome.setRegistryName(Reference.MOD_ID, name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeDictionary.addTypes(biome, types);
        Courageous.LOGGER.info(name + " registered");

        if (!ConfigHandler.COMMON.spawnBiomes.get() ||
                (name == "fruitful_forest"      && !ConfigHandler.COMMON.spawnFruitfulForest    .get()) ||
                (name == "autumnal_forest"      && !ConfigHandler.COMMON.spawnAutumnalForest    .get()) ||
                (name == "alpine_forest"        && !ConfigHandler.COMMON.spawnAlpineForest      .get()) ||
                (name == "sparse_alpine_forest" && !ConfigHandler.COMMON.spawnSparseAlpineForest.get()) ||
                (name == "tundra"               && !ConfigHandler.COMMON.spawnTundra            .get()) ||
                (name == "lush_desert"          && !ConfigHandler.COMMON.spawnLushDesert        .get())
        ) return biome;

        BiomeManager.addSpawnBiome(biome);
        BiomeManager.addBiome(biomeType, new BiomeEntry(biome, weight));

        return biome;
    }

    public static HashMap<SpawnListEntry, EntityClassification> getSpawns(Biome target) {
        HashMap<SpawnListEntry, EntityClassification> entries = new HashMap<SpawnListEntry, EntityClassification>();

        for (SpawnListEntry entry : ((Biome) target).getSpawns(EntityClassification.AMBIENT)) entries.put(entry, EntityClassification.AMBIENT);
        for (SpawnListEntry entry : ((Biome) target).getSpawns(EntityClassification.WATER_CREATURE)) entries.put(entry, EntityClassification.WATER_CREATURE);
        for (SpawnListEntry entry : ((Biome) target).getSpawns(EntityClassification.CREATURE)) entries.put(entry, EntityClassification.CREATURE);
        for (SpawnListEntry entry : ((Biome) target).getSpawns(EntityClassification.MISC)) entries.put(entry, EntityClassification.MISC);
        for (SpawnListEntry entry : ((Biome) target).getSpawns(EntityClassification.MONSTER)) entries.put(entry, EntityClassification.MONSTER);

        return entries;
    }

}
