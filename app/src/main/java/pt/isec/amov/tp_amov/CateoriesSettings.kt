package pt.isec.amov.tp_amov

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
import kotlinx.android.synthetic.main.activity_cateories_settings.*
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.activity_products.rvList
import kotlinx.android.synthetic.main.categories_card_view.*
import kotlinx.android.synthetic.main.dialog_categories_name.view.*
import pt.isec.amov.tp_amov.Dados.Category
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosProduto

class CateoriesSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cateories_settings)

        rvcList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvcList.adapter = MyRVAdapter(DadosLista.categories)


    }



    fun onPlusC(view: View){
        var dialog = LayoutInflater.from(this).inflate(R.layout.dialog_categories_name, null)


        val alertDialog = AlertDialog.Builder(this)
            .setView(dialog)
            .setTitle("")
            .setCancelable(false)
            .create()

        alertDialog.show()

        dialog.cancel.setOnClickListener { alertDialog.dismiss() }

        dialog.save.setOnClickListener {
            var name = dialog.categoryName.text.toString()
            if(name.isEmpty()){
                Toast.makeText(this, "The name must has one letter at least.", Toast.LENGTH_LONG).show()
            }else{
                var newCat = Category(name)
                DadosLista.categories.add(newCat)
                rvcList.adapter = MyRVAdapter(DadosLista.categories)
            }

        }
    }

    class MyRVAdapter(val data: ArrayList<Category>) : RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var tv1 : TextView = view.findViewById(R.id.categoryName)


            fun update(str1: String) {
                tv1.text = str1
            }


        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.categories_card_view,
                parent,
                false
            )

            return MyViewHolder(view)

        }



        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



            holder.update(data[position].getName())


        }

        override fun getItemCount(): Int = data.size
    }
}