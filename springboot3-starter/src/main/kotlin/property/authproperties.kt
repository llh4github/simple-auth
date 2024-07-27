package io.github.llh4github.simpleauth.property

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@Configuration
@ConfigurationProperties(prefix = "simple-auth")
open class SimpleAuthProperty(
    /**
     * 签发人。通常是访问域名。
     */
    var issuer: String = "simple-auth",
    var tokenExpireTime: TokenExpireTime = TokenExpireTime(),
    var idGenerator: IdGeneratorProperty = IdGeneratorProperty(),
    /**
     * 令牌秘钥
     *
     * 用base64，至少需要43个字符，不含特殊符号。
     */
    var secret: String = "VyHZ8YGV9w94dRw8ixVzJgcoDXqvRGrFOHzCxiMIgbgmM",

    var cacheKeyPrefix: String = "simple-auth",
    /**
     * 允许匿名访问的url列表
     */
    var annoUrl: List<String> = emptyList(),
) {

    /**
     * 生成密钥
     */
    val secretKey: SecretKey by lazy {
        val bytes = Decoders.BASE64.decode(secret)
        Keys.hmacShaKeyFor(bytes)
    }
}

data class TokenExpireTime(
    var access: Duration = Duration.parse("1d"),
    var refresh: Duration = Duration.parse("7d"),
) {
    val accessExpireTime: Date
        get() {
            val instant = LocalDateTime.now()
                .plus(access.toJavaDuration())
                .atZone(ZoneId.systemDefault()).toInstant()
            return Date.from(instant)
        }


    val refreshExpireTime: Date
        get() {
            val instant = LocalDateTime.now()
                .plus(refresh.toJavaDuration())
                .atZone(ZoneId.systemDefault()).toInstant()
            return Date.from(instant)
        }
}

@Configuration
@ConfigurationProperties(prefix = "simple-auth.id-generator")
open class IdGeneratorProperty(
    /**
     *  WorkerId 最大值为2^6-1，即默认最多支持64个节点。
     *
     *  **多实例部署时最好修改为不同值。**
     */
    val workerId: Short = 1,
    /**
     * **一般不用改这个配置。**
     *
     * 默认值6，限制每毫秒生成的ID个数。若生成速度超过5万个/秒，建议加大 SeqBitLength 到 10。
     */
    val seqBitLength: Byte = 6,
)