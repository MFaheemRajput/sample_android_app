package nl.enochtech.testappaa.core.api

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import android.content.Context
import java.util.concurrent.TimeUnit
import nl.enochtech.testappaa.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import nl.enochtech.testappaa.core.application.MyApplication

object ApiClient {
    private val REQUEST_TIMEOUT = 60
    private val REQUEST_READOUT = 60
    private val REQUEST_WRITEOUT = 60
    private var sRetrofit: RemoteApiService? = null
    private var mRetrofit: Retrofit? = null
    private var context: Context? = null

    fun getRetrofitClient(contextParam: Context? = MyApplication.applicationContext()): RemoteApiService? {
        this.context = contextParam!!
        if (sRetrofit == null) {
            createAPIClient()
            }
        return sRetrofit
    }

    private fun createAPIClient() {
        //-----OkHttp 3 client and intercept for logs and header authentication
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(REQUEST_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .readTimeout(REQUEST_READOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(REQUEST_WRITEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(interceptor)
        okHttpClientBuilder.addInterceptor { chain ->

            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        val okHttpClient = okHttpClientBuilder.build()
       // ----------Retrofit Client
        val apiUrl = BuildConfig.API_BASE_URL

        sRetrofit = Retrofit.Builder()
                .baseUrl(apiUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RemoteApiService::class.java)

        }

}