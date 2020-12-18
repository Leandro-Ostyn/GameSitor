package be.vives.gamesitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import be.vives.gamesitor.database.dao.*
import be.vives.gamesitor.database.dbRelationships.crossRefs.*
import be.vives.gamesitor.database.entities.*


@Database(
    entities = arrayOf(
        DatabaseBackground::class,
        DatabaseCategory::class,
        DatabaseCharacter::class,
        DatabaseEffect::class,
        DatabaseEquipment::class,
        DatabaseInventory::class,
        DatabaseItem::class,
        DatabasePlayer::class,
        DatabaseReward::class,
        DatabaseSettings::class,
        DatabaseShop::class,
        DatabaseStage::class,
        DatabaseStats::class,
        DatabaseType::class,

        CategoryTypeCrossRef::class,
        EquipmentItemsCrossRef::class,
        InventoryItemsCrossRef::class,
        ItemEffectCrossRef::class,
        ShopItemCrossRef::class,
        TypeItemCrossRef::class
    ),
    version = 3,
    exportSchema = false
)
//@TypeConverters(Converters::class)
abstract class GameSitorDatabase : RoomDatabase() {

    abstract val backgroundDao: BackgroundDao
    abstract val categoryDao: CategoryDao
    abstract val characterDao: CharacterDao
    abstract val effectDao: EffectDao
    abstract val equipmentDao: EquipmentDao
    abstract val inventoryDao: InventoryDao
    abstract val itemDao: ItemDao
    abstract val playerDao: PlayerDao
    abstract val rewardDao: RewardDao
    abstract val settingsDao: SettingsDao
    abstract val shopDao: ShopDao
    abstract val stageDao: StageDao
    abstract val statsDao: StatsDao
    abstract val typeDao: TypeDao



}

private lateinit var INSTANCE: GameSitorDatabase

fun getDatabase(context: Context): GameSitorDatabase {
    synchronized(GameSitorDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GameSitorDatabase::class.java,
                "GameSitor"
            )
                .build()
        }
    }
    return INSTANCE
}


