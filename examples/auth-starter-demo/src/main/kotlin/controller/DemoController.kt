package io.github.llh4github.simpleauth.starterdemo.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@Tag(name = "测试接口", description = "Demo controller")
@RequestMapping("demo")
class DemoController {

    @Operation(summary = "获取当前时间")
    @GetMapping
    @Parameter(name = "Authorization", description = "Bearer token", required = true, `in` = ParameterIn.HEADER)
    fun now(): Map<String, LocalDateTime> {
        return mapOf("now" to LocalDateTime.now())
    }
}