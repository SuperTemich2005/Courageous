package co.uk.squishling.courageous.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CustomPlanks extends BlockBase {

    public CustomPlanks(String name) {
        this(name, MaterialColor.WOOD);
    }

    public CustomPlanks(String name, MaterialColor color) {
        super(name, Block.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
    }

}
