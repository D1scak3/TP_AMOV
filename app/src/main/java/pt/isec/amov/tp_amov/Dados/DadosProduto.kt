package pt.isec.amov.tp_amov.Dados

import android.content.Intent

class DadosProduto {


    var note : String = ""
    var selectedToDelete : Boolean = false
    var lvlImportance:Int
    var pname:String
    var qtyType:String
    var category:String
    var intent:Intent
    var id:Int = 0
    var qty:Float
    lateinit var cat : Category
    var preco = arrayListOf<Float>()
    var img : ByteArray? = null


    lateinit var imgPath:String
    companion object{
        var i = 0
        fun getid() : Int{
            return i++
        }
    }

    fun getPrecoMaisBaixo() : Float? {
        var min:Float
        if(preco.isNotEmpty())
            return preco.minOrNull()

        return 0F
    }

    fun getLastPreco(): Float {
        if (preco.isNotEmpty())
            return preco[preco.size]
        return 0F
    }

    fun addPreco(preco:Int){

    }

    fun copy(): DadosProduto {
        var newObject : DadosProduto = DadosProduto(this.pname, "fruta", this.qty, "gostoso", 5, this.intent)
        newObject.note = this.note
        newObject.id = this.id
        newObject.img = this.img
        return newObject
    }

    constructor(pname: String, category: String, qty: Float, qtyType: String, lvlImportance: Int, intent: Intent) {

        this.pname = pname
        this.category = category
        this.qty = qty
        this.qtyType = qtyType
        this.lvlImportance = lvlImportance
        this.intent = intent
        this.id = DadosProduto.getid()
        this.imgPath = ""
    }

    fun getName(): String {
        return this.pname
    }

    fun setToDestroy(destroy : Boolean){
        this.selectedToDelete = destroy
    }

    fun setProductImgPath(path:String){
        this.imgPath = path
    }

    fun getProductImgPath():String{
        return this.imgPath
    }



}