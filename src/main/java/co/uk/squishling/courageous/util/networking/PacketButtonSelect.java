package co.uk.squishling.courageous.util.networking;

import co.uk.squishling.courageous.blocks.IHasButton;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class PacketButtonSelect implements IPacket {

    private final int index;
    private final BlockPos pos;

    public PacketButtonSelect(int index, BlockPos pos) {
        this.index = index;
        this.pos = pos;
    }

    public PacketButtonSelect(PacketBuffer buffer) {
        index = buffer.readInt();
        pos = buffer.readBlockPos();
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
        buffer.writeInt(index);
        buffer.writeBlockPos(pos);
    }

    @Override
    public void handle(Supplier<Context> context) {
        context.get().enqueueWork(() -> {
            World world = context.get().getSender().world;
            if (!(world.getTileEntity(pos) instanceof IHasButton)) { context.get().setPacketHandled(true); return; }
            IHasButton te = (IHasButton) world.getTileEntity(pos);

            te.select(index);
            context.get().setPacketHandled(true);
        });
    }

}
