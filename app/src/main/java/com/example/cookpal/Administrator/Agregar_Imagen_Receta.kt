package com.example.cookpal.Administrator

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.example.cookpal.databinding.ActivityAgregarImagenRecetaBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class Agregar_Imagen_Receta : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarImagenRecetaBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private lateinit var categoriaArrayList: ArrayList<ModeloCategoria>
    //variable para almacenar
    private var imgUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAgregarImagenRecetaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //instancia de firebase
        firebaseAuth=FirebaseAuth.getInstance()

        CargarCategorias()
        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)


        //volver a vista anterior
        binding.IbVolver.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.icAdjuntarimg.setOnClickListener {
            //funcion elegir imagen
            ElegirImg()

        }
        binding.TViewCategoriaReceta.setOnClickListener{
            SeleccionCategoria()
        }
        //
        binding.btnSubirReceta.setOnClickListener {
            //funciona para validar la informacion de la imagen
            ValidarInformacion()
        }
    }
    private var titulo=""
    private var ingredientes=""
    private var categoria=""

    private fun ValidarInformacion() {
        //obtener la informacion que se ingresena en los editText
        titulo=binding.EtTituloReceta.text.toString().trim()
        ingredientes=binding.EtDescripcionReceta.text.toString().trim()
        categoria=binding.TViewCategoriaReceta.text.toString().trim()

        if(titulo.isEmpty()){
            Toast.makeText(this,"Ingrese un titulo",Toast.LENGTH_SHORT).show()

        }else if(ingredientes.isEmpty()){
            Toast.makeText(this,"Ingrese los ingredientes de la receta",Toast.LENGTH_SHORT).show()
        }else if(categoria.isEmpty()){
            Toast.makeText(this,"SELECCIONE UNA CATEGORIA",Toast.LENGTH_SHORT).show()
        }else if(imgUri==null){
            Toast.makeText(this,"Adjunte una Imagen de su Receta",Toast.LENGTH_SHORT).show()

        }
        else{
            SubirIMG()
        }
    }

    private fun SubirIMG() {
        //firebase STORAGE
        progressDialog.setMessage("Subiendo Imagen Receta")
        progressDialog.show()
        //id del archivo
        val tiempo=System.currentTimeMillis()
        val ruta_receta="Recetas/$tiempo"
        val storageReference=FirebaseStorage.getInstance().getReference(ruta_receta)
        storageReference.putFile(imgUri!!)
            .addOnSuccessListener {tarea->
                val uriTask : Task<Uri> = tarea.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val UrlImgSubida= "${uriTask.result}"

                //funcion para obteneer la url de storage
                SubirImgBD(UrlImgSubida,tiempo)

            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"Fallo la subida de la Receta debido a ${e.message}",Toast.LENGTH_SHORT).show()



            }
    }

    private fun SubirImgBD(urlImgSubida: String, tiempo: Long) {
        progressDialog.setMessage("Subiendo receta en la BD o Nube")
        //uid del admin
        val uid=firebaseAuth.uid

        //informacion de l receta
        val hashMap: HashMap<String,Any> = HashMap()
        hashMap["uid"] = "$uid"
        hashMap["id"] = "$tiempo"
        hashMap["titulo"] = titulo
        hashMap["ingredientes"] = ingredientes
        hashMap["categoria"] = id_categoria
        hashMap["url"] = urlImgSubida
        hashMap["tiempo"] = tiempo

        val ref=FirebaseDatabase.getInstance().getReference("Recetas")
        ref.child("$tiempo")
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(this,"Receta subida correctamente",Toast.LENGTH_SHORT).show()
                //cuando se suba el libro se seteara los campos o vaciara
                binding.EtTituloReceta.setText("")
                binding.EtDescripcionReceta.setText("")
                binding.TViewCategoriaReceta.setText("")
                imgUri=null


            }
            .addOnFailureListener {e->
                progressDialog.dismiss()
                Toast.makeText(this,"Fallo la subida de la Receta debido a ${e.message}",Toast.LENGTH_SHORT).show()

            }



    }

    private fun CargarCategorias() {
        categoriaArrayList=ArrayList()
        val ref =FirebaseDatabase.getInstance().getReference("Categorias").orderByChild("categoria")
        ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                categoriaArrayList.clear()
                //desplazarse dentro de la base de datos
                for(ds in snapshot.children){
                    val modelo=ds.getValue(ModeloCategoria::class.java)
                    //agregar datos ala lista
                    categoriaArrayList.add(modelo!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private var id_categoria=""
    private var titulo_categoria=""

    private fun SeleccionCategoria(){
        //se obtendra una matriz de categorias del arreglo
        val categoriasArray= arrayOfNulls<String>(categoriaArrayList.size)
        for(i in categoriasArray.indices){
            categoriasArray[i]=categoriaArrayList[i].categoria
        }
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Seleccione una categoria")
            .setItems(categoriasArray){dialog,which->
                //aqui se maneja lo que selecciona el usuario
                id_categoria=categoriaArrayList[which].id
                titulo_categoria=categoriaArrayList[which].categoria
                binding.TViewCategoriaReceta.text=titulo_categoria

            }
            .show()

    }

    //fuincion para acceder al almacenamiento
    private fun  ElegirImg(){
        val intent= Intent()
        intent.type="application/img"
        intent.action=Intent.ACTION_GET_CONTENT
        imgActivityRL.launch(intent)
    }

    //funcion que permite obtener la uri del gestor de archivos
    val imgActivityRL=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult>{resultado->
            if(resultado.resultCode== RESULT_OK){
                imgUri=resultado.data!!.data
            }else{
                Toast.makeText(this,"cancelado",Toast.LENGTH_SHORT).show()
            }
        }
    )
}