package co.uk.squishling.courageous.util;

import co.uk.squishling.courageous.Courageous;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

import java.util.HashMap;

@EventBusSubscriber(modid = Reference.MOD_ID, bus = Bus.FORGE)
public class EventHandler {

    public static HashMap<RotatedPillarBlock, RotatedPillarBlock> STRIPPED_LOG_MAP = new HashMap<RotatedPillarBlock, RotatedPillarBlock>();

    @SubscribeEvent
    public void rightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        PlayerEntity player = event.getPlayer();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Hand hand = event.getHand();

        if (item instanceof AxeItem) {
            if (STRIPPED_LOG_MAP.get(block) != null) {
                Axis axis = state.get(RotatedPillarBlock.AXIS);

                world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.setBlockState(pos, STRIPPED_LOG_MAP.get(block).getDefaultState().with(RotatedPillarBlock.AXIS, axis), 2);
                stack.damageItem(1, player, (entity) -> {
                    entity.sendBreakAnimation(hand);
                });
                player.swingArm(hand);

                event.setResult(Result.ALLOW);
            }
        }
    }

    @SubscribeEvent
    public void entityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        entity.getArmorInventoryList().forEach((stack) -> {
            if (stack.getItem() == Items.DIAMOND_BOOTS) {
                Courageous.LOGGER.error("msg");
                float f5 = 2f;
                float f7 = entity.onGround ? f5 * 0.91F : 0.91F;
//                entity.moveRelative(f7, entity.getMotion());
                entity.setMotion(entity.getMotion().mul(0.999f, 0.999f, 0.999f));
//                entity.move(MoverType.SELF, entity.getMotion());
            }
        });
    }

    // Entity stuff
    private Vec3d func_213362_f(LivingEntity entity) {
        Vec3d p_213362_1_ = entity.getMotion();
        if (entity.isOnLadder()) {
            entity.fallDistance = 0.0F;
            float f = 0.15F;
            double d0 = MathHelper.clamp(p_213362_1_.x, (double)-0.15F, (double)0.15F);
            double d1 = MathHelper.clamp(p_213362_1_.z, (double)-0.15F, (double)0.15F);
            double d2 = Math.max(p_213362_1_.y, (double)-0.15F);
            if (d2 < 0.0D && entity.getBlockState().getBlock() != Blocks.SCAFFOLDING && entity.isSneaking() && entity instanceof PlayerEntity) {
                d2 = 0.0D;
            }

            p_213362_1_ = new Vec3d(d0, d2, d1);
        }

        return p_213362_1_;
    }

    private float func_213335_r(float p_213335_1_, LivingEntity entity) {
        return entity.onGround ? entity.getAIMoveSpeed() * (0.21600002F / (p_213335_1_ * p_213335_1_ * p_213335_1_)) : entity.jumpMovementFactor;
    }

}
