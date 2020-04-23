package co.uk.squishling.courageous.items.sandwich;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.FoodTab;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Sandwich extends ItemBase {

    public Sandwich() {
        super("sandwich", new Item.Properties().group(FoodTab.FOOD).setISTER(() -> SandwichISTER::new));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        addIngredient(stack, new ItemStack(Items.APPLE));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (LazyOptional<T>) LazyOptional.of(() -> new ItemStackHandler(4))
                        : LazyOptional.empty();
            }
        };
    }

    public void addIngredient(ItemStack stack, ItemStack ingredient) {
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) if (handler.getStackInSlot(i).isEmpty()) {
                handler.insertItem(i, ingredient, false);
                break;
            }
        });
    }

}
