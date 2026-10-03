package com.crispytwig.dappledrevamp.mixin;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.tags.ModBiomeTags;
import com.crispytwig.dappledrevamp.world.entity.animal.fox.GreyFox;
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
    private Fox dappledRevamp$self() {
        return (Fox) (Object) this;
    }

    @Override
    public boolean dappledRevamp$isGrey() {
        return DappledRevamp.greyFoxStorage().isGrey(this.dappledRevamp$self());
    }

    @Override
    public void dappledRevamp$setGrey(boolean grey) {
        DappledRevamp.greyFoxStorage().setGrey(this.dappledRevamp$self(), grey);
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"))
    private void dappledRevamp$spawnGrey(
        ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData,
        CallbackInfoReturnable<SpawnGroupData> cir
    ) {
        Fox self = this.dappledRevamp$self();
        if (self.getVariant() == Fox.Variant.RED && level.getBiome(self.blockPosition()).is(ModBiomeTags.SPAWNS_GREY_FOXES)) {
            this.dappledRevamp$setGrey(true);
        }
    }

    @Inject(
        method = "getBreedOffspring(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/AgeableMob;)Lnet/minecraft/world/entity/animal/fox/Fox;",
        at = @At("RETURN")
    )
    private void dappledRevamp$inheritGrey(ServerLevel level, AgeableMob partner, CallbackInfoReturnable<Fox> cir) {
        if (!(cir.getReturnValue() instanceof GreyFox baby) || !(partner instanceof GreyFox other)) {
            return;
        }
        boolean mine = this.dappledRevamp$isGrey();
        boolean theirs = other.dappledRevamp$isGrey();
        baby.dappledRevamp$setGrey(mine == theirs ? mine : this.dappledRevamp$self().getRandom().nextBoolean());
    }
}
