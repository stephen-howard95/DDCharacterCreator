package com.example.ddcharactercreator;

import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class SpellChooserActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<Spell>> {

    private static final String SPELL_API_BASE_URL = "https://api.open5e.com/spells/";
    private static Spell spell;
    private Character character = DetailActivity.character;
    private static final int SPELL_LOADER_ID = 1;

    private SpellAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);

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

        mAdapter = new SpellAdapter(this, new ArrayList<Spell>());

        ListView spellListView = findViewById(R.id.list);

        spellListView.setAdapter(mAdapter);

        spellListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                spell = (Spell) parent.getItemAtPosition(position);
                //prevents duplicate spells from being added to the list and warns the user
                int i=0;
                Boolean spellIsUnique = true;
                do{
                    if(character.getSpellsKnown().size() == 0){
                        break;
                    }
                    Spell knownSpell = character.getSpellsKnown().get(i);
                    if(spell.getSpellName().equals(knownSpell.getSpellName())){
                        Toast.makeText(getApplicationContext(), "You already know this spell", Toast.LENGTH_SHORT).show();
                        spellIsUnique = false;
                        break;
                    }
                    i++;
                } while(i<character.getSpellsKnown().size());
                if(spellIsUnique){
                    character.getSpellsKnown().add(spell);
                    finish();
                }
            }
        });
    }

    @Override
    public Loader<List<Spell>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(SPELL_API_BASE_URL);
        return new SpellLoader(this, baseUri.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Spell>> loader, List<Spell> spells) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.INVISIBLE);

        mAdapter.clear();

        if(spells != null && !spells.isEmpty()){
            for(int i=0; i<spells.size(); i++){
                //This is where you will also limit the spells based on class
                if(2*spells.get(i).getLevel()-1 > character.getLevel()){
                    spells.remove(i);
                    i--;
                }
            }
            mAdapter.addAll(spells);
        }
        getLoaderManager().destroyLoader(loader.getId());
    }

    @Override
    public void onLoaderReset(Loader<List<Spell>> loader) {
        mAdapter.clear();
    }
}
