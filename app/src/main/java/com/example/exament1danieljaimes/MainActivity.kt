package com.example.exament1danieljaimes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    // declarar
    private lateinit var txtCliente:TextInputEditText
    private lateinit var txtDni:TextInputEditText
    private lateinit var atvPlatos:AutoCompleteTextView
    private lateinit var chkdelivery:CheckBox
    private lateinit var chkbolsa:CheckBox

    private lateinit var btnCompra:Button

    private lateinit var tvMenuC:TextView
    private lateinit var tvBolsaC:TextView
    private lateinit var tvDeliveryC:TextView
    private lateinit var tvAdicionalC:TextView
    private lateinit var tvTotal:TextView

    private lateinit var btnImprimir:Button

    var posPlato = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtCliente = findViewById(R.id.txtCliente)
        txtDni = findViewById(R.id.txtDni)
        atvPlatos = findViewById(R.id.atvPlatos)
        chkdelivery = findViewById(R.id.chkdelivery)
        chkbolsa = findViewById(R.id.chkbolsa)

        btnCompra = findViewById(R.id.btnCompra)

        tvMenuC = findViewById(R.id.tvMenuC)
        tvBolsaC = findViewById(R.id.tvBolsaC)
        tvDeliveryC = findViewById(R.id.tvDeliveryC)
        tvAdicionalC = findViewById(R.id.tvAdicionalC)
        tvTotal = findViewById(R.id.tvTotal)

        btnImprimir = findViewById(R.id.btnImprimir)
        btnImprimir.isEnabled = false // Desactivar el boton al inicio

        atvPlatos.setOnItemClickListener(this)

        // Configuración del AutoCompleteTextView para los platos
        var listaPlatos = arrayOf("Ceviche", "Arroz con Pollo", "Lomo Saltado")
        var adaptador = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPlatos)
        atvPlatos.setAdapter(adaptador)

        // Configurar boton Grabar
        btnCompra.setOnClickListener {
            // validacion = Se debe completar todos los campos
            if(txtCliente.text.isNullOrEmpty() || txtDni.text.isNullOrEmpty() || atvPlatos.text.isNullOrEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Faltan datos")
                    .setMessage("Debe ingresar Cliente, DNI y seleccionar un Plato.")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                mostrarModal()
            }
        }

        btnImprimir.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("Éxito")
                .setMessage("El pedido fue registrado correctamente.")
                .setIcon(R.drawable.succes)
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()

                    val bolsaMonto = if(chkbolsa.isChecked) "2" else "0"
                    val deliveryMonto = if(chkdelivery.isChecked) "5" else "0"
                    var navegar = Intent(this, ImprimirActivity::class.java)
                    navegar.putExtra("cliente", txtCliente.text.toString())
                    navegar.putExtra("dni", txtDni.text.toString())
                    navegar.putExtra("plato", atvPlatos.text.toString())
                    navegar.putExtra("posicionPlato", posPlato)
                    navegar.putExtra("precioMenu", tvMenuC.text.toString())
                    navegar.putExtra("bolsa", bolsaMonto)
                    navegar.putExtra("delivery", deliveryMonto)
                    navegar.putExtra("totalAdicional", tvAdicionalC.text.toString())
                    navegar.putExtra("totalPagar", tvTotal.text.toString())
                    startActivity(navegar)
                    finish()
                }
                .show()



        }
    }

    private fun mostrarModal() {
        val cliente = txtCliente.text.toString().trim()
        val dni = txtDni.text.toString().trim()
        val plato = atvPlatos.text.toString().trim().ifEmpty { "No especificado" }
        val gastosAdicionales = buildString {
            if (chkdelivery.isChecked) append("Delivery: S/. 5\n")
            if (chkbolsa.isChecked) append("Bolsa: S/. 2")
        }.ifEmpty { "No se escogieron" }

        val mensaje = """
            === DATOS ENVIADO EXITOSAMENTE ===
            
            Cliente: $cliente
            DNI: $dni
            Plato escogido: $plato
            
            Gasto Adicional:
            $gastosAdicionales
        """.trimIndent()

        // Mostrar el modal con los datos
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirmación de datos")
            .setMessage(mensaje)
            .setPositiveButton("Confirmar") { dialog, _ ->
                dialog.dismiss()
                obtenerDatos(posPlato)
            }
            .setNegativeButton("Editar", null)
            .show()
    }

    private fun obtenerDatos(posPlato: Int){

        var preMenu = 0
        var dely = if(chkdelivery.isChecked) 5 else 0
        var bolsa = if(chkbolsa.isChecked) 2 else 0
        var totalAdici: Int
        var total: Int

        when (atvPlatos.text.toString()) {
            "Ceviche" -> preMenu = 18
            "Arroz con Pollo" -> preMenu = 17
            "Lomo Saltado" -> preMenu = 20
        }

        totalAdici = dely + bolsa
        total = preMenu + totalAdici

        tvMenuC.text = "S/. $preMenu"
        tvDeliveryC.text = "S/. $dely"
        tvBolsaC.text = "S/. $bolsa"
        tvAdicionalC.text = "S/. $totalAdici"
        tvTotal.text = "TOTAL : S/. $total"

        btnImprimir.isEnabled = true
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        posPlato = position
    }
}