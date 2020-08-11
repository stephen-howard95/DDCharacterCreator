package com.example.ddcharactercreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;
import static com.example.ddcharactercreator.DetailActivity.character;
import static com.example.ddcharactercreator.DetailActivity.spellViewModel;
import static com.example.ddcharactercreator.DetailActivity.spellcastingClass;

public class SpellChooserFragment extends Fragment {

    LiveData<List<Spell>> completeSpellsList;
    private ArrayList<Spell> spellsList = new ArrayList<Spell>();
    private ArrayList<Spell> cantripsList = new ArrayList<Spell>();
    private int spellMax;
    private int cantripMax;

    public SpellChooserFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_spell, container, false);

        completeSpellsList = spellViewModel.loadAllSpells();
        Collections.sort(completeSpellsList.getValue(), Spell.spellNameComparator);

        spellViewModel.loadAllSpells().observe(this, new Observer<List<Spell>>() {
            @Override
            public void onChanged(List<Spell> spells) {
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
                    case "Fighter":
                        spellcastingClass = "Wizard";
                        cantripMax = 2;
                        if(character.getLevel() <= 4){
                            spellMax = character.getLevel();
                        } else if(character.getLevel() <= 6){
                            spellMax = 4;
                        } else if(character.getLevel() <= 8){
                            spellMax = character.getLevel()-2;
                        } else if(character.getLevel() <= 11){
                            spellMax = character.getLevel()-3;
                        }else if(character.getLevel() <= 14){
                            spellMax = character.getLevel()-4;
                        }else if(character.getLevel() == 15){
                            spellMax = 10;
                        } else if(character.getLevel() <= 18){
                            spellMax = 11;
                        } else if(character.getLevel() <= 20){
                            spellMax = character.getLevel()-7;
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
                    case "Rogue":
                        spellcastingClass = "Wizard";
                        cantripMax = 3;
                        if(character.getLevel() <= 4){
                            spellMax = character.getLevel();
                        } else if(character.getLevel() <= 6){
                            spellMax = 4;
                        } else if(character.getLevel() <= 8){
                            spellMax = character.getLevel()-2;
                        } else if(character.getLevel() <= 11){
                            spellMax = character.getLevel()-3;
                        }else if(character.getLevel() <= 14){
                            spellMax = character.getLevel()-4;
                        }else if(character.getLevel() == 15){
                            spellMax = 10;
                        } else if(character.getLevel() <= 18){
                            spellMax = 11;
                        } else if(character.getLevel() <= 20){
                            spellMax = character.getLevel()-7;
                        }
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
                            cantripMax += 3;
                            getSpellsPerLevel(0, spells);
                        }
                        break;
                    case "Wizard":
                        spellMax = character.getLevel() + calculateModifier(character.getStatValues().get(3));
                        cantripMax = 3;
                        if(character.getSubclass().equals("School of Illusion")){
                            cantripMax += 1;
                        }
                        break;
                }
                if(spellMax < 1){
                    spellMax = 1;
                }
                if(character.getLevel() >= 10 && (character.getSubclass().equals("Eldritch Knight") || character.getSubclass().equals("Arcane Trickster"))){
                    cantripMax += 1;
                } else if(character.getLevel() >= 10){
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
                    if(cantripsList.size() >= cantripMax){
                        for(int i = 0; i< spells.size(); i++){
                            if(spells.get(i).getLevel() == 0){
                                spells.remove(i);
                                i--;
                            }
                        }
                    }
                    if(spellsList.size() >= spellMax){
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
