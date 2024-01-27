package com.spirich.changes_telegram_bot.bot.handler

import com.spirich.changes_telegram_bot.bot.ChangesBot
import com.spirich.changes_telegram_bot.bot.constant.Message
import com.spirich.changes_telegram_bot.data.model.Changes
import com.spirich.changes_telegram_bot.data.model.Subscriber
import com.spirich.changes_telegram_bot.data.repository.changes.ChangesRepository
import com.spirich.changes_telegram_bot.data.repository.subscriber.SubscriberRepository
import org.telegram.telegrambots.meta.api.objects.Chat

class TelegramCommandHandler(
	private val changesRepository: ChangesRepository,
	private val subscriberRepository: SubscriberRepository,
	private val bot: ChangesBot,
) : CommandHandler {

	override fun handleStartCommand(chat: Chat) {
		bot.sendMessage(chatId = chat.id, text = Message.GREETING)
	}

	override fun handleChangesCommand(chat: Chat) {
		val subscriber = Subscriber(chat.id, chat.userName)
		subscriberRepository.save(subscriber)
		val changes: Changes? = changesRepository.getChanges()
		if (changes == null) {
			bot.sendMessage(chatId = chat.id, text = Message.GET_CHANGES_ERROR)
		} else {
			bot.sendDocument(chatId = chat.id, text = Message.CURRENT_CHANGES, document = changes.file)
		}
	}

	override fun handleUnsubscribeCommand(chat: Chat) {
		subscriberRepository.deleteById(chat.id)
		bot.sendMessage(chatId = chat.id, text = Message.UNSUBSCRIBED)
	}

	override fun handleUnknownCommand(chat: Chat) {
		bot.sendMessage(chatId = chat.id, text = Message.GET_CHANGES)
	}

}