package org.pkozak

import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

class Sounds {
    companion object {
        val MINECRAFT: Identifier = Identifier.of("mc-movie-edition:minecraft")
        val MINECRAFT_EVENT: SoundEvent = SoundEvent.of(MINECRAFT)

        val CRAFTING_TABLE: Identifier = Identifier.of("mc-movie-edition:crafting_table")
        val CRAFTING_TABLE_EVENT: SoundEvent = SoundEvent.of(CRAFTING_TABLE)

        val STEVE: Identifier = Identifier.of("mc-movie-edition:steve")
        val STEVE_EVENT: SoundEvent = SoundEvent.of(STEVE)

        val ENDERPEARL: Identifier = Identifier.of("mc-movie-edition:enderpearl")
        val ENDERPEARL_EVENT: SoundEvent = SoundEvent.of(ENDERPEARL)

        val FLINT_AND_STEEL: Identifier = Identifier.of("mc-movie-edition:flint_and_steel")
        val FLINT_AND_STEEL_EVENT: SoundEvent = SoundEvent.of(FLINT_AND_STEEL)

        val WATER_BUCKET: Identifier = Identifier.of("mc-movie-edition:water_bucket")
        val WATER_BUCKET_EVENT: SoundEvent = SoundEvent.of(WATER_BUCKET)

        val OVERWORLD: Identifier = Identifier.of("mc-movie-edition:overworld")
        val OVERWORLD_EVENT: SoundEvent = SoundEvent.of(OVERWORLD)

        val NETHER: Identifier = Identifier.of("mc-movie-edition:nether")
        val NETHER_EVENT: SoundEvent = SoundEvent.of(NETHER)

        val CHICKEN_JOKEY: Identifier = Identifier.of("mc-movie-edition:chicken_jockey")
        val CHICKEN_JOKEY_EVENT: SoundEvent = SoundEvent.of(CHICKEN_JOKEY)
    }
}