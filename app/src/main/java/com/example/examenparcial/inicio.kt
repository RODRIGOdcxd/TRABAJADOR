package com.example.examenparcial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.w3c.dom.Text

class inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val intentOrigen = intent
        val ele = arrayOf(findViewById<TextView>(R.id.lblUsuario),findViewById<TextView>(R.id.lblCodUsuario))
        ele[0].text = intentOrigen.getStringExtra("nombre")
        ele[1].text = intentOrigen.getStringExtra("dni")
        val perfil = findViewById<ImageButton>(R.id.btnPerfil)
        val controll = findViewById<ImageButton>(R.id.btnControl)
        val cod = intentOrigen.getStringExtra("cod")
        controll.setOnClickListener(){
            val intentt = Intent(this,control::class.java)
            intentt.putExtra("codigo",cod)
            startActivity(intentt)
        }
        perfil.setOnClickListener(){
            obtenerDatos(intentOrigen.getStringExtra("cod").toString())
        }
        Salir ()
    }
    fun Salir (){
        val btnSalir = findViewById<ImageButton>(R.id.btnSalir)
        btnSalir.setOnClickListener {
            finish()
        }
    }

    fun obtenerDatos(cod:String){
        val nuevoRequest = Volley.newRequestQueue(applicationContext)
        val URL = "http://10.0.2.2:8080/serviciosWeb/datostrab.php"
        val StringRequest = object : StringRequest(
            Request.Method.POST,URL, Response.Listener<String> { response ->
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val intent = Intent(this,frmperfil::class.java)
                    val codTrab = jsonObject.getString("codTrabajador")
                    val nombreTrab = jsonObject.getString("nombreTrabajador")
                    val dniTrab = jsonObject.getString("DNI")
                    val entradaTrab = jsonObject.getString("horaEntrada")
                    val salidaTrab = jsonObject.getString("HoraSalida")
                    intent.putExtra("cod",codTrab)
                    intent.putExtra("nombre",nombreTrab)
                    intent.putExtra("dni",dniTrab)
                    intent.putExtra("entrada",entradaTrab)
                    intent.putExtra("salida",salidaTrab)
                    startActivity(intent)
                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, "Error en la carga", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["codigoTrab"] = cod
                return params
            }
        }
        Volley.newRequestQueue(this).add(StringRequest)
    }
}