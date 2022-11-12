package com.ferri.arnus.winteressentials.item;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.ferri.arnus.winteressentials.WinterEssentials;
import com.ferri.arnus.winteressentials.models.WEModelLayer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SnowShoes extends ArmorItem{

	public SnowShoes() {
		super(WEArmorMaterials.SNOWSHOES, EquipmentSlot.FEET, new Properties().tab(ItemRegistry.WINTERTAB));
	}
	
	@Override
	public void fillItemCategory(CreativeModeTab p_41391_, NonNullList<ItemStack> p_41392_) {
		if (this.allowedIn(p_41391_)) {
			ItemStack s = new ItemStack(this);
			s.getOrCreateTag().putBoolean(WinterEssentials.MODID +":snowshoe", true);
			p_41392_.add(s);
		}
	}
	
	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
					EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				return WEModelLayer.shoes;
			}
		});
	}
		
}
