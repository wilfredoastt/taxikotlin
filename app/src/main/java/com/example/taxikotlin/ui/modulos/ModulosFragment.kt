package com.example.taxikotlin.ui.modulos

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
import com.example.taxi.ui.modulos.Modulo
import com.example.taxikotlin.R

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class ModulosFragment : Fragment() {
    lateinit var progressBar: ProgressBar

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterModulos
    private lateinit var modulos: MutableList<Modulo>

    private val URL_BASE = "http://www.aderta7demayo.com/backend/modulos_lista.php?Ciudad="
    private val COCHABAMBA = "Cochabamba"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_modulos, container, false)
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
        modulos = ArrayList()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this.context)
        val jsonArrayRequest = JsonArrayRequest(URL_BASE + ciudad, Response.Listener { response ->
            for (i in 0 until response.length()) {
                // Get current JSON object
                var objectResult: JSONObject? = null
                try {
                    objectResult = response.getJSONObject(i)

                    val id:String = objectResult.getString("Modulo_ID")
                    val name:String = objectResult.getString("Nombre")
                    val direction:String = objectResult.getString("Direccion")
                    val phone:String = objectResult.getString("Telefono")
                    val phone_free:String = objectResult.getString("Linea_Gratuita")
                    val whatsapp:String = objectResult.getString("Whatsapp")
                    val city:String = objectResult.getString("Ciudad")
                    val latitude:String = objectResult.getString("Latitude")
                    val longitude:String = objectResult.getString("Longitude")
                    modulos.add(Modulo(id, name, direction, phone.trim(), phone_free.trim(), whatsapp.trim(), city, latitude, longitude))

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
        mAdapter = AdapterModulos(modulos)
        mRecyclerView.adapter = mAdapter
    }

}