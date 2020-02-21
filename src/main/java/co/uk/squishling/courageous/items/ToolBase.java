package co.uk.squishling.courageous.items;

import co.uk.squishling.courageous.tabs.Tab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;

import java.util.Set;

public class ToolBase extends ToolItem {

    public ToolBase(String name, float damage, float speed, IItemTier tier, Set<Block> blocks) {
        super(damage, speed, tier, blocks, new Item.Properties().group(Tab.COURAGEOUS_GROUP));
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

        ModItems.ITEMS.add(this);
    }

}
