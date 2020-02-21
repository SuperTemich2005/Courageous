package co.uk.squishling.courageous;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.util.EventHandler;
import co.uk.squishling.courageous.util.ModBlockColors;
import co.uk.squishling.courageous.util.ModItemColors;
import co.uk.squishling.courageous.util.Reference;
import co.uk.squishling.courageous.util.config.ConfigHandler;
import co.uk.squishling.courageous.world.gen.ModFeatures;
import co.uk.squishling.courageous.blocks.ModContainers;
import co.uk.squishling.courageous.blocks.ModTileEntities;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelScreen;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Reference.MOD_ID)
public class Courageous {

    public static Courageous instance;
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);

    public Courageous() {
        instance = this;

        ModLoadingContext.get().registerConfig(Type.COMMON, ConfigHandler.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(Type.CLIENT, ConfigHandler.CLIENT_SPEC);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientRegistry);

        ConfigHandler.loadConfig(ConfigHandler.COMMON_SPEC, FMLPaths.CONFIGDIR.get().resolve("courageous-common.toml"));
        ConfigHandler.loadConfig(ConfigHandler.CLIENT_SPEC, FMLPaths.CONFIGDIR.get().resolve("courageous-client.toml"));

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    // Preinit
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup");

        ModFeatures.addTree(Biomes.FOREST, ModFeatures.APPLE_TREE, 0, 0.2f, 1);

        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.MAPLE_LOG, (RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.MAPLE_LOG_SYRUP, (RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.MAPLE_WOOD, (RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_WOOD);

        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.PALM_LOG, (RotatedPillarBlock) ModBlocks.STRIPPED_PALM_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.PALM_WOOD, (RotatedPillarBlock) ModBlocks.STRIPPED_PALM_WOOD);

        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.REDWOOD_LOG, (RotatedPillarBlock) ModBlocks.STRIPPED_REDWOOD_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.REDWOOD_WOOD, (RotatedPillarBlock) ModBlocks.STRIPPED_REDWOOD_WOOD);
    }

    private void clientRegistry(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup");

        ModBlockColors.registerBlockColors();
        ModItemColors.registerItemColors();

        ScreenManager.registerFactory(ModContainers.POTTERY_WHEEL_CONTAINER, PotteryWheelScreen::new);
    }

    @EventBusSubscriber(bus=Bus.MOD)
    public static class Registry {

        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            LOGGER.info("Items registry");

            for (Item item : ModItems.ITEMS) {
                event.getRegistry().register(item);
            }
        }

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            LOGGER.info("Blocks registry");

            event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[ModBlocks.BLOCKS.size()]));
        }

        @SubscribeEvent
        public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
            LOGGER.info("Tile entities registry");

            event.getRegistry().registerAll(ModTileEntities.TILE_ENTITIES.toArray(new TileEntityType[ModTileEntities.TILE_ENTITIES.size()]));
        }

        @SubscribeEvent
        public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
            LOGGER.info("Features registry");

            event.getRegistry().registerAll(ModFeatures.FEATURES.toArray(new Feature[ModFeatures.FEATURES.size()]));
        }

        @SubscribeEvent
        public static void registerContainers(final RegistryEvent.Register<ContainerType<?>> event) {
            LOGGER.info("Containers registry");

            event.getRegistry().registerAll(ModContainers.CONTAINERS.toArray(new ContainerType[ModContainers.CONTAINERS.size()]));
        }

    }

}
