package com.ferri.arnus.winteressentials.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.ferri.arnus.winteressentials.LevelExtension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Redirect(method = "renderSnowAndRain(Lnet/minecraft/client/renderer/LightTexture;FDDD)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
	public boolean renderSow(Biome biome, BlockPos pos){
		return biome.warmEnoughToRain(pos) && !((LevelExtension) Minecraft.getInstance().level).isSnowing();
	}
	
	@Redirect(method = "tickRain(Lnet/minecraft/client/Camera;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"))
	public boolean noWaterParticles(Biome biome, BlockPos pos){
		return biome.warmEnoughToRain(pos) && !((LevelExtension) Minecraft.getInstance().level).isSnowing();
	}
	
}
