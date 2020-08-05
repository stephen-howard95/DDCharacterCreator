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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    @BindView(R.id.temporary_hit_points) EditText temporaryHitPoints;
    @BindView(R.id.current_hit_points) EditText currentHitPoints;
    @BindView(R.id.max_hit_points) TextView maxHitPoints;
    @BindView(R.id.hit_die) TextView hitDieTextView;
    
    @BindView(R.id.advantage) RadioButton advantageRb;
    @BindView(R.id.disadvantage) RadioButton disadvantageRb;
    @BindView(R.id.normal_roll) RadioButton normalRollRb;
    @BindView(R.id.plus_amount) EditText plusAmount;
    @BindView(R.id.minus_amount) EditText minusAmount;
    @BindView(R.id.final_roll) TextView finalRoll;

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

        //Setting Proficiency Bonus and speed values
        proficiencyBonus.setText(String.format("Proficiency Bonus: %s", DetailActivity.proficiencyBonus));
        if(character.getRace().equals("Dwarf")  || character.getRace().equals("Halfling")  || character.getRace().equals("Gnome")){
            speedTextView.setText("Speed: 25ft");
        } else if (character.getRace().equals("Elf")) {
            speedTextView.setText("Speed: 35ft");
        } else {
            speedTextView.setText("Speed: 30ft");
        }

        //Setting Passive Perception and Armor Class
        passivePerception.setText(String.format("Passive Perception: %s", (10 + calculateModifier(character.getStatValues().get(4)))));
        armorClassEditText.setText(String.valueOf(character.getCurrency().get(0)));
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
                    character.getCurrency().set(0, Integer.parseInt(armorClassEditText.getText().toString()));
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

        //Setting Temporary/Current/Max HP, hit die values, and saving throw proficiencies.
        temporaryHitPoints.setText(String.valueOf(character.getCurrency().get(7)));
        currentHitPoints.setText(String.valueOf(character.getCurrency().get(6)));
        maxHitPoints.setText(String.valueOf(character.getCurrency().get(6)));
        switch (character.getCharacterClass()){
            case "Barbarian":
                hitDieTextView.setText(String.format("d12 (%s)", character.getLevel()));
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Bard":
                hitDieTextView.setText(String.format("d8 (%s)", character.getLevel()));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Druid":
                hitDieTextView.setText(String.format("d8 (%s)", character.getLevel()));
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Fighter":
                hitDieTextView.setText(String.format("d10 (%s)", character.getLevel()));
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Monk":
                hitDieTextView.setText(String.format("d8 (%s)", character.getLevel()));
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                if(character.getLevel() >= 14){
                    constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                }
                break;
            case "Paladin":
                hitDieTextView.setText(String.format("d10 (%s)", character.getLevel()));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Ranger":
                hitDieTextView.setText(String.format("d10 (%s)", character.getLevel()));
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Rogue":
                hitDieTextView.setText(String.format("d8 (%s)", character.getLevel()));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                if(character.getLevel() >= 15){
                    wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                    wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                }
                break;
            case "Sorcerer":
                hitDieTextView.setText(String.format("d6 (%s)", character.getLevel()));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Cleric":
            case "Warlock":
                hitDieTextView.setText(String.format("d8 (%s)", character.getLevel()));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Wizard":
                hitDieTextView.setText(String.format("d6 (%s)", character.getLevel()));
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
        }
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
        temporaryHitPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!temporaryHitPoints.getText().toString().equals("")){
                    character.getCurrency().set(7, Integer.parseInt(temporaryHitPoints.getText().toString()));
                }
            }
        });
        //Changes the color of the Text to denote how much/little health the character has remaining.
        currentHitPoints.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!currentHitPoints.getText().toString().equals("")){
                    character.getCurrency().set(6, Integer.parseInt(currentHitPoints.getText().toString()));
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
        

        //Dice Roller
        normalRollRb.setChecked(true);
        finalRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(plusAmount.getText().toString().equals(null)){
                    plusAmount.setText("0");
                }
                if(minusAmount.getText().toString().equals(null)){
                    minusAmount.setText("0");
                }
                if(normalRollRb.isChecked()){
                    finalRoll.setText(String.valueOf((int) (Math.random()*20)+1 + Integer.parseInt(plusAmount.getText().toString()) - Integer.parseInt(minusAmount.getText().toString())));
                } else if(advantageRb.isChecked()){
                    int roll1 = (int) (Math.random()*20)+1 + Integer.parseInt(plusAmount.getText().toString()) - Integer.parseInt(minusAmount.getText().toString());
                    int roll2 = (int) (Math.random()*20)+1 + Integer.parseInt(plusAmount.getText().toString()) - Integer.parseInt(minusAmount.getText().toString());
                    if(roll1 >= roll2){
                        finalRoll.setText(String.valueOf(roll1));
                    } else{
                        finalRoll.setText(String.valueOf(roll2));
                    }
                } else if(disadvantageRb.isChecked()){
                    int roll1 = (int) (Math.random()*20)+1 + Integer.parseInt(plusAmount.getText().toString()) - Integer.parseInt(minusAmount.getText().toString());
                    int roll2 = (int) (Math.random()*20)+1 + Integer.parseInt(plusAmount.getText().toString()) - Integer.parseInt(minusAmount.getText().toString());
                    if(roll1 <= roll2){
                        finalRoll.setText(String.valueOf(roll1));
                    } else{
                        finalRoll.setText(String.valueOf(roll2));
                    }
                }
            }
        });
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
