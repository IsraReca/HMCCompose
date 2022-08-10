package lettuce.hmccompose.ui.adapters

import androidx.compose.ui.text.TextStyle
import lettuce.hmccompose.ui.theme.CellHeaderText_Style
import lettuce.hmccompose.ui.theme.Default_Style
import lettuce.hmccompose.ui.theme.Title1Text_Style

class StyleAdapter {
    companion object {
        private const val STYLE_TITTLE1TEXT = "title1Text"
        private const val STYLE_CELLHEADERTEXT = "cellHeaderText"

        fun getStyleById(id: String): TextStyle {
            return when(id) {
                STYLE_TITTLE1TEXT -> {
                    Title1Text_Style
                }
                STYLE_CELLHEADERTEXT -> {
                    CellHeaderText_Style
                }
                else -> {
                    Default_Style
                }
            }
        }
    }
}