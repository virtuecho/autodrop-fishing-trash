package com.autodrop.fishingtrash;

import com.autodrop.fishingtrash.config.AutoDropConfig;
import com.autodrop.fishingtrash.config.DropDirection;
import com.autodrop.fishingtrash.loot.DropMatcher;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

import java.util.ArrayList;
import java.util.List;

public final class AutoDropFishingTrashClient implements ClientModInitializer {
	private static final int MAIN_INVENTORY_FIRST_SLOT = 9;
	private static final int MAIN_INVENTORY_LAST_SLOT = 35;
	private static int ticksUntilNextScan = 0;
	private static int ticksUntilNextRotatedDrop = 0;

	@Override
	public void onInitializeClient() {
		AutoDropConfig.load();
		ClientTickEvents.END_CLIENT_TICK.register(AutoDropFishingTrashClient::onEndClientTick);
		AutoDropFishingTrash.LOGGER.info("AutoDrop Fishing Trash initialized");
	}

	private static void onEndClientTick(MinecraftClient client) {
		AutoDropConfig config = AutoDropConfig.get();
		ClientPlayerEntity player = client.player;

		if (!config.enabled || player == null || client.interactionManager == null) {
			return;
		}

		if (config.pauseInScreens && client.currentScreen != null) {
			return;
		}

		if (player.currentScreenHandler != player.playerScreenHandler) {
			return;
		}

		if (config.onlyWhileFishing && !isFishing(player)) {
			return;
		}

		if (ticksUntilNextRotatedDrop > 0) {
			ticksUntilNextRotatedDrop--;
		}

		if (ticksUntilNextScan > 0) {
			ticksUntilNextScan--;
			return;
		}

		ticksUntilNextScan = Math.max(1, config.scanIntervalTicks);
		dropMatchingMainInventoryStacks(client, player, config);
	}

	private static boolean isFishing(ClientPlayerEntity player) {
		return player.fishHook != null
			|| player.getMainHandStack().isOf(Items.FISHING_ROD)
			|| player.getOffHandStack().isOf(Items.FISHING_ROD);
	}

	private static void dropMatchingMainInventoryStacks(MinecraftClient client, ClientPlayerEntity player, AutoDropConfig config) {
		int syncId = player.playerScreenHandler.syncId;
		List<Integer> matchingSlotIds = new ArrayList<>();

		// PlayerScreenHandler slots 9-35 are the 27 main inventory slots; slots 36-44 are the hotbar.
		for (int slotId = MAIN_INVENTORY_FIRST_SLOT; slotId <= MAIN_INVENTORY_LAST_SLOT; slotId++) {
			ItemStack stack = player.playerScreenHandler.getSlot(slotId).getStack();

			if (DropMatcher.shouldDrop(stack, config)) {
				matchingSlotIds.add(slotId);
			}
		}

		if (matchingSlotIds.isEmpty()) {
			return;
		}

		DropDirection direction = config.dropDirection;
		boolean rotateForDrop = direction.usesRotationPacket();

		if (rotateForDrop) {
			if (ticksUntilNextRotatedDrop > 0) {
				return;
			}

			sendServerLookPacket(player, direction.horizontalYaw(player), 0.0F);
		}

		for (int slotId : matchingSlotIds) {
			client.interactionManager.clickSlot(syncId, slotId, 1, SlotActionType.THROW, player);
		}

		if (rotateForDrop) {
			sendServerLookPacket(player, player.getYaw(), player.getPitch());
			ticksUntilNextRotatedDrop = Math.max(1, config.rotatedDropIntervalTicks);
		}
	}

	private static void sendServerLookPacket(ClientPlayerEntity player, float yaw, float pitch) {
		// Rotation packets change the server-side drop direction without moving the local camera.
		player.networkHandler.getConnection().send(new PlayerMoveC2SPacket.LookAndOnGround(yaw, pitch, player.isOnGround(), player.horizontalCollision));
	}
}
