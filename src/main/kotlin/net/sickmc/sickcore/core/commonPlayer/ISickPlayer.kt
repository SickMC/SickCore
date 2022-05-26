package net.sickmc.sickcore.core.commonPlayer

import org.bson.Document
import java.util.UUID

interface ISickPlayer{

    val uniqueID: UUID
    val document: Document

}