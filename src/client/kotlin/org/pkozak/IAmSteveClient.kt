package org.pkozak

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.block.Blocks
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import org.pkozak.Sounds.Companion.CRAFTING_TABLE_EVENT
import org.pkozak.Sounds.Companion.ENDERPEARL_EVENT

object IAmSteveClient : ClientModInitializer {
    var triggers = mutableSetOf<Trigger>()

    // TODO: Play the sound in the main menu upon loading
    // TODO: Play the overworld/the nether sound upon changing the dimension
    override fun onInitializeClient() {
        registerSounds()

        UseBlockCallback.EVENT.register { player, world, _, hitResult ->
            val block = world.getBlockState(hitResult.blockPos)
            for (trigger in triggers) {
                if (block.isOf(trigger.block)) {
                    player.playSoundToPlayer(trigger.soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f)
                    return@register ActionResult.PASS
                }
            }

            return@register ActionResult.PASS
        }

        UseItemCallback.EVENT.register { player, world, hand ->
            val item = player.getStackInHand(hand).item
            for (trigger in triggers) {
                if (item == trigger.item) {
                    player.playSoundToPlayer(trigger.soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f)
                    return@register ActionResult.PASS
                }
            }

            return@register ActionResult.PASS
        }
    }

    private fun registerSounds() {
        Registry.register(Registries.SOUND_EVENT, Sounds.CRAFTING_TABLE, Sounds.CRAFTING_TABLE_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.STEVE, Sounds.STEVE_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.ENDERPEARL, Sounds.ENDERPEARL_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.FLINT_AND_STEEL, Sounds.FLINT_AND_STEEL_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.WATER_BUCKET, Sounds.WATER_BUCKET_EVENT)

        triggers.add(Trigger(CRAFTING_TABLE_EVENT).apply {
            onBlockUse(Blocks.CRAFTING_TABLE)
        })

        triggers.add(Trigger(ENDERPEARL_EVENT).apply {
            onItemUse(Items.ENDER_PEARL)
        })

        triggers.add(Trigger(Sounds.FLINT_AND_STEEL_EVENT).apply {
            onItemUse(Items.FLINT_AND_STEEL)
            onCraft(Items.FLINT_AND_STEEL)
        })

        triggers.add(Trigger(Sounds.WATER_BUCKET_EVENT).apply {
            onItemUse(Items.WATER_BUCKET)
        })
    }
}