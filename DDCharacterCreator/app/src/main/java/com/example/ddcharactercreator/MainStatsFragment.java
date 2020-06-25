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
        currentHitPoints.setText(String.valueOf(character.getCurrency().get(0)));
        maxHitPoints.setText(String.valueOf(character.getCurrency().get(0)));
        switch (character.getCharacterClass()){
            case "Barbarian":
                hitDieTextView.setText("d12");
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Bard":
                hitDieTextView.setText("d8");
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Druid":
                hitDieTextView.setText("d8");
                intelligenceLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                intelligenceModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Fighter":
                hitDieTextView.setText("d10");
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Monk":
                hitDieTextView.setText("d8");
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
                hitDieTextView.setText("d10");
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Ranger":
                hitDieTextView.setText("d10");
                strengthLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                strengthModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                dexterityModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Rogue":
                hitDieTextView.setText("d8");
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
                hitDieTextView.setText("d6");
                constitutionLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                constitutionModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Cleric":
            case "Warlock":
                hitDieTextView.setText("d8");
                wisdomLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                wisdomModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaLabel.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaValue.setTextColor(getResources().getColor(R.color.proficiency_blue));
                charismaModifier.setTextColor(getResources().getColor(R.color.proficiency_blue));
                break;
            case "Wizard":
                hitDieTextView.setText("d6");
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
