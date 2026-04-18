package de.mwojt.yyzsbackpackcompat.mixin.compat.spectrum;

import com.yyz.yyzsbackpack.base.BackpackMenu;
import de.mwojt.yyzsbackpackcompat.util.BackpackSlotInjector;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "de.dafuqs.spectrum.inventories.CompactingChestScreenHandler")
public abstract class SpectrumCompactingChestMixin extends AbstractContainerMenu {
    protected SpectrumCompactingChestMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void yyzsbackpackcompat$addBackpackSlots(CallbackInfo ci) {
        BackpackSlotInjector.inject(this);
        // imageHeight = 178 (non-standard) causes a 1px vertical misalignment
        ((BackpackMenu) this).setBackpackGuiPos(0, -2);
    }
}
