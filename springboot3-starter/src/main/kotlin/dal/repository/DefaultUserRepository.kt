package io.github.llh4github.simpleauth.dal.repository

import io.github.llh4github.simpleauth.dal.entity.DefaultUser
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet


open class DefaultUserRepository(
    private val jdbcTemplate: JdbcTemplate
) {

    fun findByUsername(username: String): DefaultUser? {
        val sql = "select id,username,password from auth_user where username = ?"
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