package com.example.taxikotlin.ui.emergencias

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
import com.example.taxikotlin.ui.emergencias.Emergencia
import com.example.taxikotlin.ui.taxis.AdapterEmergencias

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class EmergenciasFragment : Fragment() {
    private lateinit var progressBar: ProgressBar

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterEmergencias
    private lateinit var emergencias: MutableList<Emergencia>

    private val URL_BASE = "http://www.aderta7demayo.com/backend/emergencias_lista.php?Ciudad="
    private val COCHABAMBA = "Cochabamba"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_emergencias, container, false)
        progressBar = root.findViewById(R.id.progressBar)

        mRecyclerView = root.findViewById(R.id.my_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager

        loadEmergencias(COCHABAMBA)
        return root
    }

    private fun loadEmergencias(ciudad: String) {
        progressBar.visibility = View.VISIBLE
        emergencias = ArrayList()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this.context)
        val jsonArrayRequest = JsonArrayRequest(URL_BASE + ciudad, Response.Listener { response ->
            for (i in 0 until response.length()) {
                // Get current JSON object
                var objectResult: JSONObject
                try {
                    objectResult = response.getJSONObject(i)

                    var id:String = objectResult.getString("Telefono_ID")
                    var name: String = objectResult.getString("Nombre")
                    var direction: String = objectResult.getString("Direccion")
                    var phone:String = objectResult.getString("Telefono")
                    var phone_free:String = objectResult.getString("Linea_Gratuita")
                    var whatsapp:String = objectResult.getString("Whatsapp")
                    var city:String = objectResult.getString("Ciudad")
                    var latitude:String = objectResult.getString("Latitude")
                    var longitude:String = objectResult.getString("Longitude")
                    emergencias.add(Emergencia(id, name, direction, phone.trim(), phone_free.trim(), whatsapp.trim(), city, latitude, longitude))

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            //invisible progresBar
            progressBar.visibility = View.INVISIBLE
            inicializaAdaptador()
        }, Response.ErrorListener { error ->
            // TODO: Handle error
            //invisible progresBar
            progressBar.visibility = View.INVISIBLE
        })
        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest)
    }

    //ejecutamos la lista en el recyclerView
    private fun inicializaAdaptador() {
        mAdapter = AdapterEmergencias(emergencias)
        mRecyclerView.adapter = mAdapter
    }

}