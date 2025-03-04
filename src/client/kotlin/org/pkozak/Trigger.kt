package org.pkozak

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.sound.SoundEvent

class Trigger(val soundEvent: SoundEvent) {
    var block: Block? = null
    var item: Item? = null
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
}

