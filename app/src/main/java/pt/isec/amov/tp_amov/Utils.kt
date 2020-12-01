package pt.isec.amov.tp_amov

import android.content.res.AssetManager
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
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

    }
}