package com.spirich.changes_telegram_bot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("bot")
data class BotProperties(
	val token: String,
	val username: String
)