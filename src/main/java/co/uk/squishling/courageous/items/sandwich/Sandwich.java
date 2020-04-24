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
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Sandwich extends ItemBase implements ICapabilityProvider, ICapabilitySerializable {

    private LazyOptional<IItemHandler> handler;

    public Sandwich() {
        super("sandwich", new Item.Properties().group(FoodTab.FOOD).setISTER(() -> SandwichISTER::new));
        handler =  LazyOptional.of(() -> new ItemStackHandler(4));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        addIngredient(stack, new ItemStack(Items.APPLE));
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return handler.cast();

        return LazyOptional.empty();
    }

    public void addIngredient(ItemStack stack, ItemStack ingredient) {
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int i = 0; i < h.getSlots(); i++) if (h.getStackInSlot(i).isEmpty()) {
                h.insertItem(i, ingredient, false);
                write();
                break;
            }
        });
    }

    private CompoundNBT write() {
        CompoundNBT nbt = (CompoundNBT) handler.map(h -> CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(h, null)).orElseGet(CompoundNBT::new);
        return nbt;
    }

    private void read(CompoundNBT nbt) {
        handler.ifPresent(h -> CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(h, null, nbt));
    }

    @Override
    public INBT serializeNBT() {
        return write();
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        read((CompoundNBT) nbt);
    }

}
