package com.ferri.arnus.winteressentials.mixin;

import com.ferri.arnus.winteressentials.LevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import snownee.snow.ModUtil;
import snownee.snow.compat.sereneseasons.SereneSeasonsCompat;

@Mixin(ModUtil.class)
public abstract class ModUtilMixin {

    @Redirect(at = @At(value = "INVOKE_ASSIGN", target = "Lsnownee/snow/compat/sereneseasons/SereneSeasonsCompat;coldEnoughToSnow(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Holder;)Z"), method = "coldEnoughToSnow", remap = false)
    private static boolean moreSnow(Level level, BlockPos pos, Holder<Biome> biome) {
        return SereneSeasonsCompat.coldEnoughToSnow(level, pos, biome) || ((LevelExtension) level).isSnowing();
    }
}
