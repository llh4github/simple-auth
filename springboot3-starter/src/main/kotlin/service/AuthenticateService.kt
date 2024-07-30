package io.github.llh4github.simpleauth.service

import io.github.llh4github.simpleauth.model.LoginParam
import io.github.llh4github.simpleauth.model.LoginResultToken
import io.github.llh4github.simpleauth.model.LogoutParam
import io.github.llh4github.simpleauth.model.RefreshTokenParam

interface AuthenticateService {

    /**
     * 用户登录
     *
     * 用户名或密码错误时抛出异常
     * @throws org.springframework.security.authentication.BadCredentialsException 用户名或密码错误
     */
    fun login(param: LoginParam): LoginResultToken

    fun logout(param: LogoutParam): Boolean

    /**
     * 刷新jwt
     *
     * 禁用原有jwt，生成新的jwt
     * @throws org.springframework.security.authentication.BadCredentialsException 用户名或密码错误
     */
    fun refreshToken(param: RefreshTokenParam): LoginResultToken
}