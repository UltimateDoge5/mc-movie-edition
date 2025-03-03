package org.pkozak

import net.minecraft.sound.SoundEvent
import net.minecraft.util.Identifier

class Sounds {
    companion object {
        val CRAFTING_TABLE: Identifier = Identifier.of("iamsteve:crafting_table");
        val CRAFTING_TABLE_EVENT: SoundEvent = SoundEvent.of(CRAFTING_TABLE);

        val STEVE: Identifier = Identifier.of("iamsteve:steve");
        val STEVE_EVENT: SoundEvent = SoundEvent.of(STEVE);
    }
}