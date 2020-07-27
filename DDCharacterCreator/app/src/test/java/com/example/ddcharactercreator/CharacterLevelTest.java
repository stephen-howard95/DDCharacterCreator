package com.example.ddcharactercreator;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class CharacterLevelTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testCreateCharacter(){
        int randomRaceChoice = (int) (Math.random()*11);
        int randomClassChoice = (int) (Math.random()*12);
        int randomAlignment = (int) (Math.random()*9);

        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Character"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(randomRaceChoice).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(randomClassChoice).perform(click());
        onView(withId(R.id.alignment_spinner)).perform(click());
        onData(anything()).atPosition(randomAlignment).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }

    @Test
    public void testFullyLevelUpRandomCharacter(){
        int randomRaceChoice = (int) (Math.random()*11);
        int randomClassChoice = (int) (Math.random()*12);
        int randomAlignment = (int) (Math.random()*9);

        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Character"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(randomRaceChoice).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(randomClassChoice).perform(click());
        onView(withId(R.id.alignment_spinner)).perform(click());
        onData(anything()).atPosition(randomAlignment).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("12"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            onView(withId(R.id.finish_button)).perform(click());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }

    @Test
    public void testMaxLevelBarbarian(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Barbarian"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(7).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*2)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("12"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelBard(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Bard"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*2)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("8"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelCleric(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Cleric"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*7)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("8"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelDruid(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Druid"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(6).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*2)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("8"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelFighter(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Fighter"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*2)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        //Fighting Style
        onView(withId(R.id.level_one_choice_spinner_1)).perform(scrollTo(), click());
        onData(anything()).atPosition((int) (Math.random()*6));
        pressBack();
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("10"));
            pressBack();
            if(i == 2 || i == 4 || i == 6 || i == 10 || i == 12 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelMonk(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Monk"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(5).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*2)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("8"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelPaladin(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Paladin"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(6).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*3)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("10"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (PerformException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelRanger(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Ranger"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(7).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*2)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(scrollTo(), typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("10"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelRogue(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Rogue"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(8).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(8).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*3)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("8"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelSorcerer(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Sorcerer"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(4).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(9).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*4)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("6"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            if(i == 1){
                onView(withId(R.id.character_choice_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*8)).perform(click());
                onView(withId(R.id.character_choice_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*8)).perform(click());
            } else if(i == 8){
                onView(withId(R.id.character_choice_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            } else if(i == 15){
                onView(withId(R.id.character_choice_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*5)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelWarlock(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Warlock"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(9).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(10).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*3)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("8"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
    @Test
    public void testMaxLevelWizard(){
        onView(withId(R.id.add_character)).perform(click());
        onView(withId(R.id.character_name_edit_text)).perform(typeText("Test Wizard"));
        onView(withId(R.id.race_spinner)).perform(click());
        onData(anything()).atPosition(3).perform(click());
        onView(withId(R.id.class_spinner)).perform(click());
        onData(anything()).atPosition(11).perform(click());
        onView(withId(R.id.next_questionnaire_button)).perform(scrollTo(), click());
        //Subclass choices
        onView(withId(R.id.subclass_choice_spinner)).perform(click());
        onData(anything()).atPosition((int) (Math.random()*8)).perform(click());
        onView(withId(R.id.starting_gold_edit_text)).perform(typeText("80"));
        onView(withId(R.id.button_finished_character_creation)).perform(scrollTo(), click());
        for(int i=0; i<19; i++){
            onView(withId(R.id.fab_main)).perform(click());
            onView(withId(R.id.fab_level_up)).perform(click());
            onView(withId(R.id.more_hp)).perform(typeText("10"));
            pressBack();
            if(i == 2 || i == 6 || i == 10 || i == 14 || i == 17){
                onView(withId(R.id.ability_score_improvement_1)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
                onView(withId(R.id.ability_score_improvement_2)).perform(click());
                onData(anything()).atPosition((int) (Math.random()*6)).perform(click());
            }
            try {
                onView(withId(R.id.finish_button)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
                onView(withId(R.id.finish_button)).perform(click());
            } catch (NoMatchingViewException e) {
                onView(withId(R.id.finish_button)).perform(scrollTo(), click());
            }
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
            onView(withId(R.id.viewpager)).perform(swipeLeft());
        }
        onView(withId(R.id.fab_main)).perform(click());
        onView(withId(R.id.fab_save_character)).perform(click());
    }
}
