package com.example.ddcharactercreator;

import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
import java.util.ArrayList;
import java.util.List;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Spell>> {

    private CharacterAdapter mCharacterAdapter;
    private MutableLiveData<List<Character>> listOfCharacters = new MutableLiveData<List<Character>>();

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String SPELL_API_BASE_URL = "https://api.open5e.com/spells/";
    private static final int SPELL_LOADER_ID = 1;
    private SpellAdapter mSpellAdapter;
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
        mSpellAdapter = new SpellAdapter(this, new ArrayList<Spell>());
        if(mSpellDb.spellDao().loadAllSpells().getValue() == null){
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // Get a reference to the LoaderManager, in order to interact with loaders.
                android.app.LoaderManager loaderManager = getLoaderManager();

                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(SPELL_LOADER_ID, null, this);
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

    @Override
    public Loader<List<Spell>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(SPELL_API_BASE_URL);
        return new SpellLoader(this, baseUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Spell>> loader, List<Spell> spells) {
        mSpellAdapter.clear();
        if(spells != null && !spells.isEmpty()){
            for(int i=0; i<spells.size(); i++){
                mSpellDb.spellDao().insertSpell(spells.get(i));
            }
        }
        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<List<Spell>> loader) {
        mSpellAdapter.clear();
    }
}
