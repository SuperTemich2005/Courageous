package du.squishling.courageous.blocks.vegetation;

import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.items.ModItems;
import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class CustomWood extends RotatedPillarBlock {

    public CustomWood(String name, MaterialColor color) {
        super(Block.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
        this.setRegistryName(Reference.MOD_ID, name);

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new BlockItem(this, new Item.Properties().group(Tab.COURAGEOUS_GROUP)).setRegistryName(new ResourceLocation(Reference.MOD_ID, name)));
    }

    public CustomWood(String name) {
        this(name, MaterialColor.WOOD);
    }

}
