package com.example.ddcharactercreator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class InventoryFragment extends Fragment {

    private ArrayList<String> inventory = new ArrayList<String>();

    @BindView(R.id.copper_piece_amount) EditText copperPieceAmount;
    @BindView(R.id.silver_piece_amount) EditText silverPieceAmount;
    @BindView(R.id.electrum_piece_amount) EditText electrumPieceAmount;
    @BindView(R.id.gold_piece_amount) EditText goldPieceAmount;
    @BindView(R.id.platinum_piece_amount) EditText platinumPieceAmount;

    @BindView(R.id.inventory) RecyclerView inventoryList;
    @BindView(R.id.add_to_inventory) EditText addItemEditText;
    @BindView(R.id.add_item_button) Button addItemButton;

    public InventoryFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inventory, container, false);

        Character character = DetailActivity.character;

        ButterKnife.bind(this, rootView);

        //Setting initial currency
        copperPieceAmount.setText(String.valueOf(character.getCurrency().get(1)));
        silverPieceAmount.setText(String.valueOf(character.getCurrency().get(2)));
        electrumPieceAmount.setText(String.valueOf(character.getCurrency().get(3)));
        goldPieceAmount.setText(String.valueOf(character.getCurrency().get(4)));
        platinumPieceAmount.setText(String.valueOf(character.getCurrency().get(5)));

        //Setting initial character inventory list.
        inventory = character.getInventoryList();
        final ListAdapter adapter = new ListAdapter(getContext(), inventory, true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),
                layoutManager.getOrientation());
        inventoryList.setLayoutManager(layoutManager);
        inventoryList.addItemDecoration(dividerItemDecoration);
        inventoryList.setAdapter(adapter);

        //Add items to the list
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inventory.add(addItemEditText.getText().toString());
                addItemEditText.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onPause(){
        super.onPause();
        Character character = DetailActivity.character;

        character.getCurrency().set(1, Integer.parseInt(copperPieceAmount.getText().toString()));
        character.getCurrency().set(2, Integer.parseInt(silverPieceAmount.getText().toString()));
        character.getCurrency().set(3, Integer.parseInt(electrumPieceAmount.getText().toString()));
        character.getCurrency().set(4, Integer.parseInt(goldPieceAmount.getText().toString()));
        character.getCurrency().set(5, Integer.parseInt(platinumPieceAmount.getText().toString()));
    }
}
