package io.github.llh4github.simpleauth.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Deprecated("使用自定义接口实现")
class RestAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    private val mapper by lazy {
        ObjectMapper()
    }

    override fun attemptAuthentication(
        request: HttpServletRequest, response: HttpServletResponse
    ): Authentication {
        val token = request.inputStream.use {
            val node = mapper.readTree(it)
            val username = node.get("username").textValue()
            val password = node.get("password").textValue()
            UsernamePasswordAuthenticationToken(username, password)
        }
        setDetails(request, token)
        return authenticationManager.authenticate(token)
    }
}