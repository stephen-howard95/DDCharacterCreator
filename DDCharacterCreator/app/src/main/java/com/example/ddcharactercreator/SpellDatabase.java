package com.example.ddcharactercreator;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Spell.class}, version = 1, exportSchema = false)
public abstract class SpellDatabase extends RoomDatabase {

    private static final String LOG_TAG = SpellDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Spell List";
    private static SpellDatabase sInstance;
    public abstract SpellDao spellDao();

    public static SpellDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        SpellDatabase.class, SpellDatabase.DATABASE_NAME)
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

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private SpellDao spellDao;

        private PopulateDbAsyncTask(SpellDatabase db){
            spellDao = db.spellDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
