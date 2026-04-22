package com.autodrop.fishingtrash.config;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;

public enum DropDirection {
	CURRENT_VIEW("option.autodrop_fishing_trash.drop_direction.current_view") {
		@Override
		public boolean usesRotationPacket() {
			return false;
		}

		@Override
		public float yaw(ClientPlayerEntity player) {
			return player.getYaw();
		}
	},
	FORWARD("option.autodrop_fishing_trash.drop_direction.forward") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return player.getYaw();
		}
	},
	BACKWARD("option.autodrop_fishing_trash.drop_direction.backward") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return player.getYaw() + 180.0F;
		}
	},
	LEFT("option.autodrop_fishing_trash.drop_direction.left") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return player.getYaw() - 90.0F;
		}
	},
	RIGHT("option.autodrop_fishing_trash.drop_direction.right") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return player.getYaw() + 90.0F;
		}
	},
	NORTH("option.autodrop_fishing_trash.drop_direction.north") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return 180.0F;
		}
	},
	SOUTH("option.autodrop_fishing_trash.drop_direction.south") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return 0.0F;
		}
	},
	EAST("option.autodrop_fishing_trash.drop_direction.east") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return -90.0F;
		}
	},
	WEST("option.autodrop_fishing_trash.drop_direction.west") {
		@Override
		public float yaw(ClientPlayerEntity player) {
			return 90.0F;
		}
	};

	private final String translationKey;

	DropDirection(String translationKey) {
		this.translationKey = translationKey;
	}

	public boolean usesRotationPacket() {
		return true;
	}

	public String translationKey() {
		return translationKey;
	}

	public float horizontalYaw(ClientPlayerEntity player) {
		return MathHelper.wrapDegrees(yaw(player));
	}

	public abstract float yaw(ClientPlayerEntity player);
}
