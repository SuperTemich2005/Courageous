package co.uk.squishling.courageous.blocks.architects_table;

import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.blocks.ModContainers;
import co.uk.squishling.courageous.recipes.ArchitectTable.ArchitectsTableRecipe;
import com.google.common.collect.Lists;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ArchitectsTableContainer extends Container {
    private final IWorldPosCallable worldPosCallable;
    private final World world;
    private final IntReferenceHolder selectedRecipe;
    private List<ArchitectsTableRecipe> recipes;
    private Runnable inventoryUpdateListener;
    private ItemStack itemStackInput;
    public final IInventory inputInventory;
    private final CraftResultInventory inventory;
    final Slot inputInventorySlot;
    final Slot outputInventorySlot;
    private long lastOnTake;

    public ArchitectsTableContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory) {
        super(ModContainers.ARCHITECTS_TABLE_CONTAINER, id);
        this.selectedRecipe = IntReferenceHolder.single();
        this.recipes = Lists.newArrayList();
        this.itemStackInput = ItemStack.EMPTY;
        this.inventoryUpdateListener = () -> {};
        this.inputInventory = new Inventory(1) {
            public void markDirty() {
                super.markDirty();
                ArchitectsTableContainer.this.onCraftMatrixChanged(this);
                ArchitectsTableContainer.this.inventoryUpdateListener.run();
            }
        };
        this.inventory = new CraftResultInventory();
        this.worldPosCallable = IWorldPosCallable.of(world, pos);
        this.world = world;
        this.inputInventorySlot = this.addSlot(new Slot(this.inputInventory, 0, 20, 33));
        this.outputInventorySlot = this.addSlot(new Slot(this.inventory, 1, 143, 33) {
            public boolean isItemValid(ItemStack p_75214_1_) {
                return false;
            }

            public ItemStack onTake(PlayerEntity player, ItemStack stack) {
                ItemStack lvt_3_1_ = ArchitectsTableContainer.this.inputInventorySlot.decrStackSize(1);
                if (!lvt_3_1_.isEmpty()) {
                    ArchitectsTableContainer.this.updateRecipeResultSlot();
                }

                stack.getItem().onCreated(stack, player.world, player);
                worldPosCallable.consume((world, pos) -> {
                    long time = world.getGameTime();
                    if (ArchitectsTableContainer.this.lastOnTake != time) {
                        world.playSound((PlayerEntity)null, pos, SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        ArchitectsTableContainer.this.lastOnTake = time;
                    }

                });
                return super.onTake(player, stack);
            }
        });

        //Add player inventory
        int i;
        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        this.trackInt(this.selectedRecipe);
    }

    @OnlyIn(Dist.CLIENT)
    public int getSelectedRecipe() {
        return this.selectedRecipe.get();
    }

    @OnlyIn(Dist.CLIENT)
    public List<ArchitectsTableRecipe> getRecipeList() {
        return this.recipes;
    }

    @OnlyIn(Dist.CLIENT)
    public int getRecipeListSize() {
        return this.recipes.size();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean hasItemsinInputSlot() {
        return this.inputInventorySlot.getHasStack() && !this.recipes.isEmpty();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(worldPosCallable, playerIn, ModBlocks.ARCHITECTS_TABLE);
    }

    @Override
    public boolean enchantItem(PlayerEntity player, int recipe) {
        if (recipe >= 0 && recipe < this.recipes.size()) {
            this.selectedRecipe.set(recipe);
            this.updateRecipeResultSlot();
        }

        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        ItemStack stack = this.inputInventorySlot.getStack();
        if (stack.getItem() != this.itemStackInput.getItem()) {
            this.itemStackInput = stack.copy();
            this.updateAvailableRecipes(inventory, stack);
        }

    }

    private void updateAvailableRecipes(IInventory inventory, ItemStack stack) {
        this.recipes.clear();
        this.selectedRecipe.set(-1);
        this.outputInventorySlot.putStack(ItemStack.EMPTY);
        if (!stack.isEmpty()) {
            this.recipes = this.world.getRecipeManager().getRecipes(ArchitectsTableRecipe.recipeType, inventory, this.world);
        }
    }

    private void updateRecipeResultSlot() {
        if (!this.recipes.isEmpty()) {
            ArchitectsTableRecipe recipe = (ArchitectsTableRecipe)this.recipes.get(this.selectedRecipe.get());
            this.outputInventorySlot.putStack(recipe.getCraftingResult(this.inputInventory));
        } else {
            this.outputInventorySlot.putStack(ItemStack.EMPTY);
        }

        this.detectAndSendChanges();
    }

    @Override
    public ContainerType<?> getType() {
        return ModContainers.ARCHITECTS_TABLE_CONTAINER;
    }

    @OnlyIn(Dist.CLIENT)
    public void setInventoryUpdateListener(Runnable runnable) {
        this.inventoryUpdateListener = runnable;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.inventory && super.canMergeSlot(stack, slot);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack fromStack = slot.getStack();
            Item item = fromStack.getItem();
            stack = fromStack.copy();
            if (index == 1) {
                item.onCreated(fromStack, player.world, player);
                if (!this.mergeItemStack(fromStack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(fromStack, stack);
            } else if (index == 0) {
                if (!this.mergeItemStack(fromStack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.world.getRecipeManager().getRecipe(ArchitectsTableRecipe.recipeType, new Inventory(fromStack), this.world).isPresent()) {
                if (!this.mergeItemStack(fromStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 2 && index < 29) {
                if (!this.mergeItemStack(fromStack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 29 && index < 38 && !this.mergeItemStack(fromStack, 2, 29, false)) {
                return ItemStack.EMPTY;
            }

            if (fromStack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }

            slot.onSlotChanged();
            if (fromStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, fromStack);
            this.detectAndSendChanges();
        }

        return stack;
    }

    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.inventory.removeStackFromSlot(1);
        this.worldPosCallable.consume((world, pos) -> {
            this.clearContainer(player, player.world, this.inputInventory);
        });
    }

    /*
    private BlockPos pos;
    private final World world;

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

    @OnlyIn(Dist.CLIENT)
    public void setInventoryUpdateListener(Runnable runnable) {
        this.inventoryUpdateListener = runnable;
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
    }*/
}
