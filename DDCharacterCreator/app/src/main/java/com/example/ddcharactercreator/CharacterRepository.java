package com.example.ddcharactercreator;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;

public class CharacterRepository {
    private CharacterDao characterDao;
    private LiveData<List<Character>> allCharacters;

    public CharacterRepository(Application application){
        CharacterDatabase database = CharacterDatabase.getInstance(application);
        characterDao = database.characterDao();
        allCharacters = characterDao.loadAllCharacters();
    }

    public void insertCharacter(Character character){
        new InsertCharacterAsyncTask(characterDao).execute(character);
    }
    public void deleteCharacter(Character character){
        new DeleteCharacterAsyncTask(characterDao).execute(character);
    }
    public void updateCharacter(Character character){
        new UpdateCharacterAsyncTask(characterDao).execute(character);
    }
    public LiveData<List<Character>> loadAllCharacters(){
        return allCharacters;
    }

    private static class InsertCharacterAsyncTask extends AsyncTask<Character, Void, Void>{
        private CharacterDao characterDao;

        private InsertCharacterAsyncTask(CharacterDao characterDao){
            this.characterDao = characterDao;
        }

        @Override
        protected Void doInBackground(Character... characters) {
            characterDao.insertCharacter(characters[0]);
            return null;
        }
    }
    private static class DeleteCharacterAsyncTask extends AsyncTask<Character, Void, Void>{
        private CharacterDao characterDao;

        private DeleteCharacterAsyncTask(CharacterDao characterDao){
            this.characterDao = characterDao;
        }

        @Override
        protected Void doInBackground(Character... characters) {
            characterDao.deleteCharacter(characters[0]);
            return null;
        }
    }
    private static class UpdateCharacterAsyncTask extends AsyncTask<Character, Void, Void>{
        private CharacterDao characterDao;

        private UpdateCharacterAsyncTask(CharacterDao characterDao){
            this.characterDao = characterDao;
        }

        @Override
        protected Void doInBackground(Character... characters) {
            characterDao.updateCharacter(characters[0]);
            return null;
        }
    }
}
