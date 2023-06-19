package tech.okcredit.create_pdf.views.basic

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import java.io.Serializable

class PDFFrameLayout(context: Context?) : PDFView(context!!), Serializable {
    init {
        val linearLayout = FrameLayout(context!!)
        val childLayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER
        )
        linearLayout.layoutParams = childLayoutParams
        super.setView(linearLayout)
    }

    public override fun addView(viewToAdd: PDFView): PDFFrameLayout {
        view.addView(viewToAdd.view)
        super.addView(viewToAdd)
        return this
    }

    override fun setLayout(layoutParams: ViewGroup.LayoutParams): PDFView {
        return super.setLayout(layoutParams)
    }

    override fun getView(): FrameLayout {
        return super.getView() as FrameLayout
    }

    companion object {
        private const val TAG = "PDFFrameLayout"
    }
}