package com.autodrop.fishingtrash.config;

import com.autodrop.fishingtrash.loot.FishingLootRule;
import com.autodrop.fishingtrash.loot.FishingLootRules;
import com.autodrop.fishingtrash.util.ItemIdUtil;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
			.setDefaultValue(false)
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.ignore_open_screens"))
			.setSaveConsumer(value -> config.pauseInScreens = value)
			.build());

		general.addEntry(entries.startIntField(Text.translatable("option.autodrop_fishing_trash.scan_interval_ticks"), config.scanIntervalTicks)
			.setDefaultValue(5)
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.scan_interval_ticks"))
			.setSaveConsumer(value -> config.scanIntervalTicks = Math.max(1, Math.min(200, value)))
			.build());

		general.addEntry(entries.startEnumSelector(Text.translatable("option.autodrop_fishing_trash.drop_direction"), DropDirection.class, config.dropDirection)
			.setDefaultValue(DropDirection.CURRENT_VIEW)
			.setEnumNameProvider(value -> Text.translatable(((DropDirection) value).translationKey()))
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.drop_direction"))
			.setSaveConsumer(value -> config.dropDirection = value)
			.build());

		general.addEntry(entries.startIntField(Text.translatable("option.autodrop_fishing_trash.rotated_drop_interval_ticks"), config.rotatedDropIntervalTicks)
			.setDefaultValue(5)
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.rotated_drop_interval_ticks"))
			.setSaveConsumer(value -> config.rotatedDropIntervalTicks = Math.max(1, Math.min(200, value)))
			.build());
	}

	private static void addDefaultLootCategory(ConfigBuilder builder, ConfigEntryBuilder entries, AutoDropConfig config) {
		ConfigCategory defaultLoot = builder.getOrCreateCategory(Text.translatable("category.autodrop_fishing_trash.default_loot"));

		addRuleSubcategories(defaultLoot, entries, FishingLootRules.DEFAULT_RULES, config, RuleSet.DEFAULT);
	}

	private static void addExtraItemsCategory(ConfigBuilder builder, ConfigEntryBuilder entries, AutoDropConfig config) {
		ConfigCategory extraItems = builder.getOrCreateCategory(Text.translatable("category.autodrop_fishing_trash.extra_items"));

		addRuleSubcategories(extraItems, entries, FishingLootRules.EXTRA_RULES, config, RuleSet.EXTRA);

		extraItems.addEntry(entries.startStrList(Text.translatable("option.autodrop_fishing_trash.extra_item_ids"), new ArrayList<>(config.extraItemIds))
			.setDefaultValue(new ArrayList<>())
			.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.extra_item_ids"))
			.setSaveConsumer(value -> config.extraItemIds = ItemIdUtil.normalizeItemIds(value))
			.build());
	}

	private static void addRuleSubcategories(ConfigCategory category, ConfigEntryBuilder entries, List<FishingLootRule> rules, AutoDropConfig config, RuleSet ruleSet) {
		for (String subcategoryKey : orderedSubcategoryKeys(rules)) {
			SubCategoryBuilder subcategory = entries.startSubCategory(Text.translatable(subcategoryKey)).setExpanded(false);

			for (FishingLootRule rule : rules) {
				if (rule.subcategoryTranslationKey().equals(subcategoryKey)) {
					subcategory.add(entries.startBooleanToggle(labelFor(rule), ruleSet.isEnabled(config, rule))
						.setDefaultValue(rule.defaultEnabled())
						.setTooltip(Text.translatable("tooltip.autodrop_fishing_trash.default_rule", rule.itemId()))
						.setSaveConsumer(value -> ruleSet.setEnabled(config, rule, value))
						.build());
				}
			}

			category.addEntry(subcategory.build());
		}
	}

	private static Set<String> orderedSubcategoryKeys(List<FishingLootRule> rules) {
		Set<String> keys = new LinkedHashSet<>();

		for (FishingLootRule rule : rules) {
			keys.add(rule.subcategoryTranslationKey());
		}

		return keys;
	}

	private static Text labelFor(FishingLootRule rule) {
		return Text.translatable(rule.nameTranslationKey());
	}

	private enum RuleSet {
		DEFAULT {
			@Override
			boolean isEnabled(AutoDropConfig config, FishingLootRule rule) {
				return config.isDefaultRuleEnabled(rule);
			}

			@Override
			void setEnabled(AutoDropConfig config, FishingLootRule rule, boolean enabled) {
				config.setDefaultRuleEnabled(rule, enabled);
			}
		},
		EXTRA {
			@Override
			boolean isEnabled(AutoDropConfig config, FishingLootRule rule) {
				return config.isExtraRuleEnabled(rule);
			}

			@Override
			void setEnabled(AutoDropConfig config, FishingLootRule rule, boolean enabled) {
				config.setExtraRuleEnabled(rule, enabled);
			}
		};

		abstract boolean isEnabled(AutoDropConfig config, FishingLootRule rule);

		abstract void setEnabled(AutoDropConfig config, FishingLootRule rule, boolean enabled);
	}
}
