package org.pkozak.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import org.pkozak.McMovieEditionClient;
import org.pkozak.Sounds;
import org.pkozak.Trigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientNetworkHandlerMixin {
    @Unique
    private boolean hasPlayerDied = false;

    @Unique
    private boolean loadedNonce = false;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", shift = At.Shift.AFTER), method = "onDeathMessage(Lnet/minecraft/network/packet/s2c/play/DeathMessageS2CPacket;)V")
    public void onDeathMessage(DeathMessageS2CPacket packet, CallbackInfo ci) {
        hasPlayerDied = true;
    }

    // I tried to be all fancy with this and tried using @Local, but it didn't work, so there is this incredibly stupid solution
    @Inject(at = @At("TAIL"), method = "onPlayerRespawn(Lnet/minecraft/network/packet/s2c/play/PlayerRespawnS2CPacket;)V")
    public void onPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && hasPlayerDied) {
            player.playSoundToPlayer(Sounds.Companion.getSTEVE_EVENT(), SoundCategory.PLAYERS, 1.0f, 1.0f);
            hasPlayerDied = false;
        }
    }

    // This gets triggered every time the player loads into a world (dimension change counts). Only the first load event is important.
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/WorldLoadingState;tick()V", shift = At.Shift.AFTER), method = "tick()V")
    public void tick(CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (MinecraftClient.getInstance().player == null || loadedNonce) return;

        if (player.getHealth() == 0.0f) {
            hasPlayerDied = true;
        } else {
            player.playSoundToPlayer(Sounds.Companion.getSTEVE_EVENT(), SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

        loadedNonce = true;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;getStack()Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER), method = "onItemPickupAnimation(Lnet/minecraft/network/packet/s2c/play/ItemPickupAnimationS2CPacket;)V")
    public void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            Item item = ((ItemEntity) entity).getStack().getItem();
            for (Trigger trigger : McMovieEditionClient.INSTANCE.getTriggers()) {
                if (item == trigger.getItem()) {
                    player.playSoundToPlayer(trigger.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }
}