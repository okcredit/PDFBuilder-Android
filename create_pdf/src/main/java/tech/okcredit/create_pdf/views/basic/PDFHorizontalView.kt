package tech.okcredit.create_pdf.views.basic

import android.content.Context
import android.view.ViewGroup
import android.widget.LinearLayout
import java.io.Serializable

open class PDFHorizontalView(context: Context?) : PDFView(context!!), Serializable {
    init {
        val linearLayout = LinearLayout(context)
        val childLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, 0f
        )
        linearLayout.layoutParams = childLayoutParams
        linearLayout.orientation = LinearLayout.HORIZONTAL
        super.setView(linearLayout)
    }

    public override fun addView(viewToAdd: PDFView): PDFHorizontalView {
        view.addView(viewToAdd.view)
        super.addView(viewToAdd)
        return this
    }

    override fun setLayout(layoutParams: ViewGroup.LayoutParams): PDFHorizontalView {
        super.setLayout(layoutParams)
        return this
    }

    override fun getView(): LinearLayout {
        return super.getView() as LinearLayout
    }
}