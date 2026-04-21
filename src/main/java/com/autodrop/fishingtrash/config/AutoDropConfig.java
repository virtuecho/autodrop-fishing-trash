package com.autodrop.fishingtrash.config;

import com.autodrop.fishingtrash.AutoDropFishingTrash;
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
	public boolean pauseInScreens = true;
	public int scanIntervalTicks = 5;
	public Set<String> disabledDefaultRules = new LinkedHashSet<>();
	public Set<String> disabledExtraRules = new LinkedHashSet<>();
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

	public boolean isDefaultRuleEnabled(String ruleKey) {
		return !disabledDefaultRules.contains(ruleKey);
	}

	public void setDefaultRuleEnabled(String ruleKey, boolean enabled) {
		if (enabled) {
			disabledDefaultRules.remove(ruleKey);
		} else {
			disabledDefaultRules.add(ruleKey);
		}
	}

	public boolean isExtraRuleEnabled(String ruleKey) {
		return !disabledExtraRules.contains(ruleKey);
	}

	public void setExtraRuleEnabled(String ruleKey, boolean enabled) {
		if (enabled) {
			disabledExtraRules.remove(ruleKey);
		} else {
			disabledExtraRules.add(ruleKey);
		}
	}

	public Set<String> getNormalizedExtraItemIds() {
		return new LinkedHashSet<>(ItemIdUtil.normalizeItemIds(extraItemIds));
	}

	private void normalize() {
		if (disabledDefaultRules == null) {
			disabledDefaultRules = new LinkedHashSet<>();
		}

		if (disabledExtraRules == null) {
			disabledExtraRules = new LinkedHashSet<>();
		}

		if (extraItemIds == null) {
			extraItemIds = new ArrayList<>();
		}

		scanIntervalTicks = Math.max(1, Math.min(200, scanIntervalTicks));
		extraItemIds = ItemIdUtil.normalizeItemIds(extraItemIds);
	}
}
