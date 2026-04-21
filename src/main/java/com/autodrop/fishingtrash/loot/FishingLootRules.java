package com.autodrop.fishingtrash.loot;

import java.util.List;

public final class FishingLootRules {
	public static final List<FishingLootRule> DEFAULT_RULES = List.of(
		rule("minecraft:cod", FishingLootRuleGroup.FISH),
		rule("minecraft:salmon", FishingLootRuleGroup.FISH),
		rule("minecraft:pufferfish", FishingLootRuleGroup.FISH),
		rule("minecraft:tropical_fish", FishingLootRuleGroup.FISH),

		rule("minecraft:bow", FishingLootRuleGroup.TREASURE),
		rule("minecraft:enchanted_book", FishingLootRuleGroup.TREASURE),
		rule("minecraft:fishing_rod", FishingLootRuleGroup.TREASURE),
		rule("minecraft:name_tag", FishingLootRuleGroup.TREASURE),
		rule("minecraft:nautilus_shell", FishingLootRuleGroup.TREASURE),
		rule("minecraft:saddle", FishingLootRuleGroup.TREASURE),

		rule("minecraft:lily_pad", FishingLootRuleGroup.JUNK),
		rule("minecraft:bone", FishingLootRuleGroup.JUNK),
		rule("minecraft:bowl", FishingLootRuleGroup.JUNK),
		rule("minecraft:leather", FishingLootRuleGroup.JUNK),
		rule("minecraft:leather_boots", FishingLootRuleGroup.JUNK),
		rule("minecraft:rotten_flesh", FishingLootRuleGroup.JUNK),
		waterBottleRule(),
		rule("minecraft:tripwire_hook", FishingLootRuleGroup.JUNK),
		rule("minecraft:stick", FishingLootRuleGroup.JUNK),
		rule("minecraft:string", FishingLootRuleGroup.JUNK),
		rule("minecraft:ink_sac", FishingLootRuleGroup.JUNK),
		rule("minecraft:bamboo", FishingLootRuleGroup.JUNGLE_JUNK)
	);

	public static final List<FishingLootRule> EXTRA_RULES = List.of(
		rule("minecraft:wooden_sword", FishingLootRuleGroup.TOOL),
		rule("minecraft:wooden_pickaxe", FishingLootRuleGroup.TOOL),
		rule("minecraft:wooden_axe", FishingLootRuleGroup.TOOL),
		rule("minecraft:wooden_shovel", FishingLootRuleGroup.TOOL),
		rule("minecraft:wooden_hoe", FishingLootRuleGroup.TOOL),
		rule("minecraft:stone_sword", FishingLootRuleGroup.TOOL),
		rule("minecraft:stone_pickaxe", FishingLootRuleGroup.TOOL),
		rule("minecraft:stone_axe", FishingLootRuleGroup.TOOL),
		rule("minecraft:stone_shovel", FishingLootRuleGroup.TOOL),
		rule("minecraft:stone_hoe", FishingLootRuleGroup.TOOL),
		rule("minecraft:iron_sword", FishingLootRuleGroup.TOOL),
		rule("minecraft:iron_pickaxe", FishingLootRuleGroup.TOOL),
		rule("minecraft:iron_axe", FishingLootRuleGroup.TOOL),
		rule("minecraft:iron_shovel", FishingLootRuleGroup.TOOL),
		rule("minecraft:iron_hoe", FishingLootRuleGroup.TOOL),
		rule("minecraft:golden_sword", FishingLootRuleGroup.TOOL),
		rule("minecraft:golden_pickaxe", FishingLootRuleGroup.TOOL),
		rule("minecraft:golden_axe", FishingLootRuleGroup.TOOL),
		rule("minecraft:golden_shovel", FishingLootRuleGroup.TOOL),
		rule("minecraft:golden_hoe", FishingLootRuleGroup.TOOL),
		rule("minecraft:diamond_sword", FishingLootRuleGroup.TOOL),
		rule("minecraft:diamond_pickaxe", FishingLootRuleGroup.TOOL),
		rule("minecraft:diamond_axe", FishingLootRuleGroup.TOOL),
		rule("minecraft:diamond_shovel", FishingLootRuleGroup.TOOL),
		rule("minecraft:diamond_hoe", FishingLootRuleGroup.TOOL),
		rule("minecraft:netherite_sword", FishingLootRuleGroup.TOOL),
		rule("minecraft:netherite_pickaxe", FishingLootRuleGroup.TOOL),
		rule("minecraft:netherite_axe", FishingLootRuleGroup.TOOL),
		rule("minecraft:netherite_shovel", FishingLootRuleGroup.TOOL),
		rule("minecraft:netherite_hoe", FishingLootRuleGroup.TOOL),

		rule("minecraft:leather_helmet", FishingLootRuleGroup.ARMOR),
		rule("minecraft:leather_chestplate", FishingLootRuleGroup.ARMOR),
		rule("minecraft:leather_leggings", FishingLootRuleGroup.ARMOR),
		rule("minecraft:leather_boots", FishingLootRuleGroup.ARMOR),
		rule("minecraft:chainmail_helmet", FishingLootRuleGroup.ARMOR),
		rule("minecraft:chainmail_chestplate", FishingLootRuleGroup.ARMOR),
		rule("minecraft:chainmail_leggings", FishingLootRuleGroup.ARMOR),
		rule("minecraft:chainmail_boots", FishingLootRuleGroup.ARMOR),
		rule("minecraft:iron_helmet", FishingLootRuleGroup.ARMOR),
		rule("minecraft:iron_chestplate", FishingLootRuleGroup.ARMOR),
		rule("minecraft:iron_leggings", FishingLootRuleGroup.ARMOR),
		rule("minecraft:iron_boots", FishingLootRuleGroup.ARMOR),
		rule("minecraft:golden_helmet", FishingLootRuleGroup.ARMOR),
		rule("minecraft:golden_chestplate", FishingLootRuleGroup.ARMOR),
		rule("minecraft:golden_leggings", FishingLootRuleGroup.ARMOR),
		rule("minecraft:golden_boots", FishingLootRuleGroup.ARMOR),
		rule("minecraft:diamond_helmet", FishingLootRuleGroup.ARMOR),
		rule("minecraft:diamond_chestplate", FishingLootRuleGroup.ARMOR),
		rule("minecraft:diamond_leggings", FishingLootRuleGroup.ARMOR),
		rule("minecraft:diamond_boots", FishingLootRuleGroup.ARMOR),
		rule("minecraft:netherite_helmet", FishingLootRuleGroup.ARMOR),
		rule("minecraft:netherite_chestplate", FishingLootRuleGroup.ARMOR),
		rule("minecraft:netherite_leggings", FishingLootRuleGroup.ARMOR),
		rule("minecraft:netherite_boots", FishingLootRuleGroup.ARMOR),
		rule("minecraft:turtle_helmet", FishingLootRuleGroup.ARMOR)
	);

	private FishingLootRules() {
	}

	private static FishingLootRule rule(String itemId, FishingLootRuleGroup group) {
		return new FishingLootRule(itemId, itemId, translationKey(itemId), group, false);
	}

	private static FishingLootRule waterBottleRule() {
		return new FishingLootRule("minecraft:potion", "minecraft:potion", "rule.autodrop_fishing_trash.water_bottle", FishingLootRuleGroup.JUNK, true);
	}

	private static String translationKey(String itemId) {
		return "rule.autodrop_fishing_trash." + itemId.replace(':', '.');
	}
}
