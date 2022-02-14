package com.ferri.arnus.winteressentials.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import com.ferri.arnus.winteressentials.LevelExtension;

import net.minecraft.world.level.Level;

@Mixin(Level.class)
public class LevelMixin implements LevelExtension{
	
	@Unique
	public boolean snowing = false;

	@Override
	public boolean isSnowing() {
		return snowing;
	}

	@Override
	public void setSnowing(boolean snowing) {
		this.snowing = snowing;
	}

}
