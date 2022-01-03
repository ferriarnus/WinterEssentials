package com.ferri.arnus.winteressentials.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;

public class Skates extends ArmorItem{

	public Skates() {
		super(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Properties().tab(ItemRegistry.WINTERTAB));
	}
}
