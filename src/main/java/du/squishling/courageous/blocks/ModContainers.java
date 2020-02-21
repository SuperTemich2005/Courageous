package du.squishling.courageous.blocks;

import du.squishling.courageous.Courageous;
import du.squishling.courageous.blocks.pottery_wheel.PotteryWheelContainer;
import du.squishling.courageous.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;

import java.util.ArrayList;

public class ModContainers {

    public static final ArrayList<Container> CONTAINERS = new ArrayList<Container>();

    public static final ContainerType POTTERY_WHEEL_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
        BlockPos pos = data.readBlockPos();
        Container container = new PotteryWheelContainer(windowId, Minecraft.getInstance().world, pos, inv);
        CONTAINERS.add(container);
        return container;
    }).setRegistryName(Reference.MOD_ID, "pottery_wheel");

}
