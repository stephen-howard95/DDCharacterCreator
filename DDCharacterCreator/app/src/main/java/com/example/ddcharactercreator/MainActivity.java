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
