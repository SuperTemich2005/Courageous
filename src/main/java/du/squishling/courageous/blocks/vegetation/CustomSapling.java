package du.squishling.courageous.blocks.vegetation;

import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.items.ModItems;
import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.trees.CustomTree;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.trees.Tree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.AbstractTreeFeature;

import java.util.Random;

public class CustomSapling extends SaplingBlock implements IGrowable {

    private Tree tree;

    public CustomSapling(String name, Tree tree) {
        super(tree, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().sound(SoundType.PLANT).hardnessAndResistance(0));
        this.setRegistryName(Reference.MOD_ID, name);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new BlockItem(this, new Item.Properties().group(Tab.COURAGEOUS_GROUP)).setRegistryName(new ResourceLocation(Reference.MOD_ID, name)));

        this.tree = tree;
    }

    public CustomSapling(String name, AbstractTreeFeature tree) {
        this(name, new CustomTree(tree));
    }

    @Override
    public void grow(World p_176474_1_, Random p_176474_2_, BlockPos p_176474_3_, BlockState p_176474_4_) {
        tree.spawn(p_176474_1_, p_176474_3_, p_176474_4_, p_176474_2_);
    }

}
