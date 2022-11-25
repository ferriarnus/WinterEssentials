package com.ferri.arnus.winteressentials.mixin;

import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.ferri.arnus.winteressentials.block.BlockRegistry;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(Biome.class)
public abstract class BiomeMixin {
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z"), method = "shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z")
	public boolean dosnow(BlockState s) {
		return s.isAir() || s.getBlock() instanceof SnowLayerBlock || s.getBlock().equals(BlockRegistry.POWDERLAYERBLOCK.get());
	}
	
}
