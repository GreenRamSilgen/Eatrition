package com.example.eatrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rec_row.view.*

class MainAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    val restaurntNames = listOf("First", "Second","Third") //name of the restaurants

    override fun getItemCount(): Int {
        return restaurntNames.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val callForRow = layoutInflater.inflate(R.layout.rec_row, parent, false)
        return CustomViewHolder(callForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val restaurantName = restaurntNames.get(position) //extract restaurant name at position x
        holder?.view?.textView_restaurantName?.text = restaurantName //set name of each card here.
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}