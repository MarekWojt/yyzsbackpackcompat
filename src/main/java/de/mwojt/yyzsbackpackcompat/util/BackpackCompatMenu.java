package de.mwojt.yyzsbackpackcompat.util;

/**
 * Marker interface applied (via mixin {@code implements}) to every modded menu this mod adds
 * backpack slots to.
 *
 * <p>The per-menu {@code <init>} injection reliably runs on the <b>client</b> (the client builds
 * the menu through its own constructor). On the <b>server</b> the slot-adding constructor is often
 * a different one than the {@code <init>} injection binds to (e.g. menus whose client constructor
 * delegates via {@code this(...)} to a separate server constructor), so the server menu can end up
 * without the backpack slots. With yyzsbackpack 21.1.13's {@code initializeContents} redirect
 * (loop bound = client {@code slots.size()}), that client/server slot-count mismatch crashes with
 * an {@code IndexOutOfBoundsException}.
 *
 * <p>{@link de.mwojt.yyzsbackpackcompat.mixin.AbstractContainerMenuSyncMixin} uses this marker to
 * guarantee the server menu has the same slots before the initial content sync.
 */
public interface BackpackCompatMenu {
}
