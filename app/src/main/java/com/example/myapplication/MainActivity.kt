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
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Aqui verifica o login de usuario
        val editEmail = findViewById<EditText>(R.id.campoEmail)
        val editSenha = findViewById<EditText>(R.id.campoSenha)
        val btnLogar = findViewById<Button>(R.id.buttonLogar)
        val btnCadastrar = findViewById<Button>(R.id.buttonCadastrar)

        btnLogar.setOnClickListener {
            val email = editEmail.text.toString()
            val senha = editSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha email e senha!", Toast.LENGTH_SHORT).show()
            } else {
                val databaseUrl = "https://crudfatec-default-rtdb.firebaseio.com/"
                val databaseReference = FirebaseDatabase.getInstance().getReference("Users")

                //Esse foi o jeito mais facil que eu achei de procurar um usuario na base de dados
                //O snapshot pega a tabela e devolve pra gente
                //O snapshot.children pega todos os registros da tabela e percorremos eles com um for
                databaseReference.get().addOnSuccessListener { snapshot ->
                    var encontrou = false

                    for (filho in snapshot.children) {
                        val usuario = filho.getValue(UserData::class.java)

                        if (usuario?.email == email && usuario.senha == senha) {
                            encontrou = true

                            Toast.makeText(this, "Bem-vindo!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, MesaListActivity::class.java)
                            startActivity(intent)
                            finish()
                            break
                        }
                    }

                    if (!encontrou) {
                        Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener {
                    Toast.makeText(this, "Erro de conexão", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Aqui redireciona pro cadastrar
        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}