package org.pkozak

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object IAmSteve : ModInitializer {
    private val logger = LoggerFactory.getLogger("i-am-steve")

	override fun onInitialize() {
		logger.info("I am Steve")
	}
}