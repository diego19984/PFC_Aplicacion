package com.example.pfcaplicacion

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.pfcaplicacion.databinding.ActivityMain2Binding
import com.example.pfcaplicacion.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main2.*


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.StarBotton.setOnClickListener { sendDataToServer() }



    }

    private fun sendDataToServer() {
        val dataStr="Nombre:${binding.editTextTextEmailAddress.text}"
        val numeroPasiente="${binding.editTextNumber.text}"
        val intent = Intent(this,MainActivity::class.java)
        intent.putExtra("nombre",dataStr)
        intent.putExtra("numeroPasiente",numeroPasiente)
        startActivity(intent)
        Log.i("nombre", dataStr)
    }




}