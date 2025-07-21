package org.pkozak.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import org.pkozak.Sounds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Final
    private SoundManager soundManager;

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;createInitScreens(Ljava/util/List;)Z", shift = At.Shift.AFTER), method = "onInitFinished")
    public void onInitFinished(CallbackInfoReturnable<Runnable> ci) {
        SoundInstance sound = PositionedSoundInstance.master(Sounds.Companion.getMINECRAFT_EVENT(), 1.0F);
        soundManager.play(sound);
    }
}
