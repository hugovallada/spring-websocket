package com.github.hugovallada.chat.controller

import com.github.hugovallada.chat.service.TicketService
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/ticket")
@CrossOrigin
class TicketController(private val service: TicketService) {


    @PostMapping
    fun buildTicket(@RequestHeader(HttpHeaders.AUTHORIZATION) auth: String?): Map<String, String> {
        val token = auth?.replace("Bearer ", "") ?: ""
        val ticket = service.buildAndSaveTicket(token)
        return mapOf("ticket" to ticket)
    }

}