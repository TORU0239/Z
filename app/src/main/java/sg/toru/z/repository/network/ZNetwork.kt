package sg.toru.z.repository.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sg.toru.z.util.Utils
import java.util.concurrent.TimeUnit

object ZNetwork {

    private val okHttpClient = generateOkHttpClient()
    val retrofit = generateRetrofit()

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

    private fun generateRetrofit(baseUrl:String = Utils.BASEURL): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun provideNetworkService() = retrofit.create(ZNetworkService::class.java)
}