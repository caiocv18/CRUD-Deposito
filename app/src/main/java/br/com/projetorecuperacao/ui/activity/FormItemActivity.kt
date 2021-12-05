package br.com.projetorecuperacao.ui.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Item
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.services.SharedPreference

import android.graphics.Color
import android.text.TextUtils

//Classe utilizada para mostrar o formulário de preenchimento para o item
class FormItemActivity : AppCompatActivity(), ConstantActivities {
    private lateinit var fieldName: EditText
    private lateinit var fieldQuantity: EditText
    private lateinit var fieldPrice: EditText
    private lateinit var item: Item
    private lateinit var notificationManager : NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var build : Notification.Builder
    private val ChannelID = "RecuperationProject"
    private val desc = "Notifications"
    private val dao = ItemDAO()

    //Método responsável por criar a activity
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

    //Método para disparar uma notificação ao adicionar um item
    private fun popLocalAddItemNotification() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        notificationChannel =
            NotificationChannel(ChannelID, desc, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)
        build = Notification.Builder(this)
            .setContentTitle("New Item on List")
            .setContentText("Item saved successfully. Click here to add more items")
            .setSmallIcon(R.drawable.ic_new_item_added)
            .setChannelId(ChannelID)
            .setContentIntent(pendingIntent)
        notificationManager.notify(12345, build.build())
    }

    //Método para disparar uma notificação ao autualizar um item
    private fun popLocalUpdateItemNotification() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        notificationChannel =
            NotificationChannel(ChannelID, desc, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)
        build = Notification.Builder(this)
            .setContentTitle("Updated Item on List")
            .setContentText("Updated saved successfully. Click here to add more items")
            .setSmallIcon(R.drawable.ic_new_item_added)
            .setChannelId(ChannelID)
            .setContentIntent(pendingIntent)
        notificationManager.notify(12345, build.build())
    }

    //Método recuperar dados do formulário que foram salvos no Shared Preference
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

    //Método limpar os dados que foram preenchidos no formulário
    private fun clean(
        buttonClean: Button,
    ) {
        buttonClean.setOnClickListener {
            fillFields()
        }
    }

    //Método salvar dados do formulário que no Shared Preference
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

    //Método para inicialização de todos os botões
    private fun initializingButtons(): Triple<Button, Button, Button> {
        val buttonRecord = findViewById<Button>(R.id.activity_form_button_record)
        val buttonClean = findViewById<Button>(R.id.activity_form_button_clean)
        val buttonRecover = findViewById<Button>(R.id.activity_form_button_recover)
        return Triple(buttonRecord, buttonClean, buttonRecover)
    }

    //Método para criação do botão de menu para salvar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater
            .inflate(R.menu.activity_form_item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Método para finalizar e salvar o item ao clicar no botão de menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.getItemId()
        if (itemId == R.id.activity_form_menu_item_save) {
            finishForm()
        }
        return super.onOptionsItemSelected(item)
    }

    //Método para carregar o item e verificar se é um item que será editado ou item novo
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

    //Método para preencher os campos, no caso de um item existente que está sendo editado
    private fun fillFields() {
        fieldName.setText(item.getName())
        fieldQuantity.setText(item.getQuantity().toString())
        fieldPrice.setText(item.getPrice().toString())
    }

    //Método para inicializar os campos do formulário de acordo com cada EditText
    private fun initializingFields() {
        fieldName = findViewById(R.id.activity_form_item_name)
        fieldQuantity = findViewById(R.id.activity_form_item_quantity)
        fieldPrice = findViewById(R.id.activity_form_item_price)
    }

    //Método para preencher o item com os dados fornecidos no formulário
    private fun fillItem() {
        val name: String = fieldName.text.toString()
        val quantity: Int = fieldQuantity.text.toString().toInt()
        val price: Double = fieldPrice.text.toString().toDouble()

        item.setName(name)
        item.setQuantity(quantity)
        item.setPrice(price)
    }

    //Método para finalizar o formulário e enviar uma notitifacação de atualização ou de adição de item
    private fun finishForm() {
        fillItem()
        if (item.hasValidId()) {
            dao.edit(item)
            popLocalUpdateItemNotification()
        } else {
            dao.save(item)
            popLocalAddItemNotification()
        }
        finish()
    }
}