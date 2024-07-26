package io.github.llh4github.simpleauth.security

import io.github.llh4github.simpleauth.property.SimpleAuthProperty
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain

open class SpringSecurityConfig(
    private val property: SimpleAuthProperty
) {
    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
        return http.build()
    }

    @Bean
    open fun webSecurityCustomizer(): WebSecurityCustomizer {
        val urls = mutableListOf("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**")
        urls.addAll(property.annoUrl)
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring().requestMatchers(*urls.toTypedArray())
        }
    }


}