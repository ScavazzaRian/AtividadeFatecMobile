package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crud.ProdutoActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MesaListActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_list)

        val btnProdutos = findViewById<Button>(R.id.btnNavProdutos)
        val btnMesas = findViewById<Button>(R.id.btnNavMesas)
        val btnLogout = findViewById<Button>(R.id.btnNavLogout)

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

        val container = findViewById<LinearLayout>(R.id.containerMesas)
        database = FirebaseDatabase.getInstance().getReference("mesas")

        database.get().addOnSuccessListener { snapshot ->
            container.removeAllViews()

            for (item in snapshot.children) {
                val mesa = item.getValue(Mesa::class.java) ?: continue

                val itemLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(0, 20, 0, 20)
                }

                val titulo = Button(this)
                titulo.text = "Mesa ${mesa.numero} - ${mesa.lugares} lugares"

                titulo.setOnClickListener {
                    val intent = Intent(this, MesaUpdateActivity::class.java)
                    intent.putExtra("mesa_id", mesa.id)
                    intent.putExtra("mesa_num", mesa.numero)
                    intent.putExtra("mesa_lug", mesa.lugares)
                    startActivity(intent)
                }

                val botaoExcluir = Button(this).apply {
                    text = "Excluir"
                    setBackgroundColor(0xFFFF4444.toInt())
                }

                botaoExcluir.setOnClickListener {
                    mesa.id?.let { idMesa ->
                        database.child(idMesa).removeValue()
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "Mesa ${mesa.numero} removida!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                recreate()
                            }
                    }
                }

                itemLayout.addView(titulo)
                itemLayout.addView(botaoExcluir)
                container.addView(itemLayout)
            }
        }
    }
}
