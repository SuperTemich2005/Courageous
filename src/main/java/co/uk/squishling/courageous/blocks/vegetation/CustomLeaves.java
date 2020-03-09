package co.uk.squishling.courageous.blocks.vegetation;

import co.uk.squishling.courageous.util.lib.DefaultBlockProperties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;

public class CustomLeaves extends LeavesBlock {

    public CustomLeaves(String name) {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).sound(SoundType.PLANT).tickRandomly());
        DefaultBlockProperties.defaults(this, name);
    }

}