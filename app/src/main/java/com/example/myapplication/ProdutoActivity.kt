package com.example.crud

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProdutoActivity : AppCompatActivity() {

    private lateinit var botaoUpdate: Button
    private lateinit var botaoCreate: Button
    private lateinit var botaoDelete: Button
    private lateinit var botaoPesquisa: Button

    private lateinit var editPesquisa: EditText
    private lateinit var campo1: TextView
    private lateinit var campo2: TextView
    private lateinit var campo3: TextView

    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_produto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ---------- Referências ----------
        editPesquisa = findViewById(R.id.editPesquisa)
        campo1 = findViewById(R.id.campo1)
        campo2 = findViewById(R.id.campo2)
        campo3 = findViewById(R.id.campo3)

        botaoPesquisa = findViewById(R.id.botaoPesquisa)
        botaoUpdate = findViewById(R.id.botaoUpdate)
        botaoCreate = findViewById(R.id.botaoCreate)
        botaoDelete = findViewById(R.id.botaoDelete)

        // ---------- Botões ----------
        botaoCreate.setOnClickListener {
            startActivity(Intent(this, CreateProduto::class.java))
        }

        botaoUpdate.setOnClickListener {
            startActivity(Intent(this, UpdateProduto::class.java))
        }

        botaoDelete.setOnClickListener {
            startActivity(Intent(this, DeleteProduto::class.java))
        }

        botaoPesquisa.setOnClickListener {
            val id = editPesquisa.text.toString()

            if (id.isNotEmpty()) {
                readData(id)
            } else {
                Toast.makeText(this, "Entre com o código do produto!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun readData(idProduto: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Produtos")

        databaseReference.child(idProduto).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val nome = snapshot.child("nome").value
                val preco = snapshot.child("preco").value
                val descricao = snapshot.child("descricao").value

                Toast.makeText(this, "Produto encontrado!", Toast.LENGTH_SHORT).show()

                campo1.text = "Nome: $nome"
                campo2.text = "Preço: $preco"
                campo3.text = "Descrição: $descricao"

                editPesquisa.text.clear()

            } else {
                Toast.makeText(this, "Produto não encontrado!", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao acessar banco!", Toast.LENGTH_SHORT).show()
        }
    }
}
