package io.github.llh4github.simpleauth.service.impl

import io.github.llh4github.simpleauth.dal.repository.DefaultUserRepository
import io.github.llh4github.simpleauth.model.LoginParam
import io.github.llh4github.simpleauth.model.LoginResultToken
import io.github.llh4github.simpleauth.model.LogoutParam
import io.github.llh4github.simpleauth.model.RefreshTokenParam
import io.github.llh4github.simpleauth.service.AuthenticateService
import io.github.llh4github.simpleauth.service.JwtService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder

class AuthenticateServiceImpl(
    private val jwtService: JwtService,
    private val repository: DefaultUserRepository,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticateService {

    private val logger = KotlinLogging.logger {}

    override fun logout(param: LogoutParam): Boolean {
        jwtService.removeToken(param.accessToken)
        jwtService.removeToken(param.refreshToken)
        SecurityContextHolder.clearContext()
        return true
    }

    override fun refreshToken(param: RefreshTokenParam): LoginResultToken {
        jwtService.removeToken(param.accessToken)
        jwtService.removeToken(param.refreshToken)

        repository.findByUsername(param.username)?.let {
            val access = jwtService.createAccessToken(it)
            val refresh = jwtService.createRefreshToken(it)
            return LoginResultToken(
                username = it.username,
                accessToken = access,
                refreshToken = refresh
            )
        }
        throw BadCredentialsException("用户名或密码错误")
    }

    override fun login(param: LoginParam): LoginResultToken {
        repository.findByUsername(param.username)?.let {
            val matched = passwordEncoder.matches(param.password, it.password)
            if (matched) {
                val access = jwtService.createAccessToken(it)
                val refresh = jwtService.createRefreshToken(it)
                return LoginResultToken(
                    username = it.username,
                    accessToken = access,
                    refreshToken = refresh
                )
            }
            logger.debug { "$param 密码不匹配" }
        }

        logger.debug { "用户名${param.username}未找到对应用户" }
        throw BadCredentialsException("用户名或密码错误")
    }
}