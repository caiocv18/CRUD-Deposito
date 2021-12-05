package br.com.projetorecuperacao.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Currency
import br.com.projetorecuperacao.model.Item
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class ListItemsActivity : AppCompatActivity(), ConstantActivities {
    private lateinit var adapter: ArrayAdapter<Item>
    private var dao = ItemDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        setTitle("Item List")
        configNewItem()
        requestCurrencyWithAPI()
    }

    private fun requestCurrencyWithAPI() {
        val textView = findViewById<TextView>(R.id.tv_resultado)
        val url = "https://economia.awesomeapi.com.br/json/last/USD-BRL"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                textView.text = "Response is: ${response}"
                var gson = Gson()
                var currency = gson?.fromJson(response, Currency.Data::class.java)
                textView.text = currency.USDBRL.low
            },
            Response.ErrorListener { textView.text = "That didn't work!" })

        queue.add(stringRequest)
    }

    override fun onResume() {
        super.onResume()

        configList()
    }

    private fun configNewItem() {
        val newItemButton =
            findViewById<FloatingActionButton>(R.id.activity_list_itens_fab_new_item)

        newItemButton.setOnClickListener(object : View.OnClickListener {

            override fun onClick(view: View) {

                openFormOnInsertMode()
            }
        })
    }

    private fun openFormOnInsertMode() {
        val intent = Intent(this@ListItemsActivity, FormItemActivity::class.java)
        startActivity(intent)
    }

    private fun configList() {
        val itemList: ListView = findViewById(R.id.activity_list_items_listview)
        val items = dao.all()
        configAdapter(itemList, items)
        configListenerOfClickItem(itemList)
        itemList.setOnItemLongClickListener(OnItemLongClickListener { adapterView, view, position, id ->
            val chosenItem: Item = adapterView.getItemAtPosition(position) as Item
            dao.delete(chosenItem)
            adapter.remove(chosenItem)
            true
        })
    }

    private fun configListenerOfClickItem(itemList: ListView) {
        itemList.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, id ->
            var chosenItem = adapterView.getItemAtPosition(position) as Item
            openFormOnEditMode(chosenItem)
        })
    }

    private fun openFormOnEditMode(chosenItem: Item) {
        var goToFormItemActivity = Intent(this, FormItemActivity::class.java)
        goToFormItemActivity.putExtra(ITEM_KEY, chosenItem)
        startActivity(goToFormItemActivity)
    }

    private fun configAdapter(itemList: ListView, items: List<Item>) {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        itemList.setAdapter(adapter)
    }
}