package co.uk.squishling.courageous.world.gen.features;

import co.uk.squishling.courageous.util.Util;
import co.uk.squishling.courageous.world.gen.ModFeatures;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.*;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.gen.placement.Placement;

public class ArabianVillagePools {

    static {
        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(Util.MOD_ID+":village/arabian/town_centers"), new ResourceLocation("empty"),
                ImmutableList.of(new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/town_centers/meeting_point_1"), 50),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/town_centers/meeting_point_2"), 50)),
                JigsawPattern.PlacementBehaviour.RIGID));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(Util.MOD_ID+":village/arabian/streets"), new ResourceLocation(Util.MOD_ID+":village/arabian/terminators"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/corner_01"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/corner_02"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/straight_01"), 4),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/straight_02"), 4),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/straight_03"), 7),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/crossroad_01"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/crossroad_02"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/crossroad_03"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/turn_01"), 3),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/square_01"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/streets/square_02"), 1)),
                JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(Util.MOD_ID+":village/arabian/houses"), new ResourceLocation(Util.MOD_ID+":village/arabian/terminators"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_2"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_3"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_4"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_5"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_6"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_7"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_small_house_8"), 3),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_medium_house_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_medium_house_2"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_butcher_shop_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_tool_smith_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_fletcher_house_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_shepherds_house_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_armorer_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_fisher_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_tannery_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_cartographer_house_1"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_library_1"), 5),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_mason_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_weaponsmith_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_temple_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_temple_2"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_animal_pen_1"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_animal_pen_2"), 2),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_large_farm_1"), 4),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_farm_1"), 4),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/houses/arabian_farm_2"), 4),
                        Pair.of(EmptyJigsawPiece.INSTANCE, 10)),
                JigsawPattern.PlacementBehaviour.RIGID));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(Util.MOD_ID+":village/arabian/terminators"), new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/terminators/terminator_01"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/terminators/terminator_02"), 1)),
                JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(Util.MOD_ID+":village/arabian/trees"), new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new FeatureJigsawPiece(ModFeatures.PALM_TREE.withConfiguration(new NoFeatureConfig()), PlacementBehaviour.RIGID), 1)),
                JigsawPattern.PlacementBehaviour.RIGID));

        JigsawManager.REGISTRY.register(new JigsawPattern(new ResourceLocation(Util.MOD_ID+":village/arabian/villagers"), new ResourceLocation("empty"),
                ImmutableList.of(
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/villagers/nitwit"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/villagers/baby"), 1),
                        new Pair<>(new SingleJigsawPiece(Util.MOD_ID+":village/arabian/villagers/unemployed"), 10)),
                JigsawPattern.PlacementBehaviour.RIGID));
    }

}
