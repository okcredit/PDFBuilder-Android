package tech.okcredit.pdfbuilder_android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ai.billinguicomponents.R

val textLineSpacing1 = (-0.05).sp
val textLineSpacing2 = 0.sp
val textLineSpacing3 = 0.012.sp
val textLineSpacing4 = 0.013.sp
val textLineSpacing5 = 0.014.sp
val textLineSpacing6 = 0.015.sp
val textLineSpacing7 = 0.016.sp
val textLineSpacing8 = 0.02.sp
val textLineSpacing9 = 0.025.sp
val textLineSpacing10 = 0.05.sp

private val font = FontFamily(
    Font(R.font.noto_sans),
    Font(R.font.noto_sans_semi_bold, weight = FontWeight.SemiBold),
)

val headline1 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 64.sp,
    letterSpacing = textLineSpacing1,
    color = grey900,
)

val headline2 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 48.sp,
    letterSpacing = textLineSpacing2,
    color = grey900,
)

val headline3 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 32.sp,
    letterSpacing = textLineSpacing8,
    color = grey900,
)

val headline4 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
    letterSpacing = textLineSpacing2,
    color = grey900,
)

val headline5 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    letterSpacing = textLineSpacing6,
    color = grey900,
)

val headline6 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 18.sp,
    letterSpacing = textLineSpacing5,
    color = grey900,
)

val subtitle1 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    letterSpacing = textLineSpacing8,
    color = grey900,
)

val subtitle2 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
    letterSpacing = textLineSpacing6,
    color = grey900,
)

val subtitle3 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 13.sp,
    letterSpacing = textLineSpacing4,
    color = grey900,
)

val subtitle4 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
    letterSpacing = textLineSpacing3,
    color = grey900,
)

val body1 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    letterSpacing = textLineSpacing10,
    color = grey900,
)

val body2 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    letterSpacing = textLineSpacing9,
    color = grey900,
)

val body4 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    letterSpacing = textLineSpacing7,
    color = grey900,
)

val button = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
    letterSpacing = textLineSpacing6,
    color = grey900,
)

val darkSolidButtonTextStyle = button.copy(
    color = white
)

val lightSolidButtonTextStyle = button.copy(
    color = green_primary
)

val darkOutlineButtonTextStyle = button.copy(
    color = green_primary
)

val extendedFabTextStyle = button.copy(
    color = white
)

val caption1 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    letterSpacing = textLineSpacing3,
    color = grey900,
)

val caption2 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
    letterSpacing = textLineSpacing7,
    color = grey900,
)

val caption3 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 10.sp,
    letterSpacing = textLineSpacing6,
    color = grey900,
)

val caption3Normal = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
    letterSpacing = textLineSpacing6,
    color = grey900,
)

val caption4 = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.SemiBold,
    fontSize = 8.sp,
    letterSpacing = textLineSpacing6,
    color = grey900,
)

val overline = TextStyle(
    fontFamily = font,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Italic,
    fontSize = 10.sp,
    letterSpacing = textLineSpacing8,
    color = grey600,
)

val OkcTypography = Typography(
    defaultFontFamily = font,
    h1 = headline1,
    h2 = headline2,
    h3 = headline3,
    h4 = headline4,
    h5 = headline5,
    h6 = headline6,
    subtitle1 = subtitle1,
    subtitle2 = subtitle2,
    body1 = body1,
    body2 = body2,
    button = button,
    caption = caption1,
    overline = overline
)

val Typography.subtitle3: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.subtitle3

val Typography.subtitle4: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.subtitle4

val Typography.caption1: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.caption1

val Typography.caption2: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.caption2

val Typography.caption3: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.caption3

val Typography.caption3Normal: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.caption3Normal

val Typography.caption4: TextStyle
    get() = tech.okcredit.pdfbuilder_android.ui.theme.caption4
