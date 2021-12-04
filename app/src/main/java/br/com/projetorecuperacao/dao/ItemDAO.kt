package br.com.projetorecuperacao.dao

import br.com.projetorecuperacao.model.Item

class ItemDAO {
    companion object{
        private val items = ArrayList<Item>()
    }

    fun save(item : Item) {
        items.add(item)
    }

    fun all() : List<Item>{
        return ArrayList(items)
    }
}