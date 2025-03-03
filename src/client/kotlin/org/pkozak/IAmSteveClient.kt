package org.pkozak

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.Blocks
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import org.pkozak.Sounds.Companion.CRAFTING_TABLE
import org.pkozak.Sounds.Companion.CRAFTING_TABLE_EVENT
import org.pkozak.Sounds.Companion.STEVE
import org.pkozak.Sounds.Companion.STEVE_EVENT

object IAmSteveClient : ClientModInitializer {
    private var playedJoinSound = false

    override fun onInitializeClient() {
        registerSounds();

        UseBlockCallback.EVENT.register { player, world, _, hitResult ->
            val block = world.getBlockState(hitResult.blockPos)
            if (!block.isOf(Blocks.CRAFTING_TABLE)) return@register ActionResult.PASS

            player.playSoundToPlayer(CRAFTING_TABLE_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f)
            return@register ActionResult.PASS;
        }


        ClientTickEvents.START_CLIENT_TICK.register { client ->
            val player = client.player
            if (player != null && playedJoinSound) {
                player.playSoundToPlayer(STEVE_EVENT, SoundCategory.PLAYERS, 1.0f, 1.0f)
                playedJoinSound = true
            }
        }
    }

    private fun registerSounds() {
        Registry.register(Registries.SOUND_EVENT, CRAFTING_TABLE, CRAFTING_TABLE_EVENT);
        Registry.register(Registries.SOUND_EVENT, STEVE, STEVE_EVENT);
    }
}