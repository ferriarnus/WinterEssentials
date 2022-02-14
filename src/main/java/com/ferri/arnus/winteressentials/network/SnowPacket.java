package com.ferri.arnus.winteressentials.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SnowPacket {
	public boolean snow;

	public SnowPacket(boolean snow) {
		this.snow = snow;
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeBoolean(snow);
	}
	
	public static SnowPacket decode(FriendlyByteBuf buffer) {
		return new SnowPacket(buffer.readBoolean());
	}
	
	static void handle(final SnowPacket message, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() ->
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SnowPacketHandler.handlePacket(message, ctx)));
		ctx.get().setPacketHandled(true);
	}
}
