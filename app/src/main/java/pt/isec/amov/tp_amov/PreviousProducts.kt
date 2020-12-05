package pt.isec.amov.tp_amov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.previous_products_list.*
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosProduto

class PreviousProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.previous_products_list)

        rvpList.layoutManager = LinearLayoutManager(this@PreviousProducts, LinearLayoutManager.VERTICAL, false)
        rvpList.adapter = MyRVAdapter(DadosLista.previousProducts)

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
            val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.product_list_item,
                    parent,
                    false
            )

            return MyViewHolder(view)

        }



        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val product = DadosLista.getPreviousProductById(data[position].id)
            holder.itemView.setOnClickListener{ it ->
                DadosLista.getListById(DadosLista.lastList)?.addProduct(product!!.copy())
            }

            holder.update(data[position].pname, data[position].category, data[position].pname)


        }

        override fun getItemCount(): Int = data.size
    }
}