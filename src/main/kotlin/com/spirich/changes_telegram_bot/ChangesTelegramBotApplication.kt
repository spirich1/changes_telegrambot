package com.spirich.changes_telegram_bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ChangesTelegramBotApplication

fun main(args: Array<String>) {
	runApplication<ChangesTelegramBotApplication>(*args)
}
