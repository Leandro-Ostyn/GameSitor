package be.vives.gamesitor.services

import android.R
import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import be.vives.gamesitor.activity.MainActivity
import be.vives.gamesitor.constants.NOTIFICATION_MINUTES
import be.vives.gamesitor.constants.NOTIFICATION_SECONDS
import be.vives.gamesitor.constants.NOTIFICATION_HOUR
import java.util.*



class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHelper = NotificationHelper(context);
        val nb = notificationHelper.channelNotification;
        notificationHelper.manager?.notify(1, nb.build());

    }
}

class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private var mManager: NotificationManager? = null

    @TargetApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        val channel =
            NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
        manager!!.createNotificationChannel(channel)
    }

    val manager: NotificationManager?
        get() {
            if (mManager == null) {
                mManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            }
            return mManager
        }
    private val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    private val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
    val channelNotification: NotificationCompat.Builder
        get() = NotificationCompat.Builder(applicationContext, channelID)
            .setContentTitle("Sitor wants to play!")
            .setContentText("There are battles waiting for you!")
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)


    companion object {
        const val channelID = "channelID"
        const val channelName = "Channel Name"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }
    }
}

class AlarmSettings(val context: Context) {
    lateinit var alarmManager: AlarmManager
    private val intent = Intent(context, AlarmReceiver::class.java)
    private val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0)


    fun init(alarmManagerFun: AlarmManager) {
        alarmManager = alarmManagerFun
    }

    fun setAlarm() {
        val c = Calendar.getInstance()
        c[Calendar.HOUR_OF_DAY] = NOTIFICATION_HOUR
        c[Calendar.MINUTE] = NOTIFICATION_MINUTES
        c[Calendar.SECOND] = NOTIFICATION_SECONDS
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
    }

    fun cancelAlarm() {
        alarmManager.cancel(pendingIntent)
    }
}