package com.ferri.arnus.winteressentials;

import java.util.UUID;

import com.ferri.arnus.winteressentials.item.ItemRegistry;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.event.FOVModifierEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class Events {

	@SubscribeEvent
	static void vof(FOVModifierEvent event) {
		AttributeInstance attributeinstance = event.getEntity().getAttribute(Attributes.MOVEMENT_SPEED);
		if (attributeinstance != null) {
			if (attributeinstance.getModifier(UUID.fromString("146b38dd-8d48-460e-8d26-c33c3ed022af")) != null) {
				event.setNewfov(event.getFov());
			}	
		}
	}
	
	@SubscribeEvent
	public static void tooltip(ItemTooltipEvent event) {
		if (event.getItemStack().hasTag()) {
			if (event.getItemStack().getTag().contains(WinterEssentials.MODID + ":snowshoe")) {
				TranslatableComponent issnowshoe = new TranslatableComponent("component.winteressentials.issnowshoe");
				issnowshoe.withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC);
				event.getToolTip().add(1, issnowshoe);
			}
		}
		if (event.getItemStack().is(ItemRegistry.SNOWSHOE.get())) {
			TranslatableComponent snowshoe = new TranslatableComponent("component.winteressentials.snowshoeinfo");
			snowshoe.withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC);
			event.getToolTip().add(1, snowshoe);
		}
	}
}
