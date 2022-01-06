package com.ferri.arnus.winteressentials.crafting;

import com.ferri.arnus.winteressentials.WinterEssentials;
import com.ferri.arnus.winteressentials.item.ItemRegistry;
import com.google.gson.JsonObject;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.UpgradeRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class SnowShoeUpgradeRecipe extends UpgradeRecipe{
	
	public static final RecipeSerializer<SnowShoeUpgradeRecipe> SERIALIZER = new Serializer();
	
	public SnowShoeUpgradeRecipe(ResourceLocation p_44523_, Ingredient p_44524_, Ingredient p_44525_,
			ItemStack p_44526_) {
		super(p_44523_, p_44524_, p_44525_, p_44526_);
	}
	
	@Override
	public boolean matches(Container p_44002_, Level p_44003_) {
		if (!(p_44002_.getItem(0).getItem() instanceof ArmorItem armor && p_44002_.getItem(0).canEquip(EquipmentSlot.FEET, null))) {
			return false;
		}
		if (p_44002_.getItem(0).is(ItemRegistry.SKATES.get()) || p_44002_.getItem(0).is(ItemRegistry.SNOWSHOES.get())) {
			return false;
		}
		if (!p_44002_.getItem(1).is(ItemRegistry.SNOWSHOE.get())) {
			return false;
		}
		return true;
	}

	@Override
	public ItemStack assemble(Container p_44001_) {
		if (p_44001_.getItem(0).is(Items.LEATHER_BOOTS)) {
			ItemStack result = new ItemStack(ItemRegistry.SNOWSHOES.get());
			CompoundTag tag = p_44001_.getItem(0).copy().getTag();
			if (tag != null) {
				result.setTag(tag);
			}
			return result;
		}
		ItemStack result = p_44001_.getItem(0).copy();
		result.getOrCreateTagElement(WinterEssentials.MODID + ":snowshoe");
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
		return true;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}
	
	public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SnowShoeUpgradeRecipe> {

		@Override
		public SnowShoeUpgradeRecipe fromJson(ResourceLocation p_44103_, JsonObject p_44104_) {
			ItemStack s = new ItemStack(ItemRegistry.SNOWSHOE.get());
			s.setCount(1);
			return new SnowShoeUpgradeRecipe(p_44103_, Ingredient.of(Items.LEATHER_BOOTS), Ingredient.of(s), new ItemStack(ItemRegistry.SNOWSHOES.get()));
		}

		@Override
		public SnowShoeUpgradeRecipe fromNetwork(ResourceLocation p_44105_, FriendlyByteBuf p_44106_) {
			ItemStack s = new ItemStack(ItemRegistry.SNOWSHOE.get());
			s.setCount(1);
			return new SnowShoeUpgradeRecipe(p_44105_, Ingredient.of(Items.LEATHER_BOOTS), Ingredient.of(s), new ItemStack(ItemRegistry.SNOWSHOES.get()));
		}

		@Override
		public void toNetwork(FriendlyByteBuf p_44101_, SnowShoeUpgradeRecipe p_44102_) {
			// TODO Auto-generated method stub
			
		}
		
	}


}
