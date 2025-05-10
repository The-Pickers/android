package gsc.ThePickers

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThePickersApplication : Application(){
    companion object {
        lateinit var instance: ThePickersApplication
            private set

        fun getString(resId: Int): String {
            return instance.getString(resId)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}