package pt.isec.amov.tp_amov.Dados

object Produtos {
    var data = arrayListOf<DadosProduto>()



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