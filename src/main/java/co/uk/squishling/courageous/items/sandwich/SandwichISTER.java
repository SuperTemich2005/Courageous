package co.uk.squishling.courageous.items.sandwich;

import co.uk.squishling.courageous.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class SandwichISTER extends ItemStackTileEntityRenderer {

    @Override
    public void render(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(stack, matrix, bufferIn, combinedLightIn, combinedOverlayIn);
        matrix.push();

        matrix.translate(0.5f, 0.5f, 0.5f);
        matrix.rotate(Vector3f.XP.rotationDegrees(90));
        matrix.rotate(Vector3f.YN.rotationDegrees(90));

        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.BREAD_SLICE), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn);

        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
            for (int i = 0; i < handler.getSlots(); i++) {

                if (!handler.getStackInSlot(i).isEmpty()) {
                    matrix.translate(0f, 0f, 0.06f);
                    Minecraft.getInstance().getItemRenderer().renderItem(handler.getStackInSlot(i), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn);
                }

            }
        });

        matrix.translate(0f, 0f, 0.06f);
        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.BREAD_SLICE), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn);

        matrix.pop();
    }

}
