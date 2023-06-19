package tech.okcredit.create_pdf.views.basic

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.Serializable

class PDFImageView(context: Context) : PDFView(context), Serializable {
    init {
        val imageView = ImageView(context)
        val childLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, 0f
        )
        imageView.layoutParams = childLayoutParams
        super.setView(imageView)
    }

    @Throws(IllegalStateException::class)
    override fun addView(viewToAdd: PDFView): PDFView {
        throw IllegalStateException("Cannot add subview to Image")
    }

    fun setImageResource(@DrawableRes resId: Int): PDFImageView {
        (super.getView() as ImageView).setImageResource(resId)
        return this
    }

    fun setImageBitmap(bitmap: Bitmap): PDFImageView {
        (super.getView() as ImageView).setImageBitmap(bitmap)
        return this
    }

    @Throws(FileNotFoundException::class)
    fun setImageFile(imageFile: File): PDFImageView {
        val fileInputStream = FileInputStream(imageFile)
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        val newAddedImageBitmap = BitmapFactory.decodeStream(fileInputStream, null, options)
        (super.getView() as ImageView).clearColorFilter()
        (super.getView() as ImageView).setImageBitmap(newAddedImageBitmap)
        return this
    }

    fun setImageScale(scaleType: ScaleType): PDFImageView {
        (super.getView() as ImageView).scaleType = scaleType
        return this
    }

    override fun setLayout(layoutParams: ViewGroup.LayoutParams): PDFImageView {
        super.setLayout(layoutParams)
        return this
    }

    override fun getView(): ImageView {
        return super.getView() as ImageView
    }
}