package com.autodrop.fishingtrash.util;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class ItemIdUtil {
	private ItemIdUtil() {
	}

	public static List<String> normalizeItemIds(List<String> rawIds) {
		Set<String> normalized = new LinkedHashSet<>();

		for (String rawId : rawIds) {
			normalizeItemId(rawId).ifPresent(id -> normalized.add(id.toString()));
		}

		return new ArrayList<>(normalized);
	}

	public static Optional<Identifier> normalizeItemId(String rawId) {
		if (rawId == null) {
			return Optional.empty();
		}

		String value = rawId.trim();

		if (value.isEmpty()) {
			return Optional.empty();
		}

		if (!value.contains(":")) {
			value = "minecraft:" + value;
		}

		return Optional.ofNullable(Identifier.tryParse(value));
	}
}
