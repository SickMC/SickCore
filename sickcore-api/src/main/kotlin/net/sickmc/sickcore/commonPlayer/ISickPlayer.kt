package net.sickmc.sickcore.commonPlayer

import org.bson.Document
import java.util.UUID

interface ISickPlayer{

    val uniqueID: UUID
    val document: Document

}