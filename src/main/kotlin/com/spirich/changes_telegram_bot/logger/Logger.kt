package com.spirich.changes_telegram_bot.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

interface Logger {
	val log: Logger
		get() = LoggerFactory.getLogger(this::class.java)
}