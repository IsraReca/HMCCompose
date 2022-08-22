package lettuce.hmccompose.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import lettuce.hmccompose.R

class Font {
    companion object {
        val OpenSans = FontFamily(
            Font(R.font.opensans_regular),
            Font(R.font.opensans_italic, FontWeight.Normal, FontStyle.Italic),
            Font(R.font.opensans_semi_bold, FontWeight.SemiBold),
            Font(R.font.opensans_bold, FontWeight.Bold),
            Font(R.font.opensans_bold_italic, FontWeight.Bold, FontStyle.Italic),
            Font(R.font.opensans_extra_bold, FontWeight.ExtraBold),
            Font(R.font.opensans_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
            Font(R.font.opensans_light, FontWeight.Light),
            Font(R.font.opensans_light_italic, FontWeight.Light, FontStyle.Italic),
            Font(R.font.opensans_medium, FontWeight.Medium),
            Font(R.font.opensans_medium_italic, FontWeight.Medium, FontStyle.Italic)
        )
        val Roboto = FontFamily(
            Font(R.font.roboto_regular),
            Font(R.font.roboto_italic, FontWeight.Normal, FontStyle.Italic),
            Font(R.font.roboto_bold, FontWeight.Bold),
            Font(R.font.roboto_thin, FontWeight.Thin),
            Font(R.font.roboto_light, FontWeight.Light),
            Font(R.font.roboto_medium, FontWeight.Medium),
            Font(R.font.roboto_black, FontWeight.Black)
        )
    }
}
