package com.ferri.arnus.winteressentials.feature;

import com.ferri.arnus.winteressentials.WinterEssentials;
import com.google.common.collect.ImmutableSet;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.material.Fluids;

public class FeatureRegistry {

	public static ConfiguredFeature<?, ?> SNOWSPRING = Feature.SPRING.configured(new SpringConfiguration(Fluids.WATER.defaultFluidState(), true, 4, 1, ImmutableSet.of(Blocks.SNOW_BLOCK, Blocks.POWDER_SNOW, Blocks.PACKED_ICE)));
	public static PlacedFeature SNOWSPRING_PLACED = SNOWSPRING.placed(CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(8), 8)), BiomeFilter.biome());
	
	public static void registerConfigured() {
		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(WinterEssentials.MODID, "snowlake"), SNOWSPRING);
	}
	
	public static void registerPaced() {
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(WinterEssentials.MODID, "snowlake"), SNOWSPRING_PLACED);

	}
}
