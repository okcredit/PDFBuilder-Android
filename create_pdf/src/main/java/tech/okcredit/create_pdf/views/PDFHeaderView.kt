package tech.okcredit.create_pdf.views

import android.content.Context
import android.widget.LinearLayout
import tech.okcredit.create_pdf.views.basic.PDFVerticalView
import tech.okcredit.create_pdf.views.basic.PDFView
import java.io.Serializable

class PDFHeaderView(context: Context) : PDFVerticalView(context), Serializable {
    override fun addView(viewToAdd: PDFView): PDFHeaderView {
        super.addView(viewToAdd)
        return this
    }

    override fun getView(): LinearLayout {
        return super.getView() as LinearLayout
    }
}