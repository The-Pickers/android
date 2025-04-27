package gsc.ZupStar

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZupStarApplication : Application(){
    companion object {
        lateinit var instance: ZupStarApplication
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