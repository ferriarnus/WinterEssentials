package com.ferri.arnus.winteressentials.block;

import com.ferri.arnus.winteressentials.WinterEssentials;
import com.ferri.arnus.winteressentials.item.ItemRegistry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = WinterEssentials.MODID, value = Dist.CLIENT)
public class BlockRegistry {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WinterEssentials.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WinterEssentials.MODID);

	public static void register() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<PowderSnowLayerBlock> POWDERLAYERBLOCK = BLOCKS.register("powdersnowlayerblock", PowderSnowLayerBlock::new);
	public static final RegistryObject<Item> POWDERLAYER = ITEMS.register("powdersnowlayerblock", () -> new BlockItem(POWDERLAYERBLOCK.get(), new Properties()));
	
	public static final RegistryObject<MeltingSnow> MELTINGSNOWBLOCK = BLOCKS.register("meltingsnow", MeltingSnow::new);
	public static final RegistryObject<Item> MELTINGSNOW = ITEMS.register("meltingsnow", () -> new BlockItem(MELTINGSNOWBLOCK.get(), new Properties()));


	@SubscribeEvent
	protected void itemTab(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == ItemRegistry.WINTERTAB) {
			event.accept(new ItemStack(POWDERLAYER.get()));
			event.accept(new ItemStack(MELTINGSNOW.get()));
		}
	}
}
