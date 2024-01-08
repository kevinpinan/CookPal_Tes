package com.example.cookpal.Cliente

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.cookpal.MainActivityCliente
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityRegistroClienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registro_Cliente : AppCompatActivity() {

    private lateinit var binding:ActivityRegistroClienteBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroClienteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Espere porfavor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.IbVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.BtnRegiCliente.setOnClickListener {
            validarInformacion()

        }
        binding.TxtCuentaExistente.setOnClickListener {
            startActivity(Intent(this@Registro_Cliente,Login_cliente::class.java))
        }


    }

    var nombres=""
    var email=""
    var password=""
    var r_password=""

    private fun validarInformacion() {

        nombres=binding.EtextNombresCL.text.toString().trim()
        email=binding.EtextEmailCL.text.toString().trim()
        password=binding.EtextPasswordCL.text.toString().trim()
        r_password=binding.EtextRepetirPasswordCL.text.toString().trim()

        if(nombres.isEmpty()) {
            binding.EtextNombresCL.error = "Ingrese un nombre"
            binding.EtextNombresCL.requestFocus()
        }
        else if (email.isEmpty()){
            binding.EtextEmailCL.error = "Ingrese un correo"
            binding.EtextEmailCL.requestFocus()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.EtextEmailCL.error="Correo no valido"
            binding.EtextEmailCL.requestFocus()
        }
        else if (password.isEmpty()){
            binding.EtextPasswordCL.error="Ingrese una clave"
            binding.EtextPasswordCL.requestFocus()
        }
        else if(password.length<5){
            binding.EtextPasswordCL.error="Debe tener mas de 5 caracteres"
            binding.EtextPasswordCL.requestFocus()
        }
        else if (r_password.isEmpty()){
            binding.EtextRepetirPasswordCL.error="Confirme su clave"
            binding.EtextRepetirPasswordCL.requestFocus()
        }
        else if(password!=r_password){
            binding.EtextPasswordCL.error="El password no coinciden"
            binding.EtextPasswordCL.requestFocus()
        }
        else{
            crearCuentaCliente(email,password)
        }

    }

    private fun crearCuentaCliente(email: String, password: String) {
        progressDialog.setMessage("Creando Cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                agregarInforBD()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"fallo el registro del cliente debido a ${e.message}", Toast.LENGTH_LONG).show()

            }

    }

    private fun agregarInforBD() {
        progressDialog.setMessage("Guardando Informacion...")
        val tiempo=System.currentTimeMillis()
        val uid=firebaseAuth.uid!!
        val datos_cliente: HashMap<String, Any> = HashMap()

        datos_cliente["uid"] = uid
        datos_cliente["nombre"] = nombres
        datos_cliente["email"] = email
        datos_cliente["rol"] = "cliente"
        datos_cliente["tiempo_registro"] = tiempo
        datos_cliente["imagen"] = ""

        //referencia con firebase
        val reference = FirebaseDatabase.getInstance().getReference("usuarios")
            reference.child(uid)
                .setValue(datos_cliente)
                .addOnSuccessListener {
                    Toast.makeText(applicationContext,"se creo su cuenta", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MainActivityCliente::class.java))
                    finishAffinity()

                }
                .addOnFailureListener {e->
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext,"fallo el registro del cliente debido a ${e.message}", Toast.LENGTH_LONG).show()
                }

    }
}