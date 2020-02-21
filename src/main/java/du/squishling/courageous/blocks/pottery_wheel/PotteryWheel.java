package du.squishling.courageous.blocks.pottery_wheel;

import du.squishling.courageous.blocks.BlockBase;
import du.squishling.courageous.util.Reference;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class PotteryWheel extends BlockBase {

    public PotteryWheel() {
        super("pottery_wheel", Material.ROCK);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (Reference.isServer(world)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof INamedContainerProvider) {
                NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) te, te.getPos());
            }
        }

        return super.onBlockActivated(state, world, pos, player, hand, result);
    }

    @Override
    public boolean isSolid(BlockState p_200124_1_) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState p_149645_1_) {
        return super.getRenderType(p_149645_1_);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean causesSuffocation(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new PotteryWheelTileEntity();
    }

}
