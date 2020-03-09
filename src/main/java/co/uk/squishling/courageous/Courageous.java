package co.uk.squishling.courageous;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelScreen;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.util.EventHandler;
import co.uk.squishling.courageous.util.ModBlockColors;
import co.uk.squishling.courageous.util.ModItemColors;
import co.uk.squishling.courageous.util.Reference;
import co.uk.squishling.courageous.util.config.ConfigHandler;
import co.uk.squishling.courageous.util.networking.ModPacketHandler;
import co.uk.squishling.courageous.world.gen.ModFeatures;
import co.uk.squishling.courageous.blocks.ModContainers;
import co.uk.squishling.courageous.blocks.ModTileEntities;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.resources.*;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraft.resources.ResourcePackInfo.Priority;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

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

        DistExecutor.runWhenOn(Dist.CLIENT, () -> this::injectResourcePack);
    }

    // Preinit
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Common setup");

        ModPacketHandler.registerPackets();

        ModFeatures.addTree(Biomes.FOREST, ModFeatures.APPLE_TREE, 0, 0.2f, 1);

        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.MAPLE_LOG, (RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.MAPLE_LOG_SYRUP, (RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.MAPLE_WOOD, (RotatedPillarBlock) ModBlocks.STRIPPED_MAPLE_WOOD);

        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.PALM_LOG, (RotatedPillarBlock) ModBlocks.STRIPPED_PALM_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.PALM_WOOD, (RotatedPillarBlock) ModBlocks.STRIPPED_PALM_WOOD);

        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.REDWOOD_LOG, (RotatedPillarBlock) ModBlocks.STRIPPED_REDWOOD_LOG);
        EventHandler.STRIPPED_LOG_MAP.put((RotatedPillarBlock) ModBlocks.REDWOOD_WOOD, (RotatedPillarBlock) ModBlocks.STRIPPED_REDWOOD_WOOD);

        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.BROWN_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.BLACK_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.BLUE_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.CYAN_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.GRAY_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.GREEN_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.LIGHT_BLUE_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.LIGHT_GRAY_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.LIME_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.MAGENTA_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.ORANGE_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.PINK_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.PURPLE_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.RED_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.WHITE_UNFIRED_AMPHORA);
//        PotteryWheelTileEntity.POTTERY_PIECES.add(ModItems.YELLOW_UNFIRED_AMPHORA);
    }

    private void clientRegistry(final FMLClientSetupEvent event) {
        LOGGER.info("Client setup");

//        injectResourcePack();

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
        public static void registerContainerTypes(final RegistryEvent.Register<ContainerType<?>> event) {
            LOGGER.info("Container types registry");

            event.getRegistry().register(ModContainers.POTTERY_WHEEL_CONTAINER);
        }

    }

    public void injectResourcePack() {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return;

        try {
            File resourcesFolder = FMLLoader.getLoadingModList().getModFileById(Reference.MOD_ID).getFile().findResource("overrides").toFile();

            if (!resourcesFolder.exists() && !resourcesFolder.mkdirs()) return;
            if (!resourcesFolder.exists() || !resourcesFolder.isDirectory()) return;

            final String id = "courageous_mc_override";
            final ITextComponent name = new StringTextComponent("Courageous' Minecraft Override Resources");
            final ITextComponent description = new StringTextComponent("Resources that override vanilla, such as the main menu panorama.");

            final IResourcePack pack = new FolderPack(resourcesFolder) {
                String prefix = "assets/minecraft/";

                @Override
                protected InputStream getInputStream(String resourcePath) throws IOException {
                    if ("pack.mcmeta".equals(resourcePath)) return new ByteArrayInputStream(("{\"pack\":{\"description\": \"dummy\",\"pack_format\": 4}}").getBytes(StandardCharsets.UTF_8));
                    if (!resourcePath.startsWith(prefix)) throw new FileNotFoundException(resourcePath);

                    return super.getInputStream(resourcePath);
                }

                @Override
                public boolean resourceExists(String resourcePath) {
                    if ("pack.mcmeta".equals(resourcePath)) return true;
                    if (!resourcePath.startsWith(prefix)) return false;

                    return super.resourceExists(resourcePath);
                }

                @Override
                public Set<String> getResourceNamespaces(ResourcePackType type) {
                    return Collections.singleton("minecraft");
                }
            };

            Minecraft.getInstance().getResourcePackList().addPackFinder(new IPackFinder() {
                @Override
                public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, IFactory<T> packInfoFactory) {
                    nameToPackMap.put(id, (T) new ClientResourcePackInfo(id, true, () ->
                            pack, name, description, PackCompatibility.COMPATIBLE, Priority.TOP, false, null, false
                    ));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
