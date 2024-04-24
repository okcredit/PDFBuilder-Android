package tech.okcredit.pdfbuilder_android

import android.os.Bundle
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.okcredit.create_pdf.activity.PDFViewerActivity


class PdfPreviewActivity : PDFViewerActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBottomUIComposeContent {
            Text(
                text = "Hello World!",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 30.sp,
                )
            )
        }

    }
}