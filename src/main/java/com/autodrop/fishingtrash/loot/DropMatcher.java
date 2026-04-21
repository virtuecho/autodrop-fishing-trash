package com.autodrop.fishingtrash.loot;

import com.autodrop.fishingtrash.config.AutoDropConfig;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;

import java.util.Set;

public final class DropMatcher {
	private DropMatcher() {
	}

	public static boolean shouldDrop(ItemStack stack, AutoDropConfig config) {
		if (stack.isEmpty()) {
			return false;
		}

		for (FishingLootRule rule : FishingLootRules.DEFAULT_RULES) {
			if (config.isDefaultRuleEnabled(rule.key()) && rule.matches(stack)) {
				return true;
			}
		}

		for (FishingLootRule rule : FishingLootRules.EXTRA_RULES) {
			if (config.isExtraRuleEnabled(rule.key()) && rule.matches(stack)) {
				return true;
			}
		}

		Set<String> extraItemIds = config.getNormalizedExtraItemIds();
		String stackItemId = Registries.ITEM.getId(stack.getItem()).toString();
		return extraItemIds.contains(stackItemId);
	}
}
