package pt.isec.amov.tp_amov.Dados

import android.content.Intent
import java.io.ObjectInput

object DadosLista {
    var data = arrayListOf<Produtos>()
    var previousProducts = arrayListOf<DadosProduto>()
    lateinit var intent : Intent
    var lastList : Int = 0
    var searchedData = arrayListOf<Produtos>()
    var categories = arrayListOf<Category>()

    fun getCategoryNames(): ArrayList<String> {
        var nomes = arrayListOf<String>()

        for(c in categories)
            nomes.add(c.getName())

        return nomes
    }

    fun addToPreviousProduct(produto: DadosProduto){

        if(!previousProducts.contains(produto))
            previousProducts.add(produto)
    }

    fun removeFromDataProducts(produto: DadosProduto){
        if(previousProducts.contains(produto))
            previousProducts.remove(produto)
    }

    fun getPreviousProductById(id:Int): DadosProduto? {
        for (product in previousProducts){
            if(product.id == id)
                return product
        }
        return null
    }

    fun setNewIntent(intent : Intent){
        this.intent = intent
    }

    fun addList(lista : Produtos){
        data.add(lista)
    }

    fun removeList(lista : Produtos) : Boolean{
        return data.remove(lista)
    }

    fun getListById(id:Int) : Produtos? {
        for (l in data){
            if(l.id == id)
                return l
        }
        return null
    }


    fun search(name:String): ArrayList<Produtos> {
        searchedData.clear()
        for(product in data){
            if(product.nome.contains(name))
                searchedData.add(product)
        }
        return searchedData
    }
}