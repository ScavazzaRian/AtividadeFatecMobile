package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MesaUpdateActivity : AppCompatActivity() {
    private lateinit var editNumeroMesa: EditText
    private lateinit var editLugaresMesa: EditText
    private lateinit var botaoAtualizar: Button
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_update)
        editNumeroMesa = findViewById(R.id.atualizarNumeroMesa)
        editLugaresMesa = findViewById(R.id.atualizarLugarMesa)
        botaoAtualizar = findViewById(R.id.atualizarMesaBotao)
        val mesaId = intent.getStringExtra("mesa_id")
        val mesaNumero = intent.getIntExtra("mesa_num", 0)
        val mesaLugares = intent.getIntExtra("mesa_lug", 0)

        if (mesaId == null) {
            Toast.makeText(this, "Erro: ID não recebido!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Preenche os campos
        editNumeroMesa.setText(mesaNumero.toString())
        editLugaresMesa.setText(mesaLugares.toString())

        botaoAtualizar.setOnClickListener {

            val novoNumero = editNumeroMesa.text.toString().toInt()
            val novosLugares = editLugaresMesa.text.toString().toInt()

            atualizarMesa(mesaId, novoNumero, novosLugares)
        }

    }

    private fun atualizarMesa(id: String, numero: Int, lugares: Int) {

        databaseReference = FirebaseDatabase.getInstance().getReference("mesas")

        val atualizacao = mapOf(
            "numero" to numero,
            "lugares" to lugares,
            "id" to id
        )

        databaseReference.child(id).updateChildren(atualizacao)
            .addOnSuccessListener {
                Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show()
            }
    }
}
