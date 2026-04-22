# AutoDrop Fishing Trash

AutoDrop Fishing Trash is a Fabric 1.21.11 client mod that drops selected fishing loot from the 27 main inventory slots. It does not touch the hotbar.

## What It Drops By Default

The default list is based on Java Edition fishing loot:

- Fish: `minecraft:cod`, `minecraft:salmon`, `minecraft:pufferfish`, `minecraft:tropical_fish`
- Treasure: `minecraft:bow`, `minecraft:enchanted_book`, `minecraft:fishing_rod`, `minecraft:name_tag`, `minecraft:nautilus_shell`, `minecraft:saddle`
- Junk: `minecraft:lily_pad`, `minecraft:bone`, `minecraft:bowl`, `minecraft:leather`, `minecraft:leather_boots`, `minecraft:rotten_flesh`, `minecraft:potion` as a water bottle, `minecraft:tripwire_hook`, `minecraft:stick`, `minecraft:string`, `minecraft:ink_sac`
- Jungle junk: `minecraft:bamboo`

Each default item can be turned on or off in Mod Menu. The `minecraft:potion` default rule matches only water bottles, so normal potions are not dropped by that default rule.

## Extra Items

The Extra Items category includes toggleable presets for all wooden, stone, iron, golden, diamond, and netherite tools, plus player armor sets made from leather, chainmail, iron, golden, diamond, and netherite. Turtle helmets are included as armor too.

You can also keep adding custom item IDs manually.

## Settings

Open Minecraft's Mods screen with Mod Menu, select AutoDrop Fishing Trash, then open the config screen.

- Enabled: master switch.
- Only While Fishing: runs while holding a fishing rod or while a bobber is cast.
- Pause In Screens: avoids dropping while inventory, chests, or config screens are open. This is off by default.
- Scan Interval: how often the inventory is checked, in ticks. 20 ticks equals 1 second.
- Drop Direction: keeps vanilla current-view dropping, or temporarily changes the server-side horizontal drop direction.
- Rotated Drop Interval: minimum ticks between rotated drop batches when Drop Direction is not Current View.
- Default Drop List: grouped into Fish, Treasure, Junk, and Jungle Junk. Enchanted books and fishing rods are off by default.
- Extra Items: grouped by material. Diamond and netherite presets are off by default.
- Extra Item IDs: add custom item IDs, such as `minecraft:kelp`. For vanilla items, `kelp` is also accepted and becomes `minecraft:kelp`.

The config file is written to `.minecraft/config/autodrop-fishing-trash.json`.

## Build Requirements

- Minecraft Java Edition 1.21.11
- Fabric Loader 0.19.2 or newer
- Fabric API 0.141.3+1.21.11
- Mod Menu 17.0.0
- Cloth Config 21.11.153
- JDK 21 is recommended for Minecraft 1.21.11 development

## How To Compile

1. Install JDK 21, for example Eclipse Temurin 21.
2. In IntelliJ IDEA, open this folder as a Gradle project.
3. Set the Gradle JVM to JDK 21: Settings -> Build, Execution, Deployment -> Build Tools -> Gradle -> Gradle JVM.
4. Refresh Gradle in IntelliJ.
5. Build from the terminal:

```sh
./gradlew build
```

On Windows, use:

```bat
gradlew.bat build
```

The compiled mod jar will be in `build/libs/`. Put the AutoDrop jar, Fabric API, Mod Menu, and Cloth Config into your `.minecraft/mods` folder for a Fabric 1.21.11 installation.
