# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

NeoForge 1.21.1 compatibility mod that extends yyz's backpack side-panel display into modded menu screens. The base yyzsbackpack mod handles vanilla menus; this mod adds Mixin injections for modded menus so the backpack panel appears there too.

## Build Commands

- **Build:** `./gradlew build`
- **Run client:** `./gradlew runClient`
- **Run server:** `./gradlew runServer`
- **Run data generators:** `./gradlew runData`

Requires Java 21.

## Architecture

### How yyzsbackpack integration works (1.21.1)

The base yyzsbackpack mod uses two key mixins on abstract classes:
- `AbstractContainerMenuMixin` — implements `BackpackMenu` interface on ALL menus (visibility, position state, shift-click sorting)
- `AbstractContainerScreenMixin` — renders the backpack panel on ALL screens

However, **backpack inventory slots must be added per-menu** via constructor injection calling `SlotManager.addBackpackInventorySlots(menu, inventory)`. The base mod only does this for vanilla menus. This compat mod adds the same injection for modded menus.

### Client/server slot symmetry (critical)

yyzsbackpack 21.1.13 reworked container content sync: it removed the old `NonNullListMixin` (which tolerated index overruns) and added a `@Redirect` in its `AbstractContainerMenuMixin` that makes `AbstractContainerMenu.initializeContents` iterate the **client** menu's `slots.size()` instead of the server-sent `items.size()`. As a result, **any** menu whose client slot count exceeds the server's crashes the client with `IndexOutOfBoundsException` when the content packet arrives.

The constructor `@Inject(<init>, RETURN)` is **not reliable on the server**: for menus whose client constructor delegates via `this(...)` to a separate server constructor (common for Fabric-style `ScreenHandler`s, e.g. Spectrum), the injection effectively only binds the client constructor, so the server menu never gets backpack slots → client (with slots) > server (without) → crash.

To guarantee symmetry, every backpack-adding menu mixin **must implement the `BackpackCompatMenu` marker interface**. `AbstractContainerMenuSyncMixin` injects into `AbstractContainerMenu.sendAllDataToRemote()` (the server's initial full sync, run before the content packet) and calls `BackpackSlotInjector.inject(this)` for any `BackpackCompatMenu`, so the server always has the same slots as the client. Injection is idempotent, so this is harmless for menus whose constructor injection already added them.

### Adding support for a new menu

1. Find the menu class (extends `AbstractContainerMenu`) and verify it adds player inventory slots
2. Create a mixin in `mixin/compat/<modid>/` using `@Mixin(targets = "fully.qualified.ClassName")`
3. Make the mixin `implements BackpackCompatMenu` (the server-sync marker — required, see above)
4. Inject into `<init>` at `RETURN`, call `BackpackSlotInjector.inject(this)` (handles the client)
5. Register the mixin in `yyzsbackpackcompat.mixins.json` as `compat.<modid>.MixinName`
6. Add the mod as optional dependency in `neoforge.mods.toml` (with `versionRange`)

No manual plugin mapping is needed — `CompatMixinPlugin` auto-extracts the mod ID from the mixin's package name (first segment after `mixin.compat.`).

`BackpackSlotInjector.inject()` is safe to call multiple times — it checks for existing `BackpackStorageSlot` instances before adding.

Menus that extend vanilla menus (e.g. `AnvilMenu`, `AbstractFurnaceMenu`, `CraftingMenu`) typically don't need a compat mixin because the base mod's injection on the vanilla parent already fires via `super()`.

### Screen-side offset adjustment

For menus whose screen has a wider footprint than the standard 176px inventory (e.g. Accessories' equipment screens), the backpack panel would overlap with side elements. The pattern is:

- Target `AbstractContainerScreen` directly with high priority (`priority = 1500`) so the offset update runs **before** yyzsbackpack reads it
- Filter by `instanceof SomeScreen` to only run on the target screen
- Call `BackpackMenu.setBackpackGuiPos(xOffset, yOffset)` each render, computed from the mod's own API (e.g. `AccessoriesScreen.getPanelWidth()`, `AccessoriesExperimentalScreen.componentsForExclusionAreas()`)
- Register under `client` in `yyzsbackpackcompat.mixins.json`

For static offsets (fixed side elements like Exposure Lightroom's lip, or unusual `imageHeight` like Spectrum's Compacting Chest), `setBackpackGuiPos` is called once in the menu constructor mixin.

### Key files

- `util/BackpackSlotInjector.java` — safe (idempotent) slot injection helper (finds Inventory from existing slots). Lives outside the `mixin` package because Mixin does not allow non-mixin classes in a package it owns.
- `util/BackpackCompatMenu.java` — marker interface implemented by every backpack-adding menu mixin; drives the server-side sync hook (see "Client/server slot symmetry")
- `mixin/AbstractContainerMenuSyncMixin.java` — server-side safety net; adds backpack slots to `BackpackCompatMenu` menus in `sendAllDataToRemote()` before the initial content sync
- `mixin/CompatMixinPlugin.java` — conditionally applies mixins based on loaded mods (auto-derives mod ID from package name); always applies non-`compat.<modid>` mixins like `AbstractContainerMenuSyncMixin`
- `mixin/compat/<modid>/*.java` — one subfolder per mod, one mixin per modded menu target
- `yyzsbackpackcompat.mixins.json` — mixin registration with plugin reference; server mixins under `mixins`, client-only screen mixins under `client`

### Dependencies

- **yyzsbackpack** (required) — via Modrinth Maven (`maven.modrinth:yyzs-backpack`)
- **accessories** + **owo-lib** (optional, compileOnly) — needed to reference their public API for dynamic offset calculation (owo-lib pulled with `transitive = false` to avoid resolving all its deps)
- Other target mods are optional runtime dependencies; their classes are referenced via `@Mixin(targets = "...")` strings, so they don't need to be on the compile classpath
