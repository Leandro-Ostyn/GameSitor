package be.vives.gamesitor.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.vives.gamesitor.database.entities.DatabaseCategory

@Dao
interface CategoryDao {

    @Query("select * from databasecategory")
    fun getCategories(): LiveData<List<DatabaseCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg categories: DatabaseCategory)
}