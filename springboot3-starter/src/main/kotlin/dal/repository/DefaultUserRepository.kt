package io.github.llh4github.simpleauth.dal.repository

import io.github.llh4github.simpleauth.dal.entity.DefaultUser
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet


open class DefaultUserRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    private val logger = KotlinLogging.logger {}

    fun findByUsername(username: String): DefaultUser? {
        val sql = "select id,username,password from auth_user where username = ?"
        logger.debug { "findByUsername: $sql" }
        return jdbcTemplate.queryForObject(sql, DefaultUserMapper(), username)
    }
}

internal class DefaultUserMapper : RowMapper<DefaultUser> {
    override fun mapRow(rs: ResultSet, rowNum: Int): DefaultUser? {
        return try {
            DefaultUser(
                id = rs.getLong("id"),
                uname = rs.getString("username"),
                pwd = rs.getString("password")
            )
        } catch (e: Exception) {
            null
        }
    }
}