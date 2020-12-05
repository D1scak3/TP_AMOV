package pt.isec.amov.tp_amov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.cart_cardview.view.*
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.android.synthetic.main.product_list_item.view.*
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosProduto

class Cart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        rvcartList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvcartList.adapter = MyRVAdapter(DadosLista.getListById(DadosLista.lastList)!!.cart)
    }



    class MyRVAdapter(val data: ArrayList<DadosProduto>) : RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var tv1 : TextView = view.findViewById(R.id.namec)
            var tv2 : TextView = view.findViewById(R.id.name2c)
            var tv3 : TextView = view.findViewById(R.id.name3c)
            var vimg : ImageView = view.findViewById(R.id.img)
            fun update(str1: String, str2: String, str3: String, img: ByteArray?) {
                tv1.text = str1
                tv2.text = str2
                tv3.text = str3
                if(img != null)
                    Utils.BitmapToImage(img, vimg)

            }


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.cart_cardview,
                parent,
                false
            )

            return MyViewHolder(view)

        }



        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.itemView.remove_to_cart.setOnClickListener {
                val list = DadosLista.getListById(DadosLista.lastList)
                list!!.getProductById(data[position].id)!!.qty = 0F
                list!!.cart.remove(list!!.getProductById(data[position].id))
                notifyItemRemoved(position)

            }

            holder.update(data[position].pname, data[position].category, data[position].pname, data[position].img)


        }

        override fun getItemCount(): Int = data.size
    }


}