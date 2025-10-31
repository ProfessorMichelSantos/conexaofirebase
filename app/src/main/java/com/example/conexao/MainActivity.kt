package com.example.conexao

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var email : String = "michels@live.com.br"
    private var password : String = "123@teste"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth

        val botaocriar : Button = findViewById<Button>(R.id.btninsert)
        val botaoesqueci : Button = findViewById<Button>(R.id.btnEsqueci)
        val botaologin : Button = findViewById<Button>(R.id.btnlogin)
        val botaoinserefirebase : Button = findViewById<Button>(R.id.btninserefirebase)
        val botaolerfirebase : Button = findViewById<Button>(R.id.ler)

        botaocriar.setOnClickListener()
        {
            register()
        }

        botaoesqueci.setOnClickListener()
        {
            recover()
        }

        botaologin.setOnClickListener()
        {
            login()
        }

        botaoinserefirebase.setOnClickListener()
        {
            inseredados("Michel","Rua X")

        }
        botaolerfirebase.setOnClickListener()
        {
            ler()
        }
    }


    private fun login()
    {
        val txtcaixa : TextView = findViewById<TextView>(R.id.caixatexto)

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    txtcaixa.text = "Logado"
                } else {
                    txtcaixa.text = "Erro"
                }
            }
    }

    private fun register()
    {
        val txtcaixa : TextView = findViewById<TextView>(R.id.caixatexto)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    txtcaixa.text = "Gravado"
                } else {
                    txtcaixa.text = "Erro"
                }
            }
    }

    private fun recover()
    {
        val txtcaixa : TextView = findViewById<TextView>(R.id.caixatexto)

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    txtcaixa.text = "Enviado"
                } else {
                    txtcaixa.text = "Erro"
                }
            }
    }

    private fun inseredados(nome : String, endereco : String)
    {
        auth = FirebaseAuth.getInstance()
        val usuarioAtual = auth.currentUser

        if (usuarioAtual != null) {
            val uid = usuarioAtual.uid
            val db = FirebaseFirestore.getInstance()

            val usuario = Usuario(
                nome = nome,
                endereco = endereco,
                criadoem = System.currentTimeMillis().toString()
            )

            // Grava os dados no documento com o UID do usuário
            db.collection("usuarios").document(uid).set(usuario)
                .addOnSuccessListener {
                    Log.d("Firebase", "Usuário salvo com sucesso!")
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Erro ao salvar usuário", e)
                }
        } else {
            Log.w("Firebase", "Usuário não está autenticado")
        }

    }

    private fun ler()
    {

        val auth = FirebaseAuth.getInstance()
        val usuarioAtual = auth.currentUser

        if (usuarioAtual != null) {
            val db = FirebaseFirestore.getInstance()

            db.collection("usuarios").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val nome = document.getString("nome")
                        val endereco = document.getString("endereco")
                        val criadoem = document.getString("criadoem")

                        Log.d("Firebase", "Usuário: ${document.id}")
                        Log.d("Firebase", "Nome: $nome")
                        Log.d("Firebase", "Endereço: $endereco")
                        Log.d("Firebase", "Criado em: $criadoem")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firebase", "Erro ao ler usuários", e)
                }
        } else {
            Log.w("Firebase", "Usuário não está autenticado")
        }

    }

}