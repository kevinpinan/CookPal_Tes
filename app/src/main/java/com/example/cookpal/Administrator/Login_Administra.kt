package com.example.cookpal.Administrator

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.cookpal.MainActivity
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityLoginAdministraBinding
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("NotConstructor")
class Login_Administra : AppCompatActivity() {

    private lateinit var binding: ActivityLoginAdministraBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //
        binding=ActivityLoginAdministraBinding.inflate(layoutInflater)
        //accedemos al identificador del diseno
        setContentView(binding.root)
         firebaseAuth=FirebaseAuth.getInstance()

        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.BtnLoginAdmin.setOnClickListener{
            VerificarInformacion()
        }


    }
    private var email=""
    private var password=""

    private fun VerificarInformacion() {
        email=binding.EtextEmailAdmin.text.toString().trim()
        password=binding.EtextPasswordAdmin.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtextEmailAdmin.error="Correo Invalido"
            binding.EtextEmailAdmin.requestFocus()

        }
        else if (password.isEmpty()){

            binding.EtextPasswordAdmin.error="Ingrese la contraseña"
            binding.EtextPasswordAdmin.requestFocus()
        }
        else{
            LoginAdministra()
        }


    }
    private fun LoginAdministra() {
        progressDialog.setMessage("Iniciando sesion")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_Administra,MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"No se pudo ingresar sesion debido a ${e.message}",
                        Toast.LENGTH_SHORT).show()

            }
    }
}