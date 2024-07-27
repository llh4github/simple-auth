package io.github.llh4github.simpleauth.security

import io.github.llh4github.simpleauth.property.SimpleAuthProperty
import io.github.llh4github.simpleauth.service.JwtService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtFilter(
    private val property: SimpleAuthProperty,
    private val jwtService: JwtService,
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractJwtToken(request)
        if (token.isNotBlank()) {
            jwtService.validateJwtAndRebuildToken(token)
        }
        filterChain.doFilter(request, response)
    }

    private fun extractJwtToken(request: HttpServletRequest): String {
        val token = request.getHeader(property.tokenHeaderName) ?: ""
        return token.removePrefix(property.tokenHeaderPrefix)
    }

}