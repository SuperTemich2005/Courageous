package co.uk.squishling.courageous.items;

import co.uk.squishling.courageous.util.Reference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemBase extends Item {

    public ItemBase(String name, ItemGroup group) {
        super(new Item.Properties().group(group));
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS_ARRAY.add(this);
    }

    public ItemBase(String name, Item.Properties properties) {
        super(properties);
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS_ARRAY.add(this);
    }

}
