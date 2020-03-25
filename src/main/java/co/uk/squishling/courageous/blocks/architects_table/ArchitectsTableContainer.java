package co.uk.squishling.courageous.blocks.architects_table;

import co.uk.squishling.courageous.blocks.IHasTileEntity;
import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.blocks.ModContainers;
import co.uk.squishling.courageous.util.Reference;
import co.uk.squishling.courageous.util.networking.ModPacketHandler;
import co.uk.squishling.courageous.util.networking.PacketSlotChanged;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;

public class ArchitectsTableContainer extends Container {

    private BlockPos pos;
    private World world;

    private IItemHandler inventory;
    private IItemHandler handler = new ItemStackHandler(1);

    public static HashMap<Item, ArrayList<ItemStack>> ARCITECTS_LIST = new HashMap<Item, ArrayList<ItemStack>>();

    public ArchitectsTableContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(ModContainers.ARCHITECTS_TABLE_CONTAINER, windowId);

        inventory = new InvWrapper(playerInventory);
        this.pos = pos;
        this.world = world;

        addSlot(handler, 0, 79, 29);

        layoutPlayerInventorySlots(8, 84);
    }

    private void slotChanged(ItemStack newStack) {
        if (Reference.isServer(world)) {
            ModPacketHandler.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), new PacketSlotChanged(newStack));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(world, pos), playerIn, ModBlocks.ARCHITECTS_TABLE);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < 2) {
                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(handler, index, x, y);
            x += dx;
            index++;
        }

        return index;
    }

    public int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int i = 0; i < verAmount; i++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void addSlot(IItemHandler handler, int index, int x, int y) {
        addSlot(new SlotItemHandler(handler, index, x, y) {
            @Override
            public void onSlotChanged() {
                if (getSlotIndex() == 0) slotChanged(getStack());
            }
        });
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

}
