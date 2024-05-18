package com.paneedah.mwc.equipment.inventory;

import com.paneedah.weaponlib.inventory.BackpackTab;
import com.paneedah.weaponlib.inventory.CustomInventoryTab;
import com.paneedah.weaponlib.inventory.CustomPlayerInventoryTab;
import com.paneedah.weaponlib.inventory.InventoryTabs;
import micdoodle8.mods.galacticraft.api.client.tabs.TabRegistry;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

import static com.paneedah.mwc.proxies.ClientProxy.MC;
import static com.paneedah.mwc.utils.ModReference.ID;

public class GuiEquipment extends InventoryEffectRenderer {

    private static final ResourceLocation BACKGROUND = new ResourceLocation(ID, "textures/gui/inventory/custom_inventory.png");

    private static GuiEquipment currentGuiContainer;

    public GuiEquipment(EntityPlayer player, InventoryPlayer inventoryPlayer, EquipmentInventory inventoryCustom) {
        super(new EquipmentContainer(player, inventoryPlayer, inventoryCustom));
        allowUserInput = true;
    }
    
    @Override
    public void initGui() {
        buttonList.clear();

        super.initGui();

        guiLeft = (width - xSize) / 2;

        TabRegistry.updateTabValues(this.guiLeft, this.guiTop, CustomInventoryTab.class);
        TabRegistry.addTabsToList(this.buttonList);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	drawDefaultBackground();

        super.drawScreen(mouseX, mouseY, partialTicks);

        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1);
        MC.getTextureManager().bindTexture(BACKGROUND);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        GuiInventory.drawEntityOnScreen(guiLeft + 51, guiTop + 75, 30, (guiLeft + 51) - mouseX, (guiTop + 25) - mouseY, MC.player);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        currentGuiContainer = this;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        currentGuiContainer = this;
        super.mouseReleased(mouseX, mouseY, state);
    }

    protected static GuiEquipment getClickedGuiContainer() {
        return currentGuiContainer;
    }
}
