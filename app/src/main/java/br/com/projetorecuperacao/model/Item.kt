package br.com.projetorecuperacao.model

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class Item (name: String, quantity : Int, price : Double){
    val name = "$name".also(::println)
    val quantity = "$quantity\n".also(::println)
    val price = "$price\n".also(::println)

    override fun toString(): String {
        return name
    }
}