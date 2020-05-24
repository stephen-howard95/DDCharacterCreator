package com.example.ddcharactercreator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public TabAdapter(Context context, FragmentManager fm){
        //noinspection deprecation
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new MainStatsFragment();
        } else if(position == 1){
            return new SpellcastingFragment();
        } else if(position == 2){
            return new InventoryFragment();
        }else{
            return new CharacterFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.main_stats_tab_label);
        } else if (position == 1) {
            return mContext.getString(R.string.spellcasting_tab_label);
        } else if (position == 2) {
            return mContext.getString(R.string.inventory_tab_label);
        } else {
            return mContext.getString(R.string.character_tab_label);
        }
    }
}
