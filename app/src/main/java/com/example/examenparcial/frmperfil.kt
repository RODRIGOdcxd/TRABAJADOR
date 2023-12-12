package com.example.examenparcial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class frmperfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frmperfil)
        val ele = arrayOf(findViewById<TextView>(R.id.lblCodigo),
            findViewById<TextView>(R.id.lblNombre),
            findViewById<TextView>(R.id.lblDNI),
            findViewById<TextView>(R.id.lblEntrada),
            findViewById<TextView>(R.id.lblSalida))
        ele[0].text = intent.getStringExtra("cod")
        ele[1].text = intent.getStringExtra("nombre")
        ele[2].text = intent.getStringExtra("dni")
        ele[3].text = intent.getStringExtra("entrada")
        ele[4].text = intent.getStringExtra("salida")
    }
}