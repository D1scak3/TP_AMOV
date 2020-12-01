package pt.isec.amov.tp_amov.Dados

import android.content.Intent

object Produtos {
    var data = arrayListOf<DadosProduto>()
    lateinit var nome : String
    lateinit var intent : Intent

    fun setListName(str : String){
        nome = str
    }

    fun setListIntent(intent : Intent){
        this.intent = intent
    }

    fun addProduct(product : DadosProduto){
        data.add(product)
    }

    fun removeProduct(product : DadosProduto): Boolean {
        return data.remove(product)
    }

    fun sortByCategory(){
        data = data.sortedWith(compareBy { it.category }) as ArrayList<DadosProduto>
    }

    fun sortByQty(){
        data = data.sortedWith(compareBy { it.qty }) as ArrayList<DadosProduto>
    }

    fun sortByName(){
        data = data.sortedWith(compareBy { it.pname }) as ArrayList<DadosProduto>
    }

    fun sortByImportance(){
        data = data.sortedWith(compareBy { it.lvlImportance }) as ArrayList<DadosProduto>
    }


}