package co.uk.squishling.courageous.tabs;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import vazkii.patchouli.common.item.ItemModBook;

public class GeneralTab extends ItemGroup {

    public static final ItemGroup GENERAL = new GeneralTab();

    public GeneralTab() {
        super("courageous_general");
    }

    @Override
    public ItemStack createIcon() {
        return ItemModBook.forBook("courageous:guide");
    }

}
