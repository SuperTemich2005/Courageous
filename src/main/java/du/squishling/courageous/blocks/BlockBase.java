package du.squishling.courageous.blocks;

import du.squishling.courageous.Courageous;
import du.squishling.courageous.items.ItemBase;
import du.squishling.courageous.items.ModItems;
import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.logging.Logger;

public class BlockBase extends Block {

    public BlockBase(String name, Material material) {
        this(name, Block.Properties.create(material));
    }

    public BlockBase(String name, Block.Properties props) {
        super(props);
        this.setRegistryName(Reference.MOD_ID, name);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new BlockItem(this, new Item.Properties().group(Tab.COURAGEOUS_GROUP)).setRegistryName(new ResourceLocation(Reference.MOD_ID, name)));
    }

}
