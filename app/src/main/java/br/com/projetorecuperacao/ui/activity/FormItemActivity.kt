package br.com.projetorecuperacao.ui.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Item
import android.view.Menu
import android.view.MenuItem
import br.com.projetorecuperacao.R


class FormItemActivity : AppCompatActivity(), ConstantActivities {
    private lateinit var fieldName: EditText
    private lateinit var fieldQuantity: EditText
    private lateinit var fieldPrice: EditText
    private val dao = ItemDAO()
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_form)
        initializingFields()
        loadItem()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater
            .inflate(R.menu.activity_form_item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.getItemId()
        if (itemId == R.id.activity_form_menu_item_save) {
            finishForm()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadItem() {
        val data = intent
        if (data.hasExtra(ITEM_KEY)) {
            setTitle("Edit item")
            item = data.getSerializableExtra(ITEM_KEY) as Item
            fillFields()
        } else {
            setTitle("New item")
            item = Item()
        }
    }

    private fun fillFields() {
        fieldName.setText(item.getName())
        fieldQuantity.setText(item.getQuantity().toString())
        fieldPrice.setText(item.getPrice().toString())
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

    private fun finishForm() {
        fillItem()
        if (item.hasValidId()) {
            dao.edit(item)
        } else {
            dao.save(item)
        }
        finish()
    }
}