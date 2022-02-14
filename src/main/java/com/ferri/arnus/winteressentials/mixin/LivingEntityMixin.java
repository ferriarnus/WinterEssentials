package com.ferri.arnus.winteressentials.mixin;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.ferri.arnus.winteressentials.block.BlockRegistry;
import com.ferri.arnus.winteressentials.block.PowderSnowLayerBlock;
import com.ferri.arnus.winteressentials.item.ItemRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity{
	
	@Shadow
	boolean jumping;
	
	@Unique
	private static final UUID SPEED_MODIFIER_SNOW_SPEED_UUID = UUID.fromString("146b38dd-8d48-460e-8d26-c33c3ed022af");
	
	@Unique
	private static final UUID SPEED_MODIFIER_ICE_SPEED_UUID = UUID.fromString("530c11c4-22b0-43da-9805-32decc530a82");
	
	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot p_21127_);
	
	@Shadow
	public abstract AttributeInstance getAttribute(Attribute a);

	public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
		super(p_19870_, p_19871_);
	}
	
	private boolean onSnowBlock() {
		return this.level.getBlockState(getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.SNOW) || this.level.getBlockState(blockPosition()).is(BlockTags.SNOW);
	}
	
	protected boolean onIceBlock() {
	      return this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).is(BlockTags.ICE);
	   }
	
	@Inject(at = @At("TAIL"), method = "tryAddSoulSpeed()V", cancellable = true)
	private void tryAddSnowSpeed(CallbackInfo callback) {
		if (!this.getBlockStateOn().isAir()) {
			 if (this.onSnowBlock() && this.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.SNOWSHOES.get())) {
				 AttributeInstance attributeinstance = getAttribute(Attributes.MOVEMENT_SPEED);
				 if (attributeinstance == null) {
					 return;
				 }
				 
				 attributeinstance.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_SNOW_SPEED_UUID, "Snow speed boost", (double)(0.03F * (1.1F)), AttributeModifier.Operation.ADDITION));
			 }
			 if (this.onIceBlock() && this.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.SKATES.get())) {
				 AttributeInstance attributeinstance = getAttribute(Attributes.MOVEMENT_SPEED);
				 if (attributeinstance == null) {
					 return;
				 }
				 
				 attributeinstance.addTransientModifier(new AttributeModifier(SPEED_MODIFIER_ICE_SPEED_UUID, "Ice speed boost", (double)(0.03F * (1.3F)), AttributeModifier.Operation.ADDITION));
			 }
		}
	}
	
	@Inject(at = @At("TAIL"), method = "removeSoulSpeed()V", cancellable = true)
	private void tryRemoveSnowSpeed(CallbackInfo callback) {
		AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
		if (attributeinstance != null) {
			if (attributeinstance.getModifier(SPEED_MODIFIER_SNOW_SPEED_UUID) != null) {
				attributeinstance.removeModifier(SPEED_MODIFIER_SNOW_SPEED_UUID);
			}
			if (attributeinstance.getModifier(SPEED_MODIFIER_ICE_SPEED_UUID) != null) {
				attributeinstance.removeModifier(SPEED_MODIFIER_ICE_SPEED_UUID);
			}
		}
	}
	
	@Inject(at = @At("TAIL"), method = "handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;", cancellable = true)
	public void jumpInSnow(Vec3 p_21075_, float p_21076_, CallbackInfoReturnable<Vec3> callback) {
		if ((this.horizontalCollision || this.jumping) && (this.getFeetBlockState().is(BlockRegistry.POWDERLAYERBLOCK.get()) && PowderSnowBlock.canEntityWalkOnPowderSnow(this) && (this.blockPosition().getY() - this.getY()) < (this.level.getBlockState(blockPosition()).getValue(PowderSnowLayerBlock.LAYERS)-1) * 0.125D )) {
	         callback.setReturnValue(new Vec3(callback.getReturnValue().x, 0.2D, callback.getReturnValue().z));
	      }
	}
	
	//@Redirect(at = @At(target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F", value = "INVOKE"), method = "travel(Lnet/minecraft/world/phys/Vec3;)V")
	private float frictionOnSkates(BlockState state, LevelReader world, BlockPos pos, Entity entity) {
		if(entity instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.SKATES.get()) && state.is(BlockTags.ICE)) {
			return state.getFriction(world, pos, entity) - 0.12F;
		}
		return state.getFriction(world, pos, entity);
	}
	
}
