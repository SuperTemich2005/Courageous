package co.uk.squishling.courageous.items.sandwich;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.FoodTab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Food.Builder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
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
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class Sandwich extends ItemBase {

    private String HANDLER = "handler";

    public Sandwich() {
        super("sandwich", new Item.Properties().group(FoodTab.FOOD).food(new Builder().hunger(2).saturation(0.24f).build()).setISTER(() -> SandwichISTER::new));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.PlayerEntity playerIn, Entity entity) {
        if (Reference.isServer(playerIn.getEntityWorld())) addIngredient(stack, playerIn.getHeldItemOffhand());
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack p_77661_1_) {
        return UseAction.EAT;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (Reference.isServer(world)) {
            entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);

            if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) stack.shrink(1);

            if (entity instanceof PlayerEntity) {
                PlayerEntity player = ((PlayerEntity)entity);
                player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + getFood().getHealing());
                player.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + getFood().getSaturation());
            }

            stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                System.out.println("A");
                for (int i = 0; i < handler.getSlots(); i++) {
                    System.out.println(handler.getStackInSlot(i).getItem());
                    if (handler.getStackInSlot(i).getItem().isFood()) {
                        System.out.println(handler.getStackInSlot(i));
                        if (entity instanceof PlayerEntity) {
                            PlayerEntity player = ((PlayerEntity) entity);

                            player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + handler.getStackInSlot(i).getItem().getFood().getHealing());
                            player.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + handler.getStackInSlot(i).getItem().getFood().getSaturation());
                        }

                        for(Pair<EffectInstance, Float> pair : handler.getStackInSlot(i).getItem().getFood().getEffects()) {
                            if (pair.getLeft() != null) entity.addPotionEffect(pair.getLeft());
                        }
                    }
                }

            });

        }

        return stack;
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
                        ItemStackHandler h = new ItemStackHandler(4) {
                            @Override
                            protected void onContentsChanged(int slot) {
                                stack.getOrCreateTag().put(HANDLER, this.serializeNBT());
                            }

                            @Override
                            public int getSlotLimit(int slot) {
                                return 1;
                            }
                        };
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
            for (int i = 0; i < h.getSlots(); i++) {
                if (h.insertItem(i, ingredient, false).isEmpty()) break;
            }
        });

        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int i = 0; i < h.getSlots(); i++) {
                System.out.println(h.getStackInSlot(i));
            }
        });
    }

}
