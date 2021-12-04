package br.com.projetorecuperacao.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Item

class FormItemActivity : AppCompatActivity() {
    private lateinit var fieldName : EditText
    private lateinit var fieldQuantity : EditText
    private lateinit var fieldPrice : EditText
    private val dao = ItemDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_form)

        setTitle("New item")
        initializingFields()
        configuraBotaoSalvar()
    }

    private fun initializingFields(){
        fieldName = findViewById(R.id.activity_form_item_name)
        fieldQuantity = findViewById(R.id.activity_form_item_quantity)
        fieldPrice = findViewById(R.id.activity_form_item_price)
    }

    private fun createItem() : Item {
        val name : String = fieldName.text.toString()
        val quantity : Int = fieldQuantity.text.toString().toInt()
        val price : Double = fieldPrice.text.toString().toDouble()

        return Item(name, quantity, price)
    }

    private fun save(createdItem : Item, dao : ItemDAO){
        dao.salva(createdItem)
        finish()
    }

    private fun configuraBotaoSalvar(){
        val botaoSalvar = findViewById<Button>(R.id.activity_form_item_save_button)

        botaoSalvar.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?){
                var createdItem : Item = createItem()
                save(createdItem, dao)
            }
        })
    }
}