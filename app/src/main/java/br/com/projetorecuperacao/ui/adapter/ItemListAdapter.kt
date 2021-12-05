package br.com.projetorecuperacao.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.model.Currency
import br.com.projetorecuperacao.model.Item
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

//Classe utilizada para fazer um adapter customizado
class ItemListAdapter(context: Context) : BaseAdapter() {
    val context = context
    private val items: MutableList<Item> = ArrayList()

    //Método sobrecrito para pegar a contagem de items
    override fun getCount(): Int {
        return items.size
    }

    //Método sobrecrito para pegar a item através do ID
    override fun getItem(position: Int): Item {
        return items[position]
    }

    //Método sobrecrito para pegar o ID do item
    override fun getItemId(position: Int): Long {
        return items[position].getId().toLong()
    }

    //Método sobrecrito para pegar a view customizada que foi gerada
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {
        val createView: View = LayoutInflater
            .from(context)
            .inflate(R.layout.item_on_list, viewGroup, false)
        val item: Item = items.get(position)
        val name = createView.findViewById<TextView>(R.id.item_name_on_list)
        name.setText(item.getName())
        requestAPIandPrintPriceConverted(createView, item)
        return createView
    }

    //Método para chamar a API e imprimir o valor em reais após a conversão do preço em dólar
    private fun requestAPIandPrintPriceConverted(
        createView: View,
        item: Item
    ) {
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
    }

    //Método para atualizar os items na lista e notificar o adapter de que houveram atualizações
    fun update(items : List<Item>){
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    //Método para remover um item da lista e notificar o adapter de que houve mudança na lista
    fun remove(item: Item) {
        items.remove(item)
        notifyDataSetChanged()
    }
}