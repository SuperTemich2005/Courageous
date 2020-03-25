package co.uk.squishling.courageous.blocks.architects_table;

import co.uk.squishling.courageous.recipes.ArchitectTable.ArchitectsTableRecipe;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArchitectsTableScreen extends ContainerScreen<ArchitectsTableContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("textures/gui/container/stonecutter.png");
    private float sliderProgress;
    private boolean clickedOnSroll;
    private int recipeIndexOffset;
    private boolean hasItemsInInputSlot;
    private int selected;

    private HashMap<ItemStack, Pair<Integer, Integer>> ICONS = new HashMap<ItemStack, Pair<Integer, Integer>>();
    public ArrayList<Button> ABUTTONS = new ArrayList<Button>();

    public ArchitectsTableScreen(ArchitectsTableContainer container, PlayerInventory playerInventory, ITextComponent name) {
        super(container, playerInventory, name);
        container.setInventoryUpdateListener(this::onInventoryUpdate);
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        //renderButtonToolTip(mouseX, mouseY);
    }

    /*public void addItemButton(ItemStack item) {
        int xOff = (width / 2 - xSize / 2) - 115;
        int yOff = (height / 2 - ySize / 2);

        int x = xOff + 23 * (ABUTTONS.size() % 5);
        int y = yOff + 23 * (ABUTTONS.size() / 5);
        System.out.println("Item " + item.getItem().getRegistryName() + " added on position " + x + "," + y);
        ABUTTONS.add(new SquareButton(x, y, (button) -> {

        }));

        ICONS.put(item, new Pair<Integer, Integer>() {
            @Override
            public Integer setValue(Integer value) {
                return null;
            }

            @Override
            public Integer getLeft() {
                return x + 3;
            }

            @Override
            public Integer getRight() {
                return y + 3;
            }
        });
    }

    public void removeItemButton(int index) {
        ABUTTONS.remove(index);
        ICONS.remove(index);
    }*/

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(this.title.getFormattedText(), 8.0F, 4.0F, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 94), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int left = this.guiLeft;
        int top = this.guiTop;
        this.blit(left, top, 0, 0, this.xSize, this.ySize);
        int sliderY = (int)(41.0F * this.sliderProgress);
        this.blit(left + 119, top + 15 + sliderY, 176 + (this.canScroll() ? 0 : 12), 0, 12, 15);
        int outputLeft = this.guiLeft + 52;
        int outputTop = this.guiTop + 14;
        int indexOffset = this.recipeIndexOffset + 12;
        this.drawRecipesBackground(mouseX, mouseY, outputLeft, outputTop, indexOffset);
        this.drawRecipesItems(outputLeft, outputTop, indexOffset);
    }

    private void drawRecipesBackground(int mouseX, int mouseY, int left, int top, int offset) {
        for(int index = this.recipeIndexOffset; index < offset && index < ((ArchitectsTableContainer)this.container).getRecipeListSize(); ++index) {
            int indexRelative = index - this.recipeIndexOffset;
            int posX = left + indexRelative % 4 * 16;
            int posY = top + (indexRelative / 4) * 18 + 2;
            int ySize = this.ySize; //Look, I don't know what it does either, I simply gave it a name so it's a bit more readable
            if (index == ((ArchitectsTableContainer)this.container).getSelectedRecipe()) {
                ySize += 18;
            } else if (mouseX >= posX && mouseY >= posY && mouseX < posX + 16 && mouseY < posY + 18) {
                ySize += 36;
            }

            this.blit(posX, posY - 1, 0, ySize, 16, 18);
        }
    }

    private void drawRecipesItems(int left, int top, int indexOffset) {
        List<ArchitectsTableRecipe> recipeList = (this.container).getRecipeList();

        for(int index = this.recipeIndexOffset; index < indexOffset && index < ((ArchitectsTableContainer)this.container).getRecipeListSize(); ++index) {
            int indexRelative = index - this.recipeIndexOffset;
            int posX = left + indexRelative % 4 * 16;
            int posY = top + (indexRelative / 4) * 18 + 2;
            this.minecraft.getItemRenderer().renderItemAndEffectIntoGUI((recipeList.get(index)).getRecipeOutput(), posX, posY);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.clickedOnSroll = false;
        if (this.hasItemsInInputSlot) {
            int left = this.guiLeft + 52;
            int top = this.guiTop + 14;
            int indexOffset = this.recipeIndexOffset + 12;

            for(int index = this.recipeIndexOffset; index < indexOffset; ++index) {
                int indexRelative = index - this.recipeIndexOffset;
                double posX = mouseX - (double)(left + indexRelative % 4 * 16);
                double posY = mouseY - (double)(top + indexRelative / 4 * 18);
                if (posX >= 0.0D && posY >= 0.0D && posX < 16.0D && posY < 18.0D && ((ArchitectsTableContainer)this.container).enchantItem(this.minecraft.player, index)) {
                    Minecraft.getInstance().getSoundHandler().play(SimpleSound.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                    this.minecraft.playerController.sendEnchantPacket(((ArchitectsTableContainer)this.container).windowId, index);
                    return true;
                }
            }

            left = this.guiLeft + 119;
            top = this.guiTop + 9;
            if (mouseX >= (double)left && mouseX < (double)(left + 12) && mouseY >= (double)top && mouseY < (double)(top + 54)) {
                this.clickedOnSroll = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double posX, double posY, int button, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        if (this.clickedOnSroll && this.canScroll()) {
            int top = this.guiTop + 14;
            int bottom = top + 54;
            this.sliderProgress = ((float)posY - (float)top - 7.5F) / ((float)(bottom - top) - 15.0F);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)this.getHiddenRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(posX, posY, button, p_mouseDragged_6_, p_mouseDragged_8_);
        }
    }

    public boolean mouseScrolled(double posX, double posY, double scrollAmount) {
        if (this.canScroll()) {
            int extraRows = this.getHiddenRows();
            this.sliderProgress = (float)((double)this.sliderProgress - scrollAmount / (double)extraRows);
            this.sliderProgress = MathHelper.clamp(this.sliderProgress, 0.0F, 1.0F);
            this.recipeIndexOffset = (int)((double)(this.sliderProgress * (float)extraRows) + 0.5D) * 4;
        }

        return true;
    }

    private boolean canScroll() {
        return this.hasItemsInInputSlot && ((ArchitectsTableContainer)this.container).getRecipeListSize() > 12;
    }

    protected int getHiddenRows() {
        return (((ArchitectsTableContainer)this.container).getRecipeListSize() + 4 - 1) / 4 - 3;
    }

    private void onInventoryUpdate() {
        this.hasItemsInInputSlot = ((ArchitectsTableContainer)this.container).hasItemsinInputSlot();
        if (!this.hasItemsInInputSlot) {
            this.sliderProgress = 0.0F;
            this.recipeIndexOffset = 0;
        }
    }
}
