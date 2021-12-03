package com.ferri.arnus.winteressentials.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.world.level.Level;

@Mixin(Level.class)
public class LevelMixin {

	@Unique
	public boolean storm = false;
}
