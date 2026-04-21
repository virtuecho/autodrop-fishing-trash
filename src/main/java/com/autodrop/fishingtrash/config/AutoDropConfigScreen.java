package com.autodrop.fishingtrash.config;

import com.autodrop.fishingtrash.loot.FishingLootRule;
import com.autodrop.fishingtrash.loot.FishingLootRules;
import com.autodrop.fishingtrash.util.ItemIdUtil;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;

public final class AutoDropConfigScreen {
	private AutoDropConfigScreen() {
	}

	public static Screen create(Screen parent) {
		AutoDropConfig config = AutoDropConfig.get();
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(Text.translatable("title.autodrop_fishing_trash.config"));
		ConfigEntryBuilder entries = builder.entryBuilder();

		addGeneralCategory(builder, entries, config);
		addDefaultLootCategory(builder, entries, config);
		addExtraItemsCategory(builder, entries, config);

		builder.setSavingRunnable(config::save);
		return builder.build();
	}

	private static void addGeneralCategory(ConfigBuilder builder, ConfigEntryBuilder entries, AutoDropConfig config) {
		ConfigCategory general = builder.getOrCreateCategory(Text.translatable("category.autodrop_fishing_trash.general"));

		general.addEntry(entries.startBooleanToggle(Text.translatable("option.autodrop_fishing_trash.enabled"), config.enabled)
			.setDefaultValue(true)
			.setSaveConsumer(value -> config.enabled = value)
			.build());

		general.addEntry(entries.startBooleanToggle(Text.translatable("option.autodrop_fishing_trash.only_while_fishing"), config.onlyWhileFishing)
			.setDefaultValue(true)
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.only_while_fishing"))
			.setSaveConsumer(value -> config.onlyWhileFishing = value)
			.build());

		general.addEntry(entries.startBooleanToggle(Text.translatable("option.autodrop_fishing_trash.ignore_open_screens"), config.pauseInScreens)
			.setDefaultValue(true)
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.ignore_open_screens"))
			.setSaveConsumer(value -> config.pauseInScreens = value)
			.build());

		general.addEntry(entries.startIntField(Text.translatable("option.autodrop_fishing_trash.scan_interval_ticks"), config.scanIntervalTicks)
			.setDefaultValue(5)
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.scan_interval_ticks"))
			.setSaveConsumer(value -> config.scanIntervalTicks = Math.max(1, Math.min(200, value)))
			.build());
	}

	private static void addDefaultLootCategory(ConfigBuilder builder, ConfigEntryBuilder entries, AutoDropConfig config) {
		ConfigCategory defaultLoot = builder.getOrCreateCategory(Text.translatable("category.autodrop_fishing_trash.default_loot"));

		for (FishingLootRule rule : FishingLootRules.DEFAULT_RULES) {
			defaultLoot.addEntry(entries.startBooleanToggle(labelFor(rule), config.isDefaultRuleEnabled(rule.key()))
				.setDefaultValue(true)
				.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.default_rule", rule.itemId()))
				.setSaveConsumer(value -> config.setDefaultRuleEnabled(rule.key(), value))
				.build());
		}
	}

	private static void addExtraItemsCategory(ConfigBuilder builder, ConfigEntryBuilder entries, AutoDropConfig config) {
		ConfigCategory extraItems = builder.getOrCreateCategory(Text.translatable("category.autodrop_fishing_trash.extra_items"));

		for (FishingLootRule rule : FishingLootRules.EXTRA_RULES) {
			extraItems.addEntry(entries.startBooleanToggle(labelFor(rule), config.isExtraRuleEnabled(rule.key()))
				.setDefaultValue(true)
				.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.default_rule", rule.itemId()))
				.setSaveConsumer(value -> config.setExtraRuleEnabled(rule.key(), value))
				.build());
		}

		extraItems.addEntry(entries.startStrList(Text.translatable("option.autodrop_fishing_trash.extra_item_ids"), new ArrayList<>(config.extraItemIds))
			.setDefaultValue(new ArrayList<>())
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.extra_item_ids"))
			.setSaveConsumer(value -> config.extraItemIds = ItemIdUtil.normalizeItemIds(value))
			.build());
	}

	private static Text labelFor(FishingLootRule rule) {
		return Text.empty()
			.append(Text.translatable(rule.nameTranslationKey()))
			.append(Text.literal(" - "))
			.append(Text.translatable(rule.group().translationKey()));
	}
}
