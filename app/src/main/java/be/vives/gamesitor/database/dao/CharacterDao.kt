package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseCharacter
import be.vives.gamesitor.models.Character

@Dao
interface CharacterDao {

    @Query("select * from databasecharacter")
    fun getCharacters(): LiveData<List<DatabaseCharacter>>

    @Query("select * from databasecharacter where characterId = :characterId")
    fun getCharacterById(characterId: Int) : LiveData<DatabaseCharacter>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg characters: DatabaseCharacter)


}