package br.com.projetorecuperacao.model

import br.com.projetorecuperacao.dao.ItemDAO
import java.io.Serializable

class Item(private var name: String, private var quantity: Int, private var price: Double) : Serializable {

    constructor() : this("", 0, 0.0)

    private var id: Int = 0


    @JvmName("getId1")
    fun getId(): Int {
        return this.id
    }

    @JvmName("getName1")
    fun getName(): String {
        return this.name
    }

    @JvmName("getQuantity1")
    fun getQuantity(): Int {
        return this.quantity
    }

    @JvmName("getPrice1")
    fun getPrice(): Double {
        return this.price
    }

    @JvmName("setId1")
    fun setId(id: Int) {
        this.id = id
    }

    @JvmName("setName1")
    fun setName(name: String){
        this.name = name
    }

    @JvmName("setQuantity1")
    fun setQuantity(quantity: Int){
        this.quantity = quantity
    }

    @JvmName("setPrice1")
    fun setPrice(price: Double){
        this.price = price
    }

    override fun toString(): String {
        return name
    }

    fun hasValidId(): Boolean {
        return id > 0
    }

}