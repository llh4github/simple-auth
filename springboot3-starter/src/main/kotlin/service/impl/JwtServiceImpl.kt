package io.github.llh4github.simpleauth.service.impl

import io.github.llh4github.simpleauth.beans.IdGenerator
import io.github.llh4github.simpleauth.consts.JwtTokenType
import io.github.llh4github.simpleauth.model.AccountTrait
import io.github.llh4github.simpleauth.property.SimpleAuthProperty
import io.github.llh4github.simpleauth.service.JwtService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Jwts
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.util.DigestUtils
import java.util.*

@Service
class JwtServiceImpl(
    private val property: SimpleAuthProperty,
    private val idGenerator: IdGenerator,
    private val redisTemplate: StringRedisTemplate,
) : JwtService {
    private val logger = KotlinLogging.logger {}

    private val parser by lazy {
        Jwts.parser().verifyWith(property.secretKey).build()
    }

    private val keyPrefix = property.cacheKeyPrefix

    //#region token缓存操作

    fun cacheToken(token: String, expire: Date) {
        val hash = DigestUtils.md5Digest(token.toByteArray())
        val key = keyPrefix + "jwt:$hash"
        redisTemplate.opsForValue().set(key, token)
        redisTemplate.expireAt(key, expire)
    }

    fun isCached(token: String): Boolean {
        val hash = DigestUtils.md5Digest(token.toByteArray())
        val key = keyPrefix + "jwt:$hash"
        return redisTemplate.hasKey(key)
    }

    override fun removeToken(token: String): Boolean {
        val hash = DigestUtils.md5Digest(token.toByteArray())
        val key = keyPrefix + "jwt:$hash"
        return redisTemplate.delete(key)
    }
    //#endregion

    //#region 创建token

    override fun createToken(trait: AccountTrait, type: JwtTokenType): String {
        val expire = if (type == JwtTokenType.ACCESS_TOKEN) {
            property.tokenExpireTime.accessExpireTime
        } else {
            property.tokenExpireTime.refreshExpireTime
        }

        val map = mapOf<String, Any>(
            "username" to trait.username,
            "userId" to trait.userId(),
        )
        val tokenId = idGenerator.nextIdStr()
        val builder = Jwts.builder()
            .id(tokenId)
            .claims(map)
            .signWith(property.secretKey)
            .expiration(expire)

        builder.header().add("typ", type.name)
        val token = builder.compact()
        cacheToken(token, expire)
        return token
    }

    //#endregion

    override fun isValid(token: String): Boolean {
        try {
            parser.parse(token).payload
            return isCached(token)
        } catch (e: Exception) {
            logger.warn(e) { "token验证出错: $token" }
            return false
        }
    }
}