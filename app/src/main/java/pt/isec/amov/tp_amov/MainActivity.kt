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
import kotlinx.android.synthetic.main.activity_dialog.view.*
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosProduto
import pt.isec.amov.tp_amov.Dados.Produtos

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

}