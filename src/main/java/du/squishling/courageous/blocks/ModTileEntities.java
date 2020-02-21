package du.squishling.courageous.blocks;

import du.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ModTileEntities {

    public static final ArrayList<TileEntityType> TILE_ENTITIES = new ArrayList<TileEntityType>();

    public static final TileEntityType POTTERY_WHEEL = CustomTileEntityType.create(PotteryWheelTileEntity::new, "pottery_wheel", ModBlocks.POTTERY_WHEEL);

}
