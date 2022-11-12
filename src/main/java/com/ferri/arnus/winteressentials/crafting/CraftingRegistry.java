package com.ferri.arnus.winteressentials.crafting;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = WinterEssentials.MODID, bus = Bus.MOD)
public class CraftingRegistry {
	
	private static final DeferredRegister<RecipeSerializer<?>> SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WinterEssentials.MODID);

	public static void register() {
		SERIALIZER.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<RecipeSerializer<SnowShoeUpgradeRecipe>> SNOWSHOES = SERIALIZER.register("snowshoes", () -> SnowShoeUpgradeRecipe.SERIALIZER);
	
}
