package du.squishling.courageous.util;

import du.squishling.courageous.blocks.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IEnviromentBlockReader;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ModBlockColors implements IBlockColor {

    public static final IBlockColor INSTANCE = new ModBlockColors();
    public static Random rand = new Random();

    private static ArrayList<Block> LEAVES = new ArrayList<Block>();
    private static HashMap<Block, Integer> BLOCKS = new HashMap<Block, Integer>();

    @Override
    public int getColor(BlockState blockState, @Nullable IEnviromentBlockReader iEnviromentBlockReader, @Nullable BlockPos blockPos, int i) {
        for (Block block : BLOCKS.keySet()) if (blockState.getBlock().equals(block)) return BLOCKS.get(block);
        for (Block block : LEAVES) if (blockState.getBlock().equals(block)) return iEnviromentBlockReader.getBiome(blockPos).getFoliageColor(blockPos);

        return 0;
    }

    public static void registerFoliage(Block block) {
        LEAVES.add(block);
    }

    public static void registerAllFoliage(Block... blocks) {
        for (Block block : blocks) registerFoliage(block);
    }

    public static void registerBlock(Block block, int color) {
        BLOCKS.put(block, color);
    }

    public static void registerBlockColors() {
        registerBlock(ModBlocks.PALM_LEAVES, 0xe1ff43);

        registerAllFoliage (
            ModBlocks.ALPINE_LEAVES, ModBlocks.PEAR_LEAVES,    ModBlocks.ORANGE_LEAVES,
            ModBlocks.APPLE_LEAVES,  ModBlocks.AVOCADO_LEAVES, ModBlocks.KIWI_FRUIT_LEAVES,
            ModBlocks.LEMON_LEAVES,  ModBlocks.PLUM_LEAVES,    ModBlocks.FALLEN_LEAVES
        );

        for (Block block : BLOCKS.keySet()) Minecraft.getInstance().getBlockColors().register(INSTANCE, block);
        for (Block block : LEAVES) Minecraft.getInstance().getBlockColors().register(INSTANCE, block);
    }

}
