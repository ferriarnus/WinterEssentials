package com.ferri.arnus.winteressentials.block;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PowderSnowLayerBlock extends PowderSnowBlock{
	public static final int MAX_HEIGHT = 8;
	public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
	protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{Shapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
	public static final int HEIGHT_IMPASSABLE = 5;
	private static final VoxelShape FALLING_COLLISION_SHAPE = Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, (double)0.9F, 1.0D);
	
	public PowderSnowLayerBlock() {
		super(Properties.of(Material.TOP_SNOW).randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking((p_187417_, p_187418_, p_187419_) -> {
		      return p_187417_.getValue(PowderSnowLayerBlock.LAYERS) >= 8;
		   }));
	}
	
	@Override
	public boolean isPathfindable(BlockState p_56592_, BlockGetter p_56593_, BlockPos p_56594_, PathComputationType p_56595_) {
		switch(p_56595_) {
		case LAND:
			return p_56592_.getValue(LAYERS) < 5;
		case WATER:
			return false;
		case AIR:
			return false;
		default:
			return false;
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState p_56620_, BlockGetter p_56621_, BlockPos p_56622_, CollisionContext p_56623_) {
		return SHAPE_BY_LAYER[p_56620_.getValue(LAYERS)];
	}
	
	@Override
	public VoxelShape getCollisionShape(BlockState p_154285_, BlockGetter p_154286_, BlockPos p_154287_, CollisionContext p_154288_) {
	      if (p_154288_ instanceof EntityCollisionContext) {
	         EntityCollisionContext entitycollisioncontext = (EntityCollisionContext)p_154288_;
	         Entity entity = entitycollisioncontext.getEntity();
	         if (entity != null) {
	            if (entity.fallDistance > 2.5F) {
	               return SHAPE_BY_LAYER[p_154285_.getValue(LAYERS) - 1];
	            }

	            boolean flag = entity instanceof FallingBlockEntity;
	            if (flag || canEntityWalkOnPowderSnow(entity) && p_154288_.isAbove(SHAPE_BY_LAYER[p_154285_.getValue(LAYERS) - 1], p_154287_, false) && !p_154288_.isDescending()) {
	               return SHAPE_BY_LAYER[p_154285_.getValue(LAYERS) - 1];
	            }
	         }
	      }

	      return Shapes.empty();
	   }
	
	@Override
	public VoxelShape getBlockSupportShape(BlockState p_56632_, BlockGetter p_56633_, BlockPos p_56634_) {
		return SHAPE_BY_LAYER[p_56632_.getValue(LAYERS)];
	}
	
	@Override
	public VoxelShape getVisualShape(BlockState p_56597_, BlockGetter p_56598_, BlockPos p_56599_, CollisionContext p_56600_) {
		return SHAPE_BY_LAYER[p_56597_.getValue(LAYERS)];
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState p_56630_) {
		return true;
	}
	
	@Override
	public boolean canSurvive(BlockState p_56602_, LevelReader p_56603_, BlockPos p_56604_) {
		BlockState blockstate = p_56603_.getBlockState(p_56604_.below());
		if (!blockstate.is(Blocks.ICE) && !blockstate.is(Blocks.PACKED_ICE) && !blockstate.is(Blocks.BARRIER)) {
			if (!blockstate.is(Blocks.HONEY_BLOCK) && !blockstate.is(Blocks.SOUL_SAND)) {
				return Block.isFaceFull(blockstate.getCollisionShape(p_56603_, p_56604_.below()), Direction.UP) || blockstate.is(this) && blockstate.getValue(LAYERS) == 8;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public BlockState updateShape(BlockState p_56606_, Direction p_56607_, BlockState p_56608_, LevelAccessor p_56609_, BlockPos p_56610_, BlockPos p_56611_) {
		return !p_56606_.canSurvive(p_56609_, p_56610_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_56606_, p_56607_, p_56608_, p_56609_, p_56610_, p_56611_);
	}
	
	@Override
	public boolean canBeReplaced(BlockState p_56589_, BlockPlaceContext p_56590_) {
		int i = p_56589_.getValue(LAYERS);
		if (p_56590_.getItemInHand().is(this.asItem()) && i < 8) {
			if (p_56590_.replacingClickedOnBlock()) {
				return p_56590_.getClickedFace() == Direction.UP;
			} else {
				return true;
			}
		} else {
			return i == 1;
		}
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_56587_) {
		BlockState blockstate = p_56587_.getLevel().getBlockState(p_56587_.getClickedPos());
		if (blockstate.is(this)) {
			int i = blockstate.getValue(LAYERS);
			return blockstate.setValue(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
		} else {
			return super.getStateForPlacement(p_56587_);
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_56613_) {
		p_56613_.add(LAYERS);
	}
	
	@Override
	public void entityInside(BlockState p_154263_, Level p_154264_, BlockPos p_154265_, Entity p_154266_) {
		if (!(p_154266_ instanceof LivingEntity) || ((p_154266_.getFeetBlockState().is(this) && (p_154266_.getY() - p_154265_.getY()) < (p_154263_.getValue(LAYERS)-1) * 0.125D ))) {
			p_154266_.makeStuckInBlock(p_154263_, new Vec3((double)0.9F, 1.5D, (double)0.9F));
			if (p_154264_.isClientSide) {
				Random random = p_154264_.getRandom();
				boolean flag = p_154266_.xOld != p_154266_.getX() || p_154266_.zOld != p_154266_.getZ();
				if (flag && random.nextBoolean()) {
					p_154264_.addParticle(ParticleTypes.SNOWFLAKE, p_154266_.getX(), (double)(p_154265_.getY() + 1), p_154266_.getZ(), (double)(Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F));
				}
			}
		}
		if ((p_154266_.getY() - p_154265_.getY()) < (p_154263_.getValue(LAYERS)-1) * 0.125D ) {
			p_154266_.setIsInPowderSnow(true);
			if (!p_154264_.isClientSide) {
				if (p_154266_.isOnFire() && (p_154264_.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || p_154266_ instanceof Player) && p_154266_.mayInteract(p_154264_, p_154265_)) {
					p_154264_.destroyBlock(p_154265_, false);
				}
				
				p_154266_.setSharedFlagOnFire(false);
			}
		}
	}
}
