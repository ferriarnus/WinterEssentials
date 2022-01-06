package com.ferri.arnus.winteressentials.item;

import java.util.List;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnowPouch extends Item{
	
	private int maxSnow = 8*32;
	
	public SnowPouch() {
		super(new Properties().stacksTo(1).tab(ItemRegistry.WINTERTAB));
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
		CompoundTag tag = context.getPlayer().getItemInHand(context.getHand()).getOrCreateTag();
		if (blockState.is(BlockRegistry.POWDERLAYERBLOCK.get()) && context.getPlayer().isCrouching() && context.getLevel().getBlockState(context.getClickedPos()).getValue(PowderSnowLayerBlock.LAYERS) < 8) {
			if (tag.contains("winteressentials:powdersnow")) {
				int current = tag.getInt("winteressentials:powdersnow");
				if (current > 0) {
					tag.putInt("winteressentials:powdersnow", current-1);
				} else {
					return InteractionResult.SUCCESS;
				}
				Integer value = context.getLevel().getBlockState(context.getClickedPos()).getValue(PowderSnowLayerBlock.LAYERS);
				context.getLevel().setBlockAndUpdate(context.getClickedPos(), BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().setValue(PowderSnowLayerBlock.LAYERS, value+1));
				return InteractionResult.SUCCESS;
			}
		} else if (context.getClickedFace() == Direction.UP && BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos().relative(context.getClickedFace()))) {
			if (tag.contains("winteressentials:powdersnow")) {
				int current = tag.getInt("winteressentials:powdersnow");
				if (current > 0) {
					tag.putInt("winteressentials:powdersnow", current-1);
				} else {
					return InteractionResult.SUCCESS;
				}
				context.getLevel().setBlockAndUpdate(context.getClickedPos().above(), BlockRegistry.POWDERLAYERBLOCK.get().defaultBlockState());
				return InteractionResult.SUCCESS;
			}
		}
		if (blockState.is(BlockRegistry.POWDERLAYERBLOCK.get()) && !context.getPlayer().isCrouching()) {
			return extracted(context, blockState, blockState.getValue(PowderSnowLayerBlock.LAYERS));
		}
		if (blockState.is(Blocks.POWDER_SNOW) && !context.getPlayer().isCrouching()) {
			return extracted(context, blockState, 8);
		}
		return super.useOn(context);
	}
	
	private InteractionResult extracted(UseOnContext context, BlockState blockState, int layers) {
		CompoundTag tag = context.getPlayer().getItemInHand(context.getHand()).getOrCreateTag();
			if (tag.contains("winteressentials:powdersnow")) {
				int current = tag.getInt("winteressentials:powdersnow");
				if (current + layers > maxSnow) {
					tag.putInt("winteressentials:powdersnow", maxSnow);
					layers = current + layers - maxSnow;
				} else {
					tag.putInt("winteressentials:powdersnow", current + layers);
					layers = 0;
				}
			}
			else {
				tag.putInt("winteressentials:powdersnow", layers);
				layers = 0;
			}
			if (layers == 0) {
				context.getLevel().removeBlock(context.getClickedPos(), false);
				return InteractionResult.SUCCESS;
			}
			context.getLevel().setBlockAndUpdate(context.getClickedPos(), blockState.setValue(PowderSnowLayerBlock.LAYERS, layers));
			return InteractionResult.SUCCESS;
		}
		
		@Override
		public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
			return false;
		}
		
		@Override
		public boolean isEnchantable(ItemStack p_41456_) {
			return false;
		}
		
		@Override
		public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
			return false;
		}
		
		@Override
		public int getBarWidth(ItemStack p_150900_) {
			if (p_150900_.getOrCreateTag().contains("winteressentials:powdersnow")) {
				return (int)(((float)p_150900_.getOrCreateTag().getInt("winteressentials:powdersnow")/(float)maxSnow) * 13);
			}
			return 0;
		}
		
		@Override
		public boolean isBarVisible(ItemStack p_150899_) {
			return true;
		}
		
		@Override
		public void appendHoverText(ItemStack p_41421_, Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
			if (p_41421_.getOrCreateTag().contains("winteressentials:powdersnow")) {
				String s = p_41421_.getOrCreateTag().getInt("winteressentials:powdersnow") + "";
				TextComponent compound = new TextComponent(s + " Layers");
				compound.withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC);
				p_41423_.add(1,compound);
			}
			super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
		}
		
	}
