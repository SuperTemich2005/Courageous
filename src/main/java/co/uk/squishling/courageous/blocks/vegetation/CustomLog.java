package co.uk.squishling.courageous.blocks.vegetation;

import co.uk.squishling.courageous.util.lib.DefaultBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CustomLog extends LogBlock {

    public CustomLog(String name, MaterialColor color) {
        super(color, Block.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
        DefaultBlockProperties.defaults(this, name);
    }

}
