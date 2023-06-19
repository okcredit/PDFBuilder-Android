package tech.okcredit.create_pdf.views

import tech.okcredit.create_pdf.views.basic.PDFView
import java.io.Serializable

class PDFBody : Serializable {
    val childViewList = ArrayList<PDFView>()
    fun addView(pdfViewToAdd: PDFView): PDFBody {
        if (pdfViewToAdd is PDFTableView) {
            childViewList.addAll(pdfViewToAdd.getChildViewList())
        } else {
            childViewList.add(pdfViewToAdd)
        }
        return this
    }
}