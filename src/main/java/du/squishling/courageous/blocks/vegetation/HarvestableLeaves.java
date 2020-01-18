package du.squishling.courageous.blocks.vegetation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class HarvestableLeaves extends CustomLeaves implements IGrowable {

    public static final BooleanProperty GROWN = BooleanProperty.create("grown");
    private static Item item;

    private static int min = 1;
    private static int max = 2;

    public HarvestableLeaves(String name, Item[] drops, Item item) {
        super(name, drops);
        this.setDefaultState(this.stateContainer.getBaseState().with(GROWN, false));
        this.item = item;
    }

    public HarvestableLeaves(String name, Item item) {
        this(name, new Item[0], item);
    }

    public boolean ticksRandomly(BlockState state) {
        return !state.get(GROWN);
    }

    public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random) {
        super.randomTick(state, worldIn, pos, random);

        if (!state.get(GROWN) && random.nextInt(5) == 0 && worldIn.getLightSubtracted(pos.up(), 0) >= 9) {
            worldIn.setBlockState(pos, state.with(GROWN, true), 2);
        }

    }

    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (state.get(GROWN)) {
            System.out.println(item.getRegistryName());
            spawnAsEntity(worldIn, pos, new ItemStack(item, worldIn.getRandom().nextInt(max - min) + min));
            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_SWEET_BERRIES_PICK_FROM_BUSH, SoundCategory.BLOCKS, 1.0F, 0.8F + worldIn.rand.nextFloat() * 0.4F);
            worldIn.setBlockState(pos, state.with(GROWN, false), 2);
            return true;
        } else {
            return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(GROWN);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return !blockState.get(GROWN);
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(World world, Random random, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(blockPos, blockState.with(GROWN, true), 2);
    }

    public HarvestableLeaves setMinMax(int min, int max) {
        this.min = min;
        this.max = max;

        return this;
    }

}
