package com.ferri.arnus.winteressentials.crafting;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistryEntry;

@EventBusSubscriber(modid = WinterEssentials.MODID, bus = Bus.MOD)
public class CraftingRegistry {

	@SubscribeEvent
	public static void registerSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
		event.getRegistry().register(name(SnowShoeUpgradeRecipe.SERIALIZER, "snowshoes"));
	}
	
	private static <T extends IForgeRegistryEntry<? extends T>> T name(T entry, String name) {
		return entry.setRegistryName(new ResourceLocation(WinterEssentials.MODID, name));
	}
}
