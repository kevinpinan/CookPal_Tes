package com.example.cookpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Bienvenida : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)
        firebaseAuth = FirebaseAuth.getInstance()
        VerBienvenida()
    }

    //se crea funciones
    fun  VerBienvenida(){
        object: CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
               VerificarSesion()

            }


        }.start()
    }
    //compobar la sesion
     fun VerificarSesion(){
         val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser==null){
            startActivity(Intent(this,Elegir_rol::class.java))
            finishAffinity()

        }else{
            val reference = FirebaseDatabase.getInstance().getReference("usuarios")
            reference.child(firebaseUser.uid)
            //lectura timpo real de la base de datos
                .addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val rol = snapshot.child("rol").value
                        if(rol=="admin"){
                            startActivity(Intent(this@Bienvenida,MainActivity::class.java))
                            finishAffinity()
                        }
                        else if(rol == "cliente"){
                            startActivity(Intent(this@Bienvenida,MainActivityCliente::class.java))
                            finishAffinity()
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        }
     }
}

