package io.github.llh4github.simpleauth.dal

interface UserTrait {
    val username: String
    val password: String
    fun userId(): Long
}
