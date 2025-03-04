package org.pkozak

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object McMovieEdition : ModInitializer {
    private val logger = LoggerFactory.getLogger("mc-movie-edition")

	override fun onInitialize() {
		logger.info("I am Steve")
	}
}