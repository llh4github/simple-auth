package io.github.llh4github.simpleauth.model

import org.springframework.security.core.GrantedAuthority

data class StringGrantedAuthority(
    private val authority: String
) : GrantedAuthority {
    override fun getAuthority(): String {
        return authority
    }
}
