package co.uk.squishling.courageous.tiles.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityRenderHelper {
    public static void DrawFluidPlane(IVertexBuilder builder, MatrixStack matrixStack, Fluid fluid, World world, BlockPos pos, Float xMin, Float zMin, Float xMax, Float zMax, Float height, int light, int overlay) {
        //Get the sprite of the fluid
        TextureAtlasSprite tex = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(fluid.getAttributes().getStillTexture(world, pos));
        //Figure out the color & light
        int color = fluid.getAttributes().getColor();
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color >> 0) & 0xFF) / 255f;
        int fluidLight = fluid.getAttributes().getLuminosity() * 16;
        if ((light & 0xFF) < fluidLight) {
            light &= 0xFF00;
            light += fluidLight;
        }
        //light = Math.max(fluid.getAttributes().getLuminosity(), light) * 16; //Luminosity is in light levels. By multiplying by 16 we get a 0-256 level. Also, figure out whether the fluid itself gives off more light than the outside light, and if so, use that.
        //int skylight = world.getLightFor(LightType.SKY, pos) * 16; //Same as above, but then for the sky light

        Matrix4f posMatrix = matrixStack.getLast().getMatrix();
        builder //Bottom right corner
                .pos(posMatrix, xMin, height, zMax)
                .color(r, g, b, a)
                .tex(tex.getMinU(), tex.getMaxV())
                .lightmap(light)
                .normal(0, 1, 0)
                .overlay(overlay)
                .endVertex();
        builder //Top right corner
                .pos(posMatrix, xMax, height, zMax)
                .color(r, g, b, a)
                .tex(tex.getMaxU(), tex.getMaxV())
                .lightmap(light)
                .normal(0, 1, 0)
                .overlay(overlay)
                .endVertex();
        builder //Top left corner
                .pos(posMatrix, xMax, height, zMin)
                .color(r, g, b, a)
                .tex(tex.getMaxU(), tex.getMinV())
                .lightmap(light)
                .normal(0, 1, 0)
                .overlay(overlay)
                .endVertex();
        builder //Bottom left corner
                .pos(posMatrix, xMin, height, zMin)
                .color(r, g, b, a)
                .tex(tex.getMinU(), tex.getMinV())
                .lightmap(light)
                .normal(0, 1, 0)
                .overlay(overlay)
                .endVertex();
    }
}
