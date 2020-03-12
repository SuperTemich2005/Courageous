package co.uk.squishling.courageous.blocks.vegetation;

import co.uk.squishling.courageous.blocks.IBlock;
import co.uk.squishling.courageous.tabs.WorldTab;
import co.uk.squishling.courageous.util.lib.DefaultBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;

public class CustomLog extends LogBlock implements IBlock {

    public CustomLog(String name, MaterialColor color) {
        super(color, Block.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
        DefaultBlockProperties.defaults(this, name);
    }

    @Override
    public ItemGroup getTab() {
        return WorldTab.WORLD;
    }

}
