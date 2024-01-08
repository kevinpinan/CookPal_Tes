package com.example.cookpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookpal.Administrator.RegistrarAdmin
import com.example.cookpal.Cliente.Registro_Cliente
import com.example.cookpal.databinding.ActivityElegirRolBinding

class Elegir_rol : AppCompatActivity() {

    private lateinit var binding: ActivityElegirRolBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityElegirRolBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //eventos para los botones

        binding.BtnRolAdmin.setOnClickListener{
           // Toast.makeText(applicationContext, "Rol Admin",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@Elegir_rol, RegistrarAdmin::class.java))

        }

        binding.BtnRolUser.setOnClickListener{
            Toast.makeText(applicationContext, "Rol Usuario",Toast.LENGTH_SHORT).show()

            startActivity(Intent(this@Elegir_rol, Registro_Cliente::class.java))



        }
    }
}