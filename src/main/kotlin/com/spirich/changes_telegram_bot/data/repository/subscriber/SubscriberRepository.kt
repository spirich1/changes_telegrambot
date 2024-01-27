package com.spirich.changes_telegram_bot.data.repository.subscriber

import com.spirich.changes_telegram_bot.data.model.Subscriber
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriberRepository : JpaRepository<Subscriber, Long>