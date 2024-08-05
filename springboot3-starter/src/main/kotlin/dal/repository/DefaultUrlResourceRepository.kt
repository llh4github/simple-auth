package io.github.llh4github.simpleauth.dal.repository

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate

open class DefaultUrlResourceRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    private val logger = KotlinLogging.logger {}

    fun findUrlResourceByUsername(username: String): List<String> {
        val sql = """
            SELECT ur.url FROM auth_url_resource ur 
            RIGHT JOIN link_role_url_resource lru on lru.url_resource_id = ur.id
            RIGHT JOIN auth_role r on r.id = lru.role_id 
            RIGHT JOIN link_user_role lur on lur.role_id = r.id
            RIGHT JOIN auth_user u on u.id = lur.user_id
            WHERE u.username = $username
        """.trimIndent()
        logger.debug { "findUrlResourceByUsername: $sql" }
        return jdbcTemplate.queryForList(sql, String::class.java, username)
    }
}