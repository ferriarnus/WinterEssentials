package com.ferri.arnus.winteressentials.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WinterConfig {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC;
	public static ConfigValue<Integer> STACKSNOW;
	public static ConfigValue<Double> POWDEREDSNOWCHANCE;
	public static ConfigValue<Double> SNOWCHANCE;
	
	static {
		STACKSNOW = BUILDER.define("Snowstacking height", 3);
		POWDEREDSNOWCHANCE = BUILDER.define("Chance Powdered snow layer spawn", 0.5D);
		SNOWCHANCE = BUILDER.define("Chance it snows in a 'warm' biome", 0.5D);
		SPEC = BUILDER.build();
	}
}
