package com.ferri.arnus.winteressentials.item;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SnowShoes extends ArmorItem{

	public SnowShoes() {
		super(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Properties().tab(CreativeModeTab.TAB_COMBAT));
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
		if (this.allowdedIn(p_41391_)) {
			ItemStack s = new ItemStack(this);
			s.getOrCreateTag().putBoolean(WinterEssentials.MODID +":snowshoe", true);
			p_41392_.add(s);
		}
	}

}
