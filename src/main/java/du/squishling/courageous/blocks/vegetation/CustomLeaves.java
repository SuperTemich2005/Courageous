package du.squishling.courageous.blocks.vegetation;

import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.items.ModItems;
import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class CustomLeaves extends LeavesBlock {

    private Item[] drops;

    public CustomLeaves(String name, Item... drops) {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).sound(SoundType.PLANT).tickRandomly());
        this.setRegistryName(name);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new BlockItem(this, new Item.Properties().group(Tab.COURAGEOUS_GROUP)).setRegistryName(name));

        this.drops = drops;
    }

    public CustomLeaves(String name) {
        this(name, new Item[0]);
    }

    public Item[] getDrops() {
        return drops;
    }

}