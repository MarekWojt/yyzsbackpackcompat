package de.mwojt.yyzsbackpackcompat.mixin.compat.accessories;

import com.yyz.yyzsbackpack.base.BackpackMenu;
import io.wispforest.accessories.client.gui.AccessoriesScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Targets AbstractContainerScreen.render, runs with high priority so it
 * executes BEFORE yyzsbackpack's injections at the same method, ensuring
 * the backpack offset is up-to-date before the panel is rendered.
 * Filtered by instanceof AccessoriesScreen.
 */
@Mixin(value = AbstractContainerScreen.class, priority = 1500)
public abstract class AccessoriesScreenOffsetMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void yyzsbackpackcompat$updateAccessoriesOffset(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!((Object) this instanceof AccessoriesScreen accScreen)) return;
        if (!((Object) accScreen.getMenu() instanceof BackpackMenu bpMenu)) return;
        if (!bpMenu.isBackpackVisible()) return;
        bpMenu.setBackpackGuiPos(-(accScreen.getPanelWidth() + 18), 0);
    }
}
