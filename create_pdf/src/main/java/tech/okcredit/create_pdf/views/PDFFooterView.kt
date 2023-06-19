package tech.okcredit.create_pdf.views

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import tech.okcredit.create_pdf.views.basic.PDFCustomView
import tech.okcredit.create_pdf.views.basic.PDFVerticalView
import tech.okcredit.create_pdf.views.basic.PDFView
import java.io.Serializable

class PDFFooterView(context: Context) : PDFVerticalView(context), Serializable {
    init {
        val emptySpaceView = PDFCustomView(context, View(context), 0, 0, 1)
        addView(emptySpaceView)
    }

    override fun addView(viewToAdd: PDFView): PDFFooterView {
        super.addView(viewToAdd)
        return this
    }

    override fun getView(): LinearLayout {
        return super.getView() as LinearLayout
    }
}