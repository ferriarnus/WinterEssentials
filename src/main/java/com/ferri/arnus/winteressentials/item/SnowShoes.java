package com.ferri.arnus.winteressentials.item;

import java.util.function.Consumer;

import com.ferri.arnus.winteressentials.WinterEssentials;
import com.ferri.arnus.winteressentials.models.WEModelLayer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemRenderProperties;

public class SnowShoes extends ArmorItem{

	public SnowShoes() {
		super(WEArmorMaterials.SNOWSHOES, EquipmentSlot.FEET, new Properties().tab(ItemRegistry.WINTERTAB));
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
		if (this.allowdedIn(p_41391_)) {
			ItemStack s = new ItemStack(this);
			s.getOrCreateTag().putBoolean(WinterEssentials.MODID +":snowshoe", true);
			p_41392_.add(s);
		}
	}
	
	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		consumer.accept(new IItemRenderProperties() {
			@Override
			public <A extends HumanoidModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack,
					EquipmentSlot armorSlot, A _default) {
				return (A) WEModelLayer.shoes;
			}
		});
	}
		
}
