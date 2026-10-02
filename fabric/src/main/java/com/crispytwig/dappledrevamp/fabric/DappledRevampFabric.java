package com.crispytwig.dappledrevamp.fabric;

import com.crispytwig.dappledrevamp.DappledRevamp;
import com.crispytwig.dappledrevamp.fox.GreyFoxStorage;
import com.mojang.serialization.Codec;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.animal.fox.Fox;

public class DappledRevampFabric implements ModInitializer, GreyFoxStorage {
    private static final AttachmentType<Boolean> GREY = AttachmentRegistry.create(
        DappledRevamp.id("grey"),
        builder -> builder
            .persistent(Codec.BOOL)
            .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
    );

    @Override
    public void onInitialize() {
        DappledRevamp.init(this);
    }

    @Override
    public boolean isGrey(Fox fox) {
        return fox.getAttachedOrElse(GREY, false);
    }

    @Override
    public void setGrey(Fox fox, boolean grey) {
        fox.setAttached(GREY, grey ? Boolean.TRUE : null);
    }
}
