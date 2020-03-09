package co.uk.squishling.courageous.blocks.vegetation;

import co.uk.squishling.courageous.util.lib.DefaultBlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class CustomDoublePlant extends DoublePlantBlock {

    public CustomDoublePlant(String name) {
        super(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0).sound(SoundType.PLANT));
        DefaultBlockProperties.defaults(this, name);
    }

}
