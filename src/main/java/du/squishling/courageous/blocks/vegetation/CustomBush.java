package du.squishling.courageous.blocks.vegetation;

import du.squishling.courageous.blocks.ModBlocks;
import du.squishling.courageous.items.ModItems;
import du.squishling.courageous.tabs.Tab;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.IShearable;

public class CustomBush extends BushBlock implements IShearable {

    private Block[] acceptedBlocks;
    private VoxelShape shape;

    public CustomBush(String name, VoxelShape shape, Block... acceptedBlocks) {
        super(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.WOOD).doesNotBlockMovement().sound(SoundType.PLANT).hardnessAndResistance(0));
        setRegistryName(Reference.MOD_ID, name);

        this.acceptedBlocks = acceptedBlocks;
        this.shape = shape;

        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new BlockItem(this, new Item.Properties().group(Tab.COURAGEOUS_GROUP)).setRegistryName(new ResourceLocation(Reference.MOD_ID, name)));
    }

    public CustomBush(String name, VoxelShape shape) {
        this(name, shape, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.DIRT, Blocks.COARSE_DIRT);
    }

    public CustomBush(String name, Block... blocks) {
        this(name, Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D), blocks);
    }

    public CustomBush(String name) {
        this(name, Blocks.GRASS_BLOCK, Blocks.PODZOL, Blocks.DIRT, Blocks.COARSE_DIRT);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        for (Block blck : acceptedBlocks) if (block == blck) return true;
        return false;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return shape;
    }

}
