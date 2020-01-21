package du.squishling.courageous.world.gen.biomes;

import du.squishling.courageous.world.gen.biomes.overworld.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
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

    public static Biome LUSH_DESERT;

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        FRUIT_FOREST = registerBiome(new BiomeFruitForest(), BiomeType.COOL, "fruitful_forest", 1000, Type.FOREST, Type.COLD, Type.DENSE);
        AUTUMNAL_FOREST = registerBiome(new BiomeAutumnalForest(), BiomeType.COOL, "autumnal_forest", 12, Type.FOREST, Type.COLD, Type.DENSE);

        ALPINE_FOREST = registerBiome(new BiomeAlpineForest(), BiomeType.COOL, "alpine_forest", 12, Type.FOREST, Type.COLD, Type.CONIFEROUS, Type.HILLS);
        SPARSE_ALPINE_FOREST = registerBiome(new BiomeSparseAlpineForest(), BiomeType.COOL, "sparse_alpine_forest", 12, Type.FOREST, Type.COLD, Type.CONIFEROUS, Type.HILLS);

        LUSH_DESERT = registerBiome(new BiomeLushDesert(), BiomeType.DESERT, "lush_desert", 10, Type.LUSH, Type.DRY, Type.HILLS, Type.HOT);
    }

    public static Biome registerBiome(Biome biome, BiomeManager.BiomeType biomeType, String name, int weight, BiomeDictionary.Type... types) {
        biome.setRegistryName(name);
        ForgeRegistries.BIOMES.register(biome);
        BiomeManager.addSpawnBiome(biome);
        BiomeManager.addBiome(biomeType, new BiomeEntry(biome, weight));
        BiomeDictionary.addTypes(biome, types);
        System.out.println(name + " registered");

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
