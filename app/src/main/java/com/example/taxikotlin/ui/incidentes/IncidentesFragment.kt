package com.example.taxikotlin.ui.incidentes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.taxikotlin.R

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class IncidentesFragment : Fragment() {
    internal lateinit  var progressBar: ProgressBar

    private lateinit  var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterIncidentes
    private lateinit  var incidentes: MutableList<Incidente>

    private val URL_BASE = "http://www.aderta7demayo.com/backend/json_simulado.php"


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_incidentes, container, false)
        progressBar = root.findViewById(R.id.progressBar)

        mRecyclerView = root.findViewById(R.id.my_recycler_view)
        mRecyclerView!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = layoutManager
        loadIncidentes()

        return root
    }

    private fun loadIncidentes() {
        progressBar.visibility = View.VISIBLE
        incidentes = ArrayList()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this.context)
        val jsonArrayRequest = JsonArrayRequest(URL_BASE, Response.Listener { response ->
            for (i in 0 until response.length()) {
                // Get current JSON object
                var objectResult: JSONObject? = null
                try {
                    objectResult = response.getJSONObject(i)

                    val name = objectResult!!.getString("Nombre")
                    val direction = objectResult.getString("Direccion")
                    val phone = objectResult.getString("Telefono")
                    val whatsapp = objectResult.getString("Celular")
                    val email = objectResult.getString("email")
                    val datetime = objectResult.getString("fecha_hora")
                    val datetime_cancel = objectResult.getString("fecha_hora_c")
                    val motivo = objectResult.getString("motivo_c")
                    val detalle = objectResult.getString("detalle")
                    val latitude = objectResult.getString("latitude")
                    val longitude = objectResult.getString("longitude")

                    incidentes.add(Incidente(name, direction, phone, whatsapp, datetime, latitude, longitude, email, detalle, motivo, datetime_cancel.trim()))

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //invisible progresBar
            progressBar.visibility = View.INVISIBLE
            inicializaAdaptador()
        }, Response.ErrorListener { error ->
            // TODO: Handle error
            Log.e("RESPONSE", "" + error.toString())
            //invisible progresBar
            progressBar.visibility = View.INVISIBLE
        })
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest)
    }

    //ejecutamos la lista en el recyclerView
    private fun inicializaAdaptador() {
        mAdapter = AdapterIncidentes(incidentes)
        mRecyclerView.adapter = mAdapter
    }
}