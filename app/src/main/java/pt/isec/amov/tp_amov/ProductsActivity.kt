package pt.isec.amov.tp_amov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_dialog.*
import kotlinx.android.synthetic.main.activity_dialog.view.*
import kotlinx.android.synthetic.main.activity_products.*
import pt.isec.amov.tp_amov.Dados.DadosProduto
import pt.isec.amov.tp_amov.Dados.Produtos

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rvList.layoutManager = LinearLayoutManager(this@ProductsActivity,LinearLayoutManager.VERTICAL,false)


    }



    fun onPlus(view : View){
        //produtos.add("teste")

        //val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, produtos)
        //lv.adapter = adapter
        var intent = Intent(this@ProductsActivity, ProductDetails::class.java)

        var dialog = LayoutInflater.from(this).inflate(R.layout.activity_dialog, null)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialog)
            .setTitle("Product details")
            .setCancelable(false)
            .create()

        alertDialog.show()


        dialog.submit_buton.setOnClickListener{
            val name = dialog.name_dialog.text.toString()
            val qty = dialog.qty_dialog.text.toString()

            if(name.isEmpty() || qty.isEmpty()){
                Toast.makeText(this, "Preencha os campos de texto todos", Toast.LENGTH_LONG).show()
            }else if(qty.toInt() <= 0){
                Toast.makeText(this, "A quantidade tem de ser maior que zero", Toast.LENGTH_LONG).show()
            }
            else{
                var item = DadosProduto(name, "fruta", qty.toInt(), "gostoso", 5, intent)
                Produtos.addProduct(item)
                alertDialog.dismiss()
            }
        }

        dialog.cancel_button.setOnClickListener{
            alertDialog.dismiss()
        }

        rvList.adapter = MyRVAdapter(Produtos.data)

    }

    class MyRVAdapter(val data: ArrayList<DadosProduto>) : RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {
        class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
            var tv1 : TextView = view.findViewById(R.id.name)
            var tv2 : TextView = view.findViewById(R.id.name2)
            var tv3 : TextView = view.findViewById(R.id.name3)

            fun update(str1:String,str2:String,str3:String) {
                tv1.text = str1
                tv2.text = str2
                tv3.text = str3


            }


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list_item,parent,false)

            return MyViewHolder(view)

        }



        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.itemView.setOnClickListener{

                var context = holder.itemView.context
                data[position].intent.putExtra("teste", data[position].qty)
                context.startActivity(data[position].intent)


            }

            holder.update(data[position].pname,data[position].category,data[position].pname)


        }

        override fun getItemCount(): Int = data.size
    }

}


