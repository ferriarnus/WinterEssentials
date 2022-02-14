package com.ferri.arnus.winteressentials.network;

import java.util.function.Supplier;

import com.ferri.arnus.winteressentials.LevelExtension;

import net.minecraft.client.Minecraft;
import net.minecraftforge.network.NetworkEvent.Context;

public class SnowPacketHandler {

	public static void handlePacket(SnowPacket message, Supplier<Context> ctx) {
		((LevelExtension) Minecraft.getInstance().level).setSnowing(message.snow);
	}
}
