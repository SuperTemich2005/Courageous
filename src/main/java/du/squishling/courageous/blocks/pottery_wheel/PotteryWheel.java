package du.squishling.courageous.blocks.pottery_wheel;

import du.squishling.courageous.blocks.BlockBase;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class PotteryWheel extends BlockBase {

    public PotteryWheel() {
        super("pottery_wheel", Material.ROCK);
    }

    @Override
    public boolean isSolid(BlockState p_200124_1_) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return super.getRenderType(p_149645_1_);
    }

    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

}
