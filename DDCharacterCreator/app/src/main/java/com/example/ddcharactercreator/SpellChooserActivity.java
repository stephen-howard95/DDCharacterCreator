package com.example.ddcharactercreator;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;

public class SpellChooserActivity extends AppCompatActivity{

    private static Spell spell;
    private Character character = DetailActivity.character;
    SpellDatabase mDb;
    List<Spell> completeSpellsList;

    private ArrayList<Spell> spellsList = new ArrayList<Spell>();
    private ArrayList<Spell> cantripsList = new ArrayList<Spell>();
    private int spellMax;
    private int cantripMax;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);

        mDb = SpellDatabase.getInstance(getApplicationContext());
        completeSpellsList = mDb.spellDao().loadAllSpells();
        Collections.sort(completeSpellsList, Spell.spellNameComparator);

        switch(character.getCharacterClass()){
            case "Bard":
                if(character.getLevel() <= 9){
                    spellMax = character.getLevel() + 3;
                } else if(character.getLevel() <= 11){
                    spellMax = character.getLevel() + 4;
                } else if(character.getLevel() <= 13){
                    spellMax = character.getLevel() + 3;
                } else if(character.getLevel() <= 15){
                    spellMax = character.getLevel() + 4;
                } else if(character.getLevel() <= 17){
                    spellMax = character.getLevel() + 3;
                } else if(character.getLevel() <= 20){
                    spellMax = 22;
                }
                cantripMax = 2;
                switch(character.getLevel()){
                    case 6:
                        if(character.getSubclass().equals("College of Lore")){
                            getMagicalSecrets();
                        }
                        break;
                    case 10:
                    case 14:
                    case 18:
                        getMagicalSecrets();
                        break;
                }
                break;
            case "Cleric":
                spellMax = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                cantripMax = 3;
                break;
            case "Druid":
                spellMax = character.getLevel() + calculateModifier(character.getStatValues().get(4));
                cantripMax = 2;
                if(character.getSubclass().contains("Circle of the Land")){
                    cantripMax += 1;
                }
                break;
            case "Paladin":
                spellMax = character.getLevel()/2 + calculateModifier(character.getStatValues().get(5));
                cantripMax = -2;
                break;
            case "Ranger":
                spellMax = (character.getLevel()/2) + 1;
                cantripMax = -2;
                break;
            case "Sorcerer":
                if(character.getLevel() <= 11){
                    spellMax = character.getLevel() + 1;
                } else if(character.getLevel() <= 13){
                    spellMax = character.getLevel();
                } else if(character.getLevel() <= 15){
                    spellMax = character.getLevel() - 1;
                } else if(character.getLevel() <= 17){
                    spellMax = character.getLevel() - 2;
                } else if(character.getLevel() <= 20){
                    spellMax = 15;
                }
                cantripMax = 4;
                if(character.getSubclass().equals("Stone Sorcery")){
                    addSpellsToClassList("compelled duel");
                    addSpellsToClassList("searing smite");
                    addSpellsToClassList("thunderous smite");
                    addSpellsToClassList("wrathful smite");
                    addSpellsToClassList("branding smite");
                    addSpellsToClassList("magic weapon");
                    addSpellsToClassList("blinding smite");
                    addSpellsToClassList("elemental weapon");
                    addSpellsToClassList("staggering smite");
                }
                break;
            case "Warlock":
                if(character.getLevel() <= 9){
                    spellMax = character.getLevel() + 1;
                } else if(character.getLevel() <= 11){
                    spellMax = character.getLevel();
                } else if(character.getLevel() <= 13){
                    spellMax = character.getLevel() - 1;
                } else if(character.getLevel() <= 15){
                    spellMax = character.getLevel() - 2;
                } else if(character.getLevel() <= 17){
                    spellMax = character.getLevel() - 3;
                } else if(character.getLevel() == 18){
                    spellMax = 14;
                } else if(character.getLevel() <= 20){
                    spellMax = 15;
                }
                cantripMax = 2;
                switch(character.getSubclass()){
                    case "The Archfey":
                        addSpellsToClassList("faerie fire");
                        addSpellsToClassList("sleep");
                        addSpellsToClassList("calm emotions");
                        addSpellsToClassList("phantasmal force");
                        addSpellsToClassList("blink");
                        addSpellsToClassList("plant growth");
                        addSpellsToClassList("dominate beast");
                        addSpellsToClassList("greater invisibility");
                        addSpellsToClassList("dominate person");
                        addSpellsToClassList("seeming");
                        break;
                    case "The Fiend":
                        addSpellsToClassList("burning hands");
                        addSpellsToClassList("command");
                        addSpellsToClassList("blindness/deafness");
                        addSpellsToClassList("scorching ray");
                        addSpellsToClassList("fireball");
                        addSpellsToClassList("stinking cloud");
                        addSpellsToClassList("fire shield");
                        addSpellsToClassList("wall of fire");
                        addSpellsToClassList("flame strike");
                        addSpellsToClassList("hallow");
                        break;
                    case "The Great Old One":
                        addSpellsToClassList("dissonant whispers");
                        addSpellsToClassList("hideous laughter");
                        addSpellsToClassList("detect thoughts");
                        addSpellsToClassList("phantasmal force");
                        addSpellsToClassList("clairvoyance");
                        addSpellsToClassList("sending");
                        addSpellsToClassList("dominate beast");
                        addSpellsToClassList("black tentacles");
                        addSpellsToClassList("dominate person");
                        addSpellsToClassList("telekinesis");
                        break;
                }
                break;
            case "Wizard":
                spellMax = character.getLevel() + calculateModifier(character.getStatValues().get(3));
                cantripMax = 3;
                break;
        }
        if(spellMax < 1){
            spellMax = 1;
        }
        if(character.getLevel() >= 10){
            cantripMax += 2;
        } else if(character.getLevel() >= 4){
            cantripMax += 1;
        }

        ArrayList<Spell> characterSpellsList = character.getSpellsKnown();

        // Separates Cantrips and Spells
        for(int i = 0; i< characterSpellsList.size(); i++){
            Spell spell = characterSpellsList.get(i);
            if(spell.getLevel() == 0 && !cantripsList.contains(spell)){
                cantripsList.add(spell);
            } else if(!cantripsList.contains(spell) && !spellsList.contains(spell)){
                spellsList.add(spell);
            }
        }

        SpellAdapter mAdapter = new SpellAdapter(this, new ArrayList<Spell>());

        ListView spellListView = findViewById(R.id.list);

        spellListView.setAdapter(mAdapter);

        //Limits the available spells for the character based on their level and character class
        if(completeSpellsList != null && !completeSpellsList.isEmpty()){
            if(character.getCharacterClass().equals("Ranger") || character.getCharacterClass().equals("Paladin")){
                for(int i = 0; i < completeSpellsList.size(); i++){
                    if(4*completeSpellsList.get(i).getLevel()-3 > character.getLevel() || !completeSpellsList.get(i).getClassList().contains(character.getCharacterClass())){
                        completeSpellsList.remove(i);
                        i--;
                    }
                }
            }else{
                for(int i = 0; i< completeSpellsList.size(); i++){
                    if((character.getCharacterClass().equals("Warlock") && completeSpellsList.get(i).getLevel() >= 6) || 2*completeSpellsList.get(i).getLevel()-1 > character.getLevel() || !completeSpellsList.get(i).getClassList().contains(character.getCharacterClass())){
                        completeSpellsList.remove(i);
                        i--;
                    }
                }
            }
            if(cantripsList.size() >= cantripMax){
                for(int i = 0; i< completeSpellsList.size(); i++){
                    if(completeSpellsList.get(i).getLevel() == 0){
                        completeSpellsList.remove(i);
                        i--;
                    }
                }
            }
            if(spellsList.size() >= spellMax){
                for(int i = 0; i< completeSpellsList.size(); i++){
                    if(completeSpellsList.get(i).getLevel() >= 1){
                        completeSpellsList.remove(i);
                        i--;
                    }
                }
            }
            mAdapter.addAll(completeSpellsList);
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
    //Allows certain character classes to add a specific spell to their class list
    private void addSpellsToClassList(String spellName){
        String classList;
        for(int i=0; i<completeSpellsList.size(); i++){
            if(completeSpellsList.get(i).getSpellName().equalsIgnoreCase(spellName) && !completeSpellsList.get(i).getClassList().contains(character.getCharacterClass())){
                classList = completeSpellsList.get(i).getClassList();
                completeSpellsList.get(i).setClassList(classList + character.getCharacterClass());
            }
        }
    }
    //Allows bards access to spells outside of their character class list per the Magical Secrets Feature
    private void getMagicalSecrets(){
        for(int i=0; i<completeSpellsList.size(); i++){
            if(!completeSpellsList.get(i).getClassList().contains("Bard") && completeSpellsList.get(i).getLevel() != 0){
                completeSpellsList.get(i).setClassList(completeSpellsList.get(i).getClassList() + character.getCharacterClass());
            }
        }
    }
}
