package io.github.llh4github.simpleauth.service

interface AuthorizeService {
    /**
     * 获取用户可访问url资源
     *
     * 默认会缓存结果
     */
    fun urlResource(username: String): List<String>

    /**
     * 清除用户可访问url资源缓存
     */
    fun clearUrlResourceCache(username: String)
}