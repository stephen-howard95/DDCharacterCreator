package com.example.ddcharactercreator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        final ListAdapter adapter = new ListAdapter(getContext(), inventory);
        inventoryList.setLayoutManager(new LinearLayoutManager(getContext()));
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

        //Delete items from the list
        inventoryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + inventory.get(position));
                final int positionToRemove = position;
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        inventory.remove(positionToRemove);
                        adapter.notifyDataSetChanged();
                    }
                });
                adb.show();
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        DetailActivity.canLongRest = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onPause(){
        super.onPause();
        DetailActivity.canLongRest = true;
        Character character = DetailActivity.character;

        character.getCurrency().set(1, Integer.parseInt(copperPieceAmount.getText().toString()));
        character.getCurrency().set(2, Integer.parseInt(silverPieceAmount.getText().toString()));
        character.getCurrency().set(3, Integer.parseInt(electrumPieceAmount.getText().toString()));
        character.getCurrency().set(4, Integer.parseInt(goldPieceAmount.getText().toString()));
        character.getCurrency().set(5, Integer.parseInt(platinumPieceAmount.getText().toString()));
    }
}
