package du.squishling.courageous.util;

import du.squishling.courageous.Courageous;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

}
