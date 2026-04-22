package com.autodrop.fishingtrash.loot;

import java.util.List;

public final class FishingLootRules {
	public static final List<FishingLootRule> DEFAULT_RULES = List.of(
		defaultRule("minecraft:cod", FishingLootRuleGroup.FISH),
		defaultRule("minecraft:salmon", FishingLootRuleGroup.FISH),
		defaultRule("minecraft:pufferfish", FishingLootRuleGroup.FISH),
		defaultRule("minecraft:tropical_fish", FishingLootRuleGroup.FISH),

		defaultRule("minecraft:bow", FishingLootRuleGroup.TREASURE),
		defaultRuleDisabled("minecraft:enchanted_book", FishingLootRuleGroup.TREASURE),
		defaultRuleDisabled("minecraft:fishing_rod", FishingLootRuleGroup.TREASURE),
		defaultRule("minecraft:name_tag", FishingLootRuleGroup.TREASURE),
		defaultRule("minecraft:nautilus_shell", FishingLootRuleGroup.TREASURE),
		defaultRule("minecraft:saddle", FishingLootRuleGroup.TREASURE),

		defaultRule("minecraft:lily_pad", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:bone", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:bowl", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:leather", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:leather_boots", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:rotten_flesh", FishingLootRuleGroup.JUNK),
		waterBottleRule(),
		defaultRule("minecraft:tripwire_hook", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:stick", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:string", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:ink_sac", FishingLootRuleGroup.JUNK),
		defaultRule("minecraft:bamboo", FishingLootRuleGroup.JUNGLE_JUNK)
	);

	public static final List<FishingLootRule> EXTRA_RULES = List.of(
		extraRule("minecraft:wooden_sword", ExtraItemMaterial.WOODEN),
		extraRule("minecraft:wooden_pickaxe", ExtraItemMaterial.WOODEN),
		extraRule("minecraft:wooden_axe", ExtraItemMaterial.WOODEN),
		extraRule("minecraft:wooden_shovel", ExtraItemMaterial.WOODEN),
		extraRule("minecraft:wooden_hoe", ExtraItemMaterial.WOODEN),
		extraRule("minecraft:stone_sword", ExtraItemMaterial.STONE),
		extraRule("minecraft:stone_pickaxe", ExtraItemMaterial.STONE),
		extraRule("minecraft:stone_axe", ExtraItemMaterial.STONE),
		extraRule("minecraft:stone_shovel", ExtraItemMaterial.STONE),
		extraRule("minecraft:stone_hoe", ExtraItemMaterial.STONE),
		extraRule("minecraft:iron_sword", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_pickaxe", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_axe", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_shovel", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_hoe", ExtraItemMaterial.IRON),
		extraRule("minecraft:golden_sword", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_pickaxe", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_axe", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_shovel", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_hoe", ExtraItemMaterial.GOLDEN),
		extraRuleDisabled("minecraft:diamond_sword", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_pickaxe", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_axe", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_shovel", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_hoe", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:netherite_sword", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_pickaxe", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_axe", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_shovel", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_hoe", ExtraItemMaterial.NETHERITE),

		extraRule("minecraft:leather_helmet", ExtraItemMaterial.LEATHER),
		extraRule("minecraft:leather_chestplate", ExtraItemMaterial.LEATHER),
		extraRule("minecraft:leather_leggings", ExtraItemMaterial.LEATHER),
		extraRule("minecraft:leather_boots", ExtraItemMaterial.LEATHER),
		extraRule("minecraft:chainmail_helmet", ExtraItemMaterial.CHAINMAIL),
		extraRule("minecraft:chainmail_chestplate", ExtraItemMaterial.CHAINMAIL),
		extraRule("minecraft:chainmail_leggings", ExtraItemMaterial.CHAINMAIL),
		extraRule("minecraft:chainmail_boots", ExtraItemMaterial.CHAINMAIL),
		extraRule("minecraft:iron_helmet", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_chestplate", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_leggings", ExtraItemMaterial.IRON),
		extraRule("minecraft:iron_boots", ExtraItemMaterial.IRON),
		extraRule("minecraft:golden_helmet", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_chestplate", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_leggings", ExtraItemMaterial.GOLDEN),
		extraRule("minecraft:golden_boots", ExtraItemMaterial.GOLDEN),
		extraRuleDisabled("minecraft:diamond_helmet", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_chestplate", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_leggings", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:diamond_boots", ExtraItemMaterial.DIAMOND),
		extraRuleDisabled("minecraft:netherite_helmet", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_chestplate", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_leggings", ExtraItemMaterial.NETHERITE),
		extraRuleDisabled("minecraft:netherite_boots", ExtraItemMaterial.NETHERITE),
		extraRule("minecraft:turtle_helmet", ExtraItemMaterial.TURTLE)
	);

	private FishingLootRules() {
	}

	private static FishingLootRule defaultRule(String itemId, FishingLootRuleGroup group) {
		return defaultRule(itemId, group, true);
	}

	private static FishingLootRule defaultRuleDisabled(String itemId, FishingLootRuleGroup group) {
		return defaultRule(itemId, group, false);
	}

	private static FishingLootRule defaultRule(String itemId, FishingLootRuleGroup group, boolean defaultEnabled) {
		return new FishingLootRule(itemId, itemId, translationKey(itemId), group.translationKey(), defaultEnabled, false);
	}

	private static FishingLootRule extraRule(String itemId, ExtraItemMaterial material) {
		return extraRule(itemId, material, true);
	}

	private static FishingLootRule extraRuleDisabled(String itemId, ExtraItemMaterial material) {
		return extraRule(itemId, material, false);
	}

	private static FishingLootRule extraRule(String itemId, ExtraItemMaterial material, boolean defaultEnabled) {
		return new FishingLootRule(itemId, itemId, translationKey(itemId), material.translationKey(), defaultEnabled, false);
	}

	private static FishingLootRule waterBottleRule() {
		return new FishingLootRule("minecraft:potion", "minecraft:potion", "rule.autodrop_fishing_trash.water_bottle", FishingLootRuleGroup.JUNK.translationKey(), true, true);
	}

	private static String translationKey(String itemId) {
		return "rule.autodrop_fishing_trash." + itemId.replace(':', '.');
	}

	private enum ExtraItemMaterial {
		WOODEN("subcategory.autodrop_fishing_trash.wooden"),
		STONE("subcategory.autodrop_fishing_trash.stone"),
		LEATHER("subcategory.autodrop_fishing_trash.leather"),
		CHAINMAIL("subcategory.autodrop_fishing_trash.chainmail"),
		IRON("subcategory.autodrop_fishing_trash.iron"),
		GOLDEN("subcategory.autodrop_fishing_trash.golden"),
		DIAMOND("subcategory.autodrop_fishing_trash.diamond"),
		NETHERITE("subcategory.autodrop_fishing_trash.netherite"),
		TURTLE("subcategory.autodrop_fishing_trash.turtle");

		private final String translationKey;

		ExtraItemMaterial(String translationKey) {
			this.translationKey = translationKey;
		}

		private String translationKey() {
			return translationKey;
		}
	}
}
