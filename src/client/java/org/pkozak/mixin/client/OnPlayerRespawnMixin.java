package org.pkozak.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import org.pkozak.Sounds;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class OnPlayerRespawnMixin {
    // I tried to be all fancy with this and tried using @Local, but it didn't work, so there is this incredibly stupid solution
    @Inject(at = @At("TAIL"), method = "onPlayerRespawn(Lnet/minecraft/network/packet/s2c/play/PlayerRespawnS2CPacket;)V")
    public void onPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.playSoundToPlayer(Sounds.Companion.getSTEVE_EVENT(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    @Inject(at = @At("TAIL"), method = "onGameJoin(Lnet/minecraft/network/packet/s2c/play/GameJoinS2CPacket;)V")
    public void onGameJoin(GameJoinS2CPacket packet, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.playSoundToPlayer(Sounds.Companion.getSTEVE_EVENT(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }
}