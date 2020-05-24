package com.example.ddcharactercreator;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM characterList")
    LiveData<List<Character>> loadAllCharacters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(Character character);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCharacter(Character character);

    @Delete
    void deleteCharacter(Character character);
}
