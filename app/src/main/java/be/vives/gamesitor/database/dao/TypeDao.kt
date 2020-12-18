package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.crossRefs.TypeItemCrossRef
import be.vives.gamesitor.database.entities.DatabaseType

@Dao
interface TypeDao {

    @Query("select * from databasetype")
    fun getTypes(): LiveData<List<DatabaseType>>

    @Query("select * from databasetype where typeId= :typeId")
    fun getType(typeId: Int): LiveData<DatabaseType>

//    @Query("select * from databasetype")
//    fun getTypesWithItems(): LiveData<List<Type>>
//
//    @Query("select * from databasetype where typeId= :typeId")
//    fun getTypeWithItems(typeId: Int): LiveData<Type>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg types: DatabaseType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCrossReff(vararg types: TypeItemCrossRef)

}