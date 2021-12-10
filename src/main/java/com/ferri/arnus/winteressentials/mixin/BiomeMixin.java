package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mixin(Biome.class)
public abstract class BiomeMixin {

	@Shadow
	private Biome.ClimateSettings climateSettings;

	@Shadow
	abstract float getTemperature(BlockPos pos);
	
	public boolean warmEnoughToRain(BlockPos p_198907_) {
		if (EffectiveSide.get().isClient()) {
			ClientLevel level = Minecraft.getInstance().level;
			if (level == null) {
				return this.getTemperature(p_198907_) >= 0.15F;
			}
			return this.getTemperature(p_198907_) >= 0.15F && new Random(WinterEssentials.seed & level.getGameTime()/200 *123).nextFloat() < 0.9;
		}
		else {
			return this.getTemperature(p_198907_) >= 0.15F && new Random(ServerLifecycleHooks.getCurrentServer().getWorldData().worldGenSettings().seed() & ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD).getGameTime()/200 * 123).nextFloat() < 0.9;
		}
	}
	
	public Biome.Precipitation getPrecipitation() {
		if (this.climateSettings.precipitation == Biome.Precipitation.NONE) {
			return Biome.Precipitation.RAIN;
		}
		return this.climateSettings.precipitation;
	}
	
}
