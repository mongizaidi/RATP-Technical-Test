package m.z.ratp.test.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import m.z.ratp.test.data.api.RATPAPIClient
import m.z.ratp.test.data.api.RATPAPIClient.Companion.CLIENT_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by Mongi Zaidi on 11/03/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
object APIClientModule {

    @Provides
    fun provideBaseUrl(): String = "https://data.ratp.fr/api/records/1.0/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
        return Retrofit.Builder()
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): RATPAPIClient =
        retrofit.create(RATPAPIClient::class.java)

}