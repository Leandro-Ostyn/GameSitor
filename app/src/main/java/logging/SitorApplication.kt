package logging

import android.app.Application
import timber.log.Timber


class SitorApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

}