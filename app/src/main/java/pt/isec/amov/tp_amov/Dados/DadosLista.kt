package pt.isec.amov.tp_amov.Dados

import android.content.Intent

object DadosLista {
    var data = arrayListOf<Produtos>()
    lateinit var intent : Intent
    lateinit var nome : String

    fun setNewIntent(intent : Intent){
        this.intent = intent
    }

    fun setListListName(str : String){
        nome = str
    }

    fun addList(lista : Produtos){
        data.add(lista)
    }

    fun removeList(lista : Produtos) : Boolean{
        return data.remove(lista)
    }

}