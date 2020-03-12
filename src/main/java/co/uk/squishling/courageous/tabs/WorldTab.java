package co.uk.squishling.courageous.tabs;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WorldTab extends ItemGroup {

    public static final ItemGroup WORLD = new WorldTab();

    public WorldTab() {
        super("courageous_world");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack((Block) ModBlocks.PEAR_SAPLING);
    }

}
