package tech.okcredit.pdfbuilder_android

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.widget.LinearLayout
import tech.okcredit.create_pdf.views.basic.PDFTextView


object PdfBillConstants {
    const val PDF_BILL_SEPARATOR_COLOR = "#bdbdbd"
}

fun TaxInvoiceTitle(context: Context): PDFTextView {
    val pdfTextView = PDFTextView(context, PDFTextView.PDF_TEXT_SIZE.H1)
    pdfTextView.setBackgroundColor(Color.parseColor("#30A74F"))
    val word = SpannableString("Tax Invoice")
    word.setSpan(
        ForegroundColorSpan(Color.WHITE),
        0,
        word.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    pdfTextView.text = word
    pdfTextView.setLayout(
        LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1f
        )
    )
    pdfTextView.view.gravity = Gravity.END
    pdfTextView.view.setTypeface(pdfTextView.view.typeface, Typeface.BOLD)
    return pdfTextView
}

fun BusinessTitleTextView(context: Context, text: String): PDFTextView {
    val pdfTextView = PDFTextView(context, PDFTextView.PDF_TEXT_SIZE.H2)
    val word = SpannableString(text)
    word.setSpan(
        ForegroundColorSpan(Color.BLACK),
        0,
        word.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    pdfTextView.text = word
    pdfTextView.setLayout(
        LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1f
        )
    )
    pdfTextView.view.gravity = Gravity.START
    pdfTextView.view.setTypeface(pdfTextView.view.typeface, Typeface.BOLD)
    return pdfTextView
}

fun BusinessAddressTextView(context: Context, text: String): PDFTextView {
    val pdfTextView = PDFTextView(context, PDFTextView.PDF_TEXT_SIZE.P)
    val word = SpannableString(text)
    word.setSpan(
        ForegroundColorSpan(Color.BLACK),
        0,
        word.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    pdfTextView.text = word
    pdfTextView.setLayout(
        LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1f
        )
    )
    pdfTextView.view.gravity = Gravity.START
    pdfTextView.view.setTypeface(pdfTextView.view.typeface, Typeface.NORMAL)
    return pdfTextView
}

fun BusinessGstTextView(context: Context, text: String): PDFTextView {
    val pdfTextView = PDFTextView(context, PDFTextView.PDF_TEXT_SIZE.P)
    val word = SpannableString(text)
    word.setSpan(
        ForegroundColorSpan(Color.BLACK),
        0,
        word.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    pdfTextView.setText(text)
    pdfTextView.setLayout(
        LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1f
        )
    )
    pdfTextView.view.gravity = Gravity.END
    pdfTextView.view.setTypeface(pdfTextView.view.typeface, Typeface.NORMAL)
    return pdfTextView
}

fun BillGeneratedFromOkc(context: Context, text: String): PDFTextView {
    val pdfTextView = PDFTextView(context, PDFTextView.PDF_TEXT_SIZE.H3)
    pdfTextView.setBackgroundColor(Color.parseColor("#30A74F"))
    val word = SpannableString(text)
    word.setSpan(
        ForegroundColorSpan(Color.WHITE),
        0,
        word.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    pdfTextView.text = word
    pdfTextView.setPadding(0, 10, 0, 10)
    pdfTextView.setLayout(
        LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT, 1f
        )
    )
    pdfTextView.view.gravity = Gravity.CENTER_HORIZONTAL
    pdfTextView.view.setTypeface(pdfTextView.view.typeface, Typeface.BOLD)
    return pdfTextView
}