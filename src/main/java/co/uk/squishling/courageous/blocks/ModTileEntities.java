package co.uk.squishling.courageous.blocks;

import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.ArrayList;

public class ModTileEntities {

    public static final ArrayList<TileEntityType> TILE_ENTITIES = new ArrayList<TileEntityType>();

    public static final TileEntityType POTTERY_WHEEL = CustomTileEntityType.create(PotteryWheelTileEntity::new, "pottery_wheel", ModBlocks.POTTERY_WHEEL);

}
