package com.example.ddcharactercreator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Character.class}, version = 1, exportSchema = false)
@TypeConverters({ArrayListIntegerConverter.class, ArrayListStringConverter.class, ArrayListSpellConverter.class})
public abstract class CharacterDatabase extends RoomDatabase {

    private static final String LOG_TAG = CharacterDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Character List";
    private static CharacterDatabase sInstance;
    public abstract CharacterDao characterDao();

    public static CharacterDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        CharacterDatabase.class, CharacterDatabase.DATABASE_NAME)
                        .addCallback(roomCallback)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sInstance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private CharacterDao characterDao;

        private PopulateDbAsyncTask(CharacterDatabase db){
            characterDao = db.characterDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
