package com.example.dindintest

import androidx.multidex.MultiDexApplication
import com.example.dindintest.injection.serviceModule
import com.example.dindintest.injection.usecaseModule
import org.koin.core.context.startKoin


class RootApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(serviceModule, usecaseModule))
        }
    }
}