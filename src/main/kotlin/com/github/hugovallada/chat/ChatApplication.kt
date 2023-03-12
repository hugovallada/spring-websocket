package com.github.hugovallada.chat

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
class ChatApplication

fun main(args: Array<String>) {
	runApplication<ChatApplication>(*args)
}
