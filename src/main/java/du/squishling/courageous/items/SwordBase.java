package du.squishling.courageous.items;

import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ResourceLocation;

public class SwordBase extends SwordItem {

    public SwordBase(String name, IItemTier tier, int damage, float speed) {
        this(tier, damage, speed, new Item.Properties().group(Tab.COURAGEOUS_GROUP));
        this.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

        ModItems.ITEMS.add(this);
    }

    public SwordBase(IItemTier tier, int damage, float speed, Properties properties) {
        super(tier, damage, speed, properties);
    }

}
