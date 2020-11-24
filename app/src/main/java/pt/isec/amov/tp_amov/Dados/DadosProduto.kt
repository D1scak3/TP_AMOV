package pt.isec.amov.tp_amov.Dados

import android.content.Intent

class DadosProduto {
    var lvlImportance:Int
    var pname:String
    var qtyType:String
    var category:String
    var intent:Intent

    var qty:Int



    constructor(pname: String, category: String, qty: Int, qtyType: String, lvlImportance: Int, intent: Intent) {
        this.pname = pname
        this.category = category
        this.qty = qty
        this.qtyType = qtyType
        this.lvlImportance = lvlImportance
        this.intent = intent
    }




}