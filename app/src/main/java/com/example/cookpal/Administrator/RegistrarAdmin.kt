package com.example.cookpal.Administrator

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns

import android.widget.Toast
import com.example.cookpal.MainActivity

import com.example.cookpal.databinding.ActivityRegistrarAdminBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrarAdmin : AppCompatActivity() {

    private lateinit var binding : ActivityRegistrarAdminBinding

    //llamo a firebase

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityRegistrarAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth=FirebaseAuth.getInstance()
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)


        //evento para el boton de reregresar

        binding.IbVolver.setOnClickListener{onBackPressedDispatcher.onBackPressed()

        }

        binding.BtnRegiAdmin.setOnClickListener{
            validarInformation()
        }
        //boton tengo cuenta
        binding.TxtCuentaExistente.setOnClickListener{
            startActivity(Intent(this@RegistrarAdmin,Login_Administra::class.java))
        }

    }
    //validar campos que ingresan
    var nombres=""
    var email=""
    var password=""
    var r_password=""

//cambios prubea
    private fun validarInformation() {
        nombres=binding.EtextNombresAdmin.text.toString().trim()
        email=binding.EtextEmailAdmin.text.toString().trim()
        password=binding.EtextPasswordAdmin.text.toString().trim()
        r_password=binding.EtextRepPasswordAdmin.text.toString().trim()

        if(nombres.isEmpty()) {
            binding.EtextNombresAdmin.error = "Por favor ingrese los nombres"

            //requestfocus par aque el cursor no se mueva hasta llenar el campo
            binding.EtextNombresAdmin.requestFocus()
        }
        else if(email.isEmpty()){
            binding.EtextEmailAdmin.error="Por favor ingrese el email"
            binding.EtextEmailAdmin.requestFocus()}

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.EtextEmailAdmin.error = "Por favor ingrese el email"
            binding.EtextEmailAdmin.requestFocus()
        }
        else if (password.isEmpty()) {
            binding.EtextPasswordAdmin.error = "Por favor ingrese el password"
            binding.EtextPasswordAdmin.requestFocus()
        }
        else if (password.length<4) {
            binding.EtextPasswordAdmin.error = "La contraseña debe tener mayor mas de 4 caracteres"
            binding.EtextPasswordAdmin.requestFocus()
        }
        else if (r_password.isEmpty()) {
            binding.EtextPasswordAdmin.error = "Repita la contraseña"
            binding.EtextPasswordAdmin.requestFocus()
        }
        else if(password!=r_password) {
            binding.EtextRepPasswordAdmin.error = "La contraseña no son iguales"
            binding.EtextRepPasswordAdmin.requestFocus()
        }
        else{
            crearCuentaAdministrador(email,password)

        }

        }

    private fun crearCuentaAdministrador(email: String, password: String) {
        progressDialog.setMessage("Creando Cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener{
                agregarInfoBaseDatos()

            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"Hubo un error al crear la cuenta debido a ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }

    }

    private fun agregarInfoBaseDatos() {
        progressDialog.setMessage("Guardando Datos....")
        //obtener tiempo del sistema
        val tiempo = System.currentTimeMillis()
        //regsitramos el id del usuario en la BD

        val uid=firebaseAuth.uid
        val datosAdmin: HashMap<String, Any?> =HashMap()
        datosAdmin["uid"]= uid
        datosAdmin["nombres"]=nombres
        datosAdmin["email"]=email
        datosAdmin["rol"]="Administrator"
        datosAdmin["tiempo_reg"]=tiempo
        datosAdmin["imagen"]=""

        val reference=FirebaseDatabase.getInstance().getReference("usuarios")
        reference.child(uid!!)
            .setValue(datosAdmin)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this,MainActivity::class.java))
                finishAffinity()

            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext,"No se pudo guardar debia a  ${e.message}", Toast.LENGTH_SHORT)
                    .show()


            }


    }
}
