package com.github.hugovallada.chat.data

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
class User(
    val id: String,
    val name: String,
    val picture: String
)