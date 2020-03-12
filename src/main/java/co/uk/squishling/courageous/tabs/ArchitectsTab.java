package co.uk.squishling.courageous.tabs;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ArchitectsTab extends ItemGroup {

    public static final ItemGroup ARCHITECT = new ArchitectsTab();

    public ArchitectsTab() {
        super("courageous_architect");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.ARCHITECTS_TABLE);
    }

}
