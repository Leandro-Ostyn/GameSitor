package be.vives.gamesitor.services

import android.content.Context
import android.media.MediaPlayer
import be.vives.gamesitor.R
import timber.log.Timber

class MusicHandler(val context: Context) {
    var mediaPlayer = MediaPlayer.create(context, R.raw.sound)

    fun playMusic() {
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }

    fun stopMusic() {
        try {
        mediaPlayer.stop()
        mediaPlayer.reset()
        mediaPlayer.release()}
        catch (e : Exception){
            Timber.i(e.message)
        }
    }

    fun create() {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound)
    }

    fun isPlaying(): Boolean {
        return try {
            mediaPlayer.isPlaying
        } catch (e: Exception) {
            false

        }
    }
}
