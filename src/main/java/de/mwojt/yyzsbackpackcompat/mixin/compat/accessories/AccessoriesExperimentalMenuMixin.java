package de.mwojt.yyzsbackpackcompat.mixin.compat.accessories;

import de.mwojt.yyzsbackpackcompat.util.BackpackSlotInjector;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "io.wispforest.accessories.menu.variants.AccessoriesExperimentalMenu")
public abstract class AccessoriesExperimentalMenuMixin extends AbstractContainerMenu {
    protected AccessoriesExperimentalMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void yyzsbackpackcompat$addBackpackSlots(CallbackInfo ci) {
        BackpackSlotInjector.inject(this);
        // Offset is handled by AccessoriesExperimentalScreenOffsetMixin using componentsForExclusionAreas()
    }
}
