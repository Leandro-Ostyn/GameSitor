package sitorApplication

import android.app.Application
import be.vives.gamesitor.database.getDatabase
import be.vives.gamesitor.repository.Repository
import timber.log.Timber


class SitorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}