package sg.toru.z.repository.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ZNetwork {

    val okHttpClient = generateOkHttpClient()

    private fun generateOkHttpClient(): OkHttpClient{
        return OkHttpClient()
            .newBuilder()
            .readTimeout(3000, TimeUnit.MILLISECONDS)
            .writeTimeout(3000, TimeUnit.MILLISECONDS)
            .addInterceptor(generateLoggingInterceptor()).build()
    }

    private fun generateLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private fun generateRetrofit(baseUrl:String = "https://www.google.com"): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun provideNetworkService() = ZNetwork.generateRetrofit().create(ZNetworkService::class.java)
}