package pt.isec.amov.tp_amov.Dados

import android.content.Intent

object DadosLista {
    var data = arrayListOf<Produtos>()
    lateinit var intent : Intent


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

}