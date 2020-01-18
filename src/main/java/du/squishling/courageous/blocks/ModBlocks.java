package du.squishling.courageous.blocks;

import du.squishling.courageous.blocks.vegetation.*;
import du.squishling.courageous.items.ModItems;
import du.squishling.courageous.trees.AlpineTree;
import du.squishling.courageous.trees.PalmTree;
import du.squishling.courageous.trees.PearTree;
import du.squishling.courageous.world.gen.features.ModFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Properties;

import java.util.ArrayList;

public class ModBlocks {

    public static ArrayList<Block> BLOCKS = new ArrayList<Block>();

    public static final Block WEATHERED_BRICK = new BlockBase("weathered_brick", Material.ROCK);

    public static final Block PALM_SAPLING = new CustomSapling("palm_sapling", new PalmTree());
    public static final Block PALM_LEAVES = new CustomLeaves("palm_leaves", new BlockItem(PALM_SAPLING, new Item.Properties()));
    public static final Block PALM_LOG = new CustomLog("palm_log", MaterialColor.SAND);
    public static final Block PALM_PLANKS = new CustomPlanks("palm_planks");

    public static final Block ALPINE_SAPLING = new CustomSapling("alpine_sapling", new AlpineTree());
    public static final Block ALPINE_LEAVES = new CustomLeaves("alpine_leaves", new BlockItem(ALPINE_SAPLING, new Item.Properties()));

    public static final Block PEAR_SAPLING = new CustomSapling("pear_sapling", new PearTree());
    public static final Block PEAR_LEAVES = new HarvestableLeaves("pear_leaves", ModItems.PEAR).setMinMax(1, 2);

    public static final Block DESERT_SHRUB = new CustomBush("desert_shrub", Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 16.0D, 13.0D, 14.0D), Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.RED_SAND);

}
