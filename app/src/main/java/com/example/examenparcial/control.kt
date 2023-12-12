package com.example.examenparcial

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import java.time.LocalTime
import java.time.*
class control : AppCompatActivity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        ObtenerHoraTimePicker()
        Salir()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun ObtenerHoraTimePicker(){
        val vlbtnRegistrarEntrada = findViewById<Button>(R.id.btnMarcar)
        val vlTmControl = findViewById<TimePicker>(R.id.TmControl)
        val vllblHoraEntrada = findViewById<TextView>(R.id.lblHoraEntrada)
        val vllblHoraSalida = findViewById<TextView>(R.id.lblHoraSalida)
        vlTmControl.setOnTimeChangedListener{_,hourofDay, minute-> val vlTiempo = LocalTime.of(hourofDay, minute)
            vlbtnRegistrarEntrada.setOnClickListener() {
                vllblHoraEntrada.text = vlTiempo.toString()
                vlbtnRegistrarEntrada.setText("Registrar Salida")
            }
            if (vllblHoraEntrada.text != ""){
                vlbtnRegistrarEntrada.setOnClickListener() {
                    vllblHoraSalida.text = vlTiempo.toString()
                    val codigo = intent.getStringExtra("codigo").toString()
                    obtenerDatos(codigo,vllblHoraEntrada.text.toString(),vllblHoraSalida.text.toString())
                }
            }
        }
    }
    fun Salir (){
        val btnSalir = findViewById<Button>(R.id.btnSalirMarca)
        btnSalir.setOnClickListener {
            finish()
        }
    }

    fun obtenerDatos(cod:String, entrada:String, salida:String){
        //val nuevoRequest = Volley.newRequestQueue(applicationContext)
        val URL = "http://10.0.2.2:8080/serviciosWeb/cambiohorario.php"
        val StringRequest = object : StringRequest(
            Request.Method.POST,URL, Response.Listener<String> { response ->
                Toast.makeText(this, "HORARIO ACTUALIZADO", Toast.LENGTH_SHORT).show()
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error en la carga", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["cod"] = cod
                params["entrada"] = entrada
                params["salida"] = salida
                return params
            }
        }
        Volley.newRequestQueue(this).add(StringRequest)
    }
}