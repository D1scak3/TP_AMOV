package pt.isec.amov.tp_amov.Dados

class Category {
    private lateinit var name:String
    private var products  = arrayListOf<DadosProduto>()

    constructor(name: String) {
        this.name = name
    }


    fun addProduct(p:DadosProduto){
        if(!products.contains(p))
            products.add(p)
    }

    fun getName(): String {
        return this.name
    }
}