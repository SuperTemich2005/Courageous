//package co.uk.squishling.courageous.blocks.pottery_wheel;
//
//import co.uk.squishling.courageous.Courageous;
//import co.uk.squishling.courageous.util.Reference;
//import com.mojang.blaze3d.platform.GlStateManager;
//import net.minecraft.client.gui.screen.inventory.ContainerScreen;
//import net.minecraft.client.gui.widget.button.Button;
//import net.minecraft.client.gui.widget.button.Button.IPressable;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.text.ITextComponent;
//
//public class PotteryWheelScreen extends ContainerScreen<PotteryWheelContainer> {
//
//    private ResourceLocation GUI = new ResourceLocation(Reference.MOD_ID, "textures/gui/pottery_wheel.png");
//    private TileEntity te;
//
//    public PotteryWheelScreen(PotteryWheelContainer container, PlayerInventory playerInventory, ITextComponent name) {
//        super(container, playerInventory, name);
//        te = container.tileEntity;
//
//    }
//
//    @Override
//    public void render(int mouseX, int mouseY, float partialTicks) {
//        super.render(mouseX, mouseY, partialTicks);
//        renderHoveredToolTip(mouseX, mouseY);
//    }
//
//
//    @Override
//    protected void init() {
//        super.init();
//    }
//
//    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
//    }
//
//    @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        renderBackground();
//        GlStateManager.color4f(1f, 1f, 1f, 1f);
//        minecraft.getTextureManager().bindTexture(GUI);
//        int relX = (width - xSize) / 2;
//        int relY = (height - ySize) / 2;
//        blit(relX, relY, 0, 0, xSize, ySize);
//    }
//
//}
