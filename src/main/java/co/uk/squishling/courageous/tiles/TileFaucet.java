package co.uk.squishling.courageous.tiles;

import co.uk.squishling.courageous.blocks.BlockBambooFaucet;
import co.uk.squishling.courageous.util.config.ConfigHandler;
import co.uk.squishling.courageous.util.rendering.ModParticles;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.extensions.IForgeTileEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileFaucet extends TileEntity implements IForgeTileEntity, IFluidHandler, ITickableTileEntity {
    private FluidTank innerTank;
    private final int speed = ConfigHandler.COMMON.bambooFaucetSpeed.get();
    protected final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this).cast();

    public TileFaucet() {
        super(ModTiles.FAUCET.get());
        innerTank = new FluidTank(ConfigHandler.COMMON.bambooFaucetCapacity.get());
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public void tick() {
        if (!getFluidInTank(0).isEmpty()) {
            FluidStack drained = FluidStack.EMPTY;
            LazyOptional<IFluidHandler> handler = FluidUtil.getFluidHandler(world, pos.down(), Direction.UP);
            if (handler.isPresent()) {
                drained = FluidUtil.tryFluidTransfer(handler.orElseThrow(IllegalArgumentException::new), this, speed, true);
            }
            if (drained.isEmpty()) {
                drained = this.drain(speed, FluidAction.EXECUTE);
            }
            IParticleData drippingParticle;
            if (drained.getFluid() == Fluids.WATER) {
                drippingParticle = ParticleTypes.FALLING_WATER;
            } else if (drained.getFluid() == Fluids.LAVA) {
                drippingParticle = ParticleTypes.FALLING_LAVA;

            } else {
                drippingParticle = new BlockParticleData(ModParticles.FALLING_FLUID_PARTICLE, drained.getFluid().getDefaultState().getBlockState());
            }
            Direction dir = getBlockState().get(BlockBambooFaucet.HORIZONTAL_FACING).getOpposite();
            world.addParticle(drippingParticle,
                    pos.getX() + 0.5 + dir.getXOffset() * 0.08,
                    pos.getY() + 0.1875,
                    pos.getZ() + 0.5 + dir.getZOffset() * 0.08,
                    0, 0, 0);
        }
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return innerTank.getFluid();
    }

    @Override
    public int getTankCapacity(int tank) {
        return innerTank.getCapacity();
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return innerTank.isFluidValid(tank, stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (action == FluidAction.EXECUTE) markDirty();
        return innerTank.fill(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (action == FluidAction.EXECUTE) markDirty();
        return innerTank.drain(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (action == FluidAction.EXECUTE) markDirty();
        return innerTank.drain(maxDrain, action);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY && side == getBlockState().get(BlockBambooFaucet.HORIZONTAL_FACING).getOpposite()) {
            return fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT compoundNBT) {
        innerTank.readFromNBT(compoundNBT.getCompound("tank"));
        super.read(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        compoundNBT.put("tank", innerTank.writeToNBT(new CompoundNBT()));
        return super.write(compoundNBT);
    }

    @Override
    public net.minecraft.nbt.CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        this.read(tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        SUpdateTileEntityPacket packet = new SUpdateTileEntityPacket(this.pos, this.getType().hashCode(), this.write(new CompoundNBT()));
        return packet;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        CompoundNBT compound = packet.getNbtCompound();
        this.read(compound);
    }
}
