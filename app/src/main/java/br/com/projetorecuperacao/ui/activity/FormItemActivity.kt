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
    private lateinit var fieldName: EditText
    private lateinit var fieldQuantity: EditText
    private lateinit var fieldPrice: EditText
    private val dao = ItemDAO()
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_form)

        setTitle("New item")
        initializingFields()
        configSaveButton()

        val data = intent

        if (data.hasExtra("item")) {
            item = data.getSerializableExtra("item") as Item
            fieldName.setText(item.getName())
            fieldQuantity.setText(item.getQuantity().toString())
            fieldPrice.setText(item.getPrice().toString())
        } else {
            item = Item()
        }

    }

    private fun initializingFields() {
        fieldName = findViewById(R.id.activity_form_item_name)
        fieldQuantity = findViewById(R.id.activity_form_item_quantity)
        fieldPrice = findViewById(R.id.activity_form_item_price)
    }

    private fun fillItem() {
        val name: String = fieldName.text.toString()
        val quantity: Int = fieldQuantity.text.toString().toInt()
        val price: Double = fieldPrice.text.toString().toDouble()

        item.setName(name)
        item.setQuantity(quantity)
        item.setPrice(price)
    }

    private fun configSaveButton() {
        val saveButton = findViewById<Button>(R.id.activity_form_item_save_button)

        saveButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                fillItem()
                if (item.hasValidId()) {
                    dao.edit(item)
                }else{
                    dao.save(item)
                }
                finish()
            }
        })
    }
}