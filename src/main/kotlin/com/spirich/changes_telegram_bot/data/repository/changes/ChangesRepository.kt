package com.spirich.changes_telegram_bot.data.repository.changes

import com.spirich.changes_telegram_bot.configuration.ChangesProperties
import com.spirich.changes_telegram_bot.data.model.Changes
import com.spirich.changes_telegram_bot.logger.Logger
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service
import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths

@Service
class ChangesRepository(
	private val changesProperties: ChangesProperties
) : Logger {

	fun getChanges(): Changes? {
		return try {
			val document: Document = Jsoup.connect(changesProperties.baseUrl).get()
			val url: String = document.select("li.item").takeLast(2)[0].select("a").attr("href")
			val fileName: String = url.split("/").last()
			val path: String = changesProperties.downloadDirectory + fileName
			downloadFile(url, path)
			val file = File(path)
			Changes(file)
		} catch (e: Exception) {
			log.error("Ошибка при получении изменений: $e")
			null
		}
	}

	private fun downloadFile(url: String, path: String) {
		try {
			if (!File(path).exists()) {
				URL(url).openStream().use {
					Files.copy(it, Paths.get(path))
				}
			}
		} catch (e: Exception) {
			log.error("Ошибка при загрузке файла: $e")
		}
	}
}

