package com.example.eatrition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rec_row.view.*

class MainAdapter(val yelpFeed: YelpFeed): RecyclerView.Adapter<CustomViewHolder>() {

    var restaurantNames = listOf("First", "Second","Third") //name of the restaurants

    override fun getItemCount(): Int {
        return yelpFeed.businesses.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val callForRow = layoutInflater.inflate(R.layout.rec_row, parent, false)
        return CustomViewHolder(callForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //val restaurantName = restaurantNames.get(position) //extract restaurant name at position x
        val restaurant = yelpFeed.businesses.get(position)
        holder.view.textView_restaurantName.text = restaurant.name //set name of each card here.
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}