package tech.okcredit.create_pdf.views.basic

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import java.io.Serializable

class PDFCustomView : PDFView, Serializable {
    private constructor(context: Context) : super(context)
    constructor(context: Context, view: View, width: Int, height: Int) : super(context) {
        view.layoutParams = ViewGroup.LayoutParams(width, height)
        super.setView(view)
    }

    constructor(
        context: Context,
        view: View,
        width: Int,
        height: Int,
        weight: Int
    ) : super(context) {
        view.layoutParams = LinearLayout.LayoutParams(width, height, weight.toFloat())
        super.setView(view)
    }

    override fun getView(): View {
        return super.getView()
    }
}