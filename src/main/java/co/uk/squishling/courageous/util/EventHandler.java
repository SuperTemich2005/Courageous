package co.uk.squishling.courageous.util;

import co.uk.squishling.courageous.Courageous;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
//import net.minecraft.client.gui.screen.MainMenuScreen;
//import net.minecraft.client.gui.screen.MainMenuScreen;
//import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
//import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
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
                float f5 = 0.2f;
                float f7 = entity.onGround ? f5 * 0.91F : 0.91F;
                entity.moveRelative(entity.onGround ? entity.getAIMoveSpeed() * (0.21600002F / (f5 * f5 * f5)) : entity.jumpMovementFactor, entity.getMotion());
                entity.move(MoverType.SELF, entity.getMotion());
                Vec3d vec3d5 = entity.getMotion();

                double d10 = vec3d5.y;

                entity.setMotion(vec3d5.x * (double)f7, d10 * (double)0.98F, vec3d5.z * (double)f7);
            }
//            if (stack.getItem() == Items.DIAMOND_BOOTS) {
//                Vec3d vec3d = entity.getMotion();
//                if (Math.sqrt(Math.pow(vec3d.x, 2) + Math.pow(vec3d.z, 2)) > 0.05d && entity.onGround) {
//                    double d0 = 0.91f;
//                    entity.setMotion(new Vec3d(vec3d.x * d0, vec3d.y, vec3d.z * d0));
//                }
//            }
        });
    }

    //Panorama Replacement
    //Remove the code here to prevent it switching every time you switch back to the main menu
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onMenuOpenEvent(GuiOpenEvent event) {
        if (event.getGui() instanceof MainMenuScreen) {
            Courageous.trySetRandomPanorama();
        }
    }


}
