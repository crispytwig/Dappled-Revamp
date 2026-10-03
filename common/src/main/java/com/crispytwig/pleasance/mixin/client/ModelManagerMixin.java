package com.crispytwig.pleasance.mixin.client;

import com.crispytwig.pleasance.client.renderer.block.WithoutOverlaysModel;
import com.crispytwig.pleasance.world.level.block.Moist;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.IdentityHashMap;
import java.util.Map;

@Mixin(ModelManager.class)
public abstract class ModelManagerMixin {
    @ModifyReturnValue(method = "createBlockStateToModelDispatch", at = @At("RETURN"))
    private static Map<BlockState, BlockStateModel> pleasance$hideMoistOverlays(Map<BlockState, BlockStateModel> models) {
        Map<BlockStateModel, BlockStateModel> wrapped = new IdentityHashMap<>();
        models.replaceAll((state, model) -> Moist.isMoist(state) ? wrapped.computeIfAbsent(model, WithoutOverlaysModel::new) : model);
        return models;
    }
}
