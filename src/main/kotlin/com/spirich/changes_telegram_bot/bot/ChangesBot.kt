package com.spirich.changes_telegram_bot.bot

import com.spirich.changes_telegram_bot.bot.constant.Command
import com.spirich.changes_telegram_bot.bot.handler.TelegramCommandHandler
import com.spirich.changes_telegram_bot.bot.updates_checker.ChangesUpdatesChecker
import com.spirich.changes_telegram_bot.configuration.BotProperties
import com.spirich.changes_telegram_bot.data.model.Subscriber
import com.spirich.changes_telegram_bot.data.repository.changes.ChangesRepository
import com.spirich.changes_telegram_bot.data.repository.subscriber.SubscriberRepository
import com.spirich.changes_telegram_bot.logger.Logger
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendDocument
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import java.io.File


@Component
final class ChangesBot(
	private val botProperties: BotProperties,
	private val subscriberRepository: SubscriberRepository,
	private val changesRepository: ChangesRepository,
) : TelegramLongPollingBot(botProperties.token), Logger {

	private val commandHandler: TelegramCommandHandler =
		TelegramCommandHandler(changesRepository, subscriberRepository, this)
	private val changesUpdatesChecker = ChangesUpdatesChecker(changesRepository, this)

	@PostConstruct
	fun start() {
		try {
			TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
		} catch (e: TelegramApiException) {
			log.error("Ошибка при запуске: $e")
		}
		changesUpdatesChecker.startCheckingChangesUpdates()
	}

	override fun getBotUsername() = botProperties.username

	override fun onUpdateReceived(update: Update?) {
		update?.message?.let { message ->
			val chat = message.chat
			val text = message.text
			log.info("${chat.userName}: $text")
			when (text) {
				Command.START -> commandHandler.handleStartCommand(chat)
				Command.CHANGES -> commandHandler.handleChangesCommand(chat)
				Command.UNSUBSCRIBE -> commandHandler.handleUnsubscribeCommand(chat)
				else -> commandHandler.handleUnknownCommand(chat)
			}
		}
	}

	fun sendMessage(chatId: Long, text: String) {
		val sendMessage = SendMessage()
		sendMessage.chatId = chatId.toString()
		sendMessage.text = text
		execute(sendMessage)
	}

	fun sendDocument(chatId: Long, text: String, document: File) {
		val sendDocument = SendDocument()
		sendDocument.chatId = chatId.toString()
		sendDocument.caption = text
		sendDocument.document = InputFile(document)
		execute(sendDocument)
	}

	fun sendDocumentToAllSubscribers(text: String, document: File) {
		val subscribers: List<Subscriber> = subscriberRepository.findAll()
		subscribers.forEach { subscriber ->
			sendDocument(
				chatId = subscriber.chatId, text = text, document = document
			)
		}
	}
}