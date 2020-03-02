package co.uk.squishling.courageous.blocks;

import co.uk.squishling.courageous.util.Reference;
//import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;

import java.util.ArrayList;

public class ModContainers {

    public static final ArrayList<Container> CONTAINERS = new ArrayList<Container>();
    public static final ArrayList<ContainerType> CONTAINER_TYPES = new ArrayList<ContainerType>();


//    public static final ContainerType POTTERY_WHEEL_CONTAINER = IForgeContainerType.create((windowId, inv, data) -> {
//        BlockPos pos = data.readBlockPos();
//        Container container = new PotteryWheelContainer(windowId, Minecraft.getInstance().world, pos, inv);
//        CONTAINERS.add(container);
//        return container;
//    }).setRegistryName(Reference.MOD_ID, "pottery_wheel");
//
//    public ModContainers() {
//        CONTAINER_TYPES.add(POTTERY_WHEEL_CONTAINER);
//    }

}
