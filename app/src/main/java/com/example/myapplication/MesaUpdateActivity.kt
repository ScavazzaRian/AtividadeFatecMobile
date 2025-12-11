package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MesaUpdateActivity : AppCompatActivity() {
    private lateinit var edtNum: EditText
    private lateinit var edtLug: EditText
    private lateinit var btnSalvar: Button
    private lateinit var database: DatabaseReference

    private var mesaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_update)

        edtNum = findViewById(R.id.atualizarNumeroMesa)
        edtLug = findViewById(R.id.atualizarLugarMesa)
        btnSalvar = findViewById(R.id.atualizarMesaBotao)

        mesaId = intent.getStringExtra("mesa_id")
        val num = intent.getIntExtra("mesa_num", 0)
        val lug = intent.getIntExtra("mesa_lug", 0)

        edtNum.setText(num.toString())
        edtLug.setText(lug.toString())

        database = FirebaseDatabase.getInstance().getReference("mesas")

        btnSalvar.setOnClickListener {
            val novoNum = edtNum.text.toString()
            val novosLug = edtLug.text.toString()

            val mesaAtualizada = Mesa(
                id = mesaId!!,
                numero = novoNum,
                lugares = novosLug
            )

            database.child(mesaId!!).setValue(mesaAtualizada)
                .addOnSuccessListener {
                    Toast.makeText(this, "Atualizado!", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}
