package com.example.examenparcial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.*
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn = findViewById<Button>(R.id.btnacceder)
        btn.setOnClickListener(){
            val DatosEl = arrayOf(findViewById<TextView>(R.id.txtcod).text.toString(),findViewById<TextView>(R.id.txtdni).text.toString())
            if (DatosEl[0] != "" || DatosEl[1] != ""){
                login(DatosEl[0],DatosEl[1])
            }else{
                Toast.makeText(this,"Llena Todos los Datos", Toast.LENGTH_SHORT).show()
            }
            val lim1 = findViewById<TextView>(R.id.txtcod)
            val lim2 = findViewById<TextView>(R.id.txtdni)
            lim1.text = ""
            lim2.text = ""
        }
    }

    fun login(cod:String,dni:String){
        //URL EL CUAL SIRVE PARA LOGIN
        val URL = "http://10.0.2.2:8080/serviciosWeb/login.php"
        //CADE DE SOLICITUD EL CUAL SERVIRA PARA RECEPCIONAR LOS DATOS DEL JSON
        val StringRequest = object : StringRequest(
            Request.Method.POST,URL,Response.Listener<String> {response ->
                val jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val intent = Intent(this,inicio::class.java)
                    val nomTrabajador = jsonObject.getString("nombreTrabajador")
                    val DNI = jsonObject.getString("DNI")
                    intent.putExtra("nombre",nomTrabajador)
                    intent.putExtra("dni",DNI)
                    intent.putExtra("cod",cod)
                    startActivity(intent)
                }
            },Response.ErrorListener { error ->
                Toast.makeText(this, "Error en la carga", Toast.LENGTH_SHORT).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String,String>()
                params["codigoTrab"] = cod
                params["dniTrab"] = dni
                return params
            }
        }
        Volley.newRequestQueue(this).add(StringRequest)
    }
}