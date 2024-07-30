package io.github.llh4github.simpleauth.api

import io.github.llh4github.simpleauth.model.*
import io.github.llh4github.simpleauth.service.AuthenticateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "认证接口", description = "用户认证接口，登录、登出等操作")
@RestController
@RequestMapping("authenticate")
class AuthenticateApi(
    private val authenticateService: AuthenticateService
) {

    @Operation(summary = "登录接口", description = "用户登录")
    @PostMapping("login")
    fun login(@RequestBody loginParam: LoginParam): JsonWrapper<LoginResultToken> {
        val token = authenticateService.login(loginParam)
        return JsonWrapper.ok(token)
    }

    @Operation(summary = "登出接口", description = "用户登出")
    @PostMapping("logout")
    fun logout(@RequestBody param: LogoutParam): JsonWrapper<Boolean> {
        val logout = authenticateService.logout(param)
        return JsonWrapper.ok(logout)
    }

    @Operation(summary = "刷新jwt", description = "禁用原有jwt，生成新的jwt")
    @PostMapping("token/refresh")
    fun refreshToken(@RequestBody param: RefreshTokenParam): JsonWrapper<LoginResultToken> {
        val token = authenticateService.refreshToken(param)
        return JsonWrapper.ok(token)
    }
}