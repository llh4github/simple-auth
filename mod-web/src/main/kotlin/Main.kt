package io.github.llh4github.simpleauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WebMain

fun main(args: Array<String>) {
    runApplication<WebMain>(*args)
}