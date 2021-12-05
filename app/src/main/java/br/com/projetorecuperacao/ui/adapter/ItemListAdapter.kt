package br.com.projetorecuperacao.ui.adapter

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.model.Currency
import br.com.projetorecuperacao.model.Item
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson


class ItemListAdapter(context: Context) : BaseAdapter() {
    val context = context
    private val items: MutableList<Item> = ArrayList()
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Item {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return items[position].getId().toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {
        val createView: View = LayoutInflater
            .from(context)
            .inflate(R.layout.item_on_list, viewGroup, false)
        val item: Item = items.get(position)
        val name = createView.findViewById<TextView>(R.id.item_name_on_list)
        name.setText(item.getName())

        val price = createView.findViewById<TextView>(R.id.item_price_on_list)
        val url = "https://economia.awesomeapi.com.br/json/last/USD-BRL"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var gson = Gson()
                var currency = gson?.fromJson(response, Currency.Data::class.java)
                var priceConverted = (currency.USDBRL.bid.toDouble() * item.getPrice())
                price.setText("R$${priceConverted}")
            },
            Response.ErrorListener {
                price.setText("Request to 'API de Cotações de moedas' didn't work!")
            })
        queue.add(stringRequest)
        return createView
    }


    fun update(items : List<Item>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun remove(item: Item) {
        items.remove(item)
        notifyDataSetChanged()
    }
}