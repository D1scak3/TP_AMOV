package pt.isec.amov.tp_amov

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_carrinho.*
import kotlinx.android.synthetic.main.activity_dialog.view.*
import kotlinx.android.synthetic.main.activity_products.*
import kotlinx.android.synthetic.main.product_list_item.view.*
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosProduto
import pt.isec.amov.tp_amov.Dados.Produtos

class ProductsActivity : AppCompatActivity() {
    companion object{
        var id : Int = 0
        var strBtn = "Add to cart"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        id = DadosLista.lastList
        println(id)
        rvList.layoutManager = LinearLayoutManager(this@ProductsActivity, LinearLayoutManager.VERTICAL, false)
        rvList.adapter = MyRVAdapter(DadosLista.getListById(DadosLista.lastList)?.data!!)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        rvList.adapter = MyRVAdapter(DadosLista.getListById(id)?.data!!)
    }





    fun onDelete(view: View){
        DadosLista.getListById(id)?.removeSelectedProducts()
        rvList.adapter = MyRVAdapter(DadosLista.getListById(id)?.data!!)
    }


    fun onPlus(view: View){
        //produtos.add("teste")

        //val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, produtos)
        //lv.adapter = adapter
        var intent = Intent(this@ProductsActivity, ProductDetails::class.java)
        var intent2 = Intent(this@ProductsActivity, ProductDetails::class.java)
        var intent3 = Intent(this@ProductsActivity, ProductDetails::class.java)

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
            }else if(qty.toDouble() <= 0){
                Toast.makeText(this, "A quantidade tem de ser maior que zero", Toast.LENGTH_LONG).show()
            }
            else{
                var item = DadosProduto("b", "fruta", qty.toDouble(), "gostoso", 5, intent)
                var item2 = DadosProduto("a", "fruta", qty.toDouble(), "gostoso", 5, intent2)
                var item3 = DadosProduto("c", "fruta", qty.toDouble(), "gostoso", 5, intent3)
                DadosLista.getListById(id)?.addProduct(item)
                DadosLista.getListById(id)?.addProduct(item2)
                DadosLista.getListById(id)?.addProduct(item3)
                alertDialog.dismiss()
            }


        }

        dialog.cancel_button.setOnClickListener{
            alertDialog.dismiss()
        }

        rvList.adapter = MyRVAdapter(DadosLista.getListById(id)?.data!!)

    }



    class MyRVAdapter(val data: ArrayList<DadosProduto>) : RecyclerView.Adapter<MyRVAdapter.MyViewHolder>() {

        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var tv1 : TextView = view.findViewById(R.id.name)
            var tv2 : TextView = view.findViewById(R.id.name2)
            var tv3 : TextView = view.findViewById(R.id.name3)
            var btn : Button = view.findViewById(R.id.addToCartBtn)

            fun update(str1: String, str2: String, str3: String, btnStr : String) {
                tv1.text = str1
                tv2.text = str2
                tv3.text = str3
                btn.text = btnStr
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

            holder.itemView.setOnClickListener{ it ->

                if(data[position].selectedToDelete == true){
                    data[position].setToDestroy(false)
                    var tv : TextView = it.findViewById(R.id.name)
                    tv.setText("deselected")
                }else {
                    var context = holder.itemView.context
                    data[position].intent.putExtra("ProductId", data[position].id)
                    data[position].intent.putExtra("ListId", id)
                    context.startActivity(data[position].intent)
                }

            }

            holder.itemView.setOnLongClickListener { it ->

                if(!DadosLista.getListById(id)?.getDelete()!!){
                    DadosLista.getListById(id)?.setDelete(true)
                }

                var tv : TextView = it.findViewById(R.id.name)
                tv.text = "selected"
                data[position].setToDestroy(true)
                return@setOnLongClickListener true
            }

            holder.itemView.addToCartBtn.setOnClickListener{
                var product = DadosLista.getListById(DadosLista.lastList)!!.getProductById(data[position].id)
                if(!product!!.noCarrinho) {//adiciona ao carrinho
                    DadosLista.getListById(DadosLista.lastList)!!.carrinho.add(product)
                    product.noCarrinho = true
                    holder.itemView.addToCartBtn.setText("Remove from cart")
                    product.noCarrinhoStr = "Remove from cart"

                }else{//remove do carrinho
                    DadosLista.getListById(DadosLista.lastList)!!.carrinho.remove(product)
                    product.noCarrinho = false
                    holder.itemView.addToCartBtn.setText("Add to cart")
                    product.noCarrinhoStr = "Add to cart"

                }
            }

            holder.update(data[position].pname, data[position].category, data[position].pname, data[position].noCarrinhoStr)


        }

        override fun getItemCount(): Int = data.size
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_products_list, menu)


        val actionSearch = menu!!.findItem(R.id.search)
        val searchViewEditText = actionSearch.actionView as SearchView

        searchViewEditText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                var s = DadosLista.getListById(id)?.search(query)
                if (s?.isNotEmpty()!!)
                    rvList.adapter = MyRVAdapter(s)
                else{
                    Toast.makeText(this@ProductsActivity, "That product doesnt exists", Toast.LENGTH_LONG).show()
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                rvList.adapter = MyRVAdapter(DadosLista.getListById(id)?.data!!)
                return false
            }
        })

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.sort_name) {
            DadosLista.getListById(id)?.sortByName()
            rvList.adapter = MyRVAdapter(DadosLista.getListById(id)?.data!!)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun onCarrinho(item: MenuItem) {

        var intent = Intent(this, CarrinhoActivity::class.java)

        startActivity(intent)

    }


}


