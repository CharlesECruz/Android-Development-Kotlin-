package ca.ciccc.carlos.contacts.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://randomuser.me/api/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ContactApiService {
    @GET("?nat=ca")
    fun getContactsAsync(@Query("results") num: Int): Deferred<ContactList>
}

object ContactApi {
    val retrofitService: ContactApiService by lazy {
        retrofit.create(ContactApiService::class.java)
    }
}