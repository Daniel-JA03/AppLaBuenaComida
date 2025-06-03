package com.example.exament1danieljaimes

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class ImprimirActivity : AppCompatActivity() {
    // declara
    private lateinit var tvClienteC:TextView
    private lateinit var tvDniC:TextView
    private lateinit var tvPlatoC:TextView
    private lateinit var tvPrecioMenuC:TextView
    private lateinit var tvBolsaC:TextView
    private lateinit var tvDeliveryC:TextView
    private lateinit var tvTotalAdicionalC:TextView
    private lateinit var tvTotalPagarC:TextView
    private lateinit var imgFotoC:ImageView
    private lateinit var btnVolver:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.imprimir_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvClienteC = findViewById(R.id.tvClienteC)
        tvDniC = findViewById(R.id.tvDniC)
        tvPlatoC = findViewById(R.id.tvPlatoC)
        tvPrecioMenuC = findViewById(R.id.tvPrecioMenuC)
        tvBolsaC = findViewById(R.id.tvBolsaC)
        tvDeliveryC = findViewById(R.id.tvDeliveryC)
        tvTotalAdicionalC = findViewById(R.id.tvTotalAdicionalC)
        tvTotalPagarC = findViewById(R.id.tvTotalPagarC)
        imgFotoC = findViewById(R.id.imgFotoC)
        btnVolver = findViewById(R.id.btnVolver)

        cargar()
        btnVolver.setOnClickListener {
            var navegar = Intent(this, MainActivity::class.java)
            startActivity(navegar)
            finish()
        }
    }

    fun cargar() {
        var data = intent.extras!!
        var posP = data.getInt("posicionPlato")
        var ID:Int

        // mostrar claves
        tvClienteC.setText("CLIENTE : " + data.getString("cliente"))
        tvDniC.setText("DNI : " + data.getString("dni"))
        tvPlatoC.setText("PLATO : " + data.getString("plato"))
        tvPrecioMenuC.setText("PRECIO MENU : " + data.getString("precioMenu"))
        tvBolsaC.setText("BOLSA : S/. " + data.getString("bolsa"))
        tvDeliveryC.setText("DELIVERY : S/. " + data.getString("delivery"))
        tvTotalAdicionalC.setText("TOTAL ADICIONAL :  " + data.getString("totalAdicional"))
        tvTotalPagarC.setText("TOTAL PAGAR : " + data.getString("totalPagar"))

        ID = this.resources.getIdentifier("p"+posP, "drawable", this.packageName)
        imgFotoC.setImageResource(ID)
    }
}