package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

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
				& ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getGameTime() / 2000 * 123)
				.nextFloat() < 0.8;
		
	}
	
	@Overwrite
	public Biome.Precipitation getPrecipitation() {
		if (this.climateSettings.precipitation == Biome.Precipitation.NONE) {
			return Biome.Precipitation.RAIN;
		}
		return this.climateSettings.precipitation;
	}
	
}
