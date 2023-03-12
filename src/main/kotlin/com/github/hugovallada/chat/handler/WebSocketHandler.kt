package com.github.hugovallada.chat.handler

import com.github.hugovallada.chat.service.TicketService
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.util.UriComponentsBuilder
import java.util.concurrent.ConcurrentHashMap
import java.util.logging.Logger

@Component
class WebSocketHandler(private val ticketService: TicketService) : TextWebSocketHandler() {
    val logger: Logger = Logger.getLogger(this::class.java.name)
    val sessions: ConcurrentHashMap<String, WebSocketSession> = ConcurrentHashMap()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info("session: ${session.id}")
        val ticket = ticketOf(session)
        if (ticket.isBlank()) {
            logger.warning("Session ${session.id} without ticket")
            session.close(CloseStatus.POLICY_VIOLATION)
            return
        }
        val userId = ticketService.getUserByTicket(ticket)
        if (userId.isNullOrBlank()) {
            logger.warning("Session ${session.id} with invalid ticket")
            session.close(CloseStatus.POLICY_VIOLATION)
            return
        }
        sessions[userId] = session
        logger.info("session ${session.id} was bind to user $userId")
    }

    private fun ticketOf(session: WebSocketSession): String {
        val uri = session.uri ?: throw RuntimeException("uri not found")
        val ticket = UriComponentsBuilder.fromUri(uri).build().queryParams["ticket"]?.first() ?: ""
        return ticket.trim()
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        println("session: ${session.id} with status: ${status.code}")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        println("Message: ${message.payload}")
    }
}