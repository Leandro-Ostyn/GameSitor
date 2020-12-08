package be.vives.gamesitor.database.entities

import androidx.room.Entity

@Entity
class DatabaseStats {
    var statsId: Int = 0
    var lifepoints: Int = 0
    var attack: Int = 0
    var strength: Int = 0
    var defence: Int = 0

}
