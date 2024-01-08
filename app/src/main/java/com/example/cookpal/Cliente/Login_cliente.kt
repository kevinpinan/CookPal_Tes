package com.example.cookpal.Cliente

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.cookpal.MainActivityCliente
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityLoginClienteBinding
import com.google.firebase.auth.FirebaseAuth

class Login_cliente : AppCompatActivity() {
    private lateinit var binding : ActivityLoginClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginClienteBinding.inflate(layoutInflater)

        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Espere Por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.BtnLoginCliente.setOnClickListener {
            validarInformacion()
        }
    }
    private var email = ""
    private var password = ""

    private fun validarInformacion() {
        //extraer credenciales
        email=binding.EtextEmailCL.text.toString().trim()
        password=binding.EtextPasswordCL.text.toString().trim()

        //validacion
        if(email.isEmpty()){
            binding.EtextEmailCL.error = "Ingrese un correo"
            binding.EtextEmailCL.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtextEmailCL.error = "Correo no valido"
            binding.EtextEmailCL.requestFocus()
        }else if(password.isEmpty()){
            binding.EtextPasswordCL.error = "Ingrese una clave"
            binding.EtextPasswordCL.requestFocus()
        }else{
            loginCliente()
        }

    }

    private fun loginCliente() {
        progressDialog.setMessage("Iniciando Sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_cliente,MainActivityCliente::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "No se pudo iniciar debia a ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }
}