package com.houdin.knucklebonesclone

import android.app.Application
import com.houdin.knucklebonesclone.shared.di.appModule
import com.houdin.knucklebonesclone.shared.preferences.AppPreferences
import org.koin.core.context.startKoin

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)

        startKoin{
            modules(appModule)
        }
    }
}
