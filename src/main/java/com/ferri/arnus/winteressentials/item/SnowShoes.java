package com.ferri.arnus.winteressentials.item;

import java.util.function.Consumer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = WinterEssentials.MODID, value = Dist.CLIENT)
public class SnowShoes extends ArmorItem{

	public SnowShoes() {
		super(WEArmorMaterials.SNOWSHOES, EquipmentSlot.FEET, new Properties());
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

	@SubscribeEvent
	protected void itemTab(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == ItemRegistry.WINTERTAB) {
			NonNullList<ItemStack> list = NonNullList.create();
			ItemStack s = new ItemStack(this);
			s.getOrCreateTag().putBoolean(WinterEssentials.MODID +":snowshoe", true);
			list.add(s);
			event.acceptAll(list);
		}
	}

		
}
