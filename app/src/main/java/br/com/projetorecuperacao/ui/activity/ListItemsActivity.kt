package br.com.projetorecuperacao.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.dao.ItemDAO
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListItemsActivity : AppCompatActivity() {
    private var dao = ItemDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)

        setTitle("Item List")

        configuraNovoAluno()

    }

    override fun onResume() {
        super.onResume()

        configuraLista()
    }

    private fun configuraNovoAluno(){
        val botaoNovoAluno = findViewById<FloatingActionButton>(R.id.activity_list_itens_fab_new_item)

        botaoNovoAluno.setOnClickListener(object: View.OnClickListener {

            override fun onClick(view: View){

                abreFormularioActivity()
            }
        })
    }

    private fun abreFormularioActivity(){
        val intent = Intent(this@ListItemsActivity, FormItemActivity::class.java )
        startActivity(intent)
    }

    private fun configuraLista(){
        val listaDeAlunos : ListView = findViewById(R.id.activity_list_items_listview)
        listaDeAlunos.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1,dao.todos()))
    }
}