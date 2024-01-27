package com.spirich.changes_telegram_bot.bot.constant

object Message {
	const val GREETING =
		"Привет, отправь команду ${Command.CHANGES}, чтобы я автоматически присылал тебе изменения расписания."
	const val CURRENT_CHANGES = "Текущие изменения расписания.\nЖди, когда я пришлю новые! 😉"
	const val NEW_CHANGES = "Новые изменения расписания!"
	const val UNSUBSCRIBED = "Ты отписался от получения изменений расписания."
	const val GET_CHANGES_ERROR = "Ошибка при получении изменений. ☹\nПовторите попытку позже!"
	const val GET_CHANGES = "Отправь команду ${Command.CHANGES}, чтобы получить замены."
}