package com.ferri.arnus.winteressentials.models;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent.AddLayers;
import net.minecraftforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = WinterEssentials.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WEModelLayer {
	
	public static final ModelLayerLocation SNOWSHOES = new ModelLayerLocation(new ResourceLocation(WinterEssentials.MODID, "snowshoes"), "main" );
	
	public static SnowShoesModel<LivingEntity> shoes;

	@SubscribeEvent
	public static void initLayers(RegisterLayerDefinitions event) {
		 event.registerLayerDefinition(SNOWSHOES, () -> SnowShoesModel.createBodyLayer());
	}
	
	@SubscribeEvent
	public static void initModels(AddLayers event) {
		shoes = new SnowShoesModel<>(event.getEntityModels().bakeLayer(SNOWSHOES));
	}
}
