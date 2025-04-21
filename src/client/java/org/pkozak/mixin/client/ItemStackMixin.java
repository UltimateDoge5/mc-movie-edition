package org.pkozak.mixin.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import org.pkozak.McMovieEditionClient;
import org.pkozak.Trigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(at = @At("HEAD"), method = "onCraftByPlayer(Lnet/minecraft/entity/player/PlayerEntity;I)V")
    private void onCraftByPlayer(PlayerEntity player, int amount, CallbackInfo ci) {
        Item item = this.getItem();
        for (Trigger trigger : McMovieEditionClient.INSTANCE.getTriggers()) {
            if (trigger.isOnCraft() && item == trigger.getItem()) {
                if (trigger.getDebounceVar()) {
                    player.playSoundToPlayer(trigger.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                    trigger.setDebounceVar(false);
                } else {
                    trigger.setDebounceVar(true);
                }
            }
        }
    }
}

