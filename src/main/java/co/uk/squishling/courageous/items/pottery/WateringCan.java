package co.uk.squishling.courageous.items.pottery;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.tabs.PotteryTab;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class WateringCan extends ItemBase {

    public WateringCan(String name) {
        super(name, PotteryTab.POTTERY);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!getFluid(stack).isEmpty()) {
            String text = TextFormatting.GOLD + new TranslationTextComponent(getFluid(stack).getTranslationKey()).getFormattedText() + TextFormatting.RESET
                    + " x " + getFluid(stack).getAmount();
            tooltip.add(new StringTextComponent(text));
        }
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
        ItemStack stack = player.getHeldItem(handIn);

        FluidStack contents = getFluid(stack);

        RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != Type.BLOCK) return new ActionResult<>(ActionResultType.PASS, stack);
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
        BlockPos pos = blockraytraceresult.getPos();

        if (!world.getFluidState(pos).isEmpty()) {
            if (contents.getAmount() >= getCapacity(stack) || !world.getFluidState(pos).getFluid().equals(ForgeRegistries.FLUIDS.getValue(new ResourceLocation("minecraft:water")))) return new ActionResult<>(ActionResultType.PASS, stack);
            FluidStack fluidToFill = new FluidStack(world.getFluidState(pos).getFluid(), FluidAttributes.BUCKET_VOLUME);

            if (!canAcceptFluid(stack, fluidToFill)) return new ActionResult<>(ActionResultType.PASS, stack);
            if (!world.isRemote) {
                fill(stack, new FluidStack(world.getFluidState(pos).getFluid(), FluidAttributes.BUCKET_VOLUME));
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }

            player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

            player.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
            if (contents.getAmount() < 50) return new ActionResult<>(ActionResultType.PASS, stack);
            BlockPos pos1 = pos.offset(blockraytraceresult.getFace());
            BlockState targetState = world.getBlockState(pos1);
            if (targetState.isAir(world, pos1) || !targetState.getMaterial().isSolid() || targetState.getMaterial().isReplaceable()) {
                if (!world.isRemote) {
//                    world.setBlockState(pos1, contents.getFluid().getDefaultState().getBlockState());
//                    drain(stack, 50);
                }

                player.playSound(SoundEvents.WEATHER_RAIN_ABOVE, 0.2F, 1.5F);
                world.addParticle(ParticleTypes.NAUTILUS, player.getPosX(), player.getPosY() + 1, player.getPosZ(), 1f, -0.5f, 0f);

                player.addStat(Stats.ITEM_USED.get(this));
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
        }

        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        FluidStack fluid = getFluid(stack);
        if (fluid.isEmpty()) return 0f;
        return 1 - (float) fluid.getAmount() / getCapacity(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return !getFluid(stack).isEmpty();
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return cap == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY ? (LazyOptional<T>) LazyOptional.of(() -> new FluidHandlerItemStack(stack, 1000) {
                    @Override
                    public boolean canFillFluidType(FluidStack fluid) {
                        return fluid.getFluid().equals(Fluids.WATER);
                    }
                }) : LazyOptional.empty();
            }
        };
    }

    public FluidStack getFluid(ItemStack stack) {
        final LazyOptional<IFluidHandlerItem> cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (cap.isPresent()) {
            final FluidHandlerItemStack handler = (FluidHandlerItemStack) cap.orElseThrow(NullPointerException::new);
            return handler.getFluid();
        }
        return FluidStack.EMPTY;
    }

    public int getCapacity(ItemStack stack) {
        final LazyOptional<IFluidHandlerItem> cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (cap.isPresent()) {
            final FluidHandlerItemStack handler = (FluidHandlerItemStack) cap.orElseThrow(NullPointerException::new);
            return handler.getTankCapacity(0);
        }
        return 0;
    }

    public void fill(ItemStack stack, FluidStack resource) {
        final LazyOptional<IFluidHandlerItem> cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (cap.isPresent()) {
            final FluidHandlerItemStack handler = (FluidHandlerItemStack) cap.orElseThrow(NullPointerException::new);
            handler.fill(resource, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public void drain(ItemStack stack, int amount) {
        final LazyOptional<IFluidHandlerItem> cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (cap.isPresent()) {
            final FluidHandlerItemStack handler = (FluidHandlerItemStack) cap.orElseThrow(NullPointerException::new);
            handler.drain(amount, IFluidHandler.FluidAction.EXECUTE);
        }
    }

    public boolean canAcceptFluid(ItemStack stack, FluidStack resource) {
        final LazyOptional<IFluidHandlerItem> cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (cap.isPresent()) {
            final FluidHandlerItemStack handler = (FluidHandlerItemStack) cap.orElseThrow(NullPointerException::new);
            return handler.canFillFluidType(resource);
        }
        return false;
    }

}
