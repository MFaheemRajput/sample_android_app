package nl.enochtech.testappaa.core.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager
import com.facebook.stetho.Stetho

class MyApplication : Application() {

    init {
        instance = this
    }
    companion object {
        private var instance: MyApplication? = null
        fun applicationContext(): MyApplication {
            return instance as MyApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}