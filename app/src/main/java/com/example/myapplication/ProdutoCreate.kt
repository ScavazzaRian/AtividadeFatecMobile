package com.example.crud

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProdutoCreate : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSalvar = findViewById<Button>(R.id.btnSalvarProduto)
        val edtNome = findViewById<EditText>(R.id.edtNomeProduto)
        val edtPreco = findViewById<EditText>(R.id.edtPrecoProduto)
        val edtDescricao = findViewById<EditText>(R.id.edtDescricaoProduto)

        btnSalvar.setOnClickListener {
            val nome = edtNome.text.toString()
            val preco = edtPreco.text.toString()
            val descricao = edtDescricao.text.toString()

            databaseReference = FirebaseDatabase.getInstance().getReference("Produtos")
            val produtoId = databaseReference.push().key!!

            val produto = ProdutoData(produtoId, nome, preco, descricao)

            databaseReference.child(produtoId).setValue(produto).addOnSuccessListener {

                edtNome.text.clear()
                edtPreco.text.clear()
                edtDescricao.text.clear()

                Toast.makeText(this, "Produto salvo!", Toast.LENGTH_SHORT).show()

                finish()

            }.addOnFailureListener {
                Toast.makeText(this, "Erro ao salvar!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
