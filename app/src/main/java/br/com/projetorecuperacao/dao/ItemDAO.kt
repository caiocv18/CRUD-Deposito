package br.com.projetorecuperacao.dao

import br.com.projetorecuperacao.model.Item

class ItemDAO {
    companion object{
        private val items = ArrayList<Item>()
    }

    fun salva(item : Item) {
        items.add(item)
    }

    fun todos() : List<Item>{
        return ArrayList(items)
    }
}