package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import static com.example.ddcharactercreator.DetailActivity.calculateModifier;
import static com.example.ddcharactercreator.DetailActivity.character;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterFragment extends Fragment {

    @BindView(R.id.character_name) TextView characterName;
    @BindView(R.id.character_level) TextView characterLevel;
    @BindView(R.id.character_class) TextView characterClass;
    @BindView(R.id.character_race) TextView characterRace;
    @BindView(R.id.character_alignment) TextView characterAlignment;
    @BindView(R.id.languages) EditText languagesKnown;

    @BindView(R.id.subclass_info_textview_1) TextView subclassInfoTextView1;
    @BindView(R.id.subclass_info_textview_2) TextView subclassInfoTextView2;
    @BindView(R.id.subclass_info_textview_3) TextView subclassInfoTextView3;
    @BindView(R.id.subrace_textview_1) TextView subraceInfoTextView1;
    @BindView(R.id.subrace_textview_2) TextView subraceInfoTextView2;
    @BindView(R.id.checkboxes_textview_1) TextView checkBoxes1;
    @BindView(R.id.checkboxes_textview_2) TextView checkBoxes2;
    @BindView(R.id.checkboxes_textview_3) TextView checkBoxes3;
    @BindView(R.id.checkboxes_textview_4) TextView checkBoxes4;
    @BindView(R.id.checkboxes_textview_5) TextView checkBoxes5;
    @BindView(R.id.subrace_checkboxes_textview) TextView subraceCheckboxTextView;
    @BindView(R.id.subrace_checkbox) CheckBox subraceCheckBox;
    @BindView(R.id.checkbox_1_1) CheckBox checkBox1_1;
    @BindView(R.id.checkbox_1_2) CheckBox checkBox1_2;
    @BindView(R.id.checkbox_1_3) CheckBox checkBox1_3;
    @BindView(R.id.checkbox_1_4) CheckBox checkBox1_4;
    @BindView(R.id.checkbox_1_5) CheckBox checkBox1_5;
    @BindView(R.id.checkbox_1_6) CheckBox checkBox1_6;
    @BindView(R.id.checkbox_2_1) CheckBox checkBox2_1;
    @BindView(R.id.checkbox_2_2) CheckBox checkBox2_2;
    @BindView(R.id.checkbox_2_3) CheckBox checkBox2_3;
    @BindView(R.id.checkbox_2_4) CheckBox checkBox2_4;
    @BindView(R.id.checkbox_2_5) CheckBox checkBox2_5;
    @BindView(R.id.checkbox_2_6) CheckBox checkBox2_6;
    @BindView(R.id.checkbox_3_1) CheckBox checkBox3_1;
    @BindView(R.id.checkbox_3_2) CheckBox checkBox3_2;
    @BindView(R.id.checkbox_3_3) CheckBox checkBox3_3;
    @BindView(R.id.checkbox_4_1) CheckBox checkBox4_1;
    @BindView(R.id.checkbox_5_1) CheckBox checkBox5_1;
    @BindView(R.id.subclass_info_edittext_textview) TextView editTextTextView;
    @BindView(R.id.subclass_info_edittext) EditText subclassInfoEditText;
    @BindView(R.id.subclass_info_listview_header) TextView subclassInfoHeader;
    @BindView(R.id.subclass_info_listview) ListView subclassInfoListView;
    @BindView(R.id.extra_info_listview_header) TextView extraInfoHeader;
    @BindView(R.id.extra_info_listview) ListView bonusStatsList;

    public CharacterFragment(){
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_character, container, false);

        Character character = DetailActivity.character;
        int level = character.getLevel();

        ButterKnife.bind(this, rootView);

        //Setting Character name, level, character class, race, and alignment
        characterName.setText(character.getName());
        characterLevel.setText("Level " + level);
        characterClass.setText(character.getCharacterClass());
        characterRace.setText(character.getRace());
        characterAlignment.setText(character.getAlignment());

        //Adding in Racial Benefits and languages
        ArrayList<String> bonusStats = character.getRaceAndClassBonusStats();
        languagesKnown.setText(bonusStats.get(0));
        if(character.getRace().equals("Dragonborn")) {
            subraceInfoTextView1.setVisibility(View.VISIBLE);
            subraceInfoTextView2.setVisibility(View.VISIBLE);
            subraceCheckboxTextView.setVisibility(View.VISIBLE);
            subraceCheckBox.setVisibility(View.VISIBLE);
            if(character.getLevel() < 6){
                subraceInfoTextView1.setText("Breath Weapon Damage: 2d6");
            } else if(character.getLevel() < 11){
                subraceInfoTextView1.setText("Breath Weapon Damage: 3d6");
            } else if(character.getLevel() < 16){
                subraceInfoTextView1.setText("Breath Weapon Damage: 4d6");
            } else {
                subraceInfoTextView1.setText("Breath Weapon Damage: 5d6");
            }
            subraceInfoTextView2.setText(String.format("Breath Weapon save DC: %s", (8 + DetailActivity.proficiencyBonus + calculateModifier(character.getStatValues().get(2)))));
            subraceCheckboxTextView.setText("Breath Weapon");
        }
        if(character.getRace().equals("Half-Orc")){
            subraceCheckboxTextView.setVisibility(View.VISIBLE);
            subraceCheckboxTextView.setText("Relentless Endurance: ");
            subraceCheckBox.setVisibility(View.VISIBLE);
        }

        //Adding class info when applicable
        switch(character.getCharacterClass()){
            case "Barbarian":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.rage_damage) + "+2");
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(R.string.rage_uses);
                checkBox1_1.setVisibility(View.VISIBLE);
                checkBox1_2.setVisibility(View.VISIBLE);
                if(level >= 3){
                    checkBox1_3.setVisibility(View.VISIBLE);
                }
                if(level >= 6){
                    checkBox1_4.setVisibility(View.VISIBLE);
                }
                if(level >= 9){
                    subclassInfoTextView1.setText(getString(R.string.rage_damage) + "+3");
                }
                if(level >= 11){
                    final int[] relentlessRage = {10};
                    subclassInfoTextView2.setVisibility(View.VISIBLE);
                    subclassInfoTextView2.setText(getString(R.string.relentless_rage_DC) + String.valueOf(relentlessRage[0]));
                    subclassInfoTextView2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            relentlessRage[0] += 5;
                            subclassInfoTextView2.setText(getString(R.string.relentless_rage_DC) + String.valueOf(relentlessRage[0]));
                            //TODO: reset via long rest
                        }
                    });
                }
                if(level >= 12){
                    checkBox1_5.setVisibility(View.VISIBLE);
                }
                if(level >= 16){
                    subclassInfoTextView1.setText(getString(R.string.rage_damage) + "+4");
                }
                if(level >= 17){
                    checkBox1_6.setVisibility(View.VISIBLE);
                }
                if(level == 20){
                    checkBox1_1.setVisibility(View.INVISIBLE);
                    checkBox1_2.setVisibility(View.INVISIBLE);
                    checkBox1_3.setVisibility(View.INVISIBLE);
                    checkBox1_4.setVisibility(View.INVISIBLE);
                    checkBox1_5.setVisibility(View.INVISIBLE);
                    checkBox1_6.setVisibility(View.INVISIBLE);
                    checkBoxes1.setText(getString(R.string.rage_uses) + "infinite");
                }
                break;
            case "Bard":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.bardic_inspiration_die) + "d6");
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(getString(R.string.bardic_inspiration_uses));
                checkBox1_1.setVisibility(View.VISIBLE);
                if(calculateModifier(character.getStatValues().get(5)) >= 2){
                    checkBox1_2.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 3){
                    checkBox1_3.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 4){
                    checkBox1_4.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 5){
                    checkBox1_5.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 6){
                    checkBox1_6.setVisibility(View.VISIBLE);
                }
                if(level >= 2){
                    subclassInfoTextView2.setVisibility(View.VISIBLE);
                    subclassInfoTextView2.setText(getString(R.string.song_of_rest_die) + "d6");
                }
                if(level >= 3){
                    //TODO: Bards choose 2 proficient skills. They double their proficiency bonus for these choices.
                }
                if(level >= 5){
                    subclassInfoTextView1.setText(getString(R.string.bardic_inspiration_die) + "d8");
                }
                if(level >= 9){
                    subclassInfoTextView2.setText(getString(R.string.song_of_rest_die) + "d8");
                }
                if(level >= 10){
                    subclassInfoTextView1.setText(getString(R.string.bardic_inspiration_die) + "d10");
                    //TODO: Bards choose 2 more proficient skills. They double their proficiency bonus for these choices.
                }
                if(level >= 13){
                    subclassInfoTextView2.setText(getString(R.string.song_of_rest_die) + "d10");
                }
                if(level >= 15){
                    subclassInfoTextView1.setText(getString(R.string.bardic_inspiration_die) + "d12");
                }
                if(level >= 17){
                    subclassInfoTextView2.setText(getString(R.string.song_of_rest_die) + "d12");
                }
                break;
            case "Cleric":
                ArrayList<String> channelDivinityUses = new ArrayList<String>();
                ListAdapter channelDivinityAdapter = new ListAdapter(getContext(), channelDivinityUses);
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.divine_domain) + character.getSubclass());
                switch(character.getSubclass()){
                    case "Light":
                        checkBoxes2.setVisibility(View.VISIBLE);
                        checkBoxes2.setText(getString(R.string.warding_flare_uses));
                        checkBox2_1.setVisibility(View.VISIBLE);
                        if(calculateModifier(character.getStatValues().get(4)) >= 2){
                            checkBox2_2.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 3){
                            checkBox2_3.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 4){
                            checkBox2_4.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 5){
                            checkBox2_5.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 6){
                            checkBox2_6.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "Tempest":
                        checkBoxes2.setVisibility(View.VISIBLE);
                        checkBoxes2.setText(getString(R.string.wrath_of_the_storm_uses));
                        checkBox2_1.setVisibility(View.VISIBLE);
                        if(calculateModifier(character.getStatValues().get(4)) >= 2){
                            checkBox2_2.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 3){
                            checkBox2_3.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 4){
                            checkBox2_4.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 5){
                            checkBox2_5.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 6){
                            checkBox2_6.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "War":
                        checkBoxes2.setVisibility(View.VISIBLE);
                        checkBoxes2.setText(getString(R.string.war_priest_uses));
                        checkBox2_1.setVisibility(View.VISIBLE);
                        if(calculateModifier(character.getStatValues().get(4)) >= 2){
                            checkBox2_2.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 3){
                            checkBox2_3.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 4){
                            checkBox2_4.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 5){
                            checkBox2_5.setVisibility(View.VISIBLE);
                        }
                        if(calculateModifier(character.getStatValues().get(4)) >= 6){
                            checkBox2_6.setVisibility(View.VISIBLE);
                        }
                }
                if(level >= 2){
                    checkBoxes1.setVisibility(View.VISIBLE);
                    checkBoxes1.setText(getString(R.string.channel_divinity_uses));
                    checkBox1_1.setVisibility(View.VISIBLE);
                    subclassInfoHeader.setVisibility(View.VISIBLE);
                    subclassInfoHeader.setText(getString(R.string.channel_divinity_abilities));
                    subclassInfoListView.setVisibility(View.VISIBLE);
                    channelDivinityUses.add(getString(R.string.channel_divinity_turn_undead));
                    switch(character.getSubclass()){
                        case "Knowledge":
                            channelDivinityUses.add(getString(R.string.channel_divinity_knowledge_of_the_ages));
                            break;
                        case "Life":
                            channelDivinityUses.add(getString(R.string.channel_divinity_preserve_life));
                            break;
                        case "Light":
                            channelDivinityUses.add(getString(R.string.channel_divinity_radiance_of_the_dawn));
                            break;
                        case "Nature":
                            channelDivinityUses.add(getString(R.string.channel_divinity_charm_animals_and_plants));
                            break;
                        case "Tempest":
                            channelDivinityUses.add(getString(R.string.channel_divinity_destructive_wrath));
                            break;
                        case "Trickery":
                            channelDivinityUses.add(getString(R.string.channel_divinity_invoke_duplicity));
                            break;
                        case "War":
                            channelDivinityUses.add(getString(R.string.channel_divinity_guided_strike));
                    }
                }
                if(level >= 5){
                    subclassInfoTextView2.setVisibility(View.VISIBLE);
                    subclassInfoTextView2.setText(getString(R.string.destroy_undead_threshold) + "1/2 or lower");
                }
                if(level >= 6){
                    checkBox1_2.setVisibility(View.VISIBLE);
                    switch(character.getSubclass()){
                        case "Knowledge":
                            channelDivinityUses.add(getString(R.string.channel_divinity_read_thoughts));
                            break;
                        case "Trickery":
                            channelDivinityUses.add(getString(R.string.channel_divinity_cloak_of_shadows));
                            break;
                        case "War":
                            channelDivinityUses.add(getString(R.string.channel_divinity_war_gods_blessing));
                    }
                }
                if(level >= 8){
                    subclassInfoTextView2.setText(getString(R.string.destroy_undead_threshold) + "1 or lower");
                }
                if(level >= 10){
                    checkBoxes3.setVisibility(View.VISIBLE);
                    checkBox3_1.setVisibility(View.VISIBLE);
                    checkBoxes3.setText(getString(R.string.divine_intervention_use));
                    subclassInfoTextView3.setVisibility(View.VISIBLE);
                    subclassInfoTextView3.setText(getString(R.string.divine_intervention_success_range) + character.getLevel() + " or lower");
                }
                if(level >= 11){
                    subclassInfoTextView2.setText(getString(R.string.destroy_undead_threshold) + "2 or lower");
                }
                if(level >= 14){
                    subclassInfoTextView2.setText(getString(R.string.destroy_undead_threshold) + "3 or lower");
                }
                if(level >= 17){
                    subclassInfoTextView2.setText(getString(R.string.destroy_undead_threshold) + "4 or lower");
                    switch(character.getSubclass()){
                        case "Knowledge":
                            checkBoxes4.setVisibility(View.VISIBLE);
                            checkBox4_1.setVisibility(View.VISIBLE);
                            checkBoxes4.setText(getString(R.string.visions_of_the_past_use));
                            break;
                    }
                }
                if(level >= 18){
                    checkBox1_3.setVisibility(View.VISIBLE);
                }
                if(level == 20){
                    subclassInfoTextView3.setText(getString(R.string.divine_intervention_success_range) + "Guaranteed Success");
                }
                subclassInfoListView.setAdapter(channelDivinityAdapter);
                break;
            case "Druid":
                if(level >= 2){
                    checkBoxes1.setVisibility(View.VISIBLE);
                    checkBoxes1.setText(getString(R.string.wild_shape_uses));
                    checkBox1_1.setVisibility(View.VISIBLE);
                    checkBox1_2.setVisibility(View.VISIBLE);
                    subclassInfoTextView1.setVisibility(View.VISIBLE);
                    subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = 1/4, No Flying/Swimming Speed");
                    subclassInfoTextView2.setVisibility(View.VISIBLE);
                    subclassInfoTextView2.setText(getString(R.string.wild_shape_time_limit) + level/2 + "hours");
                    if(character.getSubclass().contains("Circle of the Land")){
                        subclassInfoTextView3.setVisibility(View.VISIBLE);
                        subclassInfoTextView3.setText(getString(R.string.natural_recovery_amount) + "<= " + level/2);
                        checkBoxes2.setVisibility(View.VISIBLE);
                        checkBoxes2.setText("Natural Recovery: ");
                        checkBox2_1.setVisibility(View.VISIBLE);
                    } else if(character.getSubclass().equals("Circle of the Moon")){
                        subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = 1, No Flying/Swimming Speed");
                    }
                }
                if(level >= 4){
                    subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = 1/2, No Flying Speed");
                    if(character.getSubclass().equals("Circle of the Moon")){
                        subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = 1, No Flying Speed");
                    }
                }
                if(level >= 6){
                    if(character.getSubclass().equals("Circle of the Moon")){
                        subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = " + level/3);
                    }
                }
                if(level >= 8){
                    subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = 1");
                    if(character.getSubclass().equals("Circle of the Moon")){
                        subclassInfoTextView1.setText(getString(R.string.wild_shape_limits) + "Max CR = " + level/3);
                    }
                }
                if(level == 20){
                    checkBox1_2.setVisibility(View.GONE);
                    checkBox1_1.setVisibility(View.GONE);
                    checkBoxes1.setText(getString(R.string.wild_shape_uses) + "Infinite");
                }
                break;
            case "Fighter":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.fighting_style));
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBox1_1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(getString(R.string.second_wind_amount) + "1d10 + " + character.getLevel());
                if(level >= 2){
                    checkBoxes2.setVisibility(View.VISIBLE);
                    checkBoxes2.setText(getString(R.string.action_surge_uses));
                    checkBox2_1.setVisibility(View.VISIBLE);
                }
                if(level >= 9){
                    checkBoxes3.setVisibility(View.VISIBLE);
                    checkBoxes3.setText(getString(R.string.indomitable_uses));
                    checkBox3_1.setVisibility(View.VISIBLE);
                }
                if(level >= 13){
                    checkBox3_2.setVisibility(View.VISIBLE);
                }
                if(level >= 17){
                    checkBox2_2.setVisibility(View.VISIBLE);
                    checkBox3_3.setVisibility(View.VISIBLE);
                }
                break;
            case "Monk":
                ListAdapter kiPointAdapter = new ListAdapter(getContext(), character.getClassBasedBonusStats2());
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(String.format("%s 1d4", getString(R.string.unarmed_strike_damage)));
                if(level >= 2){
                    editTextTextView.setVisibility(View.VISIBLE);
                    editTextTextView.setText(getString(R.string.ki_points) + " " + character.getLevel());
                    subclassInfoEditText.setVisibility(View.VISIBLE);
                    subclassInfoEditText.setText(String.valueOf(character.getLevel()));
                    subclassInfoHeader.setVisibility(View.VISIBLE);
                    subclassInfoHeader.setText(getString(R.string.ki_point_abilities));
                    subclassInfoListView.setVisibility(View.VISIBLE);
                    character.getClassBasedBonusStats2().add(getString(R.string.flurry_of_blows));
                    character.getClassBasedBonusStats2().add(getString(R.string.patient_defense));
                    character.getClassBasedBonusStats2().add(getString(R.string.step_of_the_wind));
                    subclassInfoTextView2.setVisibility(View.VISIBLE);
                    subclassInfoTextView2.setText(String.format("%s +10ft", getString(R.string.unarmored_movement)));
                }
                if(level >= 3){
                    character.getClassBasedBonusStats2().add(getString(R.string.deflect_missiles_ki));
                }
                if(level >= 5){
                    subclassInfoTextView1.setText(String.format("%s 1d6", getString(R.string.unarmed_strike_damage)));
                    character.getClassBasedBonusStats2().add(getString(R.string.stunning_strike));
                }
                if(level >= 6){
                    subclassInfoTextView2.setText(String.format("%s +15ft", getString(R.string.unarmored_movement)));
                    if(character.getSubclass().equals("Way of the Open Hand")){
                        checkBoxes1.setVisibility(View.VISIBLE);
                        checkBoxes1.setText(getString(R.string.wholeness_of_body_use));
                        checkBox1_1.setVisibility(View.VISIBLE);
                    }
                }
                if(level >= 10){
                    subclassInfoTextView2.setText(String.format("%s +20ft", getString(R.string.unarmored_movement)));
                }
                if(level >= 11){
                    subclassInfoTextView1.setText(String.format("%s 1d8", getString(R.string.unarmed_strike_damage)));
                }
                if(level >= 14){
                    subclassInfoTextView2.setText(String.format("%s +25ft", getString(R.string.unarmored_movement)));
                    character.getClassBasedBonusStats2().add(getString(R.string.diamond_soul_ki));
                }
                if(level >= 17){
                    subclassInfoTextView1.setText(String.format("%s 1d10", getString(R.string.unarmed_strike_damage)));
                }
                if(level >= 18){
                    subclassInfoTextView2.setText(String.format("%s +30ft", getString(R.string.unarmored_movement)));
                    character.getClassBasedBonusStats2().add(getString(R.string.empty_body));
                    character.getClassBasedBonusStats2().add(getString(R.string.empty_body_2));
                }
                subclassInfoListView.setAdapter(kiPointAdapter);
                break;
            case "Paladin":
                checkBoxes1.setVisibility(View.VISIBLE);
                checkBoxes1.setText(R.string.divine_sense_uses);
                checkBox1_1.setVisibility(View.VISIBLE);
                if(calculateModifier(character.getStatValues().get(5)) >= 1){
                    checkBox1_2.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 2){
                    checkBox1_3.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 3){
                    checkBox1_4.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 4){
                    checkBox1_5.setVisibility(View.VISIBLE);
                }
                if(calculateModifier(character.getStatValues().get(5)) >= 5){
                    checkBox1_6.setVisibility(View.VISIBLE);
                }
                editTextTextView.setVisibility(View.VISIBLE);
                editTextTextView.setText(R.string.lay_on_hands_pool);
                subclassInfoEditText.setVisibility(View.VISIBLE);
                subclassInfoEditText.setText(String.valueOf(character.getLevel()*5));
                //ArrayList<String> channelDivinityUsesPaladin = new ArrayList<String>();
                //ListAdapter channelDivinityAdapterPaladin = new ListAdapter(getContext(), channelDivinityUsesPaladin);
                if(level >= 3){
                    //subclassInfoHeader.setVisibility(View.VISIBLE);
                    //subclassInfoHeader.setText(getString(R.string.channel_divinity_abilities));
                    //subclassInfoListView.setVisibility(View.VISIBLE);
                    //This is where the channel divinity abilities will be added to the list when subclasses are added in.
                }
                if(level >= 6){
                    subclassInfoTextView1.setVisibility(View.VISIBLE);
                    subclassInfoTextView1.setText("Aura Radius: 10ft");
                }
                if(level >= 14){
                    checkBoxes2.setVisibility(View.VISIBLE);
                    checkBoxes2.setText(getString(R.string.cleansing_touch_uses));
                    checkBox2_1.setVisibility(View.VISIBLE);
                    if(calculateModifier(character.getStatValues().get(5)) >= 2){
                        checkBox2_2.setVisibility(View.VISIBLE);
                    }
                    if(calculateModifier(character.getStatValues().get(5)) >= 3){
                        checkBox2_3.setVisibility(View.VISIBLE);
                    }
                    if(calculateModifier(character.getStatValues().get(5)) >= 4){
                        checkBox2_4.setVisibility(View.VISIBLE);
                    }
                    if(calculateModifier(character.getStatValues().get(5)) >= 5){
                        checkBox2_5.setVisibility(View.VISIBLE);
                    }
                    if(calculateModifier(character.getStatValues().get(5)) >= 6){
                        checkBox2_6.setVisibility(View.VISIBLE);
                    }
                }
                if(level >= 18){
                    subclassInfoTextView1.setVisibility(View.VISIBLE);
                    subclassInfoTextView1.setText("Aura Radius: 30ft");
                }
                //subclassInfoListView.setAdapter(channelDivinityAdapterPaladin);
                break;
            case "Ranger":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(String.format("Favored Terrain: %s", character.getClassBasedBonusStats2().get(0)));
                subclassInfoTextView2.setVisibility(View.VISIBLE);
                subclassInfoTextView2.setText(String.format("Favored Enemies: %s", character.getClassBasedBonusStats2().get(1)));
                if(level >= 6){
                    subclassInfoTextView1.setText(String.format("Favored Terrain: %s, %s", character.getClassBasedBonusStats2().get(0), character.getClassBasedBonusStats2().get(2)));
                    subclassInfoTextView2.setText(String.format("Favored Enemies: %s, %s", character.getClassBasedBonusStats2().get(1), character.getClassBasedBonusStats2().get(3)));
                }
                if(level >= 10){
                    subclassInfoTextView1.setText(String.format("Favored Terrain: %s, %s, %s", character.getClassBasedBonusStats2().get(0), character.getClassBasedBonusStats2().get(2), character.getClassBasedBonusStats2().get(4)));
                }
                if(level >= 14){
                    subclassInfoTextView2.setText(String.format("Favored Enemies: %s, %s, %s", character.getClassBasedBonusStats2().get(1), character.getClassBasedBonusStats2().get(3), character.getClassBasedBonusStats2().get(5)));
                }
                break;
            case "Rogue":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.sneak_attack_damage) + String.valueOf((character.getLevel()+1)/2) + "d6");
                if(level >= 17){
                    if(character.getSubclass().equals("Arcane Trickster")){
                        checkBoxes1.setVisibility(View.VISIBLE);
                        checkBoxes1.setText(getString(R.string.spell_thief_use));
                        checkBox1_1.setVisibility(View.VISIBLE);
                    }
                }
                if(level == 20){
                    checkBoxes2.setVisibility(View.VISIBLE);
                    checkBoxes2.setText(getString(R.string.stroke_of_luck_use));
                    checkBox2_1.setVisibility(View.VISIBLE);
                }
                break;
            case "Sorcerer":
                ListAdapter metamagicAdapter = new ListAdapter(getContext(), character.getClassBasedBonusStats2());
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(String.format("Sorcerous Origin: %s", character.getSubclass()));
                switch(character.getSubclass()){
                    case "Wild Magic":
                        checkBoxes1.setVisibility(View.VISIBLE);
                        checkBoxes1.setText(getString(R.string.tides_of_chaos_use));
                        checkBox1_1.setVisibility(View.VISIBLE);
                        break;
                    case "Phoenix Sorcery":
                        checkBoxes1.setVisibility(View.VISIBLE);
                        checkBoxes1.setText(getString(R.string.mantle_of_flame_use));
                        checkBox1_1.setVisibility(View.VISIBLE);
                        break;
                }
                if(level >= 2){
                    editTextTextView.setVisibility(View.VISIBLE);
                    subclassInfoEditText.setVisibility(View.VISIBLE);
                    editTextTextView.setText(getString(R.string.sorcery_points) + " " + character.getLevel());
                    subclassInfoEditText.setText(String.valueOf(character.getLevel()));
                }
                if(level >= 3){
                    subclassInfoHeader.setVisibility(View.VISIBLE);
                    subclassInfoHeader.setText(getString(R.string.metamagic_options));
                    subclassInfoListView.setVisibility(View.VISIBLE);
                }
                subclassInfoListView.setAdapter(metamagicAdapter);
                break;
            case "Warlock":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.otherworldly_patron) + character.getSubclass());
                if(character.getSubclass().equals("The Archfey")){
                    checkBoxes1.setVisibility(View.VISIBLE);
                    checkBoxes1.setText(getString(R.string.fey_presence_use));
                    checkBox1_1.setVisibility(View.VISIBLE);
                }
                if(level >= 6){
                    switch(character.getSubclass()){
                        case "The Archfey":
                            checkBoxes2.setVisibility(View.VISIBLE);
                            checkBoxes2.setText(getString(R.string.misty_escape_use));
                            checkBox2_1.setVisibility(View.VISIBLE);
                            break;
                        case "The Fiend":
                            checkBoxes2.setVisibility(View.VISIBLE);
                            checkBoxes2.setText(getString(R.string.dark_ones_own_luck_use));
                            checkBox2_1.setVisibility(View.VISIBLE);
                            break;
                        case "The Great Old One":
                            checkBoxes2.setVisibility(View.VISIBLE);
                            checkBoxes2.setText(getString(R.string.entropic_ward_use));
                            checkBox2_1.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                if(level >= 14){
                    switch(character.getSubclass()){
                        case "The Archfey":
                            checkBoxes3.setVisibility(View.VISIBLE);
                            checkBoxes3.setText(getString(R.string.dark_delirium_use));
                            checkBox3_1.setVisibility(View.VISIBLE);
                            break;
                        case "The Fiend":
                            checkBoxes3.setVisibility(View.VISIBLE);
                            checkBoxes3.setText(getString(R.string.hurl_through_hell_use));
                            checkBox3_1.setVisibility(View.VISIBLE);
                            break;
                    }
                }
                if(level == 20){
                    checkBoxes4.setVisibility(View.VISIBLE);
                    checkBox4_1.setVisibility(View.VISIBLE);
                    checkBoxes4.setText(getString(R.string.eldritch_master_use));
                }
                break;
            case "Wizard":
                subclassInfoTextView1.setVisibility(View.VISIBLE);
                subclassInfoTextView1.setText(getString(R.string.arcane_recovery_amount) + (level+ 1)/2);
                switch(character.getSubclass()){
                    case "School of Abjuration":
                        editTextTextView.setVisibility(View.VISIBLE);
                        editTextTextView.setText(getString(R.string.arcane_ward_max_hp) + String.valueOf(2*character.getLevel() + calculateModifier(character.getStatValues().get(3))));
                        subclassInfoEditText.setVisibility(View.VISIBLE);
                        subclassInfoEditText.setText(String.valueOf(2*character.getLevel() + calculateModifier(character.getStatValues().get(3))));
                        break;
                    case "School of Conjuration":
                        if(level >= 6){
                            checkBoxes1.setVisibility(View.VISIBLE);
                            checkBoxes1.setText(getString(R.string.benign_transposition_use));
                            checkBox1_1.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "School of Divination":
                        editTextTextView.setVisibility(View.VISIBLE);
                        editTextTextView.setText(getString(R.string.portent_uses));
                        subclassInfoEditText.setVisibility(View.VISIBLE);
                        subclassInfoEditText.setText("20, 20");
                        break;
                    case "School of Illusion":
                        if(level >= 10){
                            checkBoxes1.setVisibility(View.VISIBLE);
                            checkBoxes1.setText(getString(R.string.illusory_self_use));
                            checkBox1_1.setVisibility(View.VISIBLE);
                        }
                        break;
                    case "School of Transmutation":
                        if(level >= 10){
                            checkBoxes1.setVisibility(View.VISIBLE);
                            checkBoxes1.setText(getString(R.string.shapechanger_use));
                            checkBox1_1.setVisibility(View.VISIBLE);
                        }
                        break;
                }
                /*if(level >= 18){
                    subclassInfoTextView1.setVisibility(View.VISIBLE);
                    subclassInfoTextView1.setText(getString(R.string.spell_mastery) + spell mastery choice 1 + ", " + spell mastery choice 2);
                }*/
                if(level == 20){
                    checkBoxes1.setVisibility(View.VISIBLE);
                    checkBoxes2.setVisibility(View.VISIBLE);
                    checkBoxes1.setText(getString(R.string.signature_spells_choice));
                    checkBoxes2.setText(getString(R.string.signature_spells_choice));
                    checkBox1_1.setVisibility(View.VISIBLE);
                    checkBox2_1.setVisibility(View.VISIBLE);
                    //checkBox1_1.setText(signature spell choice 1);
                    //checkBox2_1.setText(signature spell choice 2);
                    //set checkbox text to actual spell, set textview to R.string.signature_spell_choice??
                }
                break;
        }
        ListAdapter adapter = new ListAdapter(getContext(), bonusStats);
        bonusStatsList.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        DetailActivity.canLongRest = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        character.getRaceAndClassBonusStats().set(0, languagesKnown.getText().toString());
    }
}
