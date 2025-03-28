package org.pkozak.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.pkozak.Sounds;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldEventHandlerMixin {

    @Shadow
//    @Final
//    private World world;
    private ClientWorld world;

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "processWorldEvent")
    public void processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        // Activate only on the event of playing nether portal travel noise
        if (eventId != 1032 || client.player == null) return;

        if (this.world.getRegistryKey() == World.OVERWORLD) {
            client.player.playSoundToPlayer(Sounds.Companion.getOVERWORLD_EVENT(), SoundCategory.AMBIENT, 0.8f, 1.0f);
        } else {
            client.player.playSoundToPlayer(Sounds.Companion.getNETHER_EVENT(), SoundCategory.AMBIENT, 0.8f, 1.0f);
        }
    }
}
