package io.github.llh4github.simpleauth.model

import com.fasterxml.jackson.annotation.JsonAlias
import io.github.llh4github.simpleauth.dal.entity.DefaultUser
import org.springframework.security.core.GrantedAuthority

data class DefaultAccount(
    val id: Long,
    @JsonAlias("username")
    val usernameInner: String,
    @JsonAlias("password")
    val passwordInner: String,
    @JsonAlias("authorities")
    val authoritiesInner: List<StringGrantedAuthority>
) : AccountTrait {
    override fun userId(): String {
        return id.toString()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authoritiesInner.toMutableList()
    }

    override fun getPassword(): String {
        return passwordInner
    }

    override fun getUsername(): String {
        return usernameInner
    }

    companion object {
        fun from(user: DefaultUser): DefaultAccount {
            return DefaultAccount(
                user.id,
                user.username,
                user.password,
                emptyList()
//                user.authorities.map { StringGrantedAuthority(it) }
            )
        }
    }
}
