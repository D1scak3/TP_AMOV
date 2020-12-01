package pt.isec.amov.tp_amov.Dados

import android.content.Intent

class DadosProduto {
    var selectedToDelete : Boolean = false
    var lvlImportance:Int
    var pname:String
    var qtyType:String
    var category:String
    var intent:Intent
    var id:Int = 0
    var qty:Double
    lateinit var imgPath:String
    companion object{
        var i = 0
        fun getid() : Int{
            return i++
        }
    }


    constructor(pname: String, category: String, qty: Double, qtyType: String, lvlImportance: Int, intent: Intent) {
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