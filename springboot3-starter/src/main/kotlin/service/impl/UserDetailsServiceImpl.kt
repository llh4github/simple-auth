package io.github.llh4github.simpleauth.service.impl

import io.github.llh4github.simpleauth.dal.repository.DefaultUserRepository
import io.github.llh4github.simpleauth.model.DefaultAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class UserDetailsServiceImpl : UserDetailsService {
    @Autowired
    private lateinit var repository: DefaultUserRepository

    override fun loadUserByUsername(username: String): UserDetails {
        val user = repository.findByUsername(username) ?: throw UsernameNotFoundException("用户名或密码错误")
        return DefaultAccount.from(user)
    }
}