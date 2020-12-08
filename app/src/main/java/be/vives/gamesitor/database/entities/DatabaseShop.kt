package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
class DatabaseShop {
    var shopItems : ArrayList<DatabaseItem> = ArrayList()

}