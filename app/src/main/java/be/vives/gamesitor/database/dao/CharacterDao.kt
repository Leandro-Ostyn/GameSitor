package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.dbRelationships.CrossRefs.CategoryTypeCrossRef
import be.vives.gamesitor.database.entities.DatabaseCategory
import be.vives.gamesitor.database.entities.DatabaseCharacter

@Dao
interface CharacterDao {

    @Query("select * from databasecharacter")
    fun getCharacters(): LiveData<List<DatabaseCharacter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg characters: DatabaseCharacter)


}