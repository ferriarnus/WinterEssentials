package com.ferri.arnus.winteressentials;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.feature.FeatureRegistry;
import com.ferri.arnus.winteressentials.item.ItemRegistry;

import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
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
    	
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
         
    	modEventBus.addListener(this::setup);
    	forgeBus.addListener(this::biomeModification);

    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        	FeatureRegistry.registerConfigured();
        	FeatureRegistry.registerPaced();
        });
    }
    
    public void biomeModification(final BiomeLoadingEvent event) {
        event.getGeneration().getFeatures(GenerationStep.Decoration.FLUID_SPRINGS).add(() -> FeatureRegistry.SNOWSPRING_PLACED);
    }
}
