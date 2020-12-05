package pt.isec.amov.tp_amov

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import java.io.*
import kotlin.math.min

class Utils {
    companion object {
        fun setPic(view: View, path: String) {
            val targetW = view.width
            val targetH = view.height
            if (targetH < 1 || targetW < 1)
                return
            val bmpOptions = BitmapFactory.Options()
            bmpOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, bmpOptions)
            val photoW = bmpOptions.outWidth
            val photoH = bmpOptions.outHeight
            val scale = min(photoW / targetW, photoH / targetH)
            bmpOptions.inSampleSize = scale
            bmpOptions.inJustDecodeBounds = false
            val bitmap = BitmapFactory.decodeFile(path, bmpOptions)
            when {
                view is ImageView -> (view as ImageView).setImageBitmap(bitmap)
                //else -> view.background = bitmap.toDrawable(view.resources)
                else -> view.background = BitmapDrawable(view.resources, bitmap)
            }
        }

        fun getFileFromAsset(assetManager: AssetManager, strName: String): InputStream? {
            var istr: InputStream? = null
            try {
                istr = assetManager.open(strName)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return istr
        }

        fun setImgFromAsset(mImageView: ImageView, strName: String) {
            val assetManager = mImageView.context.assets
            try {
                val istr = assetManager.open(strName)
                val d = Drawable.createFromStream(istr, null)
                mImageView.setImageDrawable(d)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun BitmapToImage(image:ByteArray, view: ImageView){
            val bitmap = BitmapFactory.decodeByteArray(image,0,image.size)
            view.setImageBitmap(Bitmap.createScaledBitmap(bitmap,128,128,false))
        }

        fun imageTobitmap(image:ImageView): ByteArray {
            val bitmap = (image.drawable as BitmapDrawable).bitmap
            val sout = ByteArrayOutputStream()

            bitmap.compress(Bitmap.CompressFormat.PNG,90,sout)

            return sout.toByteArray()
        }

    }
}