package co.uk.squishling.courageous.util.lib;

import co.uk.squishling.courageous.blocks.IBlock;
import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.ArchitectsTab;
import co.uk.squishling.courageous.tabs.GeneralTab;
import co.uk.squishling.courageous.tabs.WorldTab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

public class DefaultBlockProperties {

    public static void defaults(Block block, String name) {
        block.setRegistryName(Reference.MOD_ID, name);

        ItemGroup group = GeneralTab.GENERAL;
        if (block instanceof IBlock) group = ((IBlock) block).getTab();

        ModBlocks.BLOCKS.add(block);
        ModItems.ITEMS.add(new BlockItem(block, new Item.Properties().group(group)).setRegistryName(new ResourceLocation(Reference.MOD_ID, name)));
    }

}
