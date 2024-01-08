package com.example.cookpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookpal.Fragmentos_Cliente.Fragment_cliente_cuenta
import com.example.cookpal.Fragmentos_Cliente.Fragment_cliente_dashboard
import com.example.cookpal.databinding.ActivityMainClienteBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivityCliente : AppCompatActivity() {
    private lateinit var binding: ActivityMainClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()
        comprobarSesion()
        //cuenta por defecto q se abrira
        verFragmentoDashboard()
        binding.BottomNavClie.setOnItemSelectedListener { item->
            when(item.itemId) {
                R.id.menu_dashboard_cliente->{
                    verFragmentoDashboard()

                    true
                }
                R.id.menu_cuenta_cliente->{
                    verFragmentoCuenta()
                    true

                }else->{
                    false

                }

            }
        }


    }
    private fun comprobarSesion(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this,Elegir_rol::class.java))
            finishAffinity()

        }else{
            Toast.makeText(applicationContext,"Bienvenido ${firebaseUser.email}",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun verFragmentoDashboard(){
        val nombre_titulo = "Dashboard"
        binding.TituloRLClient.text = nombre_titulo
        val fragment = Fragment_cliente_dashboard()
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsCliente.id,fragment,"Fragment dashboard")
        fragmentTransaction.commit()

    }
    private fun verFragmentoCuenta(){
        val nombre_titulo = "Cuenta"
        binding.TituloRLClient.text = nombre_titulo
        val fragment = Fragment_cliente_cuenta()
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsCliente.id,fragment,"Fragment cuenta")
        fragmentTransaction.commit()
    }
}