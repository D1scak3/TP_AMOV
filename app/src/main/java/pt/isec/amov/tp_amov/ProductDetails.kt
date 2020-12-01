package pt.isec.amov.tp_amov

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_product_details.*
import pt.isec.amov.tp_amov.Dados.DadosProduto
import pt.isec.amov.tp_amov.Dados.Produtos
import java.io.File
import java.io.IOException
import java.lang.reflect.Method
import java.text.SimpleDateFormat
import java.util.*

private const val assetImgPath = "Images/defaultImg.png"

class ProductDetails : AppCompatActivity() {

    var filePath: String? = null
    var id : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Utils.setImgFromAsset(img_detail, assetImgPath)
        id = intent.getIntExtra("ProductId", -1)


        if (Build.VERSION.SDK_INT >= 24) {
            try {
                val m: Method =
                        StrictMode::class.java.getMethod("disableDeathOnFileUriExposure")
                m.invoke(null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        val product : DadosProduto? = Produtos.getProductById(id)
        if(!product?.getProductImgPath()?.isEmpty()!!) {
            Utils.setPic(img_detail, product.getProductImgPath())
            tv1.setText(product.getProductImgPath())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, info: Intent?) {
        if (requestCode == 70 && resultCode == Activity.RESULT_OK) {
            Utils.setPic(img_detail, filePath!!)
            Produtos.getProductById(id)?.setProductImgPath(filePath!!)
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
            Produtos.getProductById(id)?.setProductImgPath(filePath!!)
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

    @SuppressLint("SimpleDateFormat")
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

    fun onCameraImg(view: View) { dispatchTakePictureIntent() }
    fun onGaleryImg(view:View){
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, 80)
    }
}