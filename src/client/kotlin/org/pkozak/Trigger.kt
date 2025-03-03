package org.pkozak

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.sound.SoundEvent

class Trigger(public val soundEvent: SoundEvent) {
    public var block: Block? = null
    public var item: Item? = null
    var triggers = mutableSetOf<TriggerType>()

    public fun getTriggerTypes(): Set<TriggerType> {
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
}

enum class TriggerType {
    UseItem,
    UseBlock,
    Craft,
}
