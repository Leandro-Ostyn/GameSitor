package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
data class DatabaseEffect (
    val effectId:Int,
    val value: Long,
    val attribute : String?
        )