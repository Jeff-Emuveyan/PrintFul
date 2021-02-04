package com.seamfix.printful.util.hilt.modules

import android.content.Context
import com.seamfix.printful.data.UserRepository
import com.seamfix.printful.datasource.local.AppDatabase
import com.seamfix.printful.datasource.remote.ApiClient
import com.seamfix.printful.datasource.remote.Service
import com.seamfix.printful.util.NetworkChecker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object HiltModule {

    /***  Create UserRepository ***/
    @Provides
    fun provideUserRepository(@ApplicationContext appContext: Context): UserRepository{
        val retrofit = ApiClient.getClient()
        val service: Service = retrofit.create(Service::class.java)
        val db = AppDatabase.getDatabase(appContext)

        val repository = UserRepository()
        repository.service = service
        repository.database = db
        return repository
    }

    @Provides
    fun provideNetworkChecker(@ApplicationContext appContext: Context): NetworkChecker{
        return NetworkChecker(appContext)
    }
}