//package co.uk.squishling.courageous.blocks.pottery_wheel;
//
//import co.uk.squishling.courageous.blocks.IHasButton;
//import co.uk.squishling.courageous.blocks.ModTileEntities;
//import co.uk.squishling.courageous.util.Reference;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.inventory.container.Container;
//import net.minecraft.inventory.container.INamedContainerProvider;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.Items;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.tileentity.ITickableTileEntity;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.Direction;
//import net.minecraft.util.text.ITextComponent;
//import net.minecraft.util.text.TranslationTextComponent;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.util.INBTSerializable;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.HashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class PotteryWheelTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IHasButton {
//
//    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(() -> getHandler()).cast();
//
//    private HashMap<Item, Integer> CLAY_VALUES_MAP = new HashMap<Item, Integer>();
//
//    public int selectedIndex = 0;
//    private HashMap<Item, Integer> POTTERY_MAP = new HashMap<Item, Integer>();
//
//    public PotteryWheelTileEntity() {
//        super(ModTileEntities.POTTERY_WHEEL);
//
//        CLAY_VALUES_MAP.put(Items.CLAY, 4);
//        CLAY_VALUES_MAP.put(Items.CLAY_BALL, 1);
//    }
//
//    @Override
//    public void tick() {
//        // Common
//
//        if (Reference.isServer(world)) {
//            // Server
//
//            AtomicInteger clayValue = new AtomicInteger();
//            handler.ifPresent(h -> {
//                ItemStack stack = h.getStackInSlot(0);
//                if (stack.getItem() == Items.CLAY_BALL) clayValue.set(stack.getCount());
//                if (stack.getItem() == Items.CLAY) clayValue.set(stack.getCount() * 4);
//            });
//            int clay = clayValue.get();
//
////            if (clay > POTTERY_MAP.get(POTTERY_MAP.keySet()));
//        } else {
//            // Client
//
//        }
//    }
//
//    // ---- NBT ----
//    @Override
//    public void read(CompoundNBT tag) {
//        CompoundNBT invTag = tag.getCompound("inv");
//        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
//
//        super.read(tag);
//    }
//
//    @Override
//    public CompoundNBT write(CompoundNBT tag) {
//        handler.ifPresent(h -> {
//            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
//            tag.put("inv", compound);
//        });
//
//        return super.write(tag);
//    }
//
//    // ---- Capabilities ----
//    private ItemStackHandler getHandler() {
//        return new ItemStackHandler(2) {
//            @Override
//            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
//                return slot == 1 || CLAY_VALUES_MAP.containsKey(stack.getItem());
//            }
//
//            @Nonnull
//            @Override
//            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//                if (!isItemValid(slot, stack)) return stack;
//                return super.insertItem(slot, stack, simulate);
//            }
//
//            @Override
//            protected void onContentsChanged(int slot) {
//                markDirty();
//            }
//        };
//    }
//
//    // TODO https://discordapp.com/channels/438697837958791178/438698107568521267/680787581624909859
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return handler.cast();
//        return super.getCapability(cap, side);
//    }
//
////    @Nonnull
////    @Override
////    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
////        return null;
////    }
//
//    // --- Container stuff ---
//    @Override
//    public ITextComponent getDisplayName() {
//        return new TranslationTextComponent("block.courageous.pottery_wheel");
//    }
//
//    @Nullable
//    @Override
//    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
//        return new PotteryWheelContainer(i, world, pos, playerInventory);
//    }
//
//    @Override
//    public void select(int index) {
//        selectedIndex = index;
//    }
//
//}
