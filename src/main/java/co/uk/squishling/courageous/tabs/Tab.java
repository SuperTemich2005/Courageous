package co.uk.squishling.courageous.tabs;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Tab extends ItemGroup {

    public static final ItemGroup COURAGEOUS_GROUP = new Tab("courageous_tab");

    public Tab(String name) {
        super(name);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.AMPHORA);
    }

}
