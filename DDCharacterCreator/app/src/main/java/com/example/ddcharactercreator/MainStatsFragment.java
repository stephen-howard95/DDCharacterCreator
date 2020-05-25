package com.example.ddcharactercreator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ddcharactercreator.DetailActivity.calculateModifier;

public class MainStatsFragment extends Fragment {

    @BindView(R.id.strength_label) TextView strengthLabel;
    @BindView(R.id.dexterity_label) TextView dexterityLabel;
    @BindView(R.id.constitution_label) TextView constitutionLabel;
    @BindView(R.id.intelligence_label) TextView intelligenceLabel;
    @BindView(R.id.wisdom_label) TextView wisdomLabel;
    @BindView(R.id.charisma_label) TextView charismaLabel;
    @BindView(R.id.character_screen_strength) TextView strengthValue;
    @BindView(R.id.character_screen_dexterity) TextView dexterityValue;
    @BindView(R.id.character_screen_constitution) TextView constitutionValue;
    @BindView(R.id.character_screen_intelligence) TextView intelligenceValue;
    @BindView(R.id.character_screen_wisdom) TextView wisdomValue;
    @BindView(R.id.character_screen_charisma) TextView charismaValue;
    @BindView(R.id.str_modifier) TextView strengthModifier;
    @BindView(R.id.dex_modifier) TextView dexterityModifier;
    @BindView(R.id.con_modifier) TextView constitutionModifier;
    @BindView(R.id.int_modifier) TextView intelligenceModifier;
    @BindView(R.id.wis_modifier) TextView wisdomModifier;
    @BindView(R.id.cha_modifier) TextView charismaModifier;

    @BindView(R.id.proficiency_bonus) TextView proficiencyBonus;
    @BindView(R.id.speed) TextView speedTextView;
    @BindView(R.id.passive_perception) TextView passivePerception;
    @BindView(R.id.armor_class_value) EditText armorClassEditText;
    @BindView(R.id.skill_modifiers) TextView skillModifiers;

    @BindView(R.id.current_hit_points) EditText currentHitPoints;
    @BindView(R.id.max_hit_points) TextView maxHitPoints;
    @BindView(R.id.hit_die) TextView hitDieTextView;

    @BindView(R.id.bonus_stats_list_view) ListView bonusStatsList;

    public MainStatsFragment(){
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        ButterKnife.bind(this, rootView);

        final Character character = DetailActivity.character;

        //Setting Stats and Modifiers
        strengthValue.setText(character.getStatValues().get(0).toString());
        dexterityValue.setText(character.getStatValues().get(1).toString());
        constitutionValue.setText(character.getStatValues().get(2).toString());
        intelligenceValue.setText(character.getStatValues().get(3).toString());
        wisdomValue.setText(character.getStatValues().get(4).toString());
        charismaValue.setText(character.getStatValues().get(5).toString());

        strengthModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(0))));
        dexterityModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(1))));
        constitutionModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(2))));
        intelligenceModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(3))));
        wisdomModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(4))));
        charismaModifier.setText(String.valueOf(calculateModifier(character.getStatValues().get(5))));

        //Setting Proficiency Bonus value
        StringBuilder proficiencyBonusString = new StringBuilder().append(getString(R.string.proficiency_bonus_label));
        if(character.getLevel() <= 4){
            proficiencyBonusString.append("+2");
        } else if (character.getLevel() <= 8){
            proficiencyBonusString.append("+3");
        }else if(character.getLevel() <= 12){
            proficiencyBonusString.append("+4");
        } else if(character.getLevel() <= 16){
            proficiencyBonusString.append("+5");
        } else if(character.getLevel() <= 20){
            proficiencyBonusString.append("+6");
        }
        proficiencyBonus.setText(proficiencyBonusString.toString());

        //Setting Speed value
        if(character.getRace().equals("Dwarf")  || character.getRace().equals("Halfling")  || character.getRace().equals("Gnome")){
            speedTextView.setText(getString(R.string.speed_label) + "25 FT");
        } else if (character.getRace().equals("Elf")) {
            speedTextView.setText(getString(R.string.speed_label) + "35 FT");
        } else {
            speedTextView.setText(getString(R.string.speed_label) + "30 FT");
        }

        //Setting Passive Perception and Armor Class
        passivePerception.setText(getString(R.string.passive_perception_label) + (10 + calculateModifier(character.getStatValues().get(4))));
        armorClassEditText.setText(String.valueOf(character.getCurrency().get(6)));
        armorClassEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!armorClassEditText.getText().toString().equals("")){
                    //noinspection unchecked
                    character.getCurrency().set(6, Integer.parseInt(armorClassEditText.getText().toString()));
                }
            }
        });

        //Set Skill proficiencies TextView and onClick method
        skillModifiers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch new Fragment with the list of skills and their associated modifiers.
                Intent intent = new Intent(getActivity(), SkillModifiersActivity.class);
                startActivity(intent);
            }
        });

        //Setting Current/Max HP, hit die values, and saving throw proficiencies.
        switch (character.getCharacterClass()){
            case "Barbarian":
                hitDieTextView.setText("d12");
                maxHitPoints.setText(String.valueOf(12 + calculateModifier(character.getStatValues().get(2))));
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Bard":
                hitDieTextView.setText("d8");
                maxHitPoints.setText(String.valueOf(8 + calculateModifier(character.getStatValues().get(2))));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Druid":
                hitDieTextView.setText("d8");
                maxHitPoints.setText(String.valueOf(8 + calculateModifier(character.getStatValues().get(2))));
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Monk":
                hitDieTextView.setText("d8");
                maxHitPoints.setText(String.valueOf(8 + calculateModifier(character.getStatValues().get(2))));
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Paladin":
                hitDieTextView.setText("d10");
                maxHitPoints.setText(String.valueOf(10 + calculateModifier(character.getStatValues().get(2))));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Rogue":
                hitDieTextView.setText("d8");
                maxHitPoints.setText(String.valueOf(8 + calculateModifier(character.getStatValues().get(2))));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Sorcerer":
                hitDieTextView.setText("d6");
                maxHitPoints.setText(String.valueOf(6 + calculateModifier(character.getStatValues().get(2))));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Wizard":
                hitDieTextView.setText("d6");
                maxHitPoints.setText(String.valueOf(6 + calculateModifier(character.getStatValues().get(2))));
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
        }
        currentHitPoints.setText(String.valueOf(character.getCurrency().get(0)));
        if (!currentHitPoints.getText().toString().equals("")){
            if (Integer.parseInt(currentHitPoints.getText().toString()) > Integer.parseInt(maxHitPoints.getText().toString()) * .5) {
                currentHitPoints.setTextColor(getResources().getColor(R.color.green));
            } else if (Integer.parseInt(currentHitPoints.getText().toString()) > Integer.parseInt(maxHitPoints.getText().toString()) * .3) {
                currentHitPoints.setTextColor(getResources().getColor(R.color.yellow));
            } else if (Integer.parseInt(currentHitPoints.getText().toString()) > Integer.parseInt(maxHitPoints.getText().toString()) * .1) {
                currentHitPoints.setTextColor(getResources().getColor(R.color.orange));
            } else {
                currentHitPoints.setTextColor(getResources().getColor(R.color.red));
            }
        }

        //Changes the color of the Text to denote how much/little health the character has remaining.
        currentHitPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!currentHitPoints.getText().toString().equals("")){
                    //noinspection unchecked
                    character.getCurrency().set(0, Integer.parseInt(currentHitPoints.getText().toString()));
                    if (Integer.parseInt(currentHitPoints.getText().toString()) > Integer.parseInt(maxHitPoints.getText().toString()) * .5) {
                        currentHitPoints.setTextColor(getResources().getColor(R.color.green));
                    } else if (Integer.parseInt(currentHitPoints.getText().toString()) > Integer.parseInt(maxHitPoints.getText().toString()) * .3) {
                        currentHitPoints.setTextColor(getResources().getColor(R.color.yellow));
                    } else if (Integer.parseInt(currentHitPoints.getText().toString()) > Integer.parseInt(maxHitPoints.getText().toString()) * .1) {
                        currentHitPoints.setTextColor(getResources().getColor(R.color.orange));
                    } else {
                        currentHitPoints.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            }
        });

        //Bonus Character stats based on Race and Class
        ArrayList<String> bonusStats = new ArrayList<String>();

        switch(character.getRace()){
            case "Dwarf":
                //Mountain Dwarf is chosen for this
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on saving throws against poison");
                bonusStats.add("Resistance to poison damage");
                bonusStats.add("Add double your proficiency bonus to INT History checks relating to stonework");
                break;
            case "Elf":
                //Wood Elf is chosen for this
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on saving throws against being charmed");
                bonusStats.add("Magic effects cannot put you to sleep");
                bonusStats.add("You can get a full Long Rest by only meditating for 4 hours");
                break;
            case "Halfling":
                //Stout Halfling is chosen for this
                bonusStats.add("When you roll a 1 on a D20, you can choose to re-roll and use the new roll");
                bonusStats.add("Advantage on saving throws against being frightened");
                bonusStats.add("Advantage on saving throws against poison");
                bonusStats.add("Resistance to poison damage");
                break;
            case "Gnome":
                //Forest Gnome is chosen for this
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on INT, WIS, and CHA saving throws against magic");
                bonusStats.add("Through sounds and gestures, you can communicate simple ideas with small animals");
                break;
            case "Half-Elf":
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("Advantage on saving throws against being charmed");
                bonusStats.add("Magic effects cannot put you to sleep");
                break;
            case "Half-Orc":
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("When you are reduced to 0hp, you can drop to 1 instead. You can do this once per Long Rest");
                bonusStats.add("When you score a critical hit, you can roll an extra of the weapon's damage die");
                break;
            case "Tiefling":
                bonusStats.add("Darkvision: 60 ft");
                bonusStats.add("You are resistant to Fire damage");
                break;
        }
        switch (character.getCharacterClass()){
            case "Barbarian":
                bonusStats.add("Rage uses: 2");
                bonusStats.add("Rage bonus damage: +2");
                bonusStats.add("While raging, you have advantage on Strength checks/saving throws");
                bonusStats.add("While raging, you have resistance to bludgeoning, piercing, and slashing damage");
                bonusStats.add(getString(R.string.barbarian_other_proficiencies));
                break;
            case "Bard":
                if(calculateModifier(character.getStatValues().get(5)) < 1){
                    bonusStats.add("Bardic Inspiration uses: 1");
                }else {
                    bonusStats.add("Bardic Inspiration uses: " + calculateModifier(character.getStatValues().get(5)));
                }
                bonusStats.add("Bardic Inspiration die: d6");
                bonusStats.add("Bardic Inspiration gives an ally an extra die to add to an attack, ability check, or saving throw");
                bonusStats.add("All uses of Bardic Inspiration will return once you complete a Long Rest");
                bonusStats.add(getString(R.string.bard_other_proficiencies));
                break;
            case "Druid":
                bonusStats.add(getString(R.string.druid_other_proficiencies));
                break;
            case "Monk":
                bonusStats.add("Unarmed Strike damage die: d4");
                bonusStats.add("While unarmed, you can use DEX instead of STR for attack and damage rolls");
                bonusStats.add("While unarmed, if you take the attack action, you can make another unarmed strike as a bonus action");
                bonusStats.add(getString(R.string.monk_other_proficiencies));
                break;
            case "Paladin":
                bonusStats.add("Divine Sense: As an action, you can detect the presence of any celestial, fiend, or undead within 60ft of you.");
                if(calculateModifier(character.getStatValues().get(5)) < 1){
                    bonusStats.add("Divine Sense uses: 1/Long Rest");
                }else {
                    bonusStats.add("Divine Sense uses: " + calculateModifier(character.getStatValues().get(5)) +"/Long Rest");
                }
                bonusStats.add("Lay on Hands: You have a pool of healing power that, when you touch a creature, you can spend 5 points to neutralize one poison/disease affecting it, or spend any amount to heal them");
                bonusStats.add("Lay on Hands pool: " + 5*character.getLevel());
                bonusStats.add("Lay on Hands pool fully replenishes on a Long Rest");
                bonusStats.add(getString(R.string.paladin_other_proficiencies));
                break;
            case "Rogue":
                bonusStats.add("Sneak Attack: Once per turn, you deal extra damage to an enemy if you have advantage on the attack roll or if an ally is within 5ft and you don't have disadvantage");
                bonusStats.add("Sneak Attack Damage: 1d6");
                bonusStats.add(getString(R.string.rogue_other_proficiencies));
                break;
            case "Sorcerer":
                bonusStats.add(getString(R.string.sorcerer_other_proficiencies));
                break;
            case "Wizard":
                bonusStats.add("Arcane Recovery: While taking a Short Rest, you can choose expended spell slots to recover. They must equal half your level, rounded up");
                bonusStats.add("Arcane Recovery amount: 1");
                bonusStats.add(getString(R.string.wizard_other_proficiencies));
                break;
        }

        ListAdapter adapter = new ListAdapter(getContext(), bonusStats);
        bonusStatsList.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        DetailActivity.canLongRest = true;
    }

    public void resetHealth(){
        currentHitPoints.setText(maxHitPoints.getText().toString());
    }
}
