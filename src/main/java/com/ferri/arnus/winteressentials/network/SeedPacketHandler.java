package com.ferri.arnus.winteressentials.network;

import java.util.function.Supplier;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraftforge.network.NetworkEvent.Context;

public class SeedPacketHandler {

	public static void handlePacket(SeedPacket message, Supplier<Context> ctx) {
			WinterEssentials.seed = message.getSeed();
	}
}
