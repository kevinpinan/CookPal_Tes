package com.example.cookpal.Fragmentos_Admin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookpal.Administrator.AdaptadorCateg
import com.example.cookpal.Administrator.Agregar_Catego
import com.example.cookpal.Administrator.Agregar_Imagen_Receta
import com.example.cookpal.Administrator.ModeloCategoria
import com.example.cookpal.databinding.FragmentAdminDashboardBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Fragment_admin_dashboard : Fragment() {

    private lateinit var binding:FragmentAdminDashboardBinding
    private lateinit var mContext: Context


    //declaracion de arraylist para categorias con el adaptador
    private lateinit var categoriaArrayList:ArrayList<ModeloCategoria>
    private lateinit var adaptadorCateg: AdaptadorCateg

    override fun onAttach(context: Context) {
        mContext=context
        super.onAttach(context)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAdminDashboardBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ListarCategoria()
        binding.BtnAgregarCat.setOnClickListener{
            startActivity(Intent(mContext, Agregar_Catego::class.java))
        }
        binding.agregarRecetaImg.setOnClickListener{
            startActivity(Intent(mContext, Agregar_Imagen_Receta::class.java))

        }
    }

    private fun ListarCategoria() {
        categoriaArrayList= ArrayList()
        //consulta para alfabetico
        val ref=FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //leemos en tiempo real ,limpiamos el arraylist
                categoriaArrayList.clear()
                for(ds in snapshot.children){
                    val modelo=ds.getValue(ModeloCategoria::class.java)
                    categoriaArrayList.add(modelo!!)

                }
                adaptadorCateg=AdaptadorCateg(mContext,categoriaArrayList)
                binding.categoRview.adapter=adaptadorCateg
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

}