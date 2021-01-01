package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import be.vives.gamesitor.database.entities.DatabaseCharacter

@Dao
interface CharacterDao {

    @Query("select * from databasecharacter")
    fun getCharacters(): LiveData<List<DatabaseCharacter>>

    @Query("select * from databasecharacter where characterId = :characterId")
    fun getCharacterById(characterId: Int) : LiveData<DatabaseCharacter>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg characters: DatabaseCharacter)


    @Query("UPDATE databasecharacter set exp = :exp where characterId= :characterId")
    fun updateExpFromCharacter(exp : Long ,characterId : String)


}