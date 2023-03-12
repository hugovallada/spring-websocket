package com.github.hugovallada.chat.service

import com.github.hugovallada.chat.provider.TokenProvider
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class TicketService(
    private val redisTemplate: RedisTemplate<String, String>,
    private val tokenProvider: TokenProvider
) {


    fun buildAndSaveTicket(token: String): String {
        if (token.isBlank()) throw RuntimeException("missing token")
        val ticket = UUID.randomUUID().toString()
        val user = tokenProvider.decode(token)
        val userId = user["id"] ?: throw RuntimeException("user id is invalid")
        redisTemplate.opsForValue().set(ticket, userId, Duration.ofSeconds(10))
        return ticket
    }

    fun getUserByTicket(ticket: String): String? {
        return redisTemplate.opsForValue().getAndDelete(ticket)
    }


}