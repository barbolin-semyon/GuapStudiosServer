package com.skat.database.users.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {
    private val login = Tokens.varchar("login", 25)
    private val token = Tokens.varchar("token", 50)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun fetchLogin(token: String) : TokenDTO? {
        return try {
            transaction {
                val tokenModel = Tokens.select { Tokens.token.eq(token) }.single()

                TokenDTO(
                    token = tokenModel[Tokens.token],
                    login = tokenModel[login]
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}