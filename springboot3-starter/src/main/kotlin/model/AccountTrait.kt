package io.github.llh4github.simpleauth.model

import org.springframework.security.core.userdetails.UserDetails

interface AccountTrait : UserDetails {

    fun userId(): String

}