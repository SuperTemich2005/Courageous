package co.uk.squishling.courageous.items.pottery;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.tabs.PotteryTab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.server.command.TextComponentHelper;
import org.lwjgl.system.CallbackI.S;

import javax.annotation.Nullable;
import java.util.List;

public class Amphora extends ItemBase {

    private static final int buckets = 3;

    public Amphora(String name) {
        super(name, PotteryTab.POTTERY);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag() && !ForgeRegistries.FLUIDS.getValue(new ResourceLocation(stack.getTag().getString("fluid"))).getDefaultState().isEmpty()) {
            String text = TextFormatting.GOLD + new TranslationTextComponent( ForgeRegistries.FLUIDS.getValue(new ResourceLocation(stack.getTag().getString("fluid")))
                    .getDefaultState().getBlockState().getBlock().getTranslationKey()).getFormattedText() + TextFormatting.RESET
                    + " x " + stack.getTag().getInt("buckets");
            tooltip.add(new StringTextComponent(text));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn) {
        System.out.println("A");
        ItemStack stack = player.getHeldItem(handIn);

        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
            stack.getTag().putInt("buckets", 0);
            stack.getTag().putString("fluid", Fluids.EMPTY.getRegistryName().toString());
        }

        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(stack.getTag().getString("fluid")));
        if (fluid == null) return new ActionResult<>(ActionResultType.PASS, stack);
        // fluid == Fluids.EMPTY ?
        // : RayTraceContext.FluidMode.NONE
        RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != Type.BLOCK) return new ActionResult<>(ActionResultType.PASS, stack);
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
        BlockPos pos = blockraytraceresult.getPos();

        if (!world.getFluidState(pos).isEmpty()) {
            if (stack.getTag().getInt("buckets") >= buckets) return new ActionResult<>(ActionResultType.PASS, stack);
            if (world.getBlockState(pos).getBlock() != fluid.getDefaultState().getBlockState().getBlock() && !fluid.getDefaultState().isEmpty()) return new ActionResult<>(ActionResultType.PASS, stack);

            if (Reference.isServer(world)) {
                stack.getTag().putString("fluid", world.getFluidState(pos).getFluid().getRegistryName().toString());
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                stack.getTag().putInt("buckets", stack.getTag().getInt("buckets") + 1);
            }

            SoundEvent soundevent = fluid.getAttributes().getEmptySound();
            if(soundevent == null) soundevent = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_FILL_LAVA : SoundEvents.ITEM_BUCKET_FILL;
            player.playSound(soundevent, 1.0F, 1.0F);

            player.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
            if (stack.getTag().getInt("buckets") <= 0) return new ActionResult<>(ActionResultType.PASS, stack);
            BlockPos pos1 = pos.offset(blockraytraceresult.getFace());
            if (world.getBlockState(pos1).getBlock() == Blocks.AIR) {
                if (Reference.isServer(world)) {
                    world.setBlockState(pos1, fluid.getDefaultState().getBlockState());
                    stack.getTag().putInt("buckets", stack.getTag().getInt("buckets") - 1);

                    if (stack.getTag().getInt("buckets") <= 0) stack.getTag().putString("fluid", Fluids.EMPTY.getRegistryName().toString());
                }

                SoundEvent soundevent = fluid.getAttributes().getEmptySound();
                if(soundevent == null) soundevent = fluid.isIn(FluidTags.LAVA) ? SoundEvents.ITEM_BUCKET_EMPTY_LAVA : SoundEvents.ITEM_BUCKET_EMPTY;
                player.playSound(soundevent, 1.0F, 1.0F);

                player.addStat(Stats.ITEM_USED.get(this));
                return new ActionResult<>(ActionResultType.SUCCESS, stack);
            }
        }

        return new ActionResult<>(ActionResultType.PASS, stack);
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (!stack.hasTag()) return 0f;
        return 1f - (double) stack.getTag().getInt("buckets") / (double) buckets;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTag();
        //  && !ForgeRegistries.FLUIDS.getValue(new ResourceLocation(stack.getTag().getString("fluid"))).getDefaultState().isEmpty()
    }
}
