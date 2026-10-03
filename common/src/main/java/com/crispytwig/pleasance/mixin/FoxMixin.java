package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.Pleasance;
import com.crispytwig.pleasance.tags.ModBiomeTags;
import com.crispytwig.pleasance.world.entity.animal.fox.GreyFox;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Fox.class)
public abstract class FoxMixin implements GreyFox {
    @Unique
    private Fox pleasance$self() {
        return (Fox) (Object) this;
    }

    @Override
    public boolean pleasance$isGrey() {
        return Pleasance.greyFoxStorage().isGrey(this.pleasance$self());
    }

    @Override
    public void pleasance$setGrey(boolean grey) {
        Pleasance.greyFoxStorage().setGrey(this.pleasance$self(), grey);
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void pleasance$spawnGrey(
        ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData,
        CallbackInfoReturnable<SpawnGroupData> cir
    ) {
        Fox self = this.pleasance$self();
        if (self.getVariant() == Fox.Variant.RED && level.getBiome(self.blockPosition()).is(ModBiomeTags.SPAWNS_GREY_FOXES)) {
            this.pleasance$setGrey(true);
        }
    }

    @Inject(
        method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/fox/Fox;",
        at = @At("RETURN")
    )
    private void pleasance$inheritGrey(ServerLevel level, AgeableMob partner, CallbackInfoReturnable<Fox> cir) {
        if (!(cir.getReturnValue() instanceof GreyFox baby) || !(partner instanceof GreyFox other)) {
            return;
        }
        boolean mine = this.pleasance$isGrey();
        boolean theirs = other.pleasance$isGrey();
        baby.pleasance$setGrey(mine == theirs ? mine : this.pleasance$self().getRandom().nextBoolean());
    }
}
