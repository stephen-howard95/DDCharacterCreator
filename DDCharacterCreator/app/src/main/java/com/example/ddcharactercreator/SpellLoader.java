package com.example.ddcharactercreator;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class SpellLoader extends AsyncTaskLoader {
    private String mUrl;

    public SpellLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

    @Override
    public List<Spell> loadInBackground() {
        if(mUrl == null){
            return null;
        } else{
            List<Spell> spells = JsonUtils.fetchSpellData(mUrl);
            String nextUrl = JsonUtils.fetchNextUrl(mUrl);
            for(int i=0; i<6; i++){
                List<Spell> moreSpells = JsonUtils.fetchSpellData(nextUrl);
                spells.addAll(moreSpells);
                mUrl = nextUrl;
                nextUrl = JsonUtils.fetchNextUrl(mUrl);
            }
            return spells;
        }
    }
}
