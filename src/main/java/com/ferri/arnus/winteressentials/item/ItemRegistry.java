package com.ferri.arnus.winteressentials.item;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = WinterEssentials.MODID, value = Dist.CLIENT)
public class ItemRegistry {
	
	public static CreativeModeTab WINTERTAB;
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WinterEssentials.MODID);
	
	public static void register() {
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<SnowShoes> SNOWSHOES = ITEMS.register("snowshoes", SnowShoes::new);
	public static final RegistryObject<Skates> SKATES = ITEMS.register("skates", Skates::new);
	public static final RegistryObject<SnowPouch> SNOWPOUCH = ITEMS.register("snow_pouch", SnowPouch::new);
	public static final RegistryObject<Item> SNOWSHOE = ITEMS.register("snowshoe", () -> new Item(new Properties().stacksTo(2)));

	@SubscribeEvent
	protected void registerTab(CreativeModeTabEvent.Register event) {
		WINTERTAB = event.registerCreativeModeTab(new ResourceLocation(WinterEssentials.MODID, "winteressentials"), builder -> {
			builder.icon(() -> new ItemStack(SNOWSHOES.get()));
			builder.title(Component.translatable("tabs.winteressentials.winteressentials"));
		});
	}

	@SubscribeEvent
	protected void itemTab(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == ItemRegistry.WINTERTAB) {
			event.accept(new ItemStack(SNOWSHOE.get()));
		}
	}
}
