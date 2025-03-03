package org.pkozak

import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

class Sounds {
    companion object {
        val CRAFTING_TABLE: Identifier = Identifier.of("iamsteve:crafting_table")
        val CRAFTING_TABLE_EVENT: SoundEvent = SoundEvent.of(CRAFTING_TABLE)

        val STEVE: Identifier = Identifier.of("iamsteve:steve")
        val STEVE_EVENT: SoundEvent = SoundEvent.of(STEVE)

        val ENDERPEARL: Identifier = Identifier.of("iamsteve:enderpearl")
        val ENDERPEARL_EVENT: SoundEvent = SoundEvent.of(ENDERPEARL)

        val FLINT_AND_STEEL: Identifier = Identifier.of("iamsteve:flint_and_steel")
        val FLINT_AND_STEEL_EVENT: SoundEvent = SoundEvent.of(FLINT_AND_STEEL)

        val WATER_BUCKET: Identifier = Identifier.of("iamsteve:water_bucket")
        val WATER_BUCKET_EVENT: SoundEvent = SoundEvent.of(WATER_BUCKET)
    }
}