package com.ferri.arnus.winteressentials.mixin;

import java.util.Random;
import java.util.function.Supplier;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.ferri.arnus.winteressentials.LevelExtension;
import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;
import com.ferri.arnus.winteressentials.config.WinterConfig;
import com.ferri.arnus.winteressentials.network.SnowPacket;
import com.ferri.arnus.winteressentials.network.WinterChannel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraftforge.network.PacketDistributor;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level{

	protected ServerLevelMixin(WritableLevelData pLevelData, ResourceKey<Level> pDimension,
			Holder<DimensionType> pDimensionTypeRegistration, Supplier<ProfilerFiller> pProfiler, boolean pIsClientSide,
			boolean pIsDebug, long pBiomeZoomSeed, int pMaxChainedNeighborUpdates) {
		super(pLevelData, pDimension, pDimensionTypeRegistration, pProfiler, pIsClientSide, pIsDebug, pBiomeZoomSeed,
				pMaxChainedNeighborUpdates);
	}

	@Redirect(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;setBlockAndUpdate(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 1))
	public boolean moreSnow(ServerLevel l, BlockPos pos, BlockState state) {
		double d = l.random.nextDouble();
		BlockState snow = Blocks.SNOW.defaultBlockState();
		BlockState powdersnow = BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState();
		BlockState s = l.getBlockState(pos);
		if (s.getBlock() instanceof SnowLayerBlock || s.getBlock().equals(BlockRegistry.POWDERLAYERBLOCK.get())) {
			if (s.getValue(BlockStateProperties.LAYERS) < WinterConfig.STACKSNOW.get()) {
				return l.setBlockAndUpdate(pos, s.setValue(BlockStateProperties.LAYERS, s.getValue(BlockStateProperties.LAYERS) +1));
			}
		}
		if (!Blocks.SNOW.defaultBlockState().canSurvive(l, pos) || l.getBiome(pos).get().getPrecipitation().equals(Biome.Precipitation.NONE)) {
			return false;
		}
		if (!l.getBiome(pos).get().coldEnoughToSnow(pos)) {
			snow = BlockRegistry.MELTINGSNOWBLOCK.get().defaultBlockState();
			powdersnow = BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().setValue(PowderSnowLayerBlock.PERSISTENT, false);
		}
		if (d < WinterConfig.POWDEREDSNOWCHANCE.get()) {
			return l.setBlockAndUpdate(pos, powdersnow);
		}else {
			return l.setBlockAndUpdate(pos, snow);
		}
	}
	
	@ModifyArg(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;"))
	public Heightmap.Types snowUnderTree(Heightmap.Types types) {
		if (new Random().nextDouble() < .2D) {
			return Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
		}
		return Heightmap.Types.MOTION_BLOCKING;
	}
	
	
	
	@Redirect(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
	public boolean allowSnow(Biome biome, LevelReader reader, BlockPos pos) {
		return biome.shouldSnow(reader, pos) || ((LevelExtension) this).isSnowing();
	}
	
	@Redirect(method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;coldEnoughToSnow(Lnet/minecraft/core/BlockPos;)Z"))
	public boolean makeSnow(Biome biome, BlockPos pos) {
		return biome.coldEnoughToSnow(pos) || ((LevelExtension) this).isSnowing();
	}
	
	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket;STOP_RAINING:Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket$Type;", opcode = Opcodes.GETSTATIC, shift = Shift.AFTER), method = "advanceWeatherCycle()V")
	public void stopSnowing(CallbackInfo info) {
		((LevelExtension) this).setSnowing(false);
		WinterChannel.INSTANCE.send(PacketDistributor.ALL.noArg(), new SnowPacket(false));
	}
	
	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket;START_RAINING:Lnet/minecraft/network/protocol/game/ClientboundGameEventPacket$Type;", opcode = Opcodes.GETSTATIC, shift = Shift.AFTER), method = "advanceWeatherCycle()V")
	public void startSnowing(CallbackInfo info) {
		if (new Random().nextDouble() < WinterConfig.SNOWCHANCE.get()) {
			((LevelExtension) this).setSnowing(true);
			WinterChannel.INSTANCE.send(PacketDistributor.ALL.noArg(), new SnowPacket(true));
		}
	}
	
}
