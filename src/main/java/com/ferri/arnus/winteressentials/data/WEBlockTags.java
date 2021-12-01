package com.ferri.arnus.winteressentials.data;

import com.ferri.arnus.winteressentials.WinterEssentials;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class WEBlockTags {

	public static final IOptionalNamedTag<Block> SNOWY = BlockTags.createOptional(new ResourceLocation(WinterEssentials.MODID, "snowy"));
}
