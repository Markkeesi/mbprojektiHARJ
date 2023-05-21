package com.example.budgetlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TapahtumaAdapter(private var tapahtumat: List<Tapahtuma>) :
    RecyclerView.Adapter<TapahtumaAdapter.TapahtumaHolder>() {

    class TapahtumaHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label : TextView = view.findViewById(R.id.label)
        val maara : TextView = view.findViewById(R.id.maara)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TapahtumaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tapahtuma_layout, parent, false)
        return TapahtumaHolder(view)
    }

    override fun onBindViewHolder(holder: TapahtumaHolder, position: Int) {
        val tapahtuma = tapahtumat[position]
        val context = holder.maara.context

        if (tapahtuma.amount >= 0){
            holder.maara.text = "+ €%.2f".format(tapahtuma.amount)
            holder.maara.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else {
            holder.maara.text = "- €%.2f".format(Math.abs(tapahtuma.amount))
            holder.maara.setTextColor(ContextCompat.getColor(context, R.color.red))
        }

        holder.label.text = tapahtuma.label


    }

    override fun getItemCount(): Int {
        return tapahtumat.size
    }

    fun setData(tapahtumat: List<Tapahtuma>){
        this.tapahtumat = tapahtumat
        notifyDataSetChanged()
    }
}