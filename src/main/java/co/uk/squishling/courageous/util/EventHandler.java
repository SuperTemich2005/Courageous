package co.uk.squishling.courageous.util;

import co.uk.squishling.courageous.Courageous;
import co.uk.squishling.courageous.blocks.ModBlocks;
import co.uk.squishling.courageous.blocks.planter_box.PlanterBoxTileEntity;
import net.minecraft.block.*;
//import net.minecraft.client.gui.screen.MainMenuScreen;
//import net.minecraft.client.gui.screen.MainMenuScreen;
//import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
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
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.HashMap;

@EventBusSubscriber(modid = Util.MOD_ID, bus = Bus.FORGE)
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

        if (block.getBlock() == Blocks.COMPOSTER && item instanceof BlockItem)
        {
            if (state.get(ComposterBlock.LEVEL) == 0)
            {
                world.setBlockState(pos, ModBlocks.PLANTER_BOX.getDefaultState(), 2);
                if (world.getTileEntity(pos) instanceof PlanterBoxTileEntity)
                {
                    ((PlanterBoxTileEntity)world.getTileEntity(pos)).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
                        if (handler.getStackInSlot(0).isEmpty())
                            handler.insertItem(0, new ItemStack(stack.getItem(), 1), false);
                    });
                }
                stack.shrink(1);
                event.setCanceled(true);
            }
        }
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
