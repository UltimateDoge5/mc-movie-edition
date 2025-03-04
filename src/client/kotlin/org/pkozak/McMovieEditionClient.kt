package org.pkozak

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.fabricmc.fabric.api.event.player.UseItemCallback
import net.minecraft.block.Blocks
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.Entity
import net.minecraft.entity.mob.ZombieEntity
import net.minecraft.entity.passive.ChickenEntity
import net.minecraft.item.Items
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.SoundCategory
import net.minecraft.util.ActionResult
import net.minecraft.util.math.Box
import net.minecraft.world.RaycastContext
import org.slf4j.LoggerFactory

object McMovieEditionClient : ClientModInitializer {
    val logger = LoggerFactory.getLogger("mc-movie-edition")
    private var seenJockeys = mutableSetOf<Int>()
    var triggers = mutableSetOf<Trigger>()

    // For some reason, the events are called twice, so this helps to differentiate between them
    var itemUseNonce = 0
    var blockUseNonce = 0

    override fun onInitializeClient() {
        registerSounds()

        UseBlockCallback.EVENT.register { player, world, hand, hitResult ->
            val block = world.getBlockState(hitResult.blockPos)
            val itemInHand = player.getStackInHand(hand).item

            for (trigger in triggers) {
                // These have to be separated as we wouldn't want to play the sound twice
                // or some just don't fit as right-clicking with a crafting is not it
                if (trigger.isOnBlockUse() && block.isOf(trigger.block)) {
                    if (blockUseNonce == 0) {
                        player.playSoundToPlayer(trigger.soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f)
                        blockUseNonce += 1
                    } else {
                        blockUseNonce = 0
                    }
                }

                // This also has to be here as this catches the right-click event of an item on a block e.g.,
                // igniting a block with flint and steel is handled here, not in the UseItemCallback
                if (trigger.isOnItemUse() && itemInHand == trigger.item) {
                    if (itemUseNonce == 0) {
                        player.playSoundToPlayer(trigger.soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f)
                        itemUseNonce += 1
                    } else {
                        itemUseNonce = 0
                    }
                }
            }

            return@register ActionResult.PASS
        }

        UseItemCallback.EVENT.register { player, world, hand ->
            val item = player.getStackInHand(hand).item

            for (trigger in triggers) {
                if (item == trigger.item) {
                    if (itemUseNonce == 0) {
                        player.playSoundToPlayer(trigger.soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f)
                        itemUseNonce += 1
                    } else {
                        itemUseNonce = 0
                    }
                    return@register ActionResult.PASS
                }
            }

            return@register ActionResult.PASS
        }

        // Check for nearby entities, if a chicken has a zombie riding it, and if it's in the players' view.
        // Without the visibility check it's kinda op, philza would benefit from this.
        WorldRenderEvents.END.register{ event ->
            val client = MinecraftClient.getInstance()
            val player = client.player
            val world = client.world

            if (player != null && world != null) {
                val frustum = event.frustum()
                val playerPos = player.pos

                // May have to tweak this value, rn its arbitrary
                val searchBox = Box(playerPos.add(-10.0, -10.0, -10.0), playerPos.add(10.0, 10.0, 10.0))

                for (entity in world.getEntitiesByClass(Entity::class.java, searchBox) {
                    it is ChickenEntity && it.hasPassenger { it is ZombieEntity }
                }) {
                    val isVisible = player.canSee(
                        entity,
                        RaycastContext.ShapeType.VISUAL,
                        RaycastContext.FluidHandling.NONE,
                        entity.eyeY
                    ) && frustum!!.isVisible(entity.boundingBox)

                    if (!seenJockeys.contains(entity.id) && isVisible) {
                        player.playSoundToPlayer(Sounds.CHICKEN_JOKEY_EVENT, SoundCategory.HOSTILE, 1.0f, 1.0f)
                        seenJockeys.add(entity.id)
                    } else if (seenJockeys.contains(entity.id) && !isVisible) {
                        seenJockeys.remove(entity.id)
                    }
                }
            }
        }
    }

    private fun registerSounds() {
        Registry.register(Registries.SOUND_EVENT, Sounds.CRAFTING_TABLE, Sounds.CRAFTING_TABLE_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.STEVE, Sounds.STEVE_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.ENDERPEARL, Sounds.ENDERPEARL_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.FLINT_AND_STEEL, Sounds.FLINT_AND_STEEL_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.WATER_BUCKET, Sounds.WATER_BUCKET_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.OVERWORLD, Sounds.OVERWORLD_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.NETHER, Sounds.NETHER_EVENT)
        Registry.register(Registries.SOUND_EVENT, Sounds.CHICKEN_JOKEY, Sounds.CHICKEN_JOKEY_EVENT)

        triggers.add(Trigger(Sounds.CRAFTING_TABLE_EVENT).apply {
            onBlockUse(Blocks.CRAFTING_TABLE)
        })

        triggers.add(Trigger(Sounds.ENDERPEARL_EVENT).apply {
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