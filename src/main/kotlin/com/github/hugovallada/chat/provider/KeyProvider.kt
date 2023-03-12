package com.github.hugovallada.chat.provider

import java.security.PublicKey

interface KeyProvider {
    fun getPublicKey(keyId: String): PublicKey
}