package com.example.cookpal.Fragmentos_Admin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cookpal.Elegir_rol
import com.example.cookpal.databinding.FragmentAdminCuentaBinding
import com.google.firebase.auth.FirebaseAuth

class Fragment_admin_cuenta : Fragment() {

    private lateinit var binding: FragmentAdminCuentaBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mcontext :Context

    override fun onAttach(context: Context) {
        mcontext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAdminCuentaBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAuth=FirebaseAuth.getInstance()
            binding.CerrarSesionAdminist.setOnClickListener{
                firebaseAuth.signOut()
                startActivity(Intent(context,Elegir_rol::class.java))
                activity?.finishAffinity()
            }

    }
}