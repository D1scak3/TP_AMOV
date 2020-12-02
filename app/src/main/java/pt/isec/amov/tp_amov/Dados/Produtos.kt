package pt.isec.amov.tp_amov.Dados


import android.content.Intent

class Produtos {
    var data = arrayListOf<DadosProduto>()
    lateinit var nome : String
    lateinit var intent : Intent
    var carrinho = arrayListOf<DadosProduto>()
    var id:Int = 0

    constructor(nome: String, intent: Intent) {
        this.nome = nome
        this.intent = intent
        this.id = Produtos.getid()
    }

    companion object{
        var i = 0
        fun getid() : Int{
            return i++
        }
    }




    private var showDelete : Boolean = false
    var searchedData = arrayListOf<DadosProduto>()

    fun setListIntent(intent : Intent){
        this.intent = intent
    }

    fun getDelete() : Boolean{return showDelete}
    fun setDelete(bool:Boolean){this.showDelete = bool}

    fun getProductById(id:Int) : DadosProduto? {
        for (p in data){
            if(p.id == id)
                return p
        }
        return null
    }




    fun search(name:String): ArrayList<DadosProduto> {
        searchedData.clear()
        for(product in data){
            if(product.pname.contains(name))
                searchedData.add(product)
        }
        return searchedData
    }


    fun addProduct(product : DadosProduto){
        data.add(product)
    }

    fun removeProduct(product : DadosProduto): Boolean {
        return data.remove(product)
    }



    fun removeSelectedProducts(){
        for(product in data)
            if(product.selectedToDelete)
                data.remove(product)
    }

    fun sortByCategory(){
        data.sortBy { it.category }
    }

    fun sortByQty(){
        data.sortBy { it.qty }
    }

    fun sortByName(){
        data.sortBy { it.pname.toUpperCase() }
    }

    fun sortByImportance(){
        data.sortBy { it.lvlImportance }

    }


}