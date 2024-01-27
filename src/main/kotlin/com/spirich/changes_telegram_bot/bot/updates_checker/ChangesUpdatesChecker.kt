package com.spirich.changes_telegram_bot.bot.updates_checker

import com.spirich.changes_telegram_bot.bot.ChangesBot
import com.spirich.changes_telegram_bot.bot.constant.Message
import com.spirich.changes_telegram_bot.data.model.Changes
import com.spirich.changes_telegram_bot.data.repository.changes.ChangesRepository
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private const val CHECKING_INTERVAL = 10L

class ChangesUpdatesChecker(
	private val changesRepository: ChangesRepository,
	private val bot: ChangesBot
) {
	fun startCheckingChangesUpdates() {
		var currentChanges: Changes? = changesRepository.getChanges()
		val executor = Executors.newSingleThreadScheduledExecutor()
		executor.scheduleAtFixedRate({
			val newChanges: Changes? = changesRepository.getChanges()
			if (newChanges != null && newChanges != currentChanges) {
				bot.sendDocumentToAllSubscribers(Message.NEW_CHANGES, newChanges.file)
				currentChanges = newChanges
			}
		}, 0, CHECKING_INTERVAL, TimeUnit.MINUTES)
	}
}