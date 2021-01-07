package com.example.taxikotlin.ui.taxis

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.taxikotlin.R
import com.squareup.picasso.Picasso

class AdapterRecyclerView(private val mDataSet: List<Taxi>) : RecyclerView.Adapter<AdapterRecyclerView.EmpresaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EmpresaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_taxi, parent, false)
        return EmpresaViewHolder(v)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        val taxi = mDataSet[position]

        Picasso.get()
                .load("http://www.aderta7demayo.com/assets/images/" + taxi.image)
                .placeholder(taxi.placeholder)
                .into(holder.imageView)
        holder.tvTitle.text = taxi.title
        holder.tvTextNeighborhood.text = taxi.neighborhood
        holder.tvTextDirection.text = taxi.direction
        holder.btnPhone.text = taxi.phone
        holder.btnWhatsapp.text = taxi.whatsapp


        holder.btnPhone.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + taxi.phone)
            v.context.startActivity(intent)
        }


        holder.btnWhatsapp.setOnClickListener { v ->
            val url = "https://api.whatsapp.com/send?phone=+591 " + taxi.whatsapp
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            v.context.startActivity(i)
        }

        //controlamos los botones para hacerlo visible o invisible en caso de no tener un NÚMERO
        if (holder.btnPhone.text.length > 4) holder.btnPhone.visibility = View.VISIBLE else {
            holder.btnPhone.visibility = View.GONE
        }

        if (holder.btnWhatsapp.text.length > 4) holder.btnWhatsapp.visibility = View.VISIBLE else {
            holder.btnWhatsapp.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    class EmpresaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView
        val tvTitle: TextView
        val tvTextNeighborhood: TextView
        val tvTextDirection: TextView
        val btnPhone: Button
        val btnWhatsapp: Button

        init {

            imageView = itemView.findViewById(R.id.image_view)
            tvTitle = itemView.findViewById(R.id.title)
            tvTextNeighborhood = itemView.findViewById(R.id.text_neighborhood)
            tvTextDirection = itemView.findViewById(R.id.text_direction)
            btnPhone = itemView.findViewById(R.id.button_phone)
            btnWhatsapp = itemView.findViewById(R.id.button_whatsapp)
        }
    }
}