package co.uk.squishling.courageous.blocks.architects_table;

import co.uk.squishling.courageous.blocks.BlockBase;
import co.uk.squishling.courageous.blocks.IBlock;
import co.uk.squishling.courageous.tabs.PotteryTab;
import co.uk.squishling.courageous.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class ArchitectsTable extends BlockBase implements IBlock {

    public ArchitectsTable() {
        super("architects_table", Properties.create(Material.WOOD).hardnessAndResistance(0.3f));
    }

    @Override
    public int getHarvestLevel(BlockState state) {
        return 1;
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.AXE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        if (Util.isServer(world)) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((i, inventory, player2) ->
                    new ArchitectsTableContainer(i, world, pos, inventory),
                            new TranslationTextComponent("block.courageous.architects_table")), pos);
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public ItemGroup getTab() {
        return PotteryTab.POTTERY;
    }

}
