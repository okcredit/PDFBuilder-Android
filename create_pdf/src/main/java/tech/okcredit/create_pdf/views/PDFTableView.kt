package tech.okcredit.create_pdf.views

import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import tech.okcredit.create_pdf.views.basic.PDFHorizontalView
import tech.okcredit.create_pdf.views.basic.PDFLineSeparatorView
import tech.okcredit.create_pdf.views.basic.PDFTextView
import tech.okcredit.create_pdf.views.basic.PDFVerticalView
import tech.okcredit.create_pdf.views.basic.PDFView
import java.io.Serializable

class PDFTableView(
    context: Context,
    private val headerRow: PDFTableRowView,
    private val firstRow: PDFTableRowView,
    private val color: Int = Color.RED
) : PDFView(context), Serializable {
    private var rowWidthPercent = intArrayOf()

    init {
        val verticalView = PDFVerticalView(context)
        verticalView.addView(headerRow)
        verticalView.addView(PDFLineSeparatorView(context).setBackgroundColor(color))
        verticalView.addView(firstRow)
        super.addView(verticalView)
    }

    /**
     * Does some thing in old style.
     *
     */
    @Deprecated("use {addRow()} instead.")
    @Throws(IllegalStateException::class)
    public override fun addView(viewToAdd: PDFView): PDFTableView {
        throw IllegalStateException("Add a row or column to add view")
    }

    /**
     * Add new row to table
     * A new row will be added with columnWidth (if provided)
     *
     * @param rowView row to add
     * @return current instance
     */
    fun addRow(rowView: PDFTableRowView): PDFTableView {
        if (rowWidthPercent.size > 0) {
            rowView.setColumnWidth(*rowWidthPercent)
        }
        super.addView(rowView)
        return this
    }

    /**
     * Set column width for every row
     * After calling this function, every row and header will follow this column width guideline
     *
     * @param columnWidthPercent width in percent {sum should be 100 percent}
     * @return current instance
     */
    fun setColumnWidth(vararg columnWidthPercent: Int): PDFTableView {
        headerRow.setColumnWidth(*columnWidthPercent)
        firstRow.setColumnWidth(*columnWidthPercent)
        for (pdfTableRow in childViewList) {
            if (pdfTableRow is PDFTableRowView) {
                pdfTableRow.setColumnWidth(*columnWidthPercent)
            }
        }
        rowWidthPercent = columnWidthPercent
        return this
    }

    fun addSeparatorRow(separatorView: PDFLineSeparatorView?): PDFTableView {
        super.addView(separatorView!!)
        return this
    }

    override fun setLayout(layoutParams: ViewGroup.LayoutParams): PDFView {
        return super.setLayout(layoutParams)
    }

    class PDFTableRowView(context: Context) : PDFHorizontalView(context), Serializable {
        /**
         * Does some thing in old style.
         *
         */
        @Deprecated("use {PDFTableRowView.addToRow()} instead.")
        override fun addView(viewToAdd: PDFView): PDFHorizontalView {
            throw IllegalStateException("Cannot add subview to Horizontal View, Use createRow instead")
        }

        /**
         * Set custom weight to each column in a row
         *
         * @param columnWidthPercent percent weight of column out of 100
         * @return current instance
         */
        fun setColumnWidth(vararg columnWidthPercent: Int): PDFTableRowView {
            for (i in childViewList.indices) {
                var columnWeight = 100f
                columnWeight = if (i < columnWidthPercent.size) {
                    columnWidthPercent[i].toFloat()
                } else {
                    columnWidthPercent[columnWidthPercent.size - 1].toFloat()
                }
                val pdfView = childViewList[i]
                pdfView.setLayout(
                    LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT, columnWeight / 100
                    )
                )
            }
            return this
        }

        /**
         * Add row to table, call addRow with equal number of views each time
         *
         * @param TextViewToAdd add text
         * @return current instance
         */
        fun addToRow(TextViewToAdd: PDFTextView): PDFTableRowView {
            TextViewToAdd.setLayout(
                LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT, 1f
                )
            )
            super.addView(TextViewToAdd)
            return this
        }
    }

    companion object {
        private const val TAG = "PDFTableView"
    }
}