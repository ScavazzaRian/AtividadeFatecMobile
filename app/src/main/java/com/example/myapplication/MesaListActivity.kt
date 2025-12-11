package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MesaListActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var textMesas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_list)

        textMesas = findViewById<TextView>(R.id.listaMesas)
        database = FirebaseDatabase.getInstance().getReference("mesas")

        listarMesas()
    }

    private fun listarMesas() {
        val container = findViewById<LinearLayout>(R.id.containerMesas)

        database.get().addOnSuccessListener { snapshot ->
            container.removeAllViews()

            for (item in snapshot.children) {
                val mesa = item.getValue(Mesa::class.java) ?: continue
                val idReal = item.key ?: continue

                // define o id vindo do firebase
                mesa.id = idReal

                val botao = Button(this)
                "Mesa ${mesa.numero} - ${mesa.lugares} lugares".also { botao.text = it }

                botao.setOnClickListener {
                    val intent = Intent(this, MesaUpdateActivity::class.java)
                    intent.putExtra("mesa_id", idReal)
                    intent.putExtra("mesa_num", mesa.numero)
                    intent.putExtra("mesa_lug", mesa.lugares)
                    startActivity(intent)
                }

                container.addView(botao)
            }
        }
    }
}
