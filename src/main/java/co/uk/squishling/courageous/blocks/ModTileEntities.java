package co.uk.squishling.courageous.blocks;

import co.uk.squishling.courageous.blocks.architects_table.ArchitectsTableTileEntity;
import co.uk.squishling.courageous.blocks.cutting_board.CuttingBoardTileEntity;
import co.uk.squishling.courageous.blocks.pottery_wheel.PotteryWheelTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;

import java.util.ArrayList;

public class ModTileEntities {

    public static final ArrayList<TileEntityType> TILE_ENTITIES = new ArrayList<TileEntityType>();

    public static final TileEntityType POTTERY_WHEEL = CustomTileEntityType.create(PotteryWheelTileEntity::new, "pottery_wheel", (Block) ModBlocks.POTTERY_WHEEL);
    public static final TileEntityType CUTTING_BOARD = CustomTileEntityType.create(CuttingBoardTileEntity::new, "cutting_board", (Block) ModBlocks.CUTTING_BOARD);
    public static final TileEntityType ARCHITECTS_TABLE = CustomTileEntityType.create(ArchitectsTableTileEntity::new, "architects_table", (Block) ModBlocks.ARCHITECTS_TABLE);

}
