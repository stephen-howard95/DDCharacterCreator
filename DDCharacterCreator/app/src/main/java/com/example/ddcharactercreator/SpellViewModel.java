package com.example.ddcharactercreator;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class SpellViewModel extends AndroidViewModel {

    private LiveData<List<Spell>> spells;
    private static final String TAG = SpellViewModel.class.getSimpleName();

    public SpellViewModel(@NonNull Application application) {
        super(application);
        SpellDatabase database = SpellDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the spells from the database");
        spells = database.spellDao().loadAllSpells();
    }

    public LiveData<List<Spell>> getSpells(){
        return spells;
    }
}
