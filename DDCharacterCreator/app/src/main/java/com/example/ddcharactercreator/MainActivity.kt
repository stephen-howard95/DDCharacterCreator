package com.example.ddcharactercreator

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity(contentLayoutId: Int) : AppCompatActivity(contentLayoutId) {

    private val listOfCharacters: MutableLiveData<List<Character>> = MutableLiveData<List<Character>>()
    private val mCharacterAdapter: CharacterAdapter = CharacterAdapter(this, listOfCharacters.value)

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val mAdView: AdView = findViewById(R.id.adView)
        val adRequest: AdRequest = AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
        mAdView.loadAd(adRequest)

        val characterViewModel: CharacterViewModel = ViewModelProviders.of(this).get(CharacterViewModel::class.java)
        val characterRecyclerView: RecyclerView = findViewById(R.id.character_list)
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        characterRecyclerView.addItemDecoration(dividerItemDecoration)
        characterRecyclerView.layoutManager = layoutManager
        characterRecyclerView.adapter = mCharacterAdapter
        characterViewModel.loadAllCharacters().observe(this, Observer {
            characters -> mCharacterAdapter.setCharacters(characters)
        })

        val fab: FloatingActionButton = findViewById(R.id.add_character)
        fab.setOnClickListener { createNewCharacter() }
    }

    private fun createNewCharacter(){
        val intent = Intent(this, FirstQuestionnaireActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)
    }
}
