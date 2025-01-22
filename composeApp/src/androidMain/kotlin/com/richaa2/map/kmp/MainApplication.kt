package com.richaa2.map.kmp

import android.app.Application
import com.richaa2.map.kmp.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.KoinAppDeclaration

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
         initKoin {
             androidContext(this@MainApplication)
         }
    }
}