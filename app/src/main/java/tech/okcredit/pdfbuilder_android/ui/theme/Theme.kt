package tech.okcredit.pdfbuilder_android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import tech.okcredit.pdfbuilder_android.ui.theme.OkcColors
import tech.okcredit.pdfbuilder_android.ui.theme.OkcColorsGreyBg
import tech.okcredit.pdfbuilder_android.ui.theme.OkcShapes
import tech.okcredit.pdfbuilder_android.ui.theme.OkcTypography

@Composable
fun OkcTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = OkcColors,
        typography = OkcTypography,
        shapes = OkcShapes,
        content = content
    )
}

@Composable
fun OkcGreyTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = OkcColorsGreyBg,
        typography = OkcTypography,
        shapes = OkcShapes,
        content = content
    )
}
