package com.autodrop.fishingtrash.config;

import com.autodrop.fishingtrash.AutoDropFishingTrash;
import com.autodrop.fishingtrash.loot.FishingLootRule;
import com.autodrop.fishingtrash.util.ItemIdUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class AutoDropConfig {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("autodrop-fishing-trash.json");
	private static AutoDropConfig instance;

	public boolean enabled = true;
	public boolean onlyWhileFishing = true;
	public boolean pauseInScreens = false;
	public int scanIntervalTicks = 5;
	public DropDirection dropDirection = DropDirection.CURRENT_VIEW;
	public int rotatedDropIntervalTicks = 5;
	public Set<String> disabledDefaultRules = new LinkedHashSet<>();
	public Set<String> enabledDefaultRules = new LinkedHashSet<>();
	public Set<String> disabledExtraRules = new LinkedHashSet<>();
	public Set<String> enabledExtraRules = new LinkedHashSet<>();
	public List<String> extraItemIds = new ArrayList<>();

	public static AutoDropConfig get() {
		if (instance == null) {
			load();
		}

		return instance;
	}

	public static void load() {
		if (Files.exists(CONFIG_PATH)) {
			try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
				instance = GSON.fromJson(reader, AutoDropConfig.class);
			} catch (IOException exception) {
				AutoDropFishingTrash.LOGGER.warn("Failed to read config; using defaults", exception);
				instance = new AutoDropConfig();
			}
		} else {
			instance = new AutoDropConfig();
			instance.save();
		}

		if (instance == null) {
			instance = new AutoDropConfig();
		}

		instance.normalize();
	}

	public void save() {
		normalize();

		try {
			Files.createDirectories(CONFIG_PATH.getParent());

			try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
				GSON.toJson(this, writer);
			}
		} catch (IOException exception) {
			AutoDropFishingTrash.LOGGER.warn("Failed to save config", exception);
		}
	}

	public boolean isDefaultRuleEnabled(FishingLootRule rule) {
		return isRuleEnabled(rule, enabledDefaultRules, disabledDefaultRules);
	}

	public void setDefaultRuleEnabled(FishingLootRule rule, boolean enabled) {
		setRuleEnabled(rule, enabled, enabledDefaultRules, disabledDefaultRules);
	}

	public boolean isExtraRuleEnabled(FishingLootRule rule) {
		return isRuleEnabled(rule, enabledExtraRules, disabledExtraRules);
	}

	public void setExtraRuleEnabled(FishingLootRule rule, boolean enabled) {
		setRuleEnabled(rule, enabled, enabledExtraRules, disabledExtraRules);
	}

	public Set<String> getNormalizedExtraItemIds() {
		return new LinkedHashSet<>(ItemIdUtil.normalizeItemIds(extraItemIds));
	}

	private void normalize() {
		if (disabledDefaultRules == null) {
			disabledDefaultRules = new LinkedHashSet<>();
		}

		if (enabledDefaultRules == null) {
			enabledDefaultRules = new LinkedHashSet<>();
		}

		if (disabledExtraRules == null) {
			disabledExtraRules = new LinkedHashSet<>();
		}

		if (enabledExtraRules == null) {
			enabledExtraRules = new LinkedHashSet<>();
		}

		if (extraItemIds == null) {
			extraItemIds = new ArrayList<>();
		}

		if (dropDirection == null) {
			dropDirection = DropDirection.CURRENT_VIEW;
		}

		scanIntervalTicks = Math.max(1, Math.min(200, scanIntervalTicks));
		rotatedDropIntervalTicks = Math.max(1, Math.min(200, rotatedDropIntervalTicks));
		extraItemIds = ItemIdUtil.normalizeItemIds(extraItemIds);
	}

	private static boolean isRuleEnabled(FishingLootRule rule, Set<String> enabledRules, Set<String> disabledRules) {
		if (rule.defaultEnabled()) {
			return !disabledRules.contains(rule.key());
		}

		return enabledRules.contains(rule.key());
	}

	private static void setRuleEnabled(FishingLootRule rule, boolean enabled, Set<String> enabledRules, Set<String> disabledRules) {
		if (rule.defaultEnabled()) {
			enabledRules.remove(rule.key());

			if (enabled) {
				disabledRules.remove(rule.key());
			} else {
				disabledRules.add(rule.key());
			}
		} else {
			disabledRules.remove(rule.key());

			if (enabled) {
				enabledRules.add(rule.key());
			} else {
				enabledRules.remove(rule.key());
			}
		}
	}
}
