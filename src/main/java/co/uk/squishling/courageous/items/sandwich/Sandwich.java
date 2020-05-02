package co.uk.squishling.courageous.items.sandwich;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.items.ModItems;
import co.uk.squishling.courageous.tabs.FoodTab;
import co.uk.squishling.courageous.util.Util;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.Food.Builder;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

public class Sandwich extends ItemBase {

    private String HANDLER = "handler";

    public Sandwich() {
        super("sandwich", new Item.Properties().group(FoodTab.FOOD).food(new Builder().hunger(2).saturation(0.24f).build()).setISTER(() -> SandwichISTER::new));
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, net.minecraft.entity.player.PlayerEntity playerIn, Entity entity) {
        if (Util.isServer(playerIn.getEntityWorld())) addIngredient(stack, playerIn.getHeldItemOffhand());
        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack p_77661_1_) {
        return UseAction.EAT;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ICapabilityProvider() {
            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                    if (stack.hasTag() && stack.getTag().contains(HANDLER)) {
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

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if (Util.isServer(world)) {

            entity.playSound(SoundEvents.ENTITY_GENERIC_EAT, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
            world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);

            if (entity instanceof PlayerEntity) {

                stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                    PlayerEntity player = ((PlayerEntity) entity);

                    ArrayList<Food> foods = new ArrayList<Food>();
                    foods.add(ModItems.BREAD_SLICE.getFood());
                    foods.add(ModItems.BREAD_SLICE.getFood());

                    for (int i = 0; i < handler.getSlots(); i++) {
                        System.out.println(handler.getStackInSlot(i));
                        ItemStack handlerStack = handler.getStackInSlot(i);

                        if (handlerStack.getItem().getFood() != null) foods.add(handlerStack.getItem().getFood());
                    }
                    Food food = Util.buildCombo(foods);

                    player.getFoodStats().setFoodLevel(player.getFoodStats().getFoodLevel() + food.getHealing());
                    player.getFoodStats().setFoodSaturationLevel(player.getFoodStats().getSaturationLevel() + food.getSaturation());

                    for (Pair<EffectInstance, Float> pair : food.getEffects()) {
                        if (pair.getLeft() != null && world.rand.nextFloat() < pair.getRight()) {
                            entity.addPotionEffect(new EffectInstance(pair.getLeft()));
                        }
                    }
                });

            }

            if (!(entity instanceof PlayerEntity) || !((PlayerEntity) entity).isCreative()) stack.shrink(1);

        }

        return stack;
    }

    public void addIngredient(ItemStack stack, ItemStack ingredient) {
        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int i = 0; i < h.getSlots(); i++) {
                if (h.insertItem(i, ingredient, false).isEmpty()) break;
            }
        });
    }

}
