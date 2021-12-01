package com.ferri.arnus.winteressentials.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.PowderSnowBlock;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {
	
	@Inject(at = @At("HEAD"), method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z", cancellable = true)
	private static void canEntityWalkOnPowderSnow(Entity p_154256_, CallbackInfoReturnable<Boolean> callback) {
		if (p_154256_ instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.FEET).hasTag() && living.getItemBySlot(EquipmentSlot.FEET).getOrCreateTag().contains(WinterEssentials.MODID + ":snowshoe")) {
			callback.setReturnValue(true);
		}
	}

}
