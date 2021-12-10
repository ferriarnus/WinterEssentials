package com.ferri.arnus.winteressentials.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

public class SeedPacket {
	
	private Long seed;

	public SeedPacket(Long seed) {
		this.seed = seed;
	}
	
	public Long getSeed() {
		return seed;
	}
	
	public void encode(FriendlyByteBuf buffer) {
		buffer.writeLong(this.seed);
	}
	
	public static SeedPacket decode(FriendlyByteBuf buffer) {
		return new SeedPacket(buffer.readLong());
	}
	
	static void handle(final SeedPacket message, Supplier<Context> ctx) {
		ctx.get().enqueueWork(() ->
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SeedPacketHandler.handlePacket(message, ctx)));
		ctx.get().setPacketHandled(true);
	}

}
