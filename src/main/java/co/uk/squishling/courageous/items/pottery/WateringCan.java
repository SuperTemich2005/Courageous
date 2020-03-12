package co.uk.squishling.courageous.items.pottery;

import co.uk.squishling.courageous.items.ItemBase;
import co.uk.squishling.courageous.tabs.PotteryTab;
import co.uk.squishling.courageous.util.Reference;
import net.minecraft.block.Blocks;
import net.minecraft.client.particle.WaterWakeParticle;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class WateringCan extends ItemBase {

    private static final int mb = 1000;

    public WateringCan(String name) {
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
        ItemStack stack = player.getHeldItem(handIn);

        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
            stack.getTag().putInt("mb", 0);
        }

        RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        if (raytraceresult.getType() != Type.BLOCK) return new ActionResult<>(ActionResultType.PASS, stack);
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) raytraceresult;
        BlockPos pos = blockraytraceresult.getPos();

        if (!world.getFluidState(pos).isEmpty() && player.isSneaking()) {
            if (stack.getTag().getInt("mb") >= 1000) return new ActionResult<>(ActionResultType.PASS, stack);
            if (world.getBlockState(pos).getBlock() != Blocks.WATER) return new ActionResult<>(ActionResultType.PASS, stack);

            if (Reference.isServer(world)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                stack.getTag().putInt("mb", mb);
            }

            player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);

            player.addStat(Stats.ITEM_USED.get(this));
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        } else {
//            if (stack.getTag().getInt("mb") <= 0) return new ActionResult<>(ActionResultType.PASS, stack);
            world.addParticle(ParticleTypes.FALLING_WATER, player.posX, player.posY, player.posZ, 5f, 0f, 0f);
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
        return 1f - (double) stack.getTag().getInt("mb") / (double) mb;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return stack.hasTag();
    }

}
