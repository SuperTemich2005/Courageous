package co.uk.squishling.courageous.util.networking;

import co.uk.squishling.courageous.blocks.architects_table.ArchitectsTableContainer;
import co.uk.squishling.courageous.blocks.architects_table.ArchitectsTableScreen;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelContainer;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class PacketSlotChanged implements IPacket {

    private final ItemStack stack;

    public PacketSlotChanged(ItemStack stack) {
        this.stack = stack;
    }

    public PacketSlotChanged(PacketBuffer buffer) {
        stack = buffer.readItemStack();
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeItemStack(stack);
    }

    @Override
    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            if (!(Minecraft.getInstance().currentScreen instanceof ArchitectsTableScreen)) return;
            ArchitectsTableScreen screen = (ArchitectsTableScreen) Minecraft.getInstance().currentScreen;

            for (int i = 0; i < screen.ABUTTONS.size(); i++) {
                screen.removeItemButton(i);
            }

            if(ArchitectsTableContainer.ARCITECTS_LIST.containsKey(stack.getItem())) {
                for (ItemStack itemStack : ArchitectsTableContainer.ARCITECTS_LIST.get(stack.getItem())) {
                    screen.addItemButton(itemStack);
                }
            }

            context.get().setPacketHandled(true);
        });
    }

}
