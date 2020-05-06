package co.uk.squishling.courageous.blocks.cutting_board;

import co.uk.squishling.courageous.blocks.IHasTileEntity;
import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.blocks.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CuttingBoardContainer extends Container implements IHasTileEntity {

    public TileEntity tileEntity;
    private IItemHandler inventory;

    public CuttingBoardContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(ModContainers.POTTERY_WHEEL_CONTAINER, windowId);

        tileEntity = world.getTileEntity(pos);
        inventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            addSlot(handler, 0, 41, 35);
            addSlot(handler, 1, 9, 17);
            addSlot(handler, 2, 9, 55);
            addSlot(handler, 3, 82, 7);
            addSlot(handler, 4, 73, 25);
            addSlot(handler, 5, 91, 25);
            addSlot(handler, 6, 73, 44);
            addSlot(handler, 7, 91, 44);
            addSlot(handler, 8, 82, 63);
            addSlot(handler, 9, 146, 35);
        });
        layoutPlayerInventorySlots(8, 84);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, ModBlocks.CUTTING_BOARD);
    }

//    @Override
//    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
//        ItemStack itemstack = ItemStack.EMPTY;
//        Slot slot = this.inventorySlots.get(index);
//        if (slot != null && slot.getHasStack()) {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//            if (index < 10) {
//                if (!this.mergeItemStack(itemstack1, 2, this.inventorySlots.size(), true)) {
//                    return ItemStack.EMPTY;
//                }
//            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
//                return ItemStack.EMPTY;
//            }
//
//            if (itemstack1.isEmpty()) {
//                slot.putStack(ItemStack.EMPTY);
//            } else {
//                slot.onSlotChanged();
//            }
//        }
//
//        return itemstack;
//    }

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
        addSlot(new SlotItemHandler(handler, index, x, y));
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

}
