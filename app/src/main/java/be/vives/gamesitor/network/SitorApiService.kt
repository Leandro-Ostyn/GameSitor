package be.vives.gamesitor.network

import be.vives.gamesitor.domain.models.Background
import be.vives.gamesitor.domain.models.Category
import be.vives.gamesitor.domain.models.Character
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://sitorapi.azurewebsites.net/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit : Retrofit.Builder by lazy {
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL) }

object SitorApi{
    val sitorApiService: SitorApiService by lazy {
        retrofit.build().create(SitorApiService::class.java)
    }}

interface SitorApiService {
//Hier alle getters aanmaken en later nog setters toevoegen

    @GET("CategoryApi")
     fun getCategories(): Deferred<List<Category>>

//    @GET("StatsApi/{id}")
//     fun getStats(
//        @Path("id") id: Int
//    ): Deferred<DatabaseStats>

    @GET("CharacterApi/{id}")
   fun getCharacter(
       @Path("id") id: Int
   ): Deferred<Character>

    @GET("BackgroundApi")
    fun getBackgrounds(
    ): Deferred<List<Background>>
}

