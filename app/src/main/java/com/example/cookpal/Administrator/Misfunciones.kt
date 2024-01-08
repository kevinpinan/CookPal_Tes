
package com.example.cookpal.Administrator

import android.app.Application
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Misfunciones:Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object{
        fun CargarCategoria(categoriaId: String, categoriaTv: TextView){
            val ref =FirebaseDatabase.getInstance().getReference("Categorias")
            ref.child(categoriaId)
                .addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val categoria="${snapshot.child("categoria").value}"
                        categoriaTv.text=categoria
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

        }

    }
}
