package de.mwojt.yyzsbackpackcompat.mixin.compat.exposure;

import com.yyz.yyzsbackpack.base.BackpackMenu;
import de.mwojt.yyzsbackpackcompat.util.BackpackCompatMenu;
import de.mwojt.yyzsbackpackcompat.util.BackpackSlotInjector;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "io.github.mortuusars.exposure.world.inventory.LightroomMenu")
public abstract class LightroomMenuMixin extends AbstractContainerMenu implements BackpackCompatMenu {
    protected LightroomMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void yyzsbackpackcompat$addBackpackSlots(CallbackInfo ci) {
        BackpackSlotInjector.inject(this);
        // Lightroom has a decorative lip at leftPos - 27 and mode-toggle button at leftPos - 17
        ((BackpackMenu) this).setBackpackGuiPos(-27, 0);
    }
}
