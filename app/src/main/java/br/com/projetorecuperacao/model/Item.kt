package br.com.projetorecuperacao.model

class Item (name: String, quantity : Int, price : Double){
    val name = "$name".also(::println)
    val quantity = "$quantity\n".also(::println)
    val price = "$price\n".also(::println)

    override fun toString(): String {
        return name
    }
}