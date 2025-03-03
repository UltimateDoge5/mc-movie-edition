package org.pkozak.mixin.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;
import org.pkozak.IAmSteveClient;
import org.pkozak.Trigger;
import org.pkozak.TriggerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ItemStack.class)
public abstract class ItemEventsMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(at = @At("HEAD"), method = "onCraftByPlayer(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;I)V")
    private void onCraftByPlayer(World world, PlayerEntity player, int amount, CallbackInfo ci) {
        Item item = this.getItem();
        for (Trigger trigger : IAmSteveClient.INSTANCE.getTriggers()) {
            if (trigger.getTriggerTypes().contains(TriggerType.Craft) && item == trigger.getItem()) {
                player.playSoundToPlayer(trigger.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }
}

