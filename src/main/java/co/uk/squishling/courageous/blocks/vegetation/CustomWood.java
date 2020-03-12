package co.uk.squishling.courageous.blocks.vegetation;

import co.uk.squishling.courageous.blocks.IBlock;
import co.uk.squishling.courageous.tabs.WorldTab;
import co.uk.squishling.courageous.util.lib.DefaultBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemGroup;

public class CustomWood extends RotatedPillarBlock implements IBlock {

    public CustomWood(String name, MaterialColor color) {
        super(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
        DefaultBlockProperties.defaults(this, name);
    }

    public CustomWood(String name) {
        this(name, MaterialColor.WOOD);
    }

    @Override
    public ItemGroup getTab() {
        return WorldTab.WORLD;
    }

}
