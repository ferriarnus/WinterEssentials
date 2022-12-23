package com.ferri.arnus.winteressentials.item;

import com.ferri.arnus.winteressentials.WinterEssentials;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = WinterEssentials.MODID, value = Dist.CLIENT)
public class Skates extends ArmorItem{

	public Skates() {
		super(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Properties());
	}

	@SubscribeEvent
	protected void itemTab(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == ItemRegistry.WINTERTAB) {
			event.accept(new ItemStack(this));
		}
	}
}
