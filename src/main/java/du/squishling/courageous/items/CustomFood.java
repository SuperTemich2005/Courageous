package du.squishling.courageous.items;

import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.item.Food;
import net.minecraft.item.Food.Builder;
import net.minecraft.item.Item;

public class CustomFood extends Item {

    public CustomFood(String name, Food food) {
        super(new Item.Properties().group(Tab.COURAGEOUS_GROUP).food(food));
        this.setRegistryName(Reference.MOD_ID, name);

        ModItems.ITEMS.add(this);
    }

    public CustomFood(String name, int hunger, float saturation) {
        this(name, new Builder().hunger(hunger).saturation(saturation).build());
    }

}
