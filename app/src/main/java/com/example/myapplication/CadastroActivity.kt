package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CadastroActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editNome = findViewById<EditText>(R.id.nomeCampo)
        val editEmail = findViewById<EditText>(R.id.emailCampo)
        val editIdade = findViewById<EditText>(R.id.idadeCampo)
        val editCelular = findViewById<EditText>(R.id.celularCampo)
        val editSenha = findViewById<EditText>(R.id.senhaCampo)

        val btnCadastrarCriar = findViewById<Button>(R.id.buttonCadastrarCriar)
        val btnJaTenhoConta = findViewById<Button>(R.id.buttonJaTenhoConta)

        btnCadastrarCriar.setOnClickListener {

            val nome = editNome.text.toString()
            val email = editEmail.text.toString()
            val idade = editIdade.text.toString()
            val celular = editCelular.text.toString()
            val senha = editSenha.text.toString()

            if (nome.isEmpty() || celular.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha os campos obrigatórios!", Toast.LENGTH_SHORT).show()
            } else {

                //Tive erro ao mexer com a database, e ao procurar ele pediu pra passar o link dela
                //Se o professor remover essa databaseUrl, vai conseguir ver no logCat o erro de conexao com o Firebase
                val databaseUrl = "https://crudfatec-default-rtdb.firebaseio.com/"
                databaseReference = FirebaseDatabase.getInstance().getReference("Users")

                val usuario = UserData(nome, email, idade, celular, senha)

                databaseReference.child(celular).setValue(usuario).addOnSuccessListener {

                    editNome.text.clear()
                    editEmail.text.clear()
                    editIdade.text.clear()
                    editCelular.text.clear()
                    editSenha.text.clear()

                    Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }.addOnFailureListener {
                    Toast.makeText(this, "Erro ao cadastrar.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnJaTenhoConta.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}