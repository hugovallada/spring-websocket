package com.github.hugovallada.chat.provider

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import java.security.interfaces.RSAPublicKey
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class JsonWebTokenProvider(private val keyProvider: KeyProvider): TokenProvider {
    override fun decode(token: String): Map<String, String?> {
        val jwt = JWT.decode(token)
        val publicKey = keyProvider.getPublicKey(jwt.keyId)
        val algorithm = Algorithm.RSA256(publicKey as RSAPublicKey, null)
        algorithm.verify(jwt)
        val expired = jwt.expiresAtAsInstant.atZone(ZoneId.systemDefault())
            .isBefore(ZonedDateTime.now())
        if (expired) throw RuntimeException("token is expired")
        return mapOf("id" to jwt.subject, "name" to jwt.claims["name"]?.asString(), "picture" to jwt.claims["picture"]?.asString())
    }
}