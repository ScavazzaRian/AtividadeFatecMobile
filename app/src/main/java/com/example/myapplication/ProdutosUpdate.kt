package com.example.myapplication

import android.annotation.SuppressLint
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

class ProdutosUpdate : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtNome: EditText
    private lateinit var edtPreco: EditText
    private lateinit var edtDescricao: EditText
    private lateinit var btnUpdate: Button

    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_produtos_update)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtId = findViewById<EditText>(R.id.editIdProdutoUpdate)
        edtNome = findViewById<EditText>(R.id.editNomeProdutoUpdate)
        edtPreco = findViewById<EditText>(R.id.editPrecoProdutoUpdate)
        edtDescricao = findViewById<EditText>(R.id.editDescricaoProdutoUpdate)
        btnUpdate = findViewById<Button>(R.id.btnUpdateProduto)


        btnUpdate.setOnClickListener {
            val id = edtId.text.toString()
            val nome = edtNome.text.toString()
            val preco = edtPreco.text.toString()
            val descricao = edtDescricao.text.toString()

            updateProduto(id, nome, preco, descricao)
        }
    }

    private fun updateProduto(id: String, nome: String, preco: String, descricao: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Produtos")

        val produtoAtualizado = mapOf(
            "nome" to nome,
            "preco" to preco,
            "descricao" to descricao
        )

        databaseReference.child(id).updateChildren(produtoAtualizado).addOnSuccessListener {

            edtId.text.clear()
            edtNome.text.clear()
            edtPreco.text.clear()
            edtDescricao.text.clear()

            Toast.makeText(this, "Produto atualizado!", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao atualizar!", Toast.LENGTH_SHORT).show()
        }
    }
}
