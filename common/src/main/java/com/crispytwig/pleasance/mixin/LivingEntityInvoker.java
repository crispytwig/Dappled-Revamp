package com.crispytwig.pleasance.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityInvoker {
    @Invoker("calculateFallDamage")
    int pleasance$calculateFallDamage(double fallDistance, float damageModifier);
}
