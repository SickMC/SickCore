package net.sickmc.sickcore.utils

interface Cache<K, V> : MutableMap<K, V> {

    fun getCachedEntity(entity: K): V?

    suspend fun getEntity(entity: K): V?

    suspend fun reloadEntity(entity: K): V?

    suspend fun createEntity(entity: K): V

}