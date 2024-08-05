package io.github.llh4github.simpleauth.starterdemo

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class MainTest {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun context() {
        val hash = passwordEncoder.encode("123456")
        println(hash)
    }
}