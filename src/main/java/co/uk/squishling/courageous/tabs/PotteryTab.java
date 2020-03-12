package co.uk.squishling.courageous.tabs;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class PotteryTab extends ItemGroup {

    public static final ItemGroup POTTERY = new PotteryTab();

    public PotteryTab() {
        super("courageous_pottery");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.AMPHORA);
    }

}
