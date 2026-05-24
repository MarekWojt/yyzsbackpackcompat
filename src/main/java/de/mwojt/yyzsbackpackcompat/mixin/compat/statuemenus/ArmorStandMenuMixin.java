package de.mwojt.yyzsbackpackcompat.mixin.compat.statuemenus;

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

@Mixin(targets = "fuzs.statuemenus.api.v1.world.inventory.ArmorStandMenu")
public abstract class ArmorStandMenuMixin extends AbstractContainerMenu implements BackpackCompatMenu {
    protected ArmorStandMenuMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void yyzsbackpackcompat$addBackpackSlots(CallbackInfo ci) {
        BackpackSlotInjector.inject(this);
        // Tabs stick out 32px to the left of leftPos, where the panel would render
        ((BackpackMenu) this).setBackpackGuiPos(-32, 0);
    }
}
