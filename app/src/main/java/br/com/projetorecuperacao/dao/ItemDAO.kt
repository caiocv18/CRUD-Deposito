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
        idInterator++
    }

    fun edit(item: Item) {
        var itemExists: Item? = null
        for (i in items) {
            if (i.getId() === item.getId()) {
                itemExists = i
            }
        }
        if (itemExists != null) {
            val posicaoDoAluno: Int = items.indexOf(itemExists)
            items.set(posicaoDoAluno, item)
        }
    }

    fun all(): List<Item> {
        return ArrayList(items)
    }
}