package com.ferri.arnus.winteressentials.block;

import java.util.Random;

import com.ferri.arnus.winteressentials.LevelExtension;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class MeltingSnow extends SnowLayerBlock{

	public MeltingSnow() {
		super(Properties.of(Material.TOP_SNOW).noOcclusion().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking((p_187417_, p_187418_, p_187419_) -> {
			return p_187417_.getValue(SnowLayerBlock.LAYERS) >= 8;
		}));
	}
	
	@Override
	public void randomTick(BlockState p_56615_, ServerLevel p_56616_, BlockPos p_56617_, Random p_56618_) {
		super.randomTick(p_56615_, p_56616_, p_56617_, p_56618_);
		if (!((LevelExtension) p_56616_).isSnowing()) {
			if (p_56615_.getValue(SnowLayerBlock.LAYERS) == 1) {
				p_56616_.removeBlock(p_56617_, false);
			}else {
				BlockState state = p_56615_.setValue(SnowLayerBlock.LAYERS, p_56615_.getValue(SnowLayerBlock.LAYERS)-1);
				p_56616_.setBlockAndUpdate(p_56617_, state);
			}
		}
	}

}
