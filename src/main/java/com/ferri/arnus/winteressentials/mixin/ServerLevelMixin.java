package com.ferri.arnus.winteressentials.mixin;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;
import com.ferri.arnus.winteressentials.config.WinterConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level{

	protected ServerLevelMixin(WritableLevelData p_46450_, ResourceKey<Level> p_46451_, DimensionType p_46452_,
			Supplier<ProfilerFiller> p_46453_, boolean p_46454_, boolean p_46455_, long p_46456_) {
		super(p_46450_, p_46451_, p_46452_, p_46453_, p_46454_, p_46455_, p_46456_);
	}

	@Redirect(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1))
	public boolean moreSnow(ServerLevel l, BlockPos pos, BlockState state) {
		float random = l.random.nextFloat();
		BlockState snow = Blocks.SNOW.defaultBlockState();
		BlockState powdersnow = BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState();
		BlockState s = l.getBlockState(pos);
		if (s.getBlock() instanceof SnowLayerBlock || s.getBlock().equals(BlockRegistry.POWDERLAYERBLOCK.get())) {
			if (s.getValue(BlockStateProperties.LAYERS) < WinterConfig.STACKSNOW.get()) {
				return l.setBlockAndUpdate(pos, s.setValue(BlockStateProperties.LAYERS, s.getValue(BlockStateProperties.LAYERS) +1));
			}
		}
		if (!Blocks.SNOW.canSurvive(powdersnow, l, pos) || l.getBiome(pos).getPrecipitation().equals(Biome.Precipitation.NONE)) {
			return false;
		}
		if (l.getBiome(pos).getTemperature(pos) > 0.15F) {
			snow = BlockRegistry.MELTINGSNOWBLOCK.get().defaultBlockState();
			powdersnow = BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().setValue(PowderSnowLayerBlock.PERSISTENT, false);
		}
		if (random < 0.5) {
			return l.setBlockAndUpdate(pos, powdersnow);
		}else {
			return l.setBlockAndUpdate(pos, snow);
		}
	}
	
//	//@Redirect(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
//	public boolean allowSnow(Biome biome, LevelReader reader, BlockPos pos) {
//		return biome.shouldSnow(reader, pos) || true;
//	}
	
//	@Redirect(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z"))
//	public boolean makeSnow(Biome biome, BlockPos pos) {
//		return biome.coldEnoughToSnow(pos) || true;
//	}
}
