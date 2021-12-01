package com.ferri.arnus.winteressentials;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.item.ItemRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(WinterEssentials.MODID)
public class WinterEssentials {
    public static final String MODID = "winteressentials";
    private static final Logger LOGGER = LogManager.getLogger();

    public WinterEssentials() {
    	
    	BlockRegistry.register();
    	ItemRegistry.register();
    	
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        
    }
}
