package com.autodrop.fishingtrash.loot;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;

public record FishingLootRule(
	String key,
	String itemId,
	String nameTranslationKey,
	String subcategoryTranslationKey,
	boolean defaultEnabled,
	boolean waterBottleOnly
) {
	public boolean matches(ItemStack stack) {
		if (waterBottleOnly) {
			return isWaterBottle(stack);
		}

		return itemId.equals(Registries.ITEM.getId(stack.getItem()).toString());
	}

	private static boolean isWaterBottle(ItemStack stack) {
		if (!stack.isOf(Items.POTION)) {
			return false;
		}

		PotionContentsComponent contents = stack.get(DataComponentTypes.POTION_CONTENTS);
		return contents != null && contents.matches(Potions.WATER) && contents.customEffects().isEmpty();
	}
}
