package com.example.cookpal.Cliente

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookpal.Administrator.ModeloReceta
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityListaRecetaClienteBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaRecetaCliente : AppCompatActivity() {

    private lateinit var binding: ActivityListaRecetaClienteBinding

    private var idCategoria = ""
    private var tituloCategoria = ""

    private lateinit var recetasArrayList : ArrayList<ModeloReceta>
    private lateinit var adaptadorRecetaCliente : AdaptadorRecetasCliente




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityListaRecetaClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener datos del adaptador
        val intent = intent
        idCategoria = intent.getStringExtra("idCategoria")!!
        tituloCategoria = intent.getStringExtra("tituloCategoria")!!

        binding.txtCategoriaReceta.text = tituloCategoria

        binding.IbVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


        CargarRecetas()

    }

    private fun CargarRecetas() {
        recetasArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Recetas")
        ref.orderByChild("categoria").equalTo(idCategoria)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    recetasArrayList.clear()
                    for (ds in snapshot.children){
                        val modelo = ds.getValue(ModeloReceta::class.java)
                        if(modelo!= null){
                            recetasArrayList.add(modelo)
                        }
                    }
                    adaptadorRecetaCliente = AdaptadorRecetasCliente(this@ListaRecetaCliente,recetasArrayList)
                    binding.RvRecetasCliente.adapter = adaptadorRecetaCliente
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}