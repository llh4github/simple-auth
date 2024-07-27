package io.github.llh4github.simpleauth.dal.repository

import io.github.llh4github.simpleauth.dal.entity.DefaultUser
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate


open class DefaultUserRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun findByUsername(username: String): DefaultUser? {
        val sql = "select id,username,password form auth_user where username = ?"
        val mapper = BeanPropertyRowMapper(DefaultUser::class.java)
        return jdbcTemplate.queryForObject(sql, mapper, username)
    }
}

