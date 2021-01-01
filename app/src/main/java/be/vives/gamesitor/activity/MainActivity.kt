package be.vives.gamesitor.activity

import android.app.AlarmManager
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import be.vives.gamesitor.R
import be.vives.gamesitor.constants.*
import be.vives.gamesitor.databinding.ActivityMainBinding
import java.util.*


open class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val activityViewmodel: ActivityViewModel by lazy {
        val activity = requireNotNull(this) {}
        ViewModelProvider(
            this,
            ActivityViewModel.ActivityViewModelFactory(activity.application)
        ).get(
            ActivityViewModel::class.java
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val navController = this.findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        activityViewmodel.settings.observeForever {
            if (it != null) {
                if (it.musicOn) {
                    getMusic(this).playMusic()
                }
                val alarmSettings = getNotificationSettings(this)
                alarmSettings.init(getSystemService(Context.ALARM_SERVICE) as AlarmManager)
                if (it.setNotification) {
                    alarmSettings.setAlarm()
                } else {
                    alarmSettings.cancelAlarm()
                }
            }
        }


    }
    override fun onPause() {
        super.onPause()
        getMusic(this).stopMusic()
    }


    override fun onStart() {
        super.onStart()
        activityViewmodel.settings.observe(this, {
            if (it != null) {
                if (it.musicOn) {
                    getMusic(this).create()
                    getMusic(this).playMusic()
                }
            }
        })
    }

}