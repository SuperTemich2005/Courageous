package co.uk.squishling.courageous.world.gen.biomes;

import co.uk.squishling.courageous.world.gen.BiomeRegistry;
import co.uk.squishling.courageous.world.gen.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import javax.annotation.Nullable;

public class BiomeOasis extends Biome {

    public BiomeOasis() {
        super(new Builder().surfaceBuilder(BiomeRegistry.OASIS_SURFACE_BUILDER, SurfaceBuilder.SAND_CONFIG)
                .category(Category.DESERT)
                .precipitation(RainType.RAIN)
                .downfall(0.4f)

                .temperature(2f)

                .depth(-0.1f)
                .scale(0.1f)

                .waterColor(0x5fcfdf)
                .waterFogColor(0x58bdcc)

                .parent((String) null));

        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addLakes(this);

        DefaultBiomeFeatures.addSedimentDisks(this);
        DefaultBiomeFeatures.addReedsAndPumpkins(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addStructures(this);

        ModFeatures.addUndergroundFeatures(this);
        ModFeatures.addDesertStructures(this);
        ModFeatures.addPillagerOutpost(this);

        ModFeatures.addPalmTrees(this);
        ModFeatures.addDesertVegetation(this);

        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
        this.addSpawn(EntityClassification.AMBIENT, new SpawnListEntry(EntityType.BAT, 10, 8, 8));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.WITCH, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new SpawnListEntry(EntityType.HUSK, 80, 4, 4));
    }

    @Override
    public int getGrassColor(double x, double z) {
        return 0x53c11b;
    }
}
