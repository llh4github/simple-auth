package io.github.llh4github.simpleauth.security

import io.github.llh4github.simpleauth.service.AuthorizeService
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.util.AntPathMatcher
import java.util.function.Supplier

class DynamicUrlAccessHandler(
    private val authorizeService: AuthorizeService
) : AuthorizationManager<RequestAuthorizationContext> {
    private val matcher = AntPathMatcher()

    override fun check(
        authentication: Supplier<Authentication>,
        context: RequestAuthorizationContext
    ): AuthorizationDecision {
        if (authentication.get() is AnonymousAuthenticationToken) {
            return AuthorizationDecision(false)
        }
        val username = authentication.get().principal as String
        val uri = context.request.requestURI
        val matched = authorizeService.urlResource(username).any {
            matcher.match(it, uri)
        }
        return AuthorizationDecision(matched)
    }
}