package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.ProdutoAdapter
import com.example.myapplication.databinding.ActivityProdutosBinding
import com.example.myapplication.model.Produto
import com.google.firebase.database.*

class ProdutosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProdutosBinding
    private lateinit var db: DatabaseReference
    private val listaProdutos = ArrayList<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseDatabase.getInstance().getReference("produtos")

        binding.recyclerProdutos.layoutManager = LinearLayoutManager(this)

        binding.btnAddProduto.setOnClickListener {
            startActivity(Intent(this, ProdutoFormActivity::class.java))
        }

        carregarProdutos()
    }

    private fun carregarProdutos() {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaProdutos.clear()
                for (dados in snapshot.children) {
                    val produto = dados.getValue(Produto::class.java)
                    if (produto != null) listaProdutos.add(produto)
                }

                val adapter = ProdutoAdapter(
                    listaProdutos,
                    onClick = { produto ->
                        val intent = Intent(this@ProdutosActivity, ProdutoFormActivity::class.java)
                        intent.putExtra("produtoId", produto.id)
                        startActivity(intent)
                    },
                    onDelete = { produto ->
                        db.child(produto.id!!).removeValue()
                    }
                )

                binding.recyclerProdutos.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}
