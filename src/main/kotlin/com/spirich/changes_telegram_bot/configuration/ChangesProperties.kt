package com.spirich.changes_telegram_bot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("changes")
data class ChangesProperties(
	val baseUrl: String,
	val downloadDirectory: String
)
