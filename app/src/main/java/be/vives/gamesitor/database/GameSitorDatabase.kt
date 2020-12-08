package be.vives.gamesitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.vives.gamesitor.database.dao.BackgroundDao
import be.vives.gamesitor.database.dao.PlayerDao
import be.vives.gamesitor.database.dao.SettingsDao

@Database(
    entities = arrayOf(DatabasePlayer::class, DatabaseSettings::class),
    version = 1,
    exportSchema = false
)
abstract class GameSitorDatabase : RoomDatabase() {

    abstract val backgroundDao: BackgroundDao
    abstract val playerDao: PlayerDao
    abstract val settingsDao: SettingsDao
}
    private lateinit var INSTANCE: GameSitorDatabase

    fun getDatabase(context: Context): GameSitorDatabase {
        synchronized(GameSitorDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameSitorDatabase::class.java,
                        "GameSitor"
                    ).build()
                }
        }
        return INSTANCE
    }


