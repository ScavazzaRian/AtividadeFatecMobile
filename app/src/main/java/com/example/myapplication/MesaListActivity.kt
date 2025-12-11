package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MesaListActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_list)

        val btnProdutos = findViewById<Button>(R.id.btnNavProdutos)
        val btnMesas = findViewById<Button>(R.id.btnNavMesas)
        val btnLogout = findViewById<Button>(R.id.btnNavLogout)
        val btnCriarMesa = findViewById<Button>(R.id.goToCriarMesa)

        btnProdutos.setOnClickListener {
            startActivity(Intent(this, ProdutoActivity::class.java))
        }

        btnMesas.setOnClickListener {
            startActivity(Intent(this, MesaListActivity::class.java))
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnCriarMesa.setOnClickListener {
            startActivity(Intent(this, MesaCreateActivity::class.java))
            finish()
        }

        val container = findViewById<LinearLayout>(R.id.containerMesas)
        database = FirebaseDatabase.getInstance().getReference("mesas")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                container.removeAllViews()

                for (item in snapshot.children) {
                    val mesa = item.getValue(Mesa::class.java) ?: continue

                    val itemLayout = LinearLayout(this@MesaListActivity).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(0, 20, 0, 20)
                    }

                    val titulo = Button(this@MesaListActivity).apply {
                        text = "Mesa ${mesa.numero} - ${mesa.lugares} lugares"
                    }

                    titulo.setOnClickListener {
                        val intent = Intent(this@MesaListActivity, MesaUpdateActivity::class.java)
                        intent.putExtra("mesa_id", mesa.id)
                        intent.putExtra("mesa_num", mesa.numero)
                        intent.putExtra("mesa_lug", mesa.lugares)
                        startActivity(intent)
                    }

                    val botaoExcluir = Button(this@MesaListActivity).apply {
                        text = "Excluir"
                        setBackgroundColor(0xFFFF4444.toInt())
                    }

                    botaoExcluir.setOnClickListener {
                        mesa.id?.let { idMesa ->
                            database.child(idMesa).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@MesaListActivity,
                                        "Mesa ${mesa.numero} removida!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }

                    itemLayout.addView(titulo)
                    itemLayout.addView(botaoExcluir)
                    container.addView(itemLayout)
                }
            }

            override fun onCancelled(error: DatabaseError) { }
        })
    }
}
