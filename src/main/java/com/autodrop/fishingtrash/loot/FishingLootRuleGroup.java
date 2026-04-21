package com.autodrop.fishingtrash.loot;

public enum FishingLootRuleGroup {
	FISH("group.autodrop_fishing_trash.fish"),
	TREASURE("group.autodrop_fishing_trash.treasure"),
	JUNK("group.autodrop_fishing_trash.junk"),
	JUNGLE_JUNK("group.autodrop_fishing_trash.jungle_junk"),
	TOOL("group.autodrop_fishing_trash.tool"),
	ARMOR("group.autodrop_fishing_trash.armor");

	private final String translationKey;

	FishingLootRuleGroup(String translationKey) {
		this.translationKey = translationKey;
	}

	public String translationKey() {
		return translationKey;
	}
}
