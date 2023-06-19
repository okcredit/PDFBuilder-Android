package merechant.okcredit.resources.theme

import androidx.compose.material.Colors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

// Neutrals
val white = Color(0xFFffffff)
val black = Color(0xFF000000)
val grey50 = Color(0xFFEDF1F2)
val grey100 = Color(0xFFDAE2E6)
val grey200 = Color(0xFFCBD7DC)
val grey300 = Color(0xFFe0e0e0)
val grey400 = Color(0xFFbdbdbd)
val grey500 = Color(0xFF9e9e9e)
val grey600 = Color(0xFF757575)
val grey700 = Color(0xFF616161)
val grey800 = Color(0xFF424242)
val grey900 = Color(0xFF212121)
val transparent = Color(0x1E212121)

// Reds
val red_primary = Color(0xFFD93025)
val red_lite = Color(0xFFFFEBED)
val red_lite_1 = Color(0xFFFFCDD0)
val red_ada = Color(0xFFCC291E)
val red_lite_pink = Color(0xFFFDF3F0)
val red_lite_outline = Color(0xFFF8CABF)
val red_soft = Color(0xFFFDF3F0)

// Greens
val green_ada = Color(0xFF117631)
val green_primary = Color(0xFF1c873b)
val green_1 = Color(0xFF30a74f)
val green_lite = Color(0xFFe6f4e9)
val green_lite_1 = Color(0xFFc2e4c8)

val orange_ada = Color(0xFFEF6C00)
val orange_primary = Color(0xFFF57C00)
val orange_lite = Color(0XFFFEF3E0)
val orange_lite_1 = Color(0XFFFDE0B2)

val indigo_primary = Color(0xFF0574e3)
val indigo_lite = Color(0xFFe2f2ff)

//  Miscellaneous
val tx_credit = red_primary
val tx_payment = green_primary
val tx_discount = grey900
val error_red = red_primary
val divider = grey300
val green_dark = green_primary
val yellow_undefined = Color(0XFFF9AA33)

val OkcColors = lightColors(
    primary = green_primary,
    primaryVariant = green_ada,
    onPrimary = white,
    secondary = green_primary,
    secondaryVariant = green_ada,
    onSecondary = white,
    error = red_primary,
    onError = white,
    background = white,
    onBackground = grey900,
    surface = white,
    onSurface = grey900,
)

val OkcColorsGreyBg = OkcColors.copy(background = grey50)

val Colors.colorPrimaryAda: Color
    get() = green_ada

val Colors.colorPrimaryLite: Color
    get() = green_lite

val Colors.colorPrimaryLite1: Color
    get() = green_lite_1

val Colors.colorPrimary1: Color
    get() = green_1

val Colors.colorPrimaryDark: Color
    get() = green_ada

val Colors.colorControlNormal: Color
    get() = grey900

val Colors.colorDivider: Color
    get() = grey300

val Colors.colorLight: Color
    get() = grey400

enum class OptionsMenuColor(var color: Long) {
    LightBlue(0XFF00aee6),
    LightPink(0XFFff4b87),
    LightViolet(0XFF9281ff),
    MeddarkGreen(0XFF358d6e),
    MedDarkCyan(0XFF00b2aa),
    MedRedOrange(0XFFfc7c41),
    CornPoppyCherry(0XFFed4017),
    Grey900(0xFF212121)
}
