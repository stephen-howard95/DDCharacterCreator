package com.example.ddcharactercreator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SpellChooserActivity extends AppCompatActivity{

    private static Spell spell;
    private Character character = DetailActivity.character;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);

        SpellDatabase mDb = SpellDatabase.getInstance(getApplicationContext());
        List<Spell> spellsList = mDb.spellDao().loadAllSpells();

        SpellAdapter mAdapter = new SpellAdapter(this, new ArrayList<Spell>());

        ListView spellListView = findViewById(R.id.list);

        spellListView.setAdapter(mAdapter);

        if(spellsList != null && !spellsList.isEmpty()){
            for(int i = 0; i< spellsList.size(); i++){
                if(2* spellsList.get(i).getLevel()-1 > character.getLevel()){
                    spellsList.remove(i);
                    i--;
                }
            }
            mAdapter.addAll(spellsList);
        } else{
            Toast.makeText(getApplicationContext(), "Spell Database is empty. Try again later", Toast.LENGTH_LONG).show();
        }

        spellListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spell = (Spell) parent.getItemAtPosition(position);
                //prevents duplicate spells from being added to the list and warns the user
                int i=0;
                boolean spellIsUnique = true;
                do{
                    if(character.getSpellsKnown().size() == 0){
                        break;
                    }
                    Spell knownSpell = character.getSpellsKnown().get(i);
                    if(spell.getSpellName().equals(knownSpell.getSpellName())){
                        Toast.makeText(getApplicationContext(), "You already know this spell", Toast.LENGTH_SHORT).show();
                        spellIsUnique = false;
                        break;
                    }
                    i++;
                } while(i<character.getSpellsKnown().size());
                if(spellIsUnique){
                    character.getSpellsKnown().add(spell);
                    finish();
                }
            }
        });
    }
}
