package me.anton.sickcore.core

import net.axay.kspigot.main.KSpigot

class PaperBootstrap : KSpigot() {

    private var core: PaperCore? = null

    override fun load() {
        this.core = PaperCore(this)
    }

    override fun startup() {
        this.core!!.start()
    }

    override fun shutdown() {
        this.core!!.shutdown()
    }

}