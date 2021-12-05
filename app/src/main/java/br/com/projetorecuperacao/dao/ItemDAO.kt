package br.com.projetorecuperacao.dao

import br.com.projetorecuperacao.model.Item

//Classe DAO para manipulação direta nos dados
class ItemDAO {
    companion object {
        private val items = ArrayList<Item>()
        var idInterator: Int = 1
    }

    //Método para salvar um item
    fun save(item: Item) {
        item.setId(idInterator)
        items.add(item)
        updateId()
    }

    //Método para incrementar 1 ao ID
    private fun updateId() {
        idInterator++
    }

    //Método para verificar se o item existe e editar
    fun edit(item: Item) {
        var itemExists: Item? = searchItemById(item)
        if (itemExists != null) {
            val posicaoDoAluno: Int = items.indexOf(itemExists)
            items.set(posicaoDoAluno, item)
        }
    }

    //Método para procurar o item pelo ID
    private fun searchItemById(item: Item): Item? {
        for (i in items) {
            if (i.getId() === item.getId()) {
                return i
            }
        }
        return null
    }

    //Método para retornar toda a lista de items
    fun all(): List<Item> {
        return ArrayList(items)
    }

    //Método para deletar um item da lista de items
    fun delete(item: Item) {
        var foundItem = searchItemById(item)
        if(foundItem != null){
            items.remove(foundItem)
        }
    }
}