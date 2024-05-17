package com.example.cryptoapp.di

import android.app.Application
import com.example.cryptoapp.presentation.CryptoApp
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, DomainModule::class, WorkerModule::class])
interface ApplicationComponent {

    fun activityComponentFactory(): ActivityComponent.Factory

    fun inject(application: CryptoApp)

    @Component.Factory
    interface ApplicationComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}