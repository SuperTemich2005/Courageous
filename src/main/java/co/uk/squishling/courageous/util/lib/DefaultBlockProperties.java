package co.uk.squishling.courageous.util.lib;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.Tab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class DefaultBlockProperties {

    public static void defaults(Block block, String name) {
        block.setRegistryName(Reference.MOD_ID, name);

        ModBlocks.BLOCKS.add(block);
        ModItems.ITEMS.add(new BlockItem(block, new Item.Properties().group(Tab.COURAGEOUS_GROUP)).setRegistryName(new ResourceLocation(Reference.MOD_ID, name)));
    }

}
