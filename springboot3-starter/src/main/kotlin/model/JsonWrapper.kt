package io.github.llh4github.simpleauth.model

import java.time.LocalDateTime

data class JsonWrapper(
    val msg: String,
    val code: String,
) {
    val timestamp = LocalDateTime.now()
}