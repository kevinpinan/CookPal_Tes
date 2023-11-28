package com.example.cookpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer

class Bienvenida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)
        VerBienvenida()
    }

    //se crea funciones
    fun  VerBienvenida(){
        object: CountDownTimer(2000,1000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                //dirige a main activity
                val intent = Intent(this@Bienvenida,MainActivity::class.java)
                startActivity(intent)
                finishAffinity()

            }


        }.start()
    }
}