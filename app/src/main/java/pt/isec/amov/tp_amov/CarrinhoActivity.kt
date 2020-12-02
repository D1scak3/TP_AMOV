package pt.isec.amov.tp_amov

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_carrinho.*
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_products.rvList
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosLista.intent
import pt.isec.amov.tp_amov.Dados.DadosProduto
import pt.isec.amov.tp_amov.Dados.Produtos
import pt.isec.amov.tp_amov.ProductsActivity.Companion.id

class CarrinhoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrinho)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvCarrinho.layoutManager = LinearLayoutManager(this@CarrinhoActivity, LinearLayoutManager.VERTICAL, false)

        var carrinho = DadosLista.getListById(DadosLista.lastList)!!.carrinho

        for(product in carrinho)
            println(product.getName())

        rvCarrinho.adapter = MyRVAdapter(DadosLista.getListById(DadosLista.lastList)!!.carrinho)

    }


    class MyRVAdapter(val data: ArrayList<DadosProduto>) : RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {


        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var tv1 : TextView = view.findViewById(R.id.name)
            var tv2 : TextView = view.findViewById(R.id.name2)
            var tv3 : TextView = view.findViewById(R.id.name3)

            fun update(str1: String, str2: String, str3: String) {
                tv1.text = str1
                tv2.text = str2
                tv3.text = str3
            }
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent,false)
            return MyViewHolder(view)
        }



        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.update(data[position].pname, data[position].category, data[position].pname)
        }

        override fun getItemCount(): Int = data.size
    }

}