package com.ferri.arnus.winteressentials.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class WinterConfig {
	
	public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	public static ForgeConfigSpec SPEC;
	public static ConfigValue<Boolean> MORESNOW;
	public static ConfigValue<Boolean> STACKSNOW;
	
	static {
		MORESNOW = BUILDER.define("Allow snowing in all biomes", true);
		STACKSNOW = BUILDER.define("Allow stacking of snow", true);
		SPEC = BUILDER.build();
	}
}
