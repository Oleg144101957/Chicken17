package com.chicken.two.di

import android.content.Context
import com.chicken.two.data.AppsRepositoryImpl
import com.chicken.two.data.DataStoreRepositoryImpl
import com.chicken.two.data.GaidRepositoryImpl
import com.chicken.two.data.InstallReferrerImpl
import com.chicken.two.data.NetworkCheckerRepositoryImpl
import com.chicken.two.data.PostErrorRepositoryImpl
import com.chicken.two.data.PushServiceInitializerImpl
import com.chicken.two.domain.AppsRepository
import com.chicken.two.domain.DataStoreRepository
import com.chicken.two.domain.GaidRepository
import com.chicken.two.domain.InstallReferrer
import com.chicken.two.domain.NetworkCheckerRepository
import com.chicken.two.domain.PostErrorRepository
import com.chicken.two.domain.PushServiceInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNetwork(@ApplicationContext context: Context): NetworkCheckerRepository {
        return NetworkCheckerRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideUserDataStorage(@ApplicationContext context: Context): DataStoreRepository {
        return DataStoreRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideAppsRepository(): AppsRepository {
        return AppsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun providePushRepository(): PushServiceInitializer {
        return PushServiceInitializerImpl()
    }

    @Provides
    @Singleton
    fun provideReferrer(@ApplicationContext context: Context): InstallReferrer {
        return InstallReferrerImpl(context)
    }

    @Provides
    @Singleton
    fun provideGaidRepository(@ApplicationContext context: Context): GaidRepository {
        return GaidRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun providePostError(): PostErrorRepository {
        return PostErrorRepositoryImpl()
    }


}