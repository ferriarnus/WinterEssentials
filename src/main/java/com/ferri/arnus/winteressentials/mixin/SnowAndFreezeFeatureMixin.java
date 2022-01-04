package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;

@Mixin(SnowAndFreezeFeature.class)
public class SnowAndFreezeFeatureMixin {

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"), method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z")
	public boolean snow(Biome b, LevelReader l, BlockPos pos) {
		if (b.getTemperature(pos) >= 0.15F) {
	         return false;
	      } else {
	         if (pos.getY() >= l.getMinBuildHeight() && pos.getY() < l.getMaxBuildHeight() && l.getBrightness(LightLayer.BLOCK, pos) < 10) {
	            BlockState blockstate = l.getBlockState(pos);
	            if (blockstate.isAir() && Blocks.SNOW.defaultBlockState().canSurvive(l, pos)) {
	               return true;
	            }
	         }

	         return false;
	      }
	   }
	
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 1), method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z")
	public BlockState moreSnow(Block b) {
		float f = new Random().nextFloat();
		if (f < 0.5) {
			return BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().setValue(PowderSnowLayerBlock.PERSISTENT, false);
		}
		return Blocks.SNOW.defaultBlockState();
	}
	
}
