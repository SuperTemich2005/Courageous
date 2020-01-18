package du.squishling.courageous.items;

import du.squishling.courageous.Courageous;
import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class ItemBase extends Item {

    public ItemBase(String name) {
        super(new Item.Properties().group(Tab.COURAGEOUS_GROUP));
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS.add(this);
    }

}
