package org.pkozak.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.sound.SoundCategory;
import org.pkozak.IAmSteveClient;
import org.pkozak.Sounds;
import org.pkozak.Trigger;
import org.pkozak.TriggerType;
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

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ItemEntity;getStack()Lnet/minecraft/item/ItemStack;", shift = At.Shift.AFTER), method = "onItemPickupAnimation(Lnet/minecraft/network/packet/s2c/play/ItemPickupAnimationS2CPacket;)V")
    public void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet, CallbackInfo ci, @Local Entity entity) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            Item item = ((ItemEntity) entity).getStack().getItem();
            for (Trigger trigger : IAmSteveClient.INSTANCE.getTriggers()) {
                if (item == trigger.getItem()) {
                    player.playSoundToPlayer(trigger.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }
        }
    }
}