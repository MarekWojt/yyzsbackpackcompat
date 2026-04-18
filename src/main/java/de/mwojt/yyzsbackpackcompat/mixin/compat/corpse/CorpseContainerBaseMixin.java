package de.mwojt.yyzsbackpackcompat.mixin.compat.corpse;

import de.mwojt.yyzsbackpackcompat.util.BackpackSlotInjector;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "de.maxhenkel.corpse.gui.CorpseContainerBase")
public abstract class CorpseContainerBaseMixin extends AbstractContainerMenu {
    protected CorpseContainerBaseMixin(@Nullable MenuType<?> menuType, int i) {
        super(menuType, i);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void yyzsbackpackcompat$addBackpackSlots(CallbackInfo ci) {
        BackpackSlotInjector.inject(this);
    }
}
