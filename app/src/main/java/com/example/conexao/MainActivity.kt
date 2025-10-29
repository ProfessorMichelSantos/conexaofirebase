package com.example.conexao

import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private var email : String = "michels@live.com"
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
                    txtcaixa.text = "Gravado"
                } else {
                    txtcaixa.text = "Erro"
                }
            }
    }
}