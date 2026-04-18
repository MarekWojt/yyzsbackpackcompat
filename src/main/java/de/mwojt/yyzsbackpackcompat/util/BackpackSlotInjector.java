package de.mwojt.yyzsbackpackcompat.util;

import com.yyz.yyzsbackpack.base.BackpackMenu;
import com.yyz.yyzsbackpack.base.BackpackStorageSlot;
import com.yyz.yyzsbackpack.util.SlotManager;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;

public final class BackpackSlotInjector {
    private BackpackSlotInjector() {}

    /**
     * Adds backpack slots to a menu by finding the player Inventory from already-added slots.
     * Safe to call at the end of any menu constructor that adds player inventory slots.
     */
    public static void inject(AbstractContainerMenu menu) {
        // Don't add twice
        for (Slot slot : menu.slots) {
            if (slot instanceof BackpackStorageSlot) return;
        }
        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory inv) {
                SlotManager.addBackpackInventorySlots(menu, inv);
                ((BackpackMenu) menu).setBackpackVisible(true);
                return;
            }
        }
    }
}
