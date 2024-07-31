package io.github.llh4github.simpleauth

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.llh4github.simpleauth.api.AuthenticateApi
import io.github.llh4github.simpleauth.beans.IdGenerator
import io.github.llh4github.simpleauth.dal.repository.DefaultUserRepository
import io.github.llh4github.simpleauth.property.IdGeneratorProperty
import io.github.llh4github.simpleauth.property.SimpleAuthProperty
import io.github.llh4github.simpleauth.security.JwtFilter
import io.github.llh4github.simpleauth.security.SpringSecurityConfig
import io.github.llh4github.simpleauth.service.AuthenticateService
import io.github.llh4github.simpleauth.service.JwtService
import io.github.llh4github.simpleauth.service.impl.AuthorizeServiceImpl
import io.github.llh4github.simpleauth.service.impl.JwtServiceImpl
import io.github.llh4github.simpleauth.service.impl.UserDetailsServiceImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
@EnableConfigurationProperties(value = [SimpleAuthProperty::class, IdGeneratorProperty::class])
@Import(value = [SpringSecurityConfig::class])
open class SimpleAuthConfiguration(
    private val jdbcTemplate: JdbcTemplate,
) {

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun idGenerator(property: IdGeneratorProperty) = IdGenerator(property)

    @Bean
    fun jwtFilter(property: SimpleAuthProperty, jwtService: JwtService) = JwtFilter(property, jwtService)

    @Bean
    @ConditionalOnMissingBean(name = ["userDetailsService"])
    fun userDetailsService(repository: DefaultUserRepository) = UserDetailsServiceImpl()

    @Bean
    fun defaultUserRepository() = DefaultUserRepository(jdbcTemplate)

    @Bean
    fun authorizeService(
        jwtService: JwtService,
        passwordEncoder: PasswordEncoder,
        repository: DefaultUserRepository,
    ): AuthenticateService = AuthorizeServiceImpl(jwtService, repository, passwordEncoder)

    @Bean
    fun authorizeApi(authenticateService: AuthenticateService) = AuthenticateApi(authenticateService)

    @Bean
    fun jwtService(
        property: SimpleAuthProperty,
        idGenerator: IdGenerator,
        redisTemplate: StringRedisTemplate,
    ) = JwtServiceImpl(property, idGenerator, redisTemplate)

    @Bean
    fun springSecurityConfig(
        property: SimpleAuthProperty,
        jwtFilter: JwtFilter,
        objectMapper: ObjectMapper,
    ) = SpringSecurityConfig(property, jwtFilter, objectMapper)
}