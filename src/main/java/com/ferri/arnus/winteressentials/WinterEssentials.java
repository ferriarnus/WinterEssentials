package com.ferri.arnus.winteressentials;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.config.WinterConfig;
import com.ferri.arnus.winteressentials.crafting.CraftingRegistry;
import com.ferri.arnus.winteressentials.item.ItemRegistry;
import com.ferri.arnus.winteressentials.network.SnowPacket;
import com.ferri.arnus.winteressentials.network.WinterChannel;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;

@Mod(WinterEssentials.MODID)
public class WinterEssentials {
    public static final String MODID = "winteressentials";
    public static Long seed = 123456789L;
    
    public WinterEssentials() {
    	
    	BlockRegistry.register();
    	ItemRegistry.register();
    	WinterChannel.register();
    	CraftingRegistry.register();
    	
    	IEventBus forgeBus = MinecraftForge.EVENT_BUS;
    	IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
         
    	forgeBus.addListener(this::connect);
    	ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, WinterConfig.SPEC); 

    }
    
    
    public void connect(PlayerLoggedInEvent event) {
    	if (event.getEntity() instanceof ServerPlayer player) {
        	WinterChannel.INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), new SnowPacket(((LevelExtension) ServerLifecycleHooks.getCurrentServer().getLevel(Level.OVERWORLD)).isSnowing()));
    	}
    }
}
