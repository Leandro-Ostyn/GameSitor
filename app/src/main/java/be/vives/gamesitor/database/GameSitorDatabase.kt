package be.vives.gamesitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.vives.gamesitor.database.dao.*
import be.vives.gamesitor.database.entities.*

@Database(
    entities = arrayOf(DatabaseSettings::class,DatabaseBackground::class
        ,DatabaseStats::class,DatabaseItem::class,DatabaseEffect::class),
    version = 2,
    exportSchema = false
)
abstract class GameSitorDatabase : RoomDatabase() {

    abstract val backgroundDao: BackgroundDao
 //   abstract val playerDao: PlayerDao
    abstract val settingsDao: SettingsDao
  //  abstract val categoryDao : CategoryDao
    abstract val statsDao :StatsDao
    abstract val itemDao : ItemDao
}
    private lateinit var INSTANCE: GameSitorDatabase

    fun getDatabase(context: Context): GameSitorDatabase {
        synchronized(GameSitorDatabase::class.java) {
            if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GameSitorDatabase::class.java,
                        "GameSitor"
                    )
                        .build()
                }
        }
        return INSTANCE
    }


