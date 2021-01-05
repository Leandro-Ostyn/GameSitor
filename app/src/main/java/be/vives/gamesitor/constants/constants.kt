package be.vives.gamesitor.constants

import android.content.Context
import androidx.room.Room
import be.vives.gamesitor.services.MusicHandler
import be.vives.gamesitor.database.SitorDatabase
import be.vives.gamesitor.services.AlarmSettings
import be.vives.gamesitor.repository.Repository

const val WEAPON = "Weapon"
const val H2WEAPON = "2H weapon"
const val HELMET = "Helmet"
const val BODY = "Body"
const val LEGS = "Legs"
const val SHIELD = "Shield"
const val BOOTS = "Boots"
const val EQUIP = "Equip"
const val BUY = "Buy"
const val SELL = "Sell"
const val OLD_PASSWORD = "Old password"
const val NEW_PASSWORD = "New Password"
const val CONFIRM_PASSWORD = "Confirm Password"
const val NOTIFICATION_HOUR = 11
const val NOTIFICATION_MINUTES = 5
const val NOTIFICATION_SECONDS = 0
const val VICTORY = "VICTORY"
const val DEFEAT = "DEFEAT"
const val PLAYER = "Player"
const val HITPOINTS ="Hitpoints"
const val DEFENCE = "Defence"
const val ATTACK = "Attack"
const val STRENGTH = "Strength"
const val REMOVE = "Remove"
const val ADD = "Add"
const val MULTIPLIER = 2

//create instance! "singleton" -> always same reference points

private lateinit var INSTANCE: SitorDatabase

fun getDatabase(context: Context): SitorDatabase {
    synchronized(SitorDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                SitorDatabase::class.java,
                "GameSitor"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}


private lateinit var REPOINSTANCE: Repository

fun getRepository(database: SitorDatabase): Repository {
    if (!::REPOINSTANCE.isInitialized) {
        REPOINSTANCE = Repository(database)
    }
    return REPOINSTANCE
}


private lateinit var MUSICINSTANCE: MusicHandler

fun getMusic(context: Context): MusicHandler {
    if (!::MUSICINSTANCE.isInitialized) {
        MUSICINSTANCE = MusicHandler(context)
    }
    return MUSICINSTANCE
}


private lateinit var ALARMSETTINGS: AlarmSettings

fun getNotificationSettings(context: Context): AlarmSettings {
    if (!::ALARMSETTINGS.isInitialized) {
        ALARMSETTINGS = AlarmSettings(context)
    }
    return ALARMSETTINGS
}