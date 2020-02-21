package co.uk.squishling.courageous.blocks;

import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.Tab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

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
