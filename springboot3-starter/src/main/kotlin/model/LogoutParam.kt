package io.github.llh4github.simpleauth.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "用户登出参数", description = "用户登出参数")
data class LogoutParam(
    @Schema(title = "访问令牌", description = "用于访问资源")
    val accessToken: String,
    @Schema(title = "刷新令牌", description = "用于刷新访问令牌")
    val refreshToken: String,
)
