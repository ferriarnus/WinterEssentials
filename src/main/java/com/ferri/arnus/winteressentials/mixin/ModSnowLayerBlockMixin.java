package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;
import com.ferri.arnus.winteressentials.config.WinterConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import snownee.snow.CoreModule;
import snownee.snow.block.ModSnowLayerBlock;

@Mixin(ModSnowLayerBlock.class)
public class ModSnowLayerBlockMixin {

	@Redirect(method = "convert(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", ordinal = 0))
	private static boolean moreSnow(LevelAccessor l, BlockPos pos, BlockState state, int flag) {
		double d = new Random().nextDouble();
		BlockState snow = CoreModule.BLOCK.defaultBlockState();
		BlockState powdersnow = BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState();
		BlockState s = l.getBlockState(pos);
		if (s.getBlock() instanceof SnowLayerBlock || s.getBlock().equals(BlockRegistry.POWDERLAYERBLOCK.get())) {
			if (s.getValue(BlockStateProperties.LAYERS) < WinterConfig.STACKSNOW.get()) {
				return l.setBlock(pos, s.setValue(BlockStateProperties.LAYERS, s.getValue(BlockStateProperties.LAYERS) +1),3);
			}
		}
		if (!Blocks.SNOW.defaultBlockState().canSurvive(l, pos) || l.getBiome(pos).getPrecipitation().equals(Biome.Precipitation.NONE)) {
			return false;
		}
		if (!l.getBiome(pos).coldEnoughToSnow(pos)) {
			snow = BlockRegistry.MELTINGSNOWBLOCK.get().defaultBlockState();
			powdersnow = BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().setValue(PowderSnowLayerBlock.PERSISTENT, false);
		}
		if (d < WinterConfig.POWDEREDSNOWCHANCE.get()) {
			return l.setBlock(pos, powdersnow,3);
		}else {
			return l.setBlock(pos, snow,3);
		}
	}
}
