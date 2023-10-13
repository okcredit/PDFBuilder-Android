package tech.okcredit.pdfbuilder_android

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.ai.billinguicomponents.R
import tech.okcredit.create_pdf.activity.PDFCreatorActivity
import tech.okcredit.create_pdf.utils.PDFUtil.PDFUtilListener
import tech.okcredit.create_pdf.views.PDFBody
import tech.okcredit.create_pdf.views.PDFFooterView
import tech.okcredit.create_pdf.views.PDFHeaderView
import tech.okcredit.create_pdf.views.PDFTableView
import tech.okcredit.create_pdf.views.PDFTableView.PDFTableRowView
import tech.okcredit.create_pdf.views.basic.PDFHorizontalView
import tech.okcredit.create_pdf.views.basic.PDFImageView
import tech.okcredit.create_pdf.views.basic.PDFLineSeparatorView
import tech.okcredit.create_pdf.views.basic.PDFTextView
import tech.okcredit.pdfbuilder_android.PdfBillConstants.PDF_BILL_SEPARATOR_COLOR
import tech.okcredit.pdfbuilder_android.ui.theme.green_primary
import java.io.File

class PreviewActivity : PDFCreatorActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbarTitle(
            title = "Bill Preview",
            onBackClicked = {
                onBackPressed()
            }
        )

        setBottomUIComposeContent(
            composable = {
                Text(
                    text = "Hello World!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = green_primary
                    ).copy(textAlign = TextAlign.Center)
                )
            }
        )

        createPDF("test", object : PDFUtilListener {
            override fun pdfGenerationSuccess(savedPDFFile: File) {
                Toast.makeText(this@PreviewActivity, "PDF Created", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun pdfGenerationFailure(exception: Exception) {
                Toast.makeText(
                    this@PreviewActivity,
                    "PDF NOT Created",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


    }

    override fun getHeaderView(forPage: Int): PDFHeaderView {
        val headerView = PDFHeaderView(applicationContext)
        val horizontalView = PDFHorizontalView(applicationContext)

        val pdfTextView = TaxInvoiceTitle(context = applicationContext)
        horizontalView.addView(pdfTextView)
        headerView.addView(horizontalView)

        val lineSeparatorView =
            PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        headerView.addView(lineSeparatorView)


        val horizontalViewBusinessName = PDFHorizontalView(applicationContext)
        val businessTitle = BusinessTitleTextView(context = applicationContext, "Merchant Name")
        horizontalViewBusinessName.addView(businessTitle)
        headerView.addView(horizontalViewBusinessName)

        val horizontalViewBillDetails1 = PDFHorizontalView(applicationContext)
        val businessAddress = BusinessAddressTextView(
            context = applicationContext,
            "123 Elm Street, Apt. 4B, Springfield, IL 62704, USA"
        )
        horizontalViewBillDetails1.addView(businessAddress)
        val businessGst = BusinessGstTextView(context = applicationContext, "Bill Number: DA-34322")
        horizontalViewBillDetails1.addView(businessGst)
        headerView.addView(horizontalViewBillDetails1)


        val horizontalViewBillDetails2 = PDFHorizontalView(applicationContext)
        val businessAddress2 =
            BusinessAddressTextView(context = applicationContext, "GSTIN: WWWWWWWWWWWWW")
        horizontalViewBillDetails2.addView(businessAddress2)
        val businessGst2 =
            BusinessGstTextView(context = applicationContext, "Bill Date: XX-XX-XXXX")
        horizontalViewBillDetails2.addView(businessGst2)
        headerView.addView(horizontalViewBillDetails2)


        return headerView
    }

    override fun getBodyViews(): PDFBody {
        val pdfBody = PDFBody()

        val lineSeparatorView2 =
            PDFLineSeparatorView(applicationContext).setBackgroundColor(Color.WHITE)
        lineSeparatorView2.setLayout(
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                8, 0f
            )
        )
        pdfBody.addView(lineSeparatorView2)

        val lineSeparatorView3 =
            PDFLineSeparatorView(applicationContext).setBackgroundColor(
                Color.parseColor(
                    PDF_BILL_SEPARATOR_COLOR
                )
            )
        pdfBody.addView(lineSeparatorView3)

        val widthPercent = intArrayOf(10, 25, 15, 10, 15, 15, 15) // Sum should be equal to 100%

        val textInTable = arrayOf("S.No", "Item Name", "Code", "Qty", "Rate", "GST", "Total")

        val tableHeader = PDFTableRowView(applicationContext)
        for (s in textInTable) {
            val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P)
            pdfTextView.setText(s)
            pdfTextView.setPadding(0, 10, 0, 10)
            tableHeader.setBackgroundColor(Color.parseColor("#E6F4E9"))
            tableHeader.addToRow(pdfTextView)
        }

        val tableRowView1 = PDFTableRowView(applicationContext)

        val tableView = PDFTableView(
            applicationContext,
            tableHeader,
            tableRowView1,
            Color.parseColor(PDF_BILL_SEPARATOR_COLOR)
        )

        for (i in 0..10) {
            // Create 10 rows
            val tableRowView = PDFTableRowView(applicationContext)
            for (s in textInTable) {
                val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P)
                pdfTextView.setText("$s: $i")
                pdfTextView.setPadding(0, 10, 0, 10)
                tableRowView.addToRow(pdfTextView)
            }
            tableView.addRow(tableRowView)
        }
        tableView.setColumnWidth(*widthPercent)
        pdfBody.addView(tableView)

        val lineSeparatorView4 =
            PDFLineSeparatorView(applicationContext).setBackgroundColor(
                Color.parseColor(
                    PDF_BILL_SEPARATOR_COLOR
                )
            )
        pdfBody.addView(lineSeparatorView4)

        val pdfIconLicenseView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H3)
        val icon8Link = HtmlCompat.fromHtml(
            "Total: <b>₹ 1000</b>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        pdfIconLicenseView.view.text = icon8Link
        pdfBody.addView(pdfIconLicenseView)

        return pdfBody
    }

    override fun getFooterView(pageIndex: Int): PDFFooterView {
        val footerView = PDFFooterView(applicationContext)

        val horizontalView = PDFHorizontalView(applicationContext)
        horizontalView.addView(
            BillGeneratedFromOkc(
                context = applicationContext,
                text = "Bill Generated with OkCredit"
            )
        )


        footerView.addView(horizontalView)

        return footerView
    }

    override fun getWatermarkView(forPage: Int): PDFImageView? {
        val pdfImageView = PDFImageView(applicationContext)
        val childLayoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            120, Gravity.CENTER
        )
        pdfImageView.setLayout(childLayoutParams)
        pdfImageView.setImageResource(R.drawable.ic_watermark_as_pdf)
        pdfImageView.setImageScale(ImageView.ScaleType.FIT_CENTER)
        pdfImageView.view.rotation = -45f
        pdfImageView.view.alpha = 0.1f
        return pdfImageView
    }

    override fun onNextClicked(savedPDFFile: File?) {
// TODO: Add a Preview Activity
//        val pdfUri = Uri.fromFile(savedPDFFile)
//        val intentPdfViewer =
//            Intent(this@PdfCreatorExampleActivity, PdfViewerExampleActivity::class.java)
//        intentPdfViewer.putExtra(PdfViewerExampleActivity.PDF_FILE_URI, pdfUri)
//        startActivity(intentPdfViewer)
    }


}

