package br.com.projetorecuperacao.dao

import br.com.projetorecuperacao.model.Item

class ItemDAO {
    companion object {
        private val items = ArrayList<Item>()
        var idInterator: Int = 1
    }

    fun save(item: Item) {
        item.setId(idInterator)
        items.add(item)
        updateId()
    }

    private fun updateId() {
        idInterator++
    }

    fun edit(item: Item) {
        var itemExists: Item? = searchItemById(item)
        if (itemExists != null) {
            val posicaoDoAluno: Int = items.indexOf(itemExists)
            items.set(posicaoDoAluno, item)
        }
    }

    private fun searchItemById(item: Item): Item? {
        for (i in items) {
            if (i.getId() === item.getId()) {
                return i
            }
        }
        return null
    }

    fun all(): List<Item> {
        return ArrayList(items)
    }

    fun delete(item: Item) {
        var foundItem = searchItemById(item)
        if(foundItem != null){
            items.remove(foundItem)
        }
    }
}