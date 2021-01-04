package com.example.taxikotlin.ui.taxis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.taxi.ui.taxis.Taxi
import com.example.taxikotlin.R

import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class TaxisFragment : Fragment() {

    //private TaxisViewModel taxisViewModel;
    private lateinit var progressBar: ProgressBar

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: AdapterRecyclerView
    private lateinit var empresas: MutableList<Taxi>

    private val URL_BASE = "http://www.aderta7demayo.com/backend/empresaslista.php?Zona_ID="
    private val TODOS = "%25"
    private val ESTE = "Este"
    private val OESTE = "Oeste"
    private val NORTE = "Norte"
    private val SUD = "Sud"
    private val QUILLACOLLO = "Quillacollo"
    private val SACABA = "Sacaba"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val root = inflater.inflate(R.layout.fragment_taxis, container, false)
        progressBar = root.findViewById(R.id.progressBar)

        mRecyclerView = root.findViewById(R.id.my_recycler_view)
        mRecyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView.layoutManager = layoutManager

        loadEmpresas(TODOS)
        return root
    }

    private fun loadEmpresas(zona: String) {
        progressBar.visibility = View.VISIBLE
        empresas = ArrayList()
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this.context)
        val jsonArrayRequest = JsonArrayRequest(URL_BASE + zona, Response.Listener { response ->
            for (i in 0 until response.length()) {
                // Get current JSON object
                var objectResult: JSONObject? = null
                try {
                    objectResult = response.getJSONObject(i)
                    val logo:String = objectResult!!.getString("Logo")
                    val nombre:String = objectResult.getString("Nombre")
                    val barrios:String = objectResult.getString("Barrios")
                    val direccion:String = objectResult.getString("Direccion")
                    val telefono:String = objectResult.getString("Telefono")
                    val whatsapp:String = objectResult.getString("Whatsapp")
                    empresas.add(Taxi(nombre, logo, barrios, direccion, telefono.trim(), whatsapp.trim(), R.drawable.placeholder_image))

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
        mAdapter = AdapterRecyclerView(empresas)
        mRecyclerView.adapter = mAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_taxis, menu)
        if (menu != null) {
            super.onCreateOptionsMenu(menu, menuInflater)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when (id) {
            R.id.item_todos -> {
                loadEmpresas(TODOS)
                return true
            }
            R.id.item_este -> {
                loadEmpresas(ESTE)
                return true
            }
            R.id.item_oeste -> {
                loadEmpresas(OESTE)
                return true
            }
            R.id.item_norte -> {
                loadEmpresas(NORTE)
                return true
            }
            R.id.item_sud -> {
                loadEmpresas(SUD)
                return true
            }
            R.id.item_quillacollo -> {
                loadEmpresas(QUILLACOLLO)
                return true
            }
            R.id.item_sacaba -> {
                loadEmpresas(SACABA)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}