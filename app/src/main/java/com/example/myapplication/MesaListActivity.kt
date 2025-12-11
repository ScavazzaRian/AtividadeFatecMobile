package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MesaListActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesa_list)

        val container = findViewById<LinearLayout>(R.id.containerMesas)
        database = FirebaseDatabase.getInstance().getReference("mesas")

        database.get().addOnSuccessListener { snapshot ->
            container.removeAllViews()

            for (item in snapshot.children) {
                val mesa = item.getValue(Mesa::class.java) ?: continue

                val botao = Button(this)
                botao.text = "Mesa ${mesa.numero} - ${mesa.lugares} lugares"

                botao.setOnClickListener {
                    val intent = Intent(this, MesaUpdateActivity::class.java)
                    intent.putExtra("mesa_id", mesa.id)
                    intent.putExtra("mesa_num", mesa.numero)
                    intent.putExtra("mesa_lug", mesa.lugares)
                    startActivity(intent)
                }

                container.addView(botao)
            }
        }
    }
}
