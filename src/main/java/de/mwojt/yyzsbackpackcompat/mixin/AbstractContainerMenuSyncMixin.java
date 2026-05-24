package de.mwojt.yyzsbackpackcompat.mixin;

import de.mwojt.yyzsbackpackcompat.util.BackpackCompatMenu;
import de.mwojt.yyzsbackpackcompat.util.BackpackSlotInjector;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Server-side safety net guaranteeing that menus marked with {@link BackpackCompatMenu} actually
 * carry their backpack slots before the initial content sync.
 *
 * <p>{@code sendAllDataToRemote()} is the server's initial full sync of a menu to the client
 * (driven by the synchronizer set in {@code ServerPlayer#initMenu}). It runs after the menu is
 * fully constructed but before the {@code ClientboundContainerSetContentPacket} is sent, so adding
 * the backpack slots at HEAD makes the server's slot count match the client's. Injection is
 * idempotent (see {@link BackpackSlotInjector#inject}), so menus whose constructor injection
 * already added the slots are left untouched.
 */
@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuSyncMixin {

    @Inject(method = "sendAllDataToRemote", at = @At("HEAD"))
    private void yyzsbackpackcompat$ensureBackpackSlots(CallbackInfo ci) {
        if (this instanceof BackpackCompatMenu) {
            BackpackSlotInjector.inject((AbstractContainerMenu) (Object) this);
        }
    }
}
