package de.mwojt.yyzsbackpackcompat.mixin.compat.accessories;

import com.yyz.yyzsbackpack.base.BackpackMenu;
import io.wispforest.accessories.client.gui.AccessoriesExperimentalScreen;
import io.wispforest.owo.ui.core.Component;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Targets AbstractContainerScreen.render with high priority, filtered by
 * instanceof AccessoriesExperimentalScreen. Uses componentsForExclusionAreas()
 * (same API JEI uses) for dynamic sizing, runs every frame.
 */
@Mixin(value = AbstractContainerScreen.class, priority = 1500)
public abstract class AccessoriesExperimentalScreenOffsetMixin {

    @Shadow protected int leftPos;

    @Inject(method = "render", at = @At("HEAD"))
    private void yyzsbackpackcompat$updateExperimentalOffset(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if (!((Object) this instanceof AccessoriesExperimentalScreen expScreen)) return;
        if (!((Object) expScreen.getMenu() instanceof BackpackMenu bpMenu)) return;
        if (!bpMenu.isBackpackVisible()) return;

        int minX = this.leftPos;
        for (Component component : expScreen.componentsForExclusionAreas().toList()) {
            if (component.x() < minX) {
                minX = component.x();
            }
        }

        if (minX < this.leftPos) {
            bpMenu.setBackpackGuiPos(-(this.leftPos - minX + 1), 0);
        }
    }
}
