package io.github.llh4github.simpleauth.starterdemo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("demo")
class DemoController {

    @GetMapping
    fun now(): Map<String, LocalDateTime> {
        return mapOf("now" to LocalDateTime.now())
    }
}