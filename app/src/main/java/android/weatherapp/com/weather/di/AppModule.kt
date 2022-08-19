package android.weatherapp.com.weather.di

import android.app.Application
import android.weatherapp.com.weather.database.CountryAppDatabase
import android.weatherapp.com.weather.database.CountryAppDatabase.Companion.DATABASE_NAME
import android.weatherapp.com.weather.database.CountryDao
import android.weatherapp.com.weather.location.LocationManagerService
import android.weatherapp.com.weather.network.WeatherService
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WeatherService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)

    @Provides
    @Singleton
    fun provideDatabase(app: Application): CountryAppDatabase =
        Room.databaseBuilder(app, CountryAppDatabase::class.java, DATABASE_NAME)
            .createFromAsset("database/country.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideLocationManagerService(): LocationManagerService = LocationManagerService()

    @Provides
    @Singleton
    fun provideCountryDao(database: CountryAppDatabase): CountryDao = database.countriesDao()
}
