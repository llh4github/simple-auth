package io.github.llh4github.simpleauth.dal.entity

import io.github.llh4github.simpleauth.dal.UserTrait
import io.github.llh4github.simpleauth.model.AccountTrait
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority

@Table("auth_user")
data class DefaultUser(
    val id: Long,
    private val uname: String,
    private val pwd: String,
) : UserTrait, AccountTrait {

//    override val username = uname
//    override val password = pwd

    override fun userId(): String {
        return id.toString()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf()
    }

    override fun getPassword(): String {
        return pwd
    }

    override fun getUsername(): String {
        return uname
    }
}