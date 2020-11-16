package com.example.ddcharactercreator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import java.util.*

class InventoryFragment : Fragment() {
    private var inventory = ArrayList<String>()

    @JvmField
    @BindView(R.id.copper_piece_amount)
    var copperPieceAmount: EditText? = null

    @JvmField
    @BindView(R.id.silver_piece_amount)
    var silverPieceAmount: EditText? = null

    @JvmField
    @BindView(R.id.electrum_piece_amount)
    var electrumPieceAmount: EditText? = null

    @JvmField
    @BindView(R.id.gold_piece_amount)
    var goldPieceAmount: EditText? = null

    @JvmField
    @BindView(R.id.platinum_piece_amount)
    var platinumPieceAmount: EditText? = null

    @JvmField
    @BindView(R.id.inventory)
    var inventoryList: RecyclerView? = null

    @JvmField
    @BindView(R.id.add_to_inventory)
    var addItemEditText: EditText? = null

    @JvmField
    @BindView(R.id.add_item_button)
    var addItemButton: Button? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_inventory, container, false)
        val character: Character = (activity as DetailActivity?)!!.character
        ButterKnife.bind(this, rootView)

        //Setting initial currency
        copperPieceAmount!!.setText(character.getCurrency()[1].toString())
        silverPieceAmount!!.setText(character.getCurrency()[2].toString())
        electrumPieceAmount!!.setText(character.getCurrency()[3].toString())
        goldPieceAmount!!.setText(character.getCurrency()[4].toString())
        platinumPieceAmount!!.setText(character.getCurrency()[5].toString())

        //Setting initial character inventory list.
        inventory = character.getInventoryList()
        val adapter = ListAdapter(context!!, inventory, true)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context,
                layoutManager.orientation)
        inventoryList!!.layoutManager = layoutManager
        inventoryList!!.addItemDecoration(dividerItemDecoration)
        inventoryList!!.adapter = adapter

        //Add items to the list
        addItemButton!!.setOnClickListener {
            inventory.add(addItemEditText!!.text.toString())
            addItemEditText!!.setText("")
            adapter.notifyDataSetChanged()
        }
        return rootView
    }

    override fun onPause() {
        super.onPause()
        val character: Character = (activity as DetailActivity?)!!.character
        character.getCurrency()[1] = copperPieceAmount!!.text.toString().toInt()
        character.getCurrency()[2] = silverPieceAmount!!.text.toString().toInt()
        character.getCurrency()[3] = electrumPieceAmount!!.text.toString().toInt()
        character.getCurrency()[4] = goldPieceAmount!!.text.toString().toInt()
        character.getCurrency()[5] = platinumPieceAmount!!.text.toString().toInt()
    }
}