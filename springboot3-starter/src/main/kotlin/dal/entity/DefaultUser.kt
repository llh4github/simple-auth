package io.github.llh4github.simpleauth.dal.entity

import io.github.llh4github.simpleauth.dal.UserTrait
import org.springframework.data.relational.core.mapping.Table

@Table("auth_user")
data class DefaultUser(
    val id: Long,
    override val username: String,
    override val password: String,
) : UserTrait {

    override fun userId(): Long {
        return id
    }
}