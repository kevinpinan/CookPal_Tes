package com.example.cookpal.Administrator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cookpal.R
import com.example.cookpal.databinding.ActivityDetalleRecetaAdminBinding

class DetalleReceta_Admin : AppCompatActivity() {
    lateinit var binding : ActivityDetalleRecetaAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_receta_admin)
    }
}