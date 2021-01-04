package com.example.taxikotlin.ui.modulos

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.taxi.ui.modulos.Modulo

import com.example.taxikotlin.R

class AdapterModulos(private val mDataSet: List<Modulo>) : RecyclerView.Adapter<AdapterModulos.EmpresaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EmpresaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_modulo, parent, false)
        return EmpresaViewHolder(v)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        val modulo = mDataSet[position]


        holder.tvTextName.text = modulo.name
        holder.tvTextDirection.text = modulo.direction
        holder.btnPhone.text = modulo.phone
        holder.btnPhoneFree.text = modulo.phone_free
        holder.btnWhatsapp.text = modulo.whatsapp


        holder.btnPhone.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + modulo.phone)
            v.context.startActivity(intent)
        }
        holder.btnPhoneFree.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + modulo.phone_free)
            v.context.startActivity(intent)
        }

        holder.btnWhatsapp.setOnClickListener { v ->
            val url = "https://api.whatsapp.com/send?phone=+591 " + modulo.whatsapp
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            v.context.startActivity(i)
        }

        //lo hacemos visible o invisible button en caso de no NÜMERO

        if (holder.btnPhoneFree.text.length > 4) holder.btnPhoneFree.visibility = View.VISIBLE else {
            holder.btnPhoneFree.visibility = View.GONE
        }

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

        val tvTextName: TextView
        val tvTextDirection: TextView
        val btnPhone: Button
        val btnPhoneFree: Button
        val btnWhatsapp: Button

        init {

            tvTextName = itemView.findViewById(R.id.text_name)
            tvTextDirection = itemView.findViewById(R.id.text_direction)
            btnPhone = itemView.findViewById(R.id.button_phone)
            btnPhoneFree = itemView.findViewById(R.id.button_phone_free)
            btnWhatsapp = itemView.findViewById(R.id.button_whatsapp)
        }
    }
}