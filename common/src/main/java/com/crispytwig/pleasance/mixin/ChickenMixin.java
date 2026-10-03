package com.crispytwig.pleasance.mixin;

import com.crispytwig.pleasance.world.entity.animal.worm.Worm;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chicken.class)
public abstract class ChickenMixin extends Animal {
    private ChickenMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void pleasance$addAttackDamage(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(Attributes.ATTACK_DAMAGE, 2.0);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void pleasance$huntWorms(CallbackInfo ci) {
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.0, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Worm.class, 40, true, false, null));
    }
}
