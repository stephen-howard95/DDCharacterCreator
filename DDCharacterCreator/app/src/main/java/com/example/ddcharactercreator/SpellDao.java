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
public interface SpellDao {

    @Query("SELECT * FROM spellsList")
    LiveData<List<Spell>> loadAllSpells();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSpell(Spell spell);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSpell(Spell spell);

    @Delete
    void deleteSpell(Spell spell);
}
