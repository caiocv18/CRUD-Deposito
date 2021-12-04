package br.com.projetorecuperacao.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Currency
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import javax.xml.transform.ErrorListener

class ListItemsActivity : AppCompatActivity() {
    private var dao = ItemDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)

        setTitle("Item List")

        configuraNovoAluno()

        val textView = findViewById<TextView>(R.id.tv_resultado)
        val url = "https://economia.awesomeapi.com.br/json/last/USD-BRL"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                textView.text = "Response is: ${response}"
                var gson = Gson()
                var currency = gson?.fromJson(response,Currency.Data::class.java)
                textView.text = currency.USDBRL.low
            },
            Response.ErrorListener { textView.text = "That didn't work!" })

        queue.add(stringRequest)

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