package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mixin(Biome.class)
public abstract class ServerBiomeMixin {

	@Shadow
	private Biome.ClimateSettings climateSettings;

	@Shadow
	abstract float getTemperature(BlockPos pos);
	
	@Overwrite
	public boolean warmEnoughToRain(BlockPos p_198907_) {	
		return this.getTemperature(p_198907_) >= 0.15F && new Random(ServerLifecycleHooks.getCurrentServer()
				.getWorldData().worldGenSettings().seed()
				& ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getGameTime() / 1000 * 1234)
				.nextFloat() < 0.6;
		
	}
	
	@Overwrite
	public Biome.Precipitation getPrecipitation() {
		if (this.climateSettings.precipitation == Biome.Precipitation.NONE) {
			return Biome.Precipitation.RAIN;
		}
		return this.climateSettings.precipitation;
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;warmEnoughToRain(Lnet/minecraft/core/BlockPos;)Z"), method = "shouldFreeze(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Z)Z")
	public boolean freeze(Biome b,BlockPos pos) {
		return this.getTemperature(pos) >= 0.15F;
	}
}