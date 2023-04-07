package com.example.forkids

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.nokidszone_list.view.*

class NokidszoneAdapter  : RecyclerView.Adapter<NokidszoneAdapter.ViewHolder>(){
    var items = ArrayList<Nokidszonelist>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NokidszoneAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nokidszone_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NokidszoneAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.setItem(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        fun setItem(item: Nokidszonelist) {
            itemView.cafenameView.text = item.cafename
            itemView.dateView.text = item.date
            itemView.ratingBar.rating = item.rating.toFloat()
            itemView.addressView.text = item.cafeaddress
            //itemView.locationView1.text = item.cafelocation1
            //itemView.locationView2.text = item.cafelocation2

        }
    }
}