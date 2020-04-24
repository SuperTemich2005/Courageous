package co.uk.squishling.courageous.items.sandwich;

import co.uk.squishling.courageous.items.ModItems;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.CapabilityItemHandler;

public class SandwichISTER extends ItemStackTileEntityRenderer {

    private int x = 0;

    @Override
    public void render(ItemStack stack, MatrixStack matrix, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(stack, matrix, bufferIn, combinedLightIn, combinedOverlayIn);
        matrix.push();

        matrix.translate(1f, 1f, 0.8f);

        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.BREAD_SLICE), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn);
        matrix.scale(0.8f, 0.8f, 1f);

        stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            for (int i = 0; i < h.getSlots(); i++) {

                if (!h.getStackInSlot(i).isEmpty()) {
                    x++;
                    if (x >= 200) {
                        System.out.println(h.getStackInSlot(i).getItem().getRegistryName());
                        x = 0;
                    }
                    matrix.translate(0f, 0f, 0.06f);
                    Minecraft.getInstance().getItemRenderer().renderItem(h.getStackInSlot(i), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn);
                }
            }
        });

        matrix.translate(0f, 0f, 0.06f);
        matrix.scale(1.2f, 1.2f, 1f);
        Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.BREAD_SLICE), TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrix, bufferIn);

        matrix.pop();
    }

}
