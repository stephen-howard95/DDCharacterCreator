package com.example.ddcharactercreator;

import android.content.Context;
import android.util.Log;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Character.class}, version = 1, exportSchema = false)
@TypeConverters({ArrayListIntegerConverter.class, ArrayListStringConverter.class, ArrayListSpellConverter.class})
public abstract class CharacterDatabase extends RoomDatabase {

    private static final String LOG_TAG = CharacterDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Character List";
    private static CharacterDatabase sInstance;

    public static CharacterDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CharacterDatabase.class, CharacterDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract CharacterDao characterDao();
}
