package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.dbRelationships.crossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.DatabaseType

@Dao
interface TypeDao {

    @Query("select * from databasetype")
    fun getTypes(): LiveData<List<DatabaseType>>

    @Query("select * from databasetype where type= :type")
    fun getTypeByName(type: String): LiveData<DatabaseType>

    @Query("select * from TypeItemCrossRef where typeId = :typeId")
    fun getItemsByCrossRefWithType(typeId: Int): LiveData<List<TypeItemCrossRef>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg types: DatabaseType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg types: TypeItemCrossRef)

    @Query("select * from typeitemcrossref ")
    fun getCrossReff(): LiveData<List<TypeItemCrossRef>>
}