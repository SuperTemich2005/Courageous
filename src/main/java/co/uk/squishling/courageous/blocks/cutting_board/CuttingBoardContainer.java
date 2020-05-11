package co.uk.squishling.courageous.blocks.cutting_board;

import co.uk.squishling.courageous.blocks.IHasTileEntity;
import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.blocks.ModContainers;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.items.sandwich.Sandwich;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.LoomContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CuttingBoardContainer extends Container implements IHasTileEntity {

    public TileEntity tileEntity;
    private IItemHandler inventory;

    private static final int KNIFE_SLOT = 0;
    private static final int CUTTING_INPUT_SLOT = 1;
    private static final int CUTTING_OUTPUT_SLOT = 2;
    private static final int TOP_BREAD_SLOT = 3;
    private static final int INGREDIENT_SLOT_A = 4;
    private static final int INGREDIENT_SLOT_B = 5;
    private static final int INGREDIENT_SLOT_C = 6;
    private static final int INGREDIENT_SLOT_D = 7;
    private static final int BOTTOM_BREAD_SLOT = 8;
    private static final int SANDWICH_OUTPUT_SLOT = 9;

    public CuttingBoardContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(ModContainers.CUTTING_BOARD_CONTAINER, windowId);

        tileEntity = world.getTileEntity(pos);
        inventory = new InvWrapper(playerInventory);

        tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            addIngredientSlot(handler, KNIFE_SLOT, 41, 45);

            addSlot(handler, CUTTING_INPUT_SLOT, 9, 27);
            addSlot(handler, CUTTING_OUTPUT_SLOT, 9, 65);

            addIngredientSlot(handler, TOP_BREAD_SLOT, 82, 17);
            addIngredientSlot(handler, INGREDIENT_SLOT_A, 73, 36);
            addIngredientSlot(handler, INGREDIENT_SLOT_B, 91, 36);
            addIngredientSlot(handler, INGREDIENT_SLOT_C, 73, 54);
            addIngredientSlot(handler, INGREDIENT_SLOT_D, 91, 54);
            addIngredientSlot(handler, BOTTOM_BREAD_SLOT, 82, 73);

            addSlot(new SlotItemHandler(handler, SANDWICH_OUTPUT_SLOT, 146, 45) {
                @Override
                public ItemStack onTake(PlayerEntity playerEntity, ItemStack stack) {
                    onTakeSandwich();

                    return super.onTake(playerEntity, stack);
                }
            });
        });
        layoutPlayerInventorySlots(8, 94);
    }

    public void onTakeSandwich() {
        getSlot(TOP_BREAD_SLOT).decrStackSize(1);
        getSlot(INGREDIENT_SLOT_A).decrStackSize(1);
        getSlot(INGREDIENT_SLOT_B).decrStackSize(1);
        getSlot(INGREDIENT_SLOT_C).decrStackSize(1);
        getSlot(INGREDIENT_SLOT_D).decrStackSize(1);
        getSlot(BOTTOM_BREAD_SLOT).decrStackSize(1);

        onIngredientsChanged();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerIn, ModBlocks.CUTTING_BOARD);
    }

    private void onIngredientsChanged() {
        if (getSlot(TOP_BREAD_SLOT).getStack().getItem() == ModItems.BREAD_SLICE && getSlot(BOTTOM_BREAD_SLOT).getStack().getItem() == ModItems.BREAD_SLICE
        && !getSlot(KNIFE_SLOT).getStack().isEmpty()) {
            ItemStack stack = new ItemStack(ModItems.SANDWICH);

            for (int i = INGREDIENT_SLOT_A; i <= INGREDIENT_SLOT_D; i++) {
                if (!getSlot(i).getStack().isEmpty()) Sandwich.addIngredient(stack, new ItemStack(getSlot(i).getStack().getItem()));
            }

            getSlot(SANDWICH_OUTPUT_SLOT).putStack(stack);
        } else if (!getSlot(SANDWICH_OUTPUT_SLOT).getStack().isEmpty()) getSlot(SANDWICH_OUTPUT_SLOT).putStack(ItemStack.EMPTY);
    }

    private void addIngredientSlot(IItemHandler handler, int index, int x, int y) {
        addSlot(new SlotItemHandler(handler, index, x, y) {
            @Override
            public void onSlotChanged() {
                onIngredientsChanged();
            }
        });
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == SANDWICH_OUTPUT_SLOT) onTakeSandwich();

            if(index < 10) {
                onIngredientsChanged();
                if (!this.mergeItemStack(itemstack1, 10, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 9, false)) {
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
        addSlot(new SlotItemHandler(handler, index, x, y));
    }

    protected void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

}
