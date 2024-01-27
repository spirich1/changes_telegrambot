package com.spirich.changes_telegram_bot.bot.handler

import org.telegram.telegrambots.meta.api.objects.Chat

interface CommandHandler {
	fun handleStartCommand(chat: Chat)
	fun handleChangesCommand(chat: Chat)
	fun handleUnsubscribeCommand(chat: Chat)
	fun handleUnknownCommand(chat: Chat)
}