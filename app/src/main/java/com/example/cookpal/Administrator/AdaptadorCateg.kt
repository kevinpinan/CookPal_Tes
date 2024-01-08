package com.example.cookpal.Administrator

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cookpal.Cliente.ListaRecetaCliente
import com.example.cookpal.databinding.ItemCategAdminBinding
import com.google.firebase.database.FirebaseDatabase

class AdaptadorCateg:RecyclerView.Adapter<AdaptadorCateg.HolderCategoria> {
    private lateinit var binding:ItemCategAdminBinding
    private val context:Context
    private val categoriaArrayList:ArrayList<ModeloCategoria>

    constructor(context: Context, categoriaArrayList: ArrayList<ModeloCategoria>) {
        this.context = context
        this.categoriaArrayList = categoriaArrayList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderCategoria {
        binding=ItemCategAdminBinding.inflate(LayoutInflater.from(context),parent,false)
        return HolderCategoria(binding.root)

    }

    override fun getItemCount(): Int {
        return categoriaArrayList.size
    }

    override fun onBindViewHolder(holder: HolderCategoria, position: Int) {
        val modelo = categoriaArrayList[position]
        val id = modelo.id
        val categoria = modelo.categoria
        val uid = modelo.uid
        val tiempo = modelo.tiempo

        holder.categoriaTv.text=categoria

        //evento para listar las recetas dentro dentro de cada categoria
        holder.categoriaTv.text = categoria
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ListarImgRecetas::class.java)
            intent.putExtra("idCategoria",id)
            intent.putExtra("tituloCategoria",categoria)
            context.startActivity(intent)
        }

        holder.eliminarCategIb.setOnClickListener {
            val builder=AlertDialog.Builder(context)
            builder.setTitle("Eliminar Categoria")
                .setMessage("Estas seguro de Eliminar esta Categoria")
                .setPositiveButton("Confirmar"){a,d->
                    Toast.makeText(context,"Eliminando Categoria",Toast.LENGTH_SHORT).show()
                    EliminarCategoria(modelo,holder)
                }
                .setNegativeButton("Cancelar"){a,d->
                    a.dismiss()
                }
            builder.show()
        }


    }

    private fun EliminarCategoria(modelo: ModeloCategoria, holder: HolderCategoria) {
        val id=modelo.id
        val ref=FirebaseDatabase.getInstance().getReference("Categorias")
        ref.child(id).removeValue()
            .addOnSuccessListener {
                Toast.makeText(context,"eliminando categoriass",Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { e->
                Toast.makeText(context,"no se pudo eliminar por ${e.message}",Toast.LENGTH_SHORT).show()
            }
    }
    //constructoru para inicializar contexto y arraylist


    inner class HolderCategoria(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoriaTv: TextView=binding.ItemNombreCategoAdmin
        var eliminarCategIb:ImageButton=binding.EliminarCateg
    }


}