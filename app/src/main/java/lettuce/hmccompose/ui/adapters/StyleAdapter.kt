package lettuce.hmccompose.ui.adapters

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import lettuce.hmccompose.data.complextext.FontType
import lettuce.hmccompose.data.complextext.StylingComplexTextID
import lettuce.hmccompose.ui.theme.*
import lettuce.hmccompose.ui.theme.Default_Style
import lettuce.hmccompose.ui.theme.Font

class StyleAdapter {
    companion object {
        fun getStyleById(
            styleId: String?,
            typeId: String? = null
        ): TextStyle {
            StylingComplexTextID.from(styleId)?.let {
                return when (it) {
                    StylingComplexTextID.HEAD_LINE_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.LARGE_TITLE_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 18.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.SMALL_TEXT -> {
                        // TODO: Add Missing Style
                        Default_Style
                    }
                    StylingComplexTextID.TITLE_HEADER -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 20.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.HEAD_LINE_PLAIN_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 14.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.CAPTION_TEXT -> {
                        // TODO: To Implement
                        Default_Style
                    }
                    StylingComplexTextID.CELL_SUBTITLE_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.CELL_HEADER_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.CELL_DETAIL_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 14.sp,
                        color = ParagraphGrey600
                    )
                    StylingComplexTextID.SECONDARY_TEXT -> {
                        val frontWeight = getFontWeightById(typeId)
                        val color = if (frontWeight == FontWeight.Bold) {
                            HeadingGrey700
                        } else {
                            Black
                        }
                        TextStyle(
                            fontFamily = Font.OpenSans,
                            fontWeight = frontWeight,
                            fontSize = 14.sp,
                            color = color
                        )
                    }
                    StylingComplexTextID.TITLE2_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.BODY_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = HeadingGrey700
                    )
                    StylingComplexTextID.SUB_HEAD_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SECTION_HEADER -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SECTION_PLAIN_WEB_LINK -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.JUSTIFY_TEXT_RIGHT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.JUSTIFY_TEXT_LEFT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SECONDARY_CAPTION_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SELECT_BANNER_GRID_ITEM -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SELECT_WHAT_IS_HEADER -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SUB_HEAD_LINE_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.ERROR_SUBHEAD_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.WARNING_SUBHEAD_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.GREEN_INFO_SUBHEAD_TEXT -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                    StylingComplexTextID.SECTION_BODY -> TextStyle(
                        fontFamily = Font.OpenSans,
                        fontWeight = getFontWeightById(typeId),
                        fontSize = 16.sp,
                        color = Black
                    )
                }
            }
            return Default_Style
        }

        private fun getFontWeightById(typeId: String?): FontWeight {
            FontType.from(typeId)?.let {
                return when (it) {
                    FontType.REGULAR_FONT -> FontWeight.Normal
                    FontType.BOLD_FONT -> FontWeight.Bold
                    FontType.EXTRA_BOLD_FONT -> FontWeight.ExtraBold
                    FontType.SEMI_BOLD_FONT -> FontWeight.SemiBold
                    FontType.LIGHT_FONT -> FontWeight.Light
                    FontType.MEDIUM_FONT -> FontWeight.Medium
                    FontType.BLACK_FONT -> FontWeight.Black
                }
            }
            return FontWeight.Normal
        }
    }
}