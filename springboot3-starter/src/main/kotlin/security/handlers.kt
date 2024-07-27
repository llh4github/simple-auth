package io.github.llh4github.simpleauth.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.simpleauth.model.JsonWrapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationFailureHandler

private val logger = KotlinLogging.logger {}

internal fun jsonLoginFailureHandler(mapper: ObjectMapper): AuthenticationFailureHandler {
    val json = JsonWrapper("用户登录失败", "LOGIN_FAILED")
    return AuthenticationFailureHandler { _, response, exception ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}

internal fun jsonAccessDeniedHandler(mapper: ObjectMapper): AccessDeniedHandler {
    val json = JsonWrapper("用户无权访问", "ACCESS_DENIED")
    return AccessDeniedHandler { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}

internal fun jsonAuthenticationEntryPoint(mapper: ObjectMapper): AuthenticationEntryPoint {
    val json = JsonWrapper("用户无权访问", "ACCESS_DENIED")
    return AuthenticationEntryPoint { _, response, _ ->
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        response.status = 200
        response.writer.write(mapper.writeValueAsString(json))
    }
}