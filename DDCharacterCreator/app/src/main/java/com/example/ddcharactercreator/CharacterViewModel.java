package com.example.ddcharactercreator;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CharacterViewModel extends AndroidViewModel {

    private LiveData<List<Character>> characters;
    private static final String TAG = CharacterViewModel.class.getSimpleName();

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        CharacterDatabase database = CharacterDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the characters from the database");
        characters = database.characterDao().loadAllCharacters();
    }

    public LiveData<List<Character>> getCharacters(){
        return characters;
    }
}