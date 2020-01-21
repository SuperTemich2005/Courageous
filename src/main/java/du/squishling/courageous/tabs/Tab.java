package du.squishling.courageous.tabs;

import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Tab extends ItemGroup {

    public static final ItemGroup COURAGEOUS_GROUP = new Tab("courageous_tab", ModItems.PEAR);

    private ItemStack icon;

    public Tab(String name, Item icon) {
        this(name, new ItemStack(icon));
    }

    public Tab(String name, ItemStack icon) {
        super(name);
        this.icon = icon;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.PEAR_SAPLING);
    }

}
