package com.ferri.arnus.winteressentials.block;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, WinterEssentials.MODID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WinterEssentials.MODID);

	public static void register() {
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
	
	public static final RegistryObject<PowderSnowLayerBlock> POWDERLAYERBLOCK = BLOCKS.register("powdersnowlayerblock", PowderSnowLayerBlock::new);
	public static final RegistryObject<Item> POWDERLAYER = ITEMS.register("powdersnowlayerblock", () -> new BlockItem(POWDERLAYERBLOCK.get(), new Properties()));


}
