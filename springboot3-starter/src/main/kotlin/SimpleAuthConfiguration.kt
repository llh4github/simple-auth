package io.github.llh4github.simpleauth

import io.github.llh4github.simpleauth.beans.IdGenerator
import io.github.llh4github.simpleauth.property.IdGeneratorProperty
import io.github.llh4github.simpleauth.property.SimpleAuthProperty
import io.github.llh4github.simpleauth.security.SpringSecurityConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableConfigurationProperties(value = [SimpleAuthProperty::class, IdGeneratorProperty::class])
open class SimpleAuthConfiguration {


    @Bean
    fun springSecurityConfig(property: SimpleAuthProperty) = SpringSecurityConfig(property)

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun idGenerator(property: IdGeneratorProperty) = IdGenerator(property)
}