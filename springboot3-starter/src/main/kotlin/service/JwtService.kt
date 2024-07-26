package io.github.llh4github.simpleauth.service

import io.github.llh4github.simpleauth.consts.JwtTokenType
import io.github.llh4github.simpleauth.model.AccountTrait

interface JwtService {

    fun createToken(trait: AccountTrait, type: JwtTokenType): String

    fun isValid(token: String): Boolean

    /**
     * 移除token。
     *
     * 移除的token将不会通过验证。
     * @return 移出成功返回true，否则返回false，重复移除、不存在的token都会返回false。
     */
    fun removeToken(token: String): Boolean

    fun createAccessToken(trait: AccountTrait): String {
        return createToken(trait, JwtTokenType.ACCESS_TOKEN)
    }

    fun createRefreshToken(trait: AccountTrait): String {
        return createToken(trait, JwtTokenType.REFRESH_TOKEN)
    }
}