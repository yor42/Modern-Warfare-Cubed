package com.paneedah.weaponlib.inventory;

import com.paneedah.mwc.init.MWCItems;
import com.paneedah.mwc.network.messages.OpenCustomPlayerInventoryGuiMessage;
import micdoodle8.mods.galacticraft.api.client.tabs.AbstractTab;
import net.minecraft.item.ItemStack;

import static com.paneedah.mwc.MWC.CHANNEL;

public class CustomInventoryTab extends AbstractTab {
    public CustomInventoryTab() {
        super(0, 0, 0, new ItemStack(MWCItems.vestRender));
    }

    @Override
    public void onTabClicked() {
        CHANNEL.sendToServer(new OpenCustomPlayerInventoryGuiMessage(GuiHandler.CUSTOM_PLAYER_INVENTORY_GUI_ID));
    }

    @Override
    public boolean shouldAddToList() {
        return true;
    }
}
