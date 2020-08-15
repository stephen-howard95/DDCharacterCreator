package com.example.ddcharactercreator;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static com.example.ddcharactercreator.DetailActivity.character;
import static com.example.ddcharactercreator.DetailActivity.spellViewModel;
import static com.example.ddcharactercreator.DetailActivity.spellcastingClass;
import static com.example.ddcharactercreator.DetailActivity.spellCount;
import static com.example.ddcharactercreator.DetailActivity.cantripCount;

public class SpellChooserFragment extends Fragment {

    LiveData<List<Spell>> completeSpellsList;
    private ArrayList<Spell> spellsList = new ArrayList<Spell>();
    private ArrayList<Spell> cantripsList = new ArrayList<Spell>();

    public SpellChooserFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_spell, container, false);

        ImageButton imageButton = rootView.findViewById(R.id.end_button);

        completeSpellsList = spellViewModel.loadAllSpells();
        Collections.sort(completeSpellsList.getValue(), Spell.spellNameComparator);

        spellViewModel.loadAllSpells().observe(this, new Observer<List<Spell>>() {
            @Override
            public void onChanged(List<Spell> spells) {
                switch(character.getCharacterClass()){
                    case "Bard":
                        switch(character.getLevel()){
                            case 6:
                                if(character.getSubclass().equals("College of Lore")){
                                    getMagicalSecrets(spells);
                                }
                                break;
                            case 10:
                            case 14:
                            case 18:
                                getMagicalSecrets(spells);
                                break;
                        }
                        break;
                    case "Fighter":
                    case "Rogue":
                        spellcastingClass = "Wizard";
                        break;
                    case "Sorcerer":
                        if(character.getSubclass().equals("Stone Sorcery")){
                            addSpellsToClassList("compelled duel", spells);
                            addSpellsToClassList("searing smite", spells);
                            addSpellsToClassList("thunderous smite", spells);
                            addSpellsToClassList("wrathful smite", spells);
                            addSpellsToClassList("branding smite", spells);
                            addSpellsToClassList("magic weapon", spells);
                            addSpellsToClassList("blinding smite", spells);
                            addSpellsToClassList("elemental weapon", spells);
                            addSpellsToClassList("staggering smite", spells);
                        }
                        break;
                    case "Warlock":
                        switch(character.getSubclass()){
                            case "The Archfey":
                                addSpellsToClassList("faerie fire", spells);
                                addSpellsToClassList("sleep", spells);
                                addSpellsToClassList("calm emotions", spells);
                                addSpellsToClassList("phantasmal force", spells);
                                addSpellsToClassList("blink", spells);
                                addSpellsToClassList("plant growth", spells);
                                addSpellsToClassList("dominate beast", spells);
                                addSpellsToClassList("greater invisibility", spells);
                                addSpellsToClassList("dominate person", spells);
                                addSpellsToClassList("seeming", spells);
                                break;
                            case "The Fiend":
                                addSpellsToClassList("burning hands", spells);
                                addSpellsToClassList("command", spells);
                                addSpellsToClassList("blindness/deafness", spells);
                                addSpellsToClassList("scorching ray", spells);
                                addSpellsToClassList("fireball", spells);
                                addSpellsToClassList("stinking cloud", spells);
                                addSpellsToClassList("fire shield", spells);
                                addSpellsToClassList("wall of fire", spells);
                                addSpellsToClassList("flame strike", spells);
                                addSpellsToClassList("hallow", spells);
                                break;
                            case "The Great Old One":
                                addSpellsToClassList("dissonant whispers", spells);
                                addSpellsToClassList("hideous laughter", spells);
                                addSpellsToClassList("detect thoughts", spells);
                                addSpellsToClassList("phantasmal force", spells);
                                addSpellsToClassList("clairvoyance", spells);
                                addSpellsToClassList("sending", spells);
                                addSpellsToClassList("dominate beast", spells);
                                addSpellsToClassList("black tentacles", spells);
                                addSpellsToClassList("dominate person", spells);
                                addSpellsToClassList("telekinesis", spells);
                                break;
                        }
                        if(character.getRaceAndClassBonusStats().contains(getString(R.string.pact_of_the_tome))){
                            getSpellsPerLevel(0, spells);
                        }
                        break;
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

                //Limits the available spells for the character based on their level and character class
                if(completeSpellsList != null && !spells.isEmpty()){
                    if(character.getCharacterClass().equals("Ranger") || character.getCharacterClass().equals("Paladin")){
                        for(int i = 0; i < spells.size(); i++){
                            if(4*spells.get(i).getLevel()-3 > character.getLevel() || !spells.get(i).getClassList().contains(spellcastingClass)){
                                spells.remove(i);
                                i--;
                            }
                        }
                    }else if(character.getSubclass().equals("Eldritch Knight") || character.getSubclass().equals("Arcane Trickster")){
                        for(int i=0; i<spells.size(); i++){
                            if(6*spells.get(i).getLevel()-6 >= character.getLevel() || !spells.get(i).getClassList().contains(spellcastingClass)){
                                spells.remove(i);
                                i--;
                            }
                        }
                    }else{
                        for(int i = 0; i< spells.size(); i++){
                            if((character.getCharacterClass().equals("Warlock") && spells.get(i).getLevel() >= 6) || 2*spells.get(i).getLevel()-1 > character.getLevel() || !spells.get(i).getClassList().contains(spellcastingClass)){
                                spells.remove(i);
                                i--;
                            }
                        }
                    }
                    if(cantripsList.size() >= cantripCount){
                        for(int i = 0; i< spells.size(); i++){
                            if(spells.get(i).getLevel() == 0){
                                spells.remove(i);
                                i--;
                            }
                        }
                    }
                    if(spellsList.size() >= spellCount){
                        for(int i = 0; i< spells.size(); i++){
                            if(spells.get(i).getLevel() >= 1){
                                spells.remove(i);
                                i--;
                            }
                        }
                    }
                    ArrayList<Spell> newSpellList = new ArrayList<Spell>(spells);
                    SpellAdapter mAdapter = new SpellAdapter(getContext(), newSpellList, true);
                    RecyclerView spellListView = rootView.findViewById(R.id.list);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                            layoutManager.getOrientation());
                    spellListView.addItemDecoration(dividerItemDecoration);
                    spellListView.setLayoutManager(layoutManager);
                    spellListView.setAdapter(mAdapter);
                } else{
                    Toast.makeText(getContext(), "Spell Database is empty. Try again later", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.CHARACTER, character);
                startActivity(intent);
            }
        });
        return rootView;
    }

    //Allows certain character classes to add a specific spell to their class list
    private void addSpellsToClassList(String spellName, List<Spell> spells){
        //TODO: add in the potential for a specific class/level such as Nature domain clerics getting 1 free druid cantrip??
        String classList;
        for(int i=0; i<spells.size(); i++){
            if(spells.get(i).getSpellName().equalsIgnoreCase(spellName) && !spells.get(i).getClassList().contains(spellcastingClass)){
                classList = spells.get(i).getClassList();
                spells.get(i).setClassList(classList + spellcastingClass);
            }
        }
    }
    //Allows certain character classes access to several spells outside their class list
    public void getSpellsPerLevel(int spellLevel, List<Spell> spells){
        for(int i=0; i<spells.size(); i++){
            if(!spells.get(i).getClassList().contains(spellcastingClass) && spells.get(i).getLevel() == spellLevel){
                spells.get(i).setClassList(spells.get(i).getClassList() + spellcastingClass);
            }
        }
    }
    //Allows bards access to spells outside of their character class list per the Magical Secrets Feature
    private void getMagicalSecrets(List<Spell> spells){
        for(int i=0; i<spells.size(); i++){
            if(!spells.get(i).getClassList().contains("Bard") && spells.get(i).getLevel() != 0){
                spells.get(i).setClassList(spells.get(i).getClassList() + character.getCharacterClass());
            }
        }
    }
}
