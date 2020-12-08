package be.vives.gamesitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GameSitor::class], version = 1, exportSchema = false)
abstract class GameSitorDatabase : RoomDatabase() {

 //   abstract val playerDatabaseDao: PlayerDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: GameSitorDatabase? = null

        fun getInstance(context: Context): GameSitorDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GameSitorDatabase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }

}
