package com.example.ddcharactercreator;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class CharacterViewModel extends AndroidViewModel {

    private CharacterRepository repository;
    private LiveData<List<Character>> characters;
    private static final String TAG = CharacterViewModel.class.getSimpleName();

    public CharacterViewModel(@NonNull Application application) {
        super(application);
        repository = new CharacterRepository(application);
        characters = repository.loadAllCharacters();
        CharacterDatabase database = CharacterDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the characters from the database");
        characters = database.characterDao().loadAllCharacters();
    }

    public void insertCharacter(Character character){
        repository.insertCharacter(character);
    }
    public void updateCharacter(Character character){
        repository.updateCharacter(character);
    }
    public void deleteCharacter(Character character){
        repository.deleteCharacter(character);
    }
    public LiveData<List<Character>> loadAllCharacters(){
        return characters;
    }

    public LiveData<List<Character>> getCharacters(){
        return characters;
    }
}
