package co.uk.squishling.courageous.items;

import co.uk.squishling.courageous.tabs.Tab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String name) {
        super(new Item.Properties().group(Tab.COURAGEOUS_GROUP));
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS.add(this);
    }

}
