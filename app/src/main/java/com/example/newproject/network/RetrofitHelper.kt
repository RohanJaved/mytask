package com.example.newproject.network

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.example.newproject.RoomDb.AppDatabase
import com.example.newproject.RoomDb.FavoriteDrinkDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelper {

    // Chucker Interceptor for network logging
    @Singleton
    @Provides
    fun providesChuckInterceptor(@ApplicationContext appContext: Context): ChuckerInterceptor {
        val chuckerCollector = ChuckerCollector(
            context = appContext,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
        return ChuckerInterceptor.Builder(appContext)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(true)
            .build()
    }

    // Provide Retrofit instance
    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Provide OkHttpClient with logging and Chucker
    @Singleton
    @Provides
    fun getOkHttpClient(chuckerInterceptor: ChuckerInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor())
        builder.addInterceptor(chuckerInterceptor) // Only in Debug
        return builder.build()
    }

    // Provide the ApiInterface (Retrofit service)
    @Singleton
    @Provides
    fun getApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    // Provide Context (application context)
    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    // Provide the AppDatabase instance
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration() // Optional: handle migrations
            .build()
    }

    // Provide the UserDao instance
    @Singleton
    @Provides
    fun provideUserDao(appDatabase: AppDatabase): FavoriteDrinkDao {
        return appDatabase.favoriteDrinkDao()
    }

    // Logging Interceptor for HTTP requests
    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return logInterceptor
    }
}
