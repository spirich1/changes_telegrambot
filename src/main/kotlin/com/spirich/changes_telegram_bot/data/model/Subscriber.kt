package com.spirich.changes_telegram_bot.data.model

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class Subscriber(
	@Id
	val chatId: Long,
	val userName: String,
)