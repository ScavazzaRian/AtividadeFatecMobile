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

class ProdutoDelete : AppCompatActivity() {

    private lateinit var edtIdDelete: EditText
    private lateinit var btnDelete: Button
    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_produto_delete)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtIdDelete = findViewById<EditText>(R.id.editIdDelete)
        btnDelete = findViewById<Button>(R.id.btnDeleteProduto)

        btnDelete.setOnClickListener {
            val id = edtIdDelete.text.toString()

            if (id.isNotEmpty()) {
                deleteProduto(id)
            } else {
                Toast.makeText(this, "Informe o ID do produto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteProduto(id: String) {

        databaseReference = FirebaseDatabase.getInstance().getReference("Produtos")

        databaseReference.child(id).removeValue().addOnSuccessListener {

            edtIdDelete.text.clear()
            Toast.makeText(this, "Produto removido!", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this, "Erro ao remover!", Toast.LENGTH_SHORT).show()
        }
    }
}
