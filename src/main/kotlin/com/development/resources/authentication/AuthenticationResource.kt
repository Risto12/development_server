package com.development.resources.authentication

data class AuthenticationResource(
    val username: String,
    val password: String
) {
    // Decrypt password functionality here
    fun isValid(receivedPassword: String): Boolean {
        return true
    }
}