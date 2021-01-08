package com.example.taxikotlin.ui.incidentes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.taxikotlin.MainActivity
import com.example.taxikotlin.R

class AdapterIncidentes(private val mDataSet: List<Incidente>) : RecyclerView.Adapter<AdapterIncidentes.EmpresaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EmpresaViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_incidente, parent, false)
        return EmpresaViewHolder(v)
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        val incidencia = mDataSet[position]

        holder.tvName.text = incidencia.name
        holder.tvDirection.text = incidencia.direction
        holder.tvDateTime.text = incidencia.datetime
        holder.tvMotiveCancel.text = incidencia.motive_cancel
        holder.tvDetail.text = incidencia.detail
        holder.tvDateTimeCancel.text = incidencia.datetime_cancel
        holder.btnPhone.text = incidencia.phone
        holder.btnWhatsapp.text = incidencia.whatsapp


        holder.btnPhone.setOnClickListener { v ->
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + incidencia.phone)
            v.context.startActivity(intent)
        }

        holder.btnWhatsapp.setOnClickListener { v ->
            val url = "https://api.whatsapp.com/send?phone=+591 " + incidencia.whatsapp
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            v.context.startActivity(i)
        }

        //Evento del Boton MAP
        holder.btnMap.setOnClickListener {
            val latitude = incidencia.latitude
            val longitude = incidencia.longitude
            Log.e("MAP", "$latitude $longitude")
        }

        //para abrir el modal
        holder.btnModal.setOnClickListener { v -> showDialog(v.context, incidencia) }

        //lo hacemos visible o invisible button en caso de no NÜMERO
        if (holder.btnPhone.text.length > 4) {
            holder.btnPhone.visibility = View.VISIBLE
        } else {
            holder.btnPhone.visibility = View.GONE
        }

        if (holder.btnWhatsapp.text.length > 4) {
            holder.btnWhatsapp.visibility = View.VISIBLE
        } else {
            holder.btnWhatsapp.visibility = View.GONE
        }

        //controlamos que CarwView mostrar Activo o Cancelado

        if (holder.tvDateTimeCancel.text.toString().length > 0) {
            holder.cardViewActive.visibility = View.GONE
            holder.cardViewCancel.visibility = View.VISIBLE
        } else {
            holder.cardViewActive.visibility = View.VISIBLE
            holder.cardViewCancel.visibility = View.GONE
        }

        //Visble o Ocultado Button PENCIL

        val email_user = "cfrias@radiotaxicordial.com"

        if (incidencia.email == email_user) {
            holder.btnModal.visibility = View.VISIBLE
        } else {
            holder.btnModal.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    class EmpresaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvDirection: TextView
        val tvDateTime: TextView
        val tvMotiveCancel: TextView
        val tvDetail: TextView
        val tvDateTimeCancel: TextView
        val btnPhone: Button
        val btnWhatsapp: Button
        val btnMap: ImageButton
        val btnModal: ImageButton
        val cardViewActive: CardView
        val cardViewCancel: CardView

        init {

            tvName = itemView.findViewById(R.id.text_name)
            tvDirection = itemView.findViewById(R.id.text_direction)
            tvDateTime = itemView.findViewById(R.id.text_datetime)
            tvMotiveCancel = itemView.findViewById(R.id.text_motive_can)
            tvDetail = itemView.findViewById(R.id.text_detail_can)
            tvDateTimeCancel = itemView.findViewById(R.id.text_datetime_cancel)
            btnMap = itemView.findViewById(R.id.button_map)
            btnModal = itemView.findViewById(R.id.button_modal)
            btnPhone = itemView.findViewById(R.id.button_phone)
            btnWhatsapp = itemView.findViewById(R.id.button_whatsapp)
            cardViewActive = itemView.findViewById(R.id.cardView_active)
            cardViewCancel = itemView.findViewById(R.id.cardView_cancel)

        }
    }

    fun showDialog(c: Context, incidencia: Incidente) {

        val ft = (c as MainActivity).supportFragmentManager.beginTransaction()

        val prev = c.supportFragmentManager.findFragmentByTag("dialog")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        // Create and show the dialog.
        val newFragment = ModalFragment.newInstance(incidencia)
        newFragment.show(ft, "dialog")
    }
}