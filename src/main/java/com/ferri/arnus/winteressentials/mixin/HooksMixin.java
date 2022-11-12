//package com.ferri.arnus.winteressentials.mixin;
//
//import java.util.Random;
//
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Redirect;
//
//import com.ferri.arnus.winteressentials.block.BlockRegistry;
//import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;
//import com.ferri.arnus.winteressentials.config.WinterConfig;
//
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.state.BlockState;
//import snownee.snow.Hooks;
//
//@Mixin(Hooks.class)
//public class HooksMixin {
//
//	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;", ordinal = 1), method = "place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z")
//	private static BlockState moreSnow(Block b) {
//		double d = new Random().nextDouble();
//		if (d < WinterConfig.POWDEREDSNOWCHANCE.get()) {
//			return BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().setValue(PowderSnowLayerBlock.PERSISTENT, false);
//		}
//		return Blocks.SNOW.defaultBlockState();
//	}
//}
