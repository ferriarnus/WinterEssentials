package com.ferri.arnus.winteressentials.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WinterConfig {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC;
	public static ConfigValue<Boolean> MORESNOW;
	public static ConfigValue<Integer> STACKSNOW;
	
	static {
		MORESNOW = BUILDER.define("Allow snowing in all biomes", true);
		STACKSNOW = BUILDER.define("Snowstacking height", 3);
		SPEC = BUILDER.build();
	}
}
