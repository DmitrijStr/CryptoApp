package com.example.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.workers.CoinWorkerFactory
import com.example.cryptoapp.di.DaggerApplicationComponent
import javax.inject.Inject

class CryptoApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CoinWorkerFactory

    val appModule by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        appModule.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }
}