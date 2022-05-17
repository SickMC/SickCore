package net.sickmc.sickcore.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.axay.kspigot.main.KSpigot
import org.litote.kmongo.serialization.SerializationClassMappingTypeService

class PaperBootstrap : KSpigot() {

    private var core: PaperCore? = null
    private val bootScope = CoroutineScope(Dispatchers.Default)

    override fun load() {
        System.setProperty("org.litote.mongo.mapping.service", SerializationClassMappingTypeService::class.qualifiedName!!)
    }

    override fun startup() {
        this.core = PaperCore(this)
        core!!.init()
        bootScope.launch {
            core?.start()
        }
    }

    override fun shutdown() {
        bootScope.launch {
            core?.shutdown()
        }
    }

}