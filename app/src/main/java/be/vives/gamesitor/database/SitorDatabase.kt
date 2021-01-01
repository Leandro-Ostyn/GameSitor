package be.vives.gamesitor.database

import androidx.room.Database
import androidx.room.RoomDatabase
import be.vives.gamesitor.database.dao.*
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.*
import be.vives.gamesitor.database.entities.*


@Database(
    entities = [DatabaseBackground::class, DatabaseCategory::class,
        DatabaseCharacter::class, DatabaseEffect::class, DatabaseEquipment::class,
        DatabaseInventory::class, DatabaseItem::class, DatabasePlayer::class,
        DatabaseReward::class, DatabaseSettings::class, DatabaseShop::class,
        DatabaseStage::class, DatabaseStats::class, DatabaseType::class,
        CategoryTypeCrossRef::class, EquipmentItemsCrossRef::class,
        InventoryItemsCrossRef::class, ItemEffectCrossRef::class,
        ShopItemCrossRef::class, TypeItemCrossRef::class],
    version = 8,
    exportSchema = false
)

abstract class SitorDatabase : RoomDatabase() {

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


