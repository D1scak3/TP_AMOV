package pt.isec.amov.tp_amov.Dados


import android.content.Intent

class Produtos {
    var dataP = arrayListOf<DadosProduto>()
    lateinit var nome : String
    lateinit var intent : Intent
    var id:Int = 0
    var cart = arrayListOf<DadosProduto>()

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


    fun AddToCart(product: DadosProduto){
        if(cart.contains(product)){
            for(p in cart){
                if(p.id == product.id)
                    p.qty += product.qty
            }
        }else{
            cart.add(product)
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
        for (p in dataP){
            if(p.id == id)
                return p
        }
        return null
    }




    fun search(name:String): ArrayList<DadosProduto> {
        searchedData.clear()
        for(product in dataP){
            if(product.pname.contains(name))
                searchedData.add(product)
        }
        return searchedData
    }


    fun addProduct(product : DadosProduto){
        dataP.add(product)
    }

    fun removeProduct(product : DadosProduto): Boolean {
        return dataP.remove(product)
    }



    fun removeSelectedProducts(){
        for(product in dataP)
            if(product.selectedToDelete)
                dataP.remove(product)
    }

    fun sortByCategory(){
        dataP.sortBy { it.category }
    }

    fun sortByQty(){
        dataP.sortBy { it.qty }
    }

    fun sortByName(){
        dataP.sortBy { it.pname.toUpperCase() }
    }

    fun sortByImportance(){
        dataP.sortBy { it.lvlImportance }

    }


}