package com.paneedah.weaponlib.inventory;

import com.paneedah.mwc.capabilities.EquipmentCapability;
import com.paneedah.mwc.equipment.inventory.EquipmentInventory;
import com.paneedah.mwc.items.equipment.carryable.ItemBackpack;
import com.paneedah.mwc.network.messages.OpenCustomPlayerInventoryGuiMessage;
import micdoodle8.mods.galacticraft.api.client.tabs.AbstractTab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;

import static com.paneedah.mwc.MWC.CHANNEL;

public class BackpackTab extends AbstractTab {

    protected static ItemStack getBackpackStackInSlot() {
        EquipmentInventory customInventory = EquipmentCapability.getInventory(FMLClientHandler.instance().getClientPlayerEntity());
        return customInventory != null ? customInventory.getStackInSlot(0) : null;
    }

    public BackpackTab() {
        super(0, 0, 0, getBackpackStackInSlot());
    }

    @Override
    public void onTabClicked() {
        CHANNEL.sendToServer(new OpenCustomPlayerInventoryGuiMessage(GuiHandler.STORAGE_ITEM_INVENTORY_GUI_ID));
    }

    @Override
    public boolean shouldAddToList() {
        final ItemStack stackInSlot = getBackpackStackInSlot();
        return stackInSlot != null && stackInSlot.getItem() instanceof ItemBackpack;
    }
}
