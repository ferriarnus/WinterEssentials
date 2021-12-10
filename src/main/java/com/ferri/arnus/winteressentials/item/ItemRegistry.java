package com.ferri.arnus.winteressentials.item;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WinterEssentials.MODID);
	
	public static void register() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<SnowShoes> SNOWSHOES = ITEMS.register("snowshoes", SnowShoes::new);
	public static final RegistryObject<Skates> SKATES = ITEMS.register("skates", Skates::new);
	public static final RegistryObject<SnowPouch> SNOWPOUCH = ITEMS.register("snow_pouch", SnowPouch::new);

}
