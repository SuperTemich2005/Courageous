package co.uk.squishling.courageous.tiles.renderers;

import co.uk.squishling.courageous.tiles.TileFaucet;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraftforge.fluids.FluidStack;

public class FaucetRenderer extends TileEntityRenderer<TileFaucet> {
    private static final Float PX = 1 / 16F; //Constant how big one pixel is

    public FaucetRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(TileFaucet tileFaucet, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        FluidStack fluidStack = tileFaucet.getFluidInTank(0);
        if (!fluidStack.isEmpty()) {
            matrixStack.push();

            RenderType renderType = RenderType.getTranslucent();
            IVertexBuilder builder = iRenderTypeBuffer.getBuffer(renderType);
            Fluid fluid = fluidStack.getFluid(); //Find out what fluid is in the tank

            AxisAlignedBB box = tileFaucet.getBlockState().getBlock().getShape(tileFaucet.getBlockState(), tileFaucet.getWorld(), tileFaucet.getPos(), ISelectionContext.dummy()).getBoundingBox();
            Float percentFull = (float) fluidStack.getAmount() / tileFaucet.getTankCapacity(0); //Find out how full the tank is now
            Float layerHeight = (float) (((box.maxY - 1.1 * PX) - (box.minY + PX)) * percentFull + box.minY + PX);

            TileEntityRenderHelper.DrawFluidPlane(builder, matrixStack, fluid, tileFaucet.getWorld(), tileFaucet.getPos(), (float) box.minX, (float) box.minZ, (float) box.maxX, (float) box.maxZ, layerHeight, i1);

            matrixStack.pop();
        }
    }

}
