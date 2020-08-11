package com.example.ddcharactercreator;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class SpellRepository{
    private SpellDao spellDao;
    private LiveData<List<Spell>> allSpells;

    public SpellRepository(Application application){
        SpellDatabase database = SpellDatabase.getInstance(application);
        spellDao = database.spellDao();
        allSpells = spellDao.loadAllSpells();
    }

    public void insertSpell(Spell spell){
        new SpellRepository.InsertSpellAsyncTask(spellDao).execute(spell);
    }
    public void deleteSpell(Spell spell){
        new SpellRepository.DeleteSpellAsyncTask(spellDao).execute(spell);
    }
    public void updateSpell(Spell spell){
        new SpellRepository.UpdateSpellAsyncTask(spellDao).execute(spell);
    }
    public LiveData<List<Spell>> loadAllSpells(){
        return allSpells;
    }

    private static class InsertSpellAsyncTask extends AsyncTask<Spell, Void, Void> {
        private SpellDao spellDao;

        private InsertSpellAsyncTask(SpellDao spellDao){
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(Spell... spells) {
            spellDao.insertSpell(spells[0]);
            return null;
        }
    }
    private static class DeleteSpellAsyncTask extends AsyncTask<Spell, Void, Void>{
        private SpellDao spellDao;

        private DeleteSpellAsyncTask(SpellDao spellDao){
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(Spell... spells) {
            spellDao.deleteSpell(spells[0]);
            return null;
        }
    }
    private static class UpdateSpellAsyncTask extends AsyncTask<Spell, Void, Void>{
        private SpellDao spellDao;

        private UpdateSpellAsyncTask(SpellDao spellDao){
            this.spellDao = spellDao;
        }

        @Override
        protected Void doInBackground(Spell... spells) {
            spellDao.updateSpell(spells[0]);
            return null;
        }
    }
}