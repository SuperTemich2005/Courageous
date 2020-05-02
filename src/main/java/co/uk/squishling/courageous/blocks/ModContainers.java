package co.uk.squishling.courageous.blocks;

import co.uk.squishling.courageous.blocks.architects_table.ArchitectsTableContainer;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelContainer;
import co.uk.squishling.courageous.util.Util;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;

import java.util.ArrayList;

public class ModContainers {

    public static final ArrayList<Container> CONTAINERS = new ArrayList<Container>();
    public static final ArrayList<ContainerType> CONTAINER_TYPES = new ArrayList<ContainerType>();


    public static final ContainerType POTTERY_WHEEL_CONTAINER = IForgeContainerType.create((windowId, inv, data) ->
            new PotteryWheelContainer(windowId, inv.player.world, data.readBlockPos(), inv)).setRegistryName(Util.MOD_ID, "pottery_wheel");

    public static final ContainerType ARCHITECTS_TABLE_CONTAINER = IForgeContainerType.create((windowId, inv, data) ->
            //new ArchitectsTableContainer(windowId, inv.player.world, data.readBlockPos(), inv)).setRegistryName(Reference.MOD_ID, "architects_table");
            new ArchitectsTableContainer(windowId, inv.player.world, data.readBlockPos(), inv)).setRegistryName(Util.MOD_ID, "pottery_wheela");

}
