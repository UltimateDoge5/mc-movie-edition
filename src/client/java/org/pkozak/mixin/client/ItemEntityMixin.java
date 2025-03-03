package org.pkozak.mixin.client;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {
    @Inject(at = @At("HEAD"), method = "onPlayerCollision(Lnet/minecraft/entity/player/PlayerEntity;)V ")
    public void onPlayerCollision(PlayerEntity player, CallbackInfo ci) {

    }
}
