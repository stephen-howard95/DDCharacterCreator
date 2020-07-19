package com.example.ddcharactercreator;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private CharacterAdapter mCharacterAdapter;
    private MutableLiveData<List<Character>> listOfCharacters = new MutableLiveData<List<Character>>();
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SPELL_API_BASE_URL = "https://api.open5e.com/";
    private SpellDatabase mSpellDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AdView mAdView = findViewById(R.id.adView);
        //noinspection deprecation
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mSpellDb = SpellDatabase.getInstance(getApplicationContext());
        List<Spell> spellsList = mSpellDb.spellDao().loadAllSpells();
        if(spellsList.size() == 0){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SPELL_API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceholderApi jsonPlaceholderApi = retrofit.create(JsonPlaceholderApi.class);
            for(int i=1; i<=7; i++){
                Call<SpellContainer> call = jsonPlaceholderApi.getSpellContainer(i);
                call.enqueue(new Callback<SpellContainer>() {
                    @Override
                    public void onResponse(Call<SpellContainer> call, Response<SpellContainer> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                        } else{
                            if(response.body() != null && !response.body().getResults().isEmpty()){
                                for(int j=0; j<response.body().getResults().size(); j++){
                                    mSpellDb.spellDao().insertSpell(response.body().getResults().get(j));
                                    //There is a duplicate spell in the API. This deletes one of the copies.
                                    if(response.body().getResults().get(j).getSpellName().equalsIgnoreCase("Eye bite")){
                                        mSpellDb.spellDao().deleteSpell(response.body().getResults().get(j));
                                    }
                                    //Manually adds spells missing from the API
                                    mSpellDb.spellDao().insertSpell(new Spell("Banishing Smite", "The next time you hit a creature with a weapon attack before this spell ends, your weapon crackles with force and the attack deals an extra 5d10 force damage. If this attack reduces the target to 50 or less HP, you banish it. If the target is native to a different plane of existence, it disappears, returning to its home plane. If the target is native to the plane you are on, it is sent to a harmless demiplane until the spell ends. While there, the target is incapacitated. When the spell ends, the target reappears in the closest unoccupied space to where it was before.", "", "Self", "up to 1 minute", "yes", "1 bonus action", 5, "Paladin", "Abjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Beast Sense", "You touch a willing beast. For the duration, you can use your action to see through the beasts eyes and hear what it hears and continue to do so until you use your action to return to your normal senses. While perceiving through the beasts senses, you gain the benefits of any special senses the beast has, but are blinded and deafened to your own surroundings", "", "Touch", "up to 1 hour", "yes", "1 Action", 2, "Druid, Ranger", "Divination"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Blinding Smite", "The next time you hit a creature with a melee weapon attack during this spell's duration, your weapon flares with bright light and the attack deals an extra 3d8 radiant damage. The target must also succeed on a Constitution saving throw or be blinded until the spell ends. A creature blinded by this spell makes a Constitution saving throw at the end of each of it's turns. On a successful save, it is no longer blinded.", "", "Self", "up to 1 minute", "yes", "1 bonus action", 3, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Compelled Duel", "You attempt to compel a creature into a duel. One creature you can see within range must make a Wisdom saving throw. On a failed save, the creature is drawn to you, compelled by your divine demand. For the duration, it has disadvantage on attack rolls against any creature besides you and must make a Wisdom saving throw every time it tries to move to a spot more than 30 feet from you. If it succeeds this saving throw, the creature's movement is not restricted this turn.\nThe spell ends if you attack any other creature, if you cast a spell that targets a hostile creature other than the target, if a creature friendly to you damages tha target/casts a harmful spell on it, or if you end your turn more than 30 feet away form the target", "", "30 feet", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Crusader's Mantle", "Holy power radiates from you in an aura within a 30-foot radius, awakening boldness in friendly creatures. The aura moves with you, cantered on you. While in the aura, each non-hostile creature in the aura, including yourself, deal an extra 1d4 Radiant damage when it hits with a weapon attack", "", "Self", "up to 1 minute", "yes", "1 Action", 3, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Destructive Wave", "You strike the ground, creating a burst of divine energy that ripples outward from you. Each creature you choose within 30 feet of you must succeed on a Constitution saving throw or take 5d6 Thunder damage, as well as 5d6 Radiant or Necrotic damage (your choice), and be knocked prone. A creature that succeeds on its saving throw takes half as much damage and is not knocked prone.", "", "Self(30-foot radius)", "Instantaneous", "no", "1 Action", 5, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Dissonant Whispers", "You whisper a Discordant melody that only one creature of your choice within range can hear, wracking it with terrible pain. The target must make a Wisdom saving throw. On a failed save, it takes 3d6 psychic damage and must immediately use its reaction, if available, to move as far as its speed allows away from you. The creature doesn't move into obviously dangerous ground, such as fire or a pit. On a successful save, the target takes half as much damage and doesn't have to move. A deafened creature automatically succeeds on the save.", "When you cast this spell at a higher level, the damage increases by 1d6 for each spell slot above the 1st.", "60 feet", "Instantaneous", "no", "1 Action", 1, "Bard", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Elemental Weapon", "A nonmagical weapon you touch becomes magical for the duration. Choose 1 of the following damage types: Acid, Cold, Fire, Lightning, or Thunder. For the duration, the weapon has a +1 bonus to attack rolls and deals an extra 1d4 damage of the chosen type.", "When you cast this spell using a 5th or 6th level spell slot, the bonus to attack rolls becomes +2 and the extra damage is 2d4. Using a spell slot of 7th level or higher, it becomes +3 to hit and deals an extra 3d4 damage.", "Touch", "up to 1 hour", "yes", "1 Action", 3, "Paladin", "Transmutation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Friends", "For the duration, you have advantage on all Charisma checks directed at 1 creature of your choice that isn't hostile toward you. When the spell ends, the creature realizes you used magic to influence its mood and may become hostile.", "", "Self", "up to 1 minute", "yes", "1 action", 0, "Bard, Sorcerer, Wizard", "Enchantment"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Grasping Vine", "You conjure a vine that sprouts from the ground in an area you can see within range. The vine lashes out at a creature of your choice within 30 feet of it that you can see. That creature must succeed on a Dexterity saving throw or be pulled 20 feet toward the vine. Until the spell ends, you may use a bonus action to have the vine lash out at the same creature or a different one.", "", "30 feet", "up to 1 minute", "yes", "1 bonus action", 4, "Druid, Ranger", "Conjuration"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Searing Smite", "The next time you hit with a melee weapon attack during this spell's duration, your weapon flares with a white-hot intensity, and the attack deals an extra 1d6 Fire damage and causes the target to ignite in flames. At the start of each of its turns until the spell ends, the target must make a Constitution saving throw. On a failed save, the target takes 1d6 fire damage. The spell ends if the target or a creature within 5 feet of the target use their action to douse the flames, the target succeeds on their Constitution saving throw, or if an effect douses the flames such as the target being submerged in water", "When you cast this spell at a higher level, the initial extra damage dealt by the attack increases by 1d6 for each spell slot above the first", "Self", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Staggering Smite", "The next time you hit with a melee weapon attack during this spell's duration, your weapon pierces both body and mind, and the attack deals an extra 4d6 Psychic damage. The target must then make a Wisdom saving throw. On a failed save, it has disadvantage on attack rolls and ability checks and cannot take reactions until the end of it's next turn", "", "Self", "up to 1 minute", "yes", "1 bonus action", 4, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Thunderous Smite", "The next time you hit with a melee weapon attack during this spell's duration, your weapon rings with thunder that is audible within 300 feet of you, and the attack deals an extra 2d6 Thunder damage. Additionally, if the target is a creature, it must succeed on a Strength saving throw or be pushed 10 feet away from you and knocked prone", "", "Self", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Evocation"));
                                    mSpellDb.spellDao().insertSpell(new Spell("Wrathful Smite", "The next time you hit with a melee weapon attack during this spell's duration, your attack deals an extra 1d6 Psychic damage. Additionally, if the target is a creature, it mus make a Wisdom saving throw or be frightened of you until the spell ends. As an action, the creature can make a Wisdom check against your spell save DC to steel its resolve and end the spell", "", "Self", "up to 1 minute", "yes", "1 bonus action", 1, "Paladin", "Evocation"));
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SpellContainer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        mCharacterAdapter = new CharacterAdapter(this, new ArrayList<Character>());
        ListView characterListView = findViewById(R.id.character_list);
        characterListView.setAdapter(mCharacterAdapter);
        characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetailActivity((Character) parent.getItemAtPosition(position));
            }
        });

        CharacterDatabase mCharacterDb = CharacterDatabase.getInstance(getApplicationContext());
        LiveData<List<Character>> savedCharacterList = mCharacterDb.characterDao().loadAllCharacters();
        savedCharacterList.observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                listOfCharacters.postValue(characters);
            }
        });

        listOfCharacters.observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                mCharacterAdapter.clear();
                mCharacterAdapter.addAll(characters);
            }
        });

        FloatingActionButton fab = findViewById(R.id.add_character);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewCharacter();
            }
        });
        retrieveCharacters();
    }

    private void createNewCharacter(){
        Intent intent = new Intent(this, FirstQuestionnaireActivity.class);
        startActivity(intent);
    }

    private void launchDetailActivity(Character character){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.CHARACTER, character);
        startActivity(intent);
    }

    private void retrieveCharacters(){
        CharacterViewModel viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(CharacterViewModel.class);
        viewModel.getCharacters().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                Log.d(TAG, "Receiving database update from LiveData");
                mCharacterAdapter.setCharacters(characters);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
