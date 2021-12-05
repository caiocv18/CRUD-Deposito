package br.com.projetorecuperacao.services

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    //Nome para o "Conteiner"
    private val PREFERENCE_NAME = "PROJETORECUPERACAO"
    //MODE_PRIVATE é a forma de acesso ao SharedPreference
    val sharedPreference: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun save(key_name: String, value: String){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(key_name, value)
        //Envia o dado para o "Conteiner"
        editor.commit()
    }


    fun getValue(key_name: String) : String? {
        return sharedPreference.getString(key_name, null)
    }

    fun removeValue(key_name: String) {
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.remove(key_name)
        editor.commit()
    }

}