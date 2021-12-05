package br.com.projetorecuperacao.ui.activity

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import br.com.projetorecuperacao.R
import br.com.projetorecuperacao.dao.ItemDAO
import br.com.projetorecuperacao.model.Item
import br.com.projetorecuperacao.ui.adapter.ItemListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

//Classe utilizada para listar todos os items
class ListItemsActivity : AppCompatActivity(), ConstantActivities {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var build: Notification.Builder
    private lateinit var adapter: ItemListAdapter
    private val ChannelID = "RecuperationProject"
    private val desc = "Notifications"
    private var dao = ItemDAO()

    //Método responsável por criar a activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        setTitle("Item List")
        configNewItem()
        configShareButton()
        configVideoButton()
        configAudioButton()
        configList()
    }

    //Método para configurar o FAB de audio
    private fun configAudioButton() {
        val audioButton =
            findViewById<FloatingActionButton>(R.id.activity_list_itens_fab_audio)
        audioButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(this@ListItemsActivity, AudioViewActivity::class.java)
                startActivity(intent)
            }
        })
    }

    //Método para configurar o FAB de video
    private fun configVideoButton() {
        val videoButton =
            findViewById<FloatingActionButton>(R.id.activity_list_itens_fab_video)
        videoButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(this@ListItemsActivity, VideoViewActivity::class.java)
                startActivity(intent)
            }
        })
    }

    //Método para configurar o FAB de compartilhar
    private fun configShareButton() {
        val buttonShare = findViewById<FloatingActionButton>(R.id.activity_list_itens_fab_share)
        buttonShare.setOnClickListener() {
            val intent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, dao.all().toString())
                type = "text/plain"
            }
            startActivity(intent)
        }
    }

    //Método para criar o menu de contexto com a opção de deletar item
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.activity_list_items_menu, menu)
    }

    //Método utilizado pela activity no momento de retomar após uma parada (chamada de outra activity e etc)
    override fun onResume() {
        super.onResume()
        updateItens()
    }

    //Método para atualizar os itens da lista
    private fun updateItens() {
        adapter.update(dao.all())
    }

    //Método para configurar o botão de adicionar um novo item
    private fun configNewItem() {
        val newItemButton =
            findViewById<FloatingActionButton>(R.id.activity_list_itens_fab_new_item)
        newItemButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                openFormOnInsertMode()
            }
        })
    }

    //Método para abrir o formulário de maneira a adicionar um novo item
    private fun openFormOnInsertMode() {
        val intent = Intent(this@ListItemsActivity, FormItemActivity::class.java)
        startActivity(intent)
    }

    //Método para configurar a lista de items
    private fun configList() {
        val itemList: ListView = findViewById(R.id.activity_list_items_listview)
        configAdapter(itemList)
        configListenerOfClickItem(itemList)
        registerForContextMenu(itemList)
    }

    //Método para deletar um item e mostrar a notificação de item deletado
    private fun delete(item: Item) {
        dao.delete(item)
        adapter.remove(item)
        popLocalDeletedItemNotification()
    }

    //Método para ser acionado quando algum item da lista for clicado
    private fun configListenerOfClickItem(itemList: ListView) {
        itemList.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, position, id ->
            var chosenItem = adapterView.getItemAtPosition(position) as Item
            openFormOnEditMode(chosenItem)
        })
    }

    //Método para abrir o formulário de maneira a editar um item
    private fun openFormOnEditMode(chosenItem: Item) {
        var goToFormItemActivity = Intent(this, FormItemActivity::class.java)
        goToFormItemActivity.putExtra(ITEM_KEY, chosenItem)
        startActivity(goToFormItemActivity)
    }

    //Método para ser acionado quando algum item for segurado e verificar se o usuário deseja deletar o item
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemId: Int = item.getItemId()
        if (itemId == R.id.acitivity_context_menu_delete) {
            val menuInfo: AdapterView.AdapterContextMenuInfo =
                item.getMenuInfo() as AdapterView.AdapterContextMenuInfo
            val chosenItem: Item? = adapter.getItem(menuInfo.position)
            if (chosenItem != null) {
                delete(chosenItem)
            }
        }
        return super.onContextItemSelected(item)
    }

    //Método para configurar o adapter personalizado
    private fun configAdapter(itemsList: ListView) {
        adapter = ItemListAdapter(this@ListItemsActivity)
        itemsList.setAdapter(adapter)
    }

    //Método para lançar a notificação de item deletado
    fun popLocalDeletedItemNotification() {
        val intent = Intent(this, FormItemActivity::class.java)
        notificationManager =
            getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        notificationChannel =
            NotificationChannel(ChannelID, desc, NotificationManager.IMPORTANCE_HIGH)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)
        build = Notification.Builder(this)
            .setContentTitle("Deleted Item on List")
            .setContentText("See your list. Click here to add more items")
            .setSmallIcon(R.drawable.ic_new_item_added)
            .setChannelId(ChannelID)
            .setContentIntent(pendingIntent)
        notificationManager.notify(12345, build.build())
    }
}