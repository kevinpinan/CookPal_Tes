package com.example.cookpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookpal.Fragmentos_Admin.Fragment_admin_cuenta
import com.example.cookpal.Fragmentos_Admin.Fragment_admin_dashboard
import com.example.cookpal.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    //mantener la sesion activa
    private lateinit var firebaseAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //crea instancia para mantener activo
        firebaseAuth= FirebaseAuth.getInstance()
        //llamo ala funcion antes que elfragmento se abra
        VerificarSesion()
        VerFragmentoDashboard()

        binding.BottomNvAdmin.setOnItemSelectedListener { item ->
            when( item.itemId ) {
                R.id.Menu_panel->{
                    VerFragmentoDashboard()
                    true
                }
                R.id.Menu_cuenta->{
                    VerFragmentoCuenta()
                    true
                }
                else ->{
                    false
                }

            }
        }
    }
    //se crea 2 funciones
    private fun VerFragmentoDashboard(){
        val nombre_titulo="Dashboard"
        binding.TituloRlAdmin.text= nombre_titulo

        //visualizamos el fragmento dentro del frame layout
        //dependiendo de lo que se seleccione el usuario ,se ira mostrando dentro del frame layout
        val fragment=Fragment_admin_dashboard()
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id,fragment,"Fragment_dashboard")
        fragmentTransaction.commit()

    }
    private fun VerFragmentoCuenta(){
        val nombre_titulo="Cuenta"
        binding.TituloRlAdmin.text= nombre_titulo

        val fragment=Fragment_admin_cuenta()
        val fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.FragmentsAdmin.id,fragment,"Fragment_cuenta")
        fragmentTransaction.commit()
    }

    //funcion para mantener abierto
    private fun VerificarSesion(){
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser == null){
            startActivity(Intent(this,Elegir_rol::class.java))
            finishAffinity()
        }else{
            Toast.makeText(applicationContext,"Bienvenido(a)${firebaseUser.email}",
            Toast.LENGTH_SHORT).show()
        }
    }
}