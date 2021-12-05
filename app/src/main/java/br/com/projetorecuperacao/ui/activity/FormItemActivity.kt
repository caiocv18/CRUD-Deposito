package br.com.projetorecuperacao.ui.activity

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Item
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.services.SharedPreference
import android.widget.Toast

import android.content.SharedPreferences
import android.view.View


class FormItemActivity : AppCompatActivity(), ConstantActivities {
    private lateinit var fieldName: EditText
    private lateinit var fieldQuantity: EditText
    private lateinit var fieldPrice: EditText
    private val dao = ItemDAO()
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference  = SharedPreference(this)
        setContentView(R.layout.activity_item_form)
        initializingFields()
        loadItem()

        val (buttonRecord, buttonClean, buttonRecover) = initializingButtons()

        record(buttonRecord, sharedPreference)

        clean(buttonClean)

        recover(buttonRecover, sharedPreference)

    }

    private fun recover(
        buttonRecover: Button,
        sharedPreference: SharedPreference
    ) {
        buttonRecover.setOnClickListener {
            fieldName.setText(sharedPreference.getValue("name"))
            fieldQuantity.setText(sharedPreference.getValue("quantity").toString())
            fieldPrice.setText(sharedPreference.getValue("price"))
        }
    }

    private fun clean(
        buttonClean: Button,
    ) {
        buttonClean.setOnClickListener {
            fillFields()
        }
    }

    private fun record(
        buttonRecord: Button,
        sharedPreference: SharedPreference
    ) {
        buttonRecord.setOnClickListener {
            sharedPreference.save("name", fieldName.text.toString())
            sharedPreference.save("quantity", fieldQuantity.text.toString())
            sharedPreference.save("price", fieldPrice.text.toString())
        }
    }

    private fun initializingButtons(): Triple<Button, Button, Button> {
        val buttonRecord = findViewById<Button>(R.id.activity_form_button_record)
        val buttonClean = findViewById<Button>(R.id.activity_form_button_clean)
        val buttonRecover = findViewById<Button>(R.id.activity_form_button_recover)
        return Triple(buttonRecord, buttonClean, buttonRecover)
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