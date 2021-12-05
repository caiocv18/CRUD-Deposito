package br.com.projetorecuperacao.model

import java.io.Serializable

//Classe para modelagem de um Item
class Item(private var name: String, private var quantity: Int, private var price: Double) :
    Serializable {

    constructor() : this("", 0, 0.0)

    private var id: Int = 0

    //Método para pegar o ID do item
    @JvmName("getId1")
    fun getId(): Int {
        return this.id
    }

    //Método para pegar o nome do item
    @JvmName("getName1")
    fun getName(): String {
        return this.name
    }

    //Método para pegar a quantidade do item
    @JvmName("getQuantity1")
    fun getQuantity(): Int {
        return this.quantity
    }

    //Método para pegar o preço do item
    @JvmName("getPrice1")
    fun getPrice(): Double {
        return this.price
    }

    //Método para alterar o ID do item
    @JvmName("setId1")
    fun setId(id: Int) {
        this.id = id
    }

    //Método para alterar o nome do item
    @JvmName("setName1")
    fun setName(name: String) {
        this.name = name
    }

    //Método para alterar a quantidade do item
    @JvmName("setQuantity1")
    fun setQuantity(quantity: Int) {
        this.quantity = quantity
    }

    //Método para alterar o preço do item
    @JvmName("setPrice1")
    fun setPrice(price: Double) {
        this.price = price
    }

    //Método para retornar o nome do item quando for feita uma chamada ao toString classe
    override fun toString(): String {
        return name
    }

    //Método para verificar se o ID é válido
    fun hasValidId(): Boolean {
        return id > 0
    }

}