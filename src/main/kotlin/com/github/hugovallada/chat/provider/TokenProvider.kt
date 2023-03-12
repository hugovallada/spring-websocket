package com.github.hugovallada.chat.provider

interface TokenProvider {
    fun decode(token: String): Map<String, String?>
}