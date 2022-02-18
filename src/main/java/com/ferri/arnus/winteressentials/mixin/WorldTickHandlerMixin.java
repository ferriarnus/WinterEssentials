package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.ferri.arnus.winteressentials.LevelExtension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import snownee.snow.WorldTickHandler;

@Mixin(WorldTickHandler.class)
public class WorldTickHandlerMixin {

	@ModifyArg(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/LevelChunk;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"))
	private static Heightmap.Types snowUnderTree(Heightmap.Types types) {
		if (new Random().nextDouble() < .2D) {
			return Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
		}
		return Heightmap.Types.MOTION_BLOCKING;
	}
	
	@Redirect(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/LevelChunk;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z"))
	private static boolean stopReturn(Biome biome, BlockPos pos) {
		return true;
	}
	
	@Inject(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/LevelChunk;Ljava/util/Random;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z", shift = Shift.BEFORE), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private static void makeSnow(ServerLevel level, LevelChunk chunk, Random random, CallbackInfo info, int x, int y, MutableBlockPos pos, Biome biome) {
		if (!biome.coldEnoughToSnow(pos) && !((LevelExtension) level).isSnowing()) {
			info.cancel();
		}
	}
}
