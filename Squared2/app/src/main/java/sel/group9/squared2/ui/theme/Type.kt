package sel.group9.squared2.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import sel.group9.squared2.R

private val pixelFont = FontFamily(Font(R.font.vt323_regular, FontWeight.Medium))

@Composable
fun errorTextStyle() = MaterialTheme.typography.caption.plus(TextStyle(color = Color(0xFFD80D0D)))

val Typography = Typography(
//    body1 = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp
//    )


    h1 = TextStyle(
        fontFamily = pixelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 72.sp
    ),

    h2 = TextStyle(
        fontFamily = pixelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    ),

    body1 = TextStyle(
        fontFamily = pixelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),

    button = TextStyle(
        fontFamily = pixelFont,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    ),

    caption = TextStyle(
        fontFamily = pixelFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)