# yyz's backpack compat

A compatibility mod for [yyz's backpack](https://modrinth.com/mod/yyzs-backpack) that extends its backpack side-panel display into menus added by other mods.

yyz's backpack by default only shows the backpack panel in vanilla menus (chest, crafting table, furnace, etc.) and a few select mods (like [Applied Energistics 2](https://modrinth.com/mod/ae2)). This mod adds support for a wide range of popular modded menus so you can access your backpack from anywhere.

## How it works

The base mod's backpack panel has to be initialized for each menu. This mod does exactly that via mixins. Each mixin only activates if the related mod is installed.

For screens with variable width (like the Accessories mod's experimental equipment screen), the mod queries the host mod's own API to calculate the correct offset dynamically, so the backpack panel never overlaps with the main UI.

## Supported mods

Each integration only activates if the matching mod is present, so any combination works out of the box.

| Mod | Supported menus |
|---|---|
| [The Aether](https://modrinth.com/mod/the-aether) | Altar, Freezer, Incubator, Book of Lore |
| [Deep Aether](https://modrinth.com/mod/deep-aether) | Combiner |
| [Accessories](https://modrinth.com/mod/accessories) | Original + Experimental screens (with dynamic sizing) |
| [Armor Statues](https://modrinth.com/mod/armor-statues) | Equipment screen (only screen with player inventory) |
| [Better Archeology](https://modrinth.com/mod/better-archeology) | Fossil Inventory, Identifying |
| [CERBON's Better Beacons](https://modrinth.com/mod/cerbons-better-beacons) | New Beacon Menu |
| [Chisel Reborn](https://modrinth.com/mod/chisel-reborn) | Chisel |
| [Corpse](https://modrinth.com/mod/corpse) | Corpse Inventory, Additional Items |
| [Create](https://modrinth.com/mod/create) | Toolbox, Crafting Blueprint, Schematic Table, Schematicannon |
| [Exposure](https://modrinth.com/mod/exposure) | Lightroom |
| [Farmer's Delight](https://modrinth.com/mod/farmers-delight) | Cooking Pot |
| [Universal Sawmill](https://modrinth.com/mod/universal-sawmill) | Sawmill |
| [SecurityCraft](https://modrinth.com/mod/securitycraft) | Inventory Scanner, Briefcase |
| [Spectrum](https://modrinth.com/mod/spectrum) | Pedestal, Crafting Tablet, Fabrication Chest, Compacting Chest, Black Hole Chest, Potion Workshop, Color Picker, Cinderhearth, Filtering, Particle Spawner, 3x3 Containers |
| [Storage Drawers](https://modrinth.com/mod/storage-drawers) | All drawer containers |

Menus from mods that extend vanilla menus (Easy Anvils, Easy Magic, Visual Workbench, Trading Post, SecurityCraft keypad furnaces) are already supported by yyz's backpack itself via class inheritance, no extra compat needed.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.226+
- [yyz's backpack](https://modrinth.com/mod/yyzs-backpack) 21.1.2+

## Installation

Drop the jar into your `mods` folder alongside yyz's backpack and any of the supported mods you use.

## License

MIT — see [LICENSE](LICENSE).

## Credits

Based on [yyz's backpack](https://modrinth.com/mod/yyzs-backpack) by yyz729.
