package tech.okcredit.create_pdf.custom

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import tech.okcredit.create_pdf.adapter.RecyclerPdfViewerAdapter
import tech.okcredit.create_pdf.utils.PDFUtil
import java.io.File

class RecyclerPDFViewer : RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun loadPdf(pdfFile: File?) {
        try {
            val pdfBitmapList = PDFUtil.pdfToBitmap(pdfFile)
            var layoutManager = LinearLayoutManager(this.context)
            val snapHelper: SnapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(this)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = layoutManager
            this.adapter = RecyclerPdfViewerAdapter(pdfBitmapList)
            this.isEnabled = false
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}