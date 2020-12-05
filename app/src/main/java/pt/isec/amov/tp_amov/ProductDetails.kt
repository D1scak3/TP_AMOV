package pt.isec.amov.tp_amov

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.img_dialog.view.*
import pt.isec.amov.tp_amov.Dados.Category
import pt.isec.amov.tp_amov.Dados.DadosLista
import pt.isec.amov.tp_amov.Dados.DadosProduto
import pt.isec.amov.tp_amov.Dados.Produtos
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*

private const val assetImgPath = "Images/defaultImg.png"

class ProductDetails : AppCompatActivity() {

    var filePath: String? = null

    var id : Int = 0
    var idList : Int = 0
    lateinit var produtos : Produtos
    lateinit var product : DadosProduto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        DadosLista.categories.add(Category("teste"))

        spinnerCategory.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, DadosLista.getCategoryNames())


        Utils.setImgFromAsset(img_detail, assetImgPath)
        var teste2 = intent.getIntExtra("teste", 77)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Utils.setImgFromAsset(img_detail, assetImgPath)
        id = intent.getIntExtra("ProductId", -1)
        idList = intent.getIntExtra("ListId", -1)
        produtos = DadosLista.getListById(idList)!!

        produtos.intent.replaceExtras(produtos.intent).putExtra("idList", idList)

        product = DadosLista.getListById(DadosLista.lastList)!!.getProductById(id)!!
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m: Method =
                        StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        val product : DadosProduto? = produtos.getProductById(id)

        loadInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, info: Intent?) {
        if (requestCode == 70 && resultCode == Activity.RESULT_OK) {
            Utils.setPic(img_detail, filePath!!)

            produtos.getProductById(id)?.setProductImgPath(filePath!!)

            return
        }
        if(requestCode == 80 && resultCode == Activity.RESULT_OK && info!=null){
            val uri = info.data?.apply {
                val cursor = contentResolver.query(this,
                        arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
                if (cursor !=null && cursor.moveToFirst())
                    filePath = cursor.getString(0)
            }
            Utils.setPic(img_detail, filePath!!)

            produtos.getProductById(id)?.setProductImgPath(filePath!!)

            if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1234)
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, info)
    }


    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            filePath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    return
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "pt.isec.amov.tp_amov.fileProvider",
                            it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, 70)
                }
            }
        }
    }

    fun chooseImg(view: View){
        var dialog = LayoutInflater.from(this).inflate(R.layout.img_dialog, null)


        val alertDialog = AlertDialog.Builder(this)
                .setView(dialog)
                .setTitle("Select image from")
                .create()


        alertDialog.show()


    }

    fun onCancel(view: View){
        finish()
    }

    fun onSave(view: View){

        var notes = notes.text
        product.note = notes.toString()

        product.pname = product_name.text.toString()

        product.img = Utils.imageTobitmap(img_detail)

        finish()
    }

    fun loadInfo(){
        if(product.note.isNotEmpty())
            notes.setText(product.note)
        product_name.setText(product.pname)

        if(product.img != null){
            Utils.BitmapToImage(product.img!!, img_detail)
        }

    }

    fun onCameraImg(view: View) { dispatchTakePictureIntent() }
    fun onGaleryImg(view: View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, 80)
    }
}