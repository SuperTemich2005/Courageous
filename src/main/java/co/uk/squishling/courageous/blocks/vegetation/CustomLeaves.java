package co.uk.squishling.courageous.blocks.vegetation;

import co.uk.squishling.courageous.blocks.IBlock;
import co.uk.squishling.courageous.tabs.WorldTab;
import co.uk.squishling.courageous.util.lib.DefaultBlockProperties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;

public class CustomLeaves extends LeavesBlock implements IBlock {

    public CustomLeaves(String name) {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).sound(SoundType.PLANT).tickRandomly());
        DefaultBlockProperties.defaults(this, name);
    }

    @Override
    public ItemGroup getTab() {
        return WorldTab.WORLD;
    }

}