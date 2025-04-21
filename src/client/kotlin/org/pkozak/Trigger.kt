package org.pkozak

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.sound.SoundEvent

class Trigger(val soundEvent: SoundEvent) {
    var block: Block? = null
    var item: Item? = null

    // Sometimes the events are called twice, this lets me track them
    var debounceVar = false;
    var triggers = mutableSetOf<TriggerType>()

    fun getTriggerTypes(): Set<TriggerType> {
        return triggers
    }

    fun onBlockUse(block: Block) {
        triggers.add(TriggerType.UseBlock)
        this.block = block
    }

    fun onItemUse(item: Item) {
        triggers.add(TriggerType.UseItem)
        this.item = item
    }

    fun onCraft(item: Item) {
        triggers.add(TriggerType.Craft)
        this.item = item
    }

    fun isOnItemUse(): Boolean {
        return triggers.contains(TriggerType.UseItem)
    }

    fun isOnBlockUse(): Boolean {
        return triggers.contains(TriggerType.UseBlock)
    }

    fun isOnCraft(): Boolean {
        return triggers.contains(TriggerType.Craft)
    }
}

