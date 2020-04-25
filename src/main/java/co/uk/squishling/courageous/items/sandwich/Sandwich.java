package co.uk.squishling.courageous.items.sandwich;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.FoodTab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Sandwich extends ItemBase {

    private String HANDLER = "handler";

    public Sandwich() {
        super("sandwich", new Item.Properties().group(FoodTab.FOOD).setISTER(() -> SandwichISTER::new));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (Reference.isServer(worldIn))
        addIngredient(stack, handIn == Hand.MAIN_HAND ? playerIn.getHeldItemOffhand() : playerIn.getHeldItemMainhand());
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    if (stack.hasTag() && stack.getTag().contains(HANDLER, Constants.NBT.TAG_COMPOUND)) {
                        ItemStackHandler h = new ItemStackHandler();
                        h.deserializeNBT((CompoundNBT) stack.getTag().get(HANDLER));
                        return LazyOptional.of(() -> h).cast();
                    } else {
                        return LazyOptional.of(() -> new ItemStackHandler(4) {
                            @Override
                            protected void onContentsChanged(int slot) {
                                stack.getOrCreateTag().put(HANDLER, this.serializeNBT());
                            }

                            @Override
                            public int getSlotLimit(int slot) {
                                return 1;
                            }
                        }).cast();
                    }
                }

                return LazyOptional.empty();
            }
        };
    }

    public void addIngredient(ItemStack stack, ItemStack ingredient) {
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            ItemStack leftover = ingredient.copy();
            for (int i = 0; i < h.getSlots(); i++) {
                System.out.println(leftover);
                leftover = h.insertItem(i, leftover, false);
                if (leftover.isEmpty()) break;
            }
        });

        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int i = 0; i < h.getSlots(); i++) {
                System.out.println(h.getStackInSlot(i));
            }
        });
    }

}
