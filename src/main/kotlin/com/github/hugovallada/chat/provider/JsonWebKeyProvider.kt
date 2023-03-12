package com.github.hugovallada.chat.provider

import com.auth0.jwk.UrlJwkProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.security.PublicKey

@Component
class JsonWebKeyProvider() : KeyProvider {

    @Value("\${app.auth.jwks-url}")
    private lateinit var jwksUrl: String


    @Cacheable("public-key")
    override fun getPublicKey(keyId: String): PublicKey {
        val urlJwkProvider = UrlJwkProvider(jwksUrl)
        val jwk = urlJwkProvider.get(keyId)
        return jwk.publicKey
    }
}