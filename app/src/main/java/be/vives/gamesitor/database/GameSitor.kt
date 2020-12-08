package be.vives.gamesitor.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Player")
data class GameSitor(
    @PrimaryKey(autoGenerate = true)
    var playerId: Long = 0L,

    @ColumnInfo(name = "Name")
    val name: String = "",

    @ColumnInfo(name = "Password")
    var password: String = "",

    @ColumnInfo(name = "CharacterId")
    var charachterId: Int = 0,

    @ColumnInfo(name = "Coins")
    var coins: Long = 0L,

    @ColumnInfo(name = "InventoryId")
    var inventoryId: Int = 0
)
