package com.ferri.arnus.winteressentials;

import java.util.UUID;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.event.FOVModifierEvent;
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
}
