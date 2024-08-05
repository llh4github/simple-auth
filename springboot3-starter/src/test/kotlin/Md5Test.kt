package io.github.llh4github.simpleauth

import org.junit.jupiter.api.Test
import org.springframework.util.DigestUtils

class Md5Test {

    @Test
    fun a() {
        val token = "abcd"
        val hash = DigestUtils.md5DigestAsHex(token.toByteArray())
        println(hash)
    }
}