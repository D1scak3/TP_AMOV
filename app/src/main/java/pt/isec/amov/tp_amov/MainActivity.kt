
package pt.isec.amov.tp_amov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list_dialog.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_products.*
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.Produtos

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvListList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvListList.adapter = MyRVAdapter(DadosLista.data)

    }


    fun onAddLista(view : View){
        var intent = Intent(this@MainActivity, ProductsActivity::class.java)

        var dialog = LayoutInflater.from(this).inflate(R.layout.activity_list_dialog, null)

        val alertDialog = AlertDialog.Builder(this)
                .setView(dialog)
                .setTitle("Nova/Com base noutra")
                .setCancelable(false)
                .create()

        alertDialog.show()

        dialog.list_submit_button.setOnClickListener{
            val nome = dialog.list_name.text.toString()

            if(nome.isEmpty())
                Toast.makeText(this, "Introduza o nome para a lista.", Toast.LENGTH_LONG).show()
            else{
                var item = Produtos(nome, intent)//lista

                DadosLista.addList(item)//adiciona lista à lista de listas
                alertDialog.dismiss()
            }
        }

        dialog.list_cancel_button.setOnClickListener{
            alertDialog.dismiss()
        }

        rvListList.adapter = MyRVAdapter(DadosLista.data)

    }


    class MyRVAdapter(val data : ArrayList<Produtos>) : RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {
        class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
            var nome : TextView = view.findViewById(R.id.ListListName)

            fun update(str : String){
                nome.text = str
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

            return MyViewHolder(view)
        }

        //inicia a activity de cada sub lista
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.itemView.setOnClickListener{
                var context = holder.itemView.context
                //data[position].intent.putExtra("idList", data[position].id)
                DadosLista.lastList = data[position].id
                context.startActivity(data[position].intent)
            }
            holder.update(data[position].nome)
        }

        override fun getItemCount(): Int {
            return data.size
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.lists_menu, menu)


        val actionSearch = menu!!.findItem(R.id.search)
        val searchViewEditText = actionSearch.actionView as SearchView

        searchViewEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var s = DadosLista.getListById(ProductsActivity.id)?.search(query)
                if (s?.isNotEmpty()!!)
                    rvList.adapter = ProductsActivity.MyRVAdapter(s)
                else{
                    Toast.makeText(this@MainActivity, "That product doesnt exists", Toast.LENGTH_LONG).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rvList.adapter = MainActivity.MyRVAdapter(DadosLista.data)
                return false
            }
        })

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort_name) {
            DadosLista.getListById(ProductsActivity.id)?.sortByName()
            rvList.adapter = ProductsActivity.MyRVAdapter(DadosLista.getListById(ProductsActivity.id)?.dataP!!)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}