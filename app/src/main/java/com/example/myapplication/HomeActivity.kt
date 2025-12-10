package com.example.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.ProdutoActivity
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val auth = FirebaseAuth.getInstance()

        val produtos = this.findViewById<Button>(R.id.btnProdutos)
        //val pessoas = findViewById<ImageView>(R.id.iconePessoas)
        val logout = this.findViewById<Button>(R.id.btnLogout)

        produtos.setOnClickListener {
            startActivity(Intent(this, ProdutoActivity::class.java))
        }

        //pessoas.setOnClickListener {
        //    startActivity(Intent(this, MesasActivity::class.java))
        //}

        logout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
