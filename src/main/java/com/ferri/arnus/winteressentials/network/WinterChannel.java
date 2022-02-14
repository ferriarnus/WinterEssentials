package com.ferri.arnus.winteressentials.network;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class WinterChannel {

private static final String PROTOCOL_VERSION = "1";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(WinterEssentials.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
			);
	
	public static void register() {
		INSTANCE.registerMessage(0, SeedPacket.class, SeedPacket::encode, SeedPacket::decode, SeedPacket::handle);
		INSTANCE.registerMessage(1, SnowPacket.class, SnowPacket::encode, SnowPacket::decode, SnowPacket::handle);
	}
}
