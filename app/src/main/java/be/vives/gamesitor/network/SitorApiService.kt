package be.vives.gamesitor.network

import be.vives.gamesitor.database.dbRelationships.CrossRefs.*
import be.vives.gamesitor.database.entities.*
import be.vives.gamesitor.domain.models.*
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

private val retrofit: Retrofit.Builder by lazy {
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
}

object SitorApi {
    val sitorApiService: SitorApiService by lazy {
        retrofit.build().create(SitorApiService::class.java)
    }
}

interface SitorApiService {

    @GET("BackgroundApi")
    fun getBackgrounds(): Deferred<List<DatabaseBackground>>

    @GET("CategoryApi")
    fun getCategories(): Deferred<List<DatabaseCategory>>

    @GET("CategoryTypeApi")
    fun getCategoryTypes(): Deferred<List<CategoryTypeCrossRef>>

    @GET("EffectApi")
    fun getEffects(): Deferred<List<DatabaseEffect>>

    @GET("EffectListApi")
    fun getEffectLists(): Deferred<List<ItemEffectCrossRef>>

    @GET("CharacterApi")
    fun getCharacters(): Deferred<List<DatabaseCharacter>>

    @GET("EquipmentApi")
    fun getEquipments(): Deferred<List<DatabaseEquipment>>

    @GET("EquipmentItemsApi")
    fun getEquipmentItems(): Deferred<List<EquipmentItemsCrossRef>>

    @GET("InventoryApi")
    fun getInventory(): Deferred<List<DatabaseInventory>>

    @GET("InventoryItemsApi")
    fun getInventoryItemsSet(): Deferred<List<InventoryItemsCrossRef>>

    @GET("ItemApi")
    fun getItems(): Deferred<List<DatabaseItem>>

    @GET("RewardApi")
    fun getRewards(): Deferred<List<DatabaseReward>>

    @GET("ShopApi")
    fun getShops(): Deferred<List<DatabaseShop>>

    @GET("StageApi")
    fun getStages(): Deferred<List<DatabaseStage>>

    @GET("StatsApi")
    fun getStats(): Deferred<List<DatabaseStats>>

    @GET("StockApi")
    fun getStocks(): Deferred<List<ShopItemCrossRef>>

    @GET("TypeApi")
    fun getTypes(): Deferred<List<DatabaseType>>

    @GET("TypeItemApi")
    fun getTypeItems(): Deferred<List<TypeItemCrossRef>>

}


