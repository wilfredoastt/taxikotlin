package com.example.taxikotlin.ui.incidentes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView

import androidx.fragment.app.DialogFragment
import com.example.taxikotlin.R

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ModalFragment : DialogFragment() {

    lateinit var name: String
    lateinit var email: String
    var checked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.name = arguments?.getString("name").toString()
        this.email = arguments?.getString("email").toString()
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Material_Light_Dialog_Alert)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.modal_incidente, container, false)

        //this.name = arguments?.getString("name").toString()
        //this.email = arguments?.getString("email").toString()


        val btnCancel = v.findViewById<Button>(R.id.button_modal_cancel)
        val btnRegister = v.findViewById<Button>(R.id.button_modal_register)
        val editTextDetail = v.findViewById<EditText>(R.id.modal_detail)
        val sw = v.findViewById<Switch>(R.id.switch_modal)
        val tvError = v.findViewById<TextView>(R.id.error)
        tvError.visibility = View.GONE

        btnCancel.setOnClickListener {
            dialog?.cancel()
        }

        btnRegister.setOnClickListener {
            tvError.visibility = View.GONE
            val detail = editTextDetail.text.toString()
            if (detail.isNotEmpty()) {
                val enviado = enviarDatos(name, email, checked, detail, dateTime)
                if (enviado) {
                   dialog?.cancel()
                } else {
                    tvError.visibility = View.VISIBLE
                }
            }
        }

        //Switch cambio de estado del checket
        sw.setOnCheckedChangeListener { buttonView, isChecked -> checked = isChecked }

        return v
    }

    //Funcion para enviar los datos
    private fun enviarDatos(name: String?, email: String?, selected: Boolean, detail: String, date: String): Boolean {

        Log.e("RESULT", "$name $selected $email $detail $date")
        return true
    }
    private val dateTime: String
        get() = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())

    companion object {

        internal fun newInstance(incidente: Incidente): ModalFragment {
            val f = ModalFragment()

            val args = Bundle()

            args.putString("name", incidente.name)
            args.putString("email", incidente.email)
            f.arguments = args

            return f
        }
    }



}
