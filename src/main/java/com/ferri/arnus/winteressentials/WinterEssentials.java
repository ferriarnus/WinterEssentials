package com.ferri.arnus.winteressentials;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.config.WinterConfig;
import com.ferri.arnus.winteressentials.feature.FeatureRegistry;
import com.ferri.arnus.winteressentials.item.ItemRegistry;
import com.ferri.arnus.winteressentials.network.SeedPacket;
import com.ferri.arnus.winteressentials.network.WinterChannel;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(WinterEssentials.MODID)
public class WinterEssentials {
    public static final String MODID = "winteressentials";
    public static Long seed;
    
    public WinterEssentials() {
    	
    	BlockRegistry.register();
    	ItemRegistry.register();
    	WinterChannel.register();
    	
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
         
    	modEventBus.addListener(this::setup);
    	forgeBus.addListener(this::connect);
    	forgeBus.addListener(this::biomeModification);
    	ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WinterConfig.SPEC); 

    }

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
        	FeatureRegistry.registerConfigured();
        	FeatureRegistry.registerPaced();
        });
    }
    
    
    public void connect(PlayerLoggedInEvent event) {
    	if (event.getPlayer() instanceof ServerPlayer player) {
        	WinterChannel.INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), new SeedPacket(ServerLifecycleHooks.getCurrentServer().getWorldData().worldGenSettings().seed()));
    	}
    }
    
    public void biomeModification(final BiomeLoadingEvent event) {
        event.getGeneration().getFeatures(GenerationStep.Decoration.FLUID_SPRINGS).add(() -> FeatureRegistry.SNOWSPRING_PLACED);
    }
}
