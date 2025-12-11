package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MesaCreateActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_create)

        val botaoCriarMesa = findViewById<Button>(R.id.createMesa)
        val editNumeroMesa = findViewById<EditText>(R.id.numeroMesa)
        val editLugaresMesa = findViewById<EditText>(R.id.lugaresMesa)

        database = FirebaseDatabase.getInstance().getReference("mesas")

        botaoCriarMesa.setOnClickListener {

            val numero = editNumeroMesa.text.toString()
            val lugares = editLugaresMesa.text.toString()

            if (numero.isEmpty() || lugares.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = database.push().key!!

            val mesa = Mesa(
                id = id,
                numero = numero,
                lugares = lugares
            )

            database.child(id).setValue(mesa)
                .addOnSuccessListener {
                    Toast.makeText(this, "Mesa cadastrada!", Toast.LENGTH_SHORT).show()
                    editNumeroMesa.text.clear()
                    editLugaresMesa.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
