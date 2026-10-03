package com.crispytwig.dappledrevamp.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Zombie.class, AbstractSkeleton.class})
public abstract class PumpkinHeadMobMixin {
    @Unique
    private static final float dappledRevamp$PUMPKIN_CHANCE = 0.25F;
    @Unique
    private static final float dappledRevamp$JACK_O_LANTERN_CHANCE = 0.1F;

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void dappledRevamp$dappledForestPumpkin(
        ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData,
        CallbackInfoReturnable<SpawnGroupData> cir
    ) {
        Mob self = (Mob) (Object) this;
        if (self.getType() != EntityTypes.ZOMBIE && self.getType() != EntityTypes.SKELETON) {
            return;
        }
        RandomSource random = level.getRandom();
        if (self.getItemBySlot(EquipmentSlot.HEAD).isEmpty()
            && level.getBiome(self.blockPosition()).is(Biomes.DAPPLED_FOREST)
            && random.nextFloat() < dappledRevamp$PUMPKIN_CHANCE) {
            self.setItemSlot(EquipmentSlot.HEAD, new ItemStack(
                random.nextFloat() < dappledRevamp$JACK_O_LANTERN_CHANCE ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN
            ));
            self.setDropChance(EquipmentSlot.HEAD, 0.0F);
        }
    }
}
