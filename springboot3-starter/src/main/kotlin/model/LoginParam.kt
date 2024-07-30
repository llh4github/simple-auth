package io.github.llh4github.simpleauth.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "用户登录参数", description = "用户登录参数")
data class LoginParam(
    @Schema(title = "用户名", description = "用户登录名")
    val username: String,
    @Schema(title = "密码", description = "用户登录密码")
    val password: String
)
