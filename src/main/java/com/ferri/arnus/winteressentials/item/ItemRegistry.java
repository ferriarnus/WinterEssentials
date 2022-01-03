package com.ferri.arnus.winteressentials.item;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {
	
	public static final CreativeModeTab WINTERTAB = new CreativeModeTab("winteressentials") {
		
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(SNOWSHOES.get());
		}
	};
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WinterEssentials.MODID);
	
	public static void register() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<SnowShoes> SNOWSHOES = ITEMS.register("snowshoes", SnowShoes::new);
	public static final RegistryObject<Skates> SKATES = ITEMS.register("skates", Skates::new);
	public static final RegistryObject<SnowPouch> SNOWPOUCH = ITEMS.register("snow_pouch", SnowPouch::new);
	public static final RegistryObject<Item> SNOWSHOE = ITEMS.register("snowshoe", () -> new Item(new Properties().stacksTo(2).tab(WINTERTAB)));

}
