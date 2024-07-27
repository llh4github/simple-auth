package io.github.llh4github.simpleauth.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.simpleauth.property.SimpleAuthProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
open class SpringSecurityConfig(
    private val property: SimpleAuthProperty,
    private val jwtFilter: JwtFilter,
    private val objectMapper: ObjectMapper,
) {
    private val logger = KotlinLogging.logger {}

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler(jsonAccessDeniedHandler(objectMapper))
                it.authenticationEntryPoint(jsonAuthenticationEntryPoint(objectMapper))
            }

            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { it.anyRequest().authenticated() }

            .addFilterAt(restFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        return http.build()
    }

    private fun restFilter(): RestAuthenticationFilter {
        val filter = RestAuthenticationFilter()
        filter.setAuthenticationFailureHandler(jsonLoginFailureHandler(objectMapper))
        return filter
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