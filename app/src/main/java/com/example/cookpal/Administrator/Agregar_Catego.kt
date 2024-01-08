package com.example.cookpal.Administrator

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.cookpal.MainActivity
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityAgregarCategoBinding
import com.example.cookpal.databinding.ActivityAgregarRecetasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Agregar_Catego : AppCompatActivity() {

    private lateinit var  binding: ActivityAgregarCategoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var proggressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAgregarCategoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth=FirebaseAuth.getInstance()

        proggressDialog=ProgressDialog(this)
        proggressDialog.setTitle("Espere por favor")
        proggressDialog.setCanceledOnTouchOutside(false)

        //establecer eventos
        binding.IbVolver.setOnClickListener {
            //para cuando presione servirar para que regrese ala actividad anterior
            onBackPressedDispatcher.onBackPressed()
        }
        //aaceder a material buton de la bd
        binding.AgregarCateBaseDatos.setOnClickListener {
            ValidarDatos()
        }
    }

    private var categoria=""
    private fun ValidarDatos() {
        categoria=binding.ETextCategoria.text.toString().trim()
        if(categoria.isEmpty()){
            Toast.makeText(applicationContext,"Ingrese una categoria",Toast.LENGTH_LONG).show()

        }else{
            AgregarCategoriaBD()
        }
    }

    private fun AgregarCategoriaBD() {
        proggressDialog.setMessage("Creando Categoria")
        proggressDialog.show()
        //obetner tiempo como atributo por cada categoria como id e identificar las categorias
        val tiempo=System.currentTimeMillis()

        val hashMap=HashMap<String,Any>()
        //informacion enviada desde android ala base de datos
        hashMap["id"]="$tiempo"
        hashMap["categoria"]=categoria
        hashMap["tiempo"]=tiempo
        //identificar q adminsitrador publico esa categoria
        hashMap["uid"]="${firebaseAuth.uid}"

        val ref= FirebaseDatabase.getInstance().getReference("Categorias")
        ref.child("$tiempo")
            .setValue(hashMap)
            .addOnSuccessListener {
                proggressDialog.dismiss()
                Toast.makeText(applicationContext,"Se agrego categoria en BD", Toast.LENGTH_SHORT).show()
                //despues de agregar la categoria se limpia el campo
                binding.ETextCategoria.setText("")
                startActivity(Intent(this@Agregar_Catego, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener {e->
                proggressDialog.dismiss()
                Toast.makeText(applicationContext,"No se pudo agregar la categoria debido a ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }
}