package du.squishling.courageous.blocks.pottery_wheel;

import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;
import du.squishling.courageous.Courageous;
import du.squishling.courageous.blocks.ModTileEntities;
import du.squishling.courageous.util.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.server.command.TextComponentHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;

public class PotteryWheelTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(() -> getHandler()).cast();

    private HashMap<Item, Integer> CLAY_VALUES_MAP = new HashMap<Item, Integer>();

    public PotteryWheelTileEntity() {
        super(ModTileEntities.POTTERY_WHEEL);

        CLAY_VALUES_MAP.put(Items.CLAY, 4);
        CLAY_VALUES_MAP.put(Items.CLAY_BALL, 1);
    }

    @Override
    public void tick() {
        // Common

        if (Reference.isServer(world)) {
            // Server

        } else {
            // Client

        }
    }

    // ---- NBT ----
    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });

        return super.write(tag);
    }

    // ---- Capabilities ----
    private ItemStackHandler getHandler() {
        return new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return CLAY_VALUES_MAP.containsKey(stack.getItem());
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) return stack;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (LazyOptional<T>) handler;
        return super.getCapability(cap, side);
    }

    // --- Container stuff ---
    @Override
    public ITextComponent getDisplayName() {
        return (ITextComponent) new StringTextComponent("pottery_wheel");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new PotteryWheelContainer(i, world, pos, playerInventory);
    }

}
