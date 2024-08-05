package io.github.llh4github.simpleauth.service.impl

import io.github.llh4github.simpleauth.dal.repository.DefaultUrlResourceRepository
import io.github.llh4github.simpleauth.service.AuthorizeService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable

class AuthorizeServiceImpl : AuthorizeService {

    @Autowired
    private lateinit var repository: DefaultUrlResourceRepository
    private val logger = KotlinLogging.logger {}

    @Cacheable(cacheNames = [cachePrefix], key = "#username")
    override fun urlResource(username: String): List<String> {
        return repository.findUrlResourceByUsername(username)
    }

    @CacheEvict(cacheNames = [cachePrefix], key = "#username")
    override fun clearUrlResourceCache(username: String) {
        logger.debug { "clearUrlResourceCache: $username" }
    }
}

private const val cachePrefix = "authorize:url-resource:"
