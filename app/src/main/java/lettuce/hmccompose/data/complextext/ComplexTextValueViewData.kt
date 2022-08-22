package lettuce.hmccompose.data.complextext

class ComplexTextValueViewData(
    val value: String? = null,
    val type: String? = null,
    val stylingId: String? = null
)

enum class StylingComplexTextID(val id: String) {
    HEAD_LINE_TEXT("headlineText"),
    LARGE_TITLE_TEXT("largeTitleText"),
    SMALL_TEXT("smallText"),
    TITLE_HEADER("sectionTitleHeader"),
    HEAD_LINE_PLAIN_TEXT("headlinePlainText"),
    CAPTION_TEXT("captionText"),
    CELL_SUBTITLE_TEXT("cellSubtitleText"),
    CELL_HEADER_TEXT("cellHeaderText"),
    CELL_DETAIL_TEXT("cellDetailText"),
    SECONDARY_TEXT("secondaryText"),
    TITLE2_TEXT("title2Text"),
    BODY_TEXT("bodyText"),
    SUB_HEAD_TEXT("subHeadText"),
    SECTION_HEADER("sectionHeader"),
    SECTION_PLAIN_WEB_LINK("plainWebLink"),
    JUSTIFY_TEXT_RIGHT("justifyTextRight"),
    JUSTIFY_TEXT_LEFT("justifyTextLeft"),
    SECONDARY_CAPTION_TEXT("secondaryCaptionText"),
    SELECT_BANNER_GRID_ITEM("selectBannerGridItem"),
    SELECT_WHAT_IS_HEADER("selectWhatIsHeader"),
    SUB_HEAD_LINE_TEXT("subHeadlineText"),
    ERROR_SUBHEAD_TEXT("errorSubHeadText"),
    WARNING_SUBHEAD_TEXT("warningSubHeadText"),
    GREEN_INFO_SUBHEAD_TEXT("greenInfoSubHeadText"),
    SECTION_BODY("sectionBody");

    companion object {
        fun from(id: String?): StylingComplexTextID? =
            when (id) {
                HEAD_LINE_TEXT.id -> HEAD_LINE_TEXT
                LARGE_TITLE_TEXT.id -> LARGE_TITLE_TEXT
                SMALL_TEXT.id -> SMALL_TEXT
                CAPTION_TEXT.id -> CAPTION_TEXT
                CELL_SUBTITLE_TEXT.id -> CELL_SUBTITLE_TEXT
                CELL_HEADER_TEXT.id -> CELL_HEADER_TEXT
                CELL_DETAIL_TEXT.id -> CELL_DETAIL_TEXT
                SECONDARY_TEXT.id -> SECONDARY_TEXT
                TITLE_HEADER.id -> TITLE_HEADER
                CAPTION_TEXT.id -> CAPTION_TEXT
                HEAD_LINE_PLAIN_TEXT.id -> HEAD_LINE_PLAIN_TEXT
                TITLE2_TEXT.id -> TITLE2_TEXT
                BODY_TEXT.id -> BODY_TEXT
                SECTION_HEADER.id -> SECTION_HEADER
                SUB_HEAD_TEXT.id -> SUB_HEAD_TEXT
                SECTION_PLAIN_WEB_LINK.id -> SECTION_PLAIN_WEB_LINK
                JUSTIFY_TEXT_RIGHT.id -> JUSTIFY_TEXT_RIGHT
                JUSTIFY_TEXT_LEFT.id -> JUSTIFY_TEXT_LEFT
                SECONDARY_CAPTION_TEXT.id -> SECONDARY_CAPTION_TEXT
                SELECT_WHAT_IS_HEADER.id -> SELECT_WHAT_IS_HEADER
                SELECT_BANNER_GRID_ITEM.id -> SELECT_BANNER_GRID_ITEM
                SECONDARY_CAPTION_TEXT.id -> SECONDARY_CAPTION_TEXT
                SUB_HEAD_LINE_TEXT.id -> SUB_HEAD_LINE_TEXT
                ERROR_SUBHEAD_TEXT.id -> ERROR_SUBHEAD_TEXT
                WARNING_SUBHEAD_TEXT.id -> WARNING_SUBHEAD_TEXT
                GREEN_INFO_SUBHEAD_TEXT.id -> GREEN_INFO_SUBHEAD_TEXT
                SECTION_BODY.id -> SECTION_BODY
                else -> null
            }
    }
}

enum class TypeID(val id: String) {
    LINK("link"),
    NEW_LINE("newLine"),
    BOLD_TYPE("bold"),
    NEW_PARAGRAPH("newParagraph");

    companion object {
        fun from(id: String?): TypeID? =
            when (id) {
                LINK.id -> LINK
                NEW_LINE.id -> NEW_LINE
                BOLD_TYPE.id -> BOLD_TYPE
                NEW_PARAGRAPH.id -> NEW_PARAGRAPH
                else -> null
            }
    }
}

enum class FontType(val font: String) {
    REGULAR_FONT("regular"),
    BOLD_FONT("bold"),
    EXTRA_BOLD_FONT("extraBold"),
    SEMI_BOLD_FONT("semiBold"),
    LIGHT_FONT("light"),
    MEDIUM_FONT("medium"),
    BLACK_FONT("black");

    companion object {
        fun from(id: String?): FontType? =
            when (id) {
                EXTRA_BOLD_FONT.font -> EXTRA_BOLD_FONT
                BOLD_FONT.font -> BOLD_FONT
                SEMI_BOLD_FONT.font -> SEMI_BOLD_FONT
                REGULAR_FONT.font -> REGULAR_FONT
                LIGHT_FONT.font -> LIGHT_FONT
                MEDIUM_FONT.font -> MEDIUM_FONT
                BLACK_FONT.font -> BLACK_FONT
                else -> null
            }
    }
}


enum class ValueType(val id: String) {
    SPACE(" "),
    NEW_LINE("\n");

    companion object {
        fun from(id: String?): ValueType? =
            when (id) {
                SPACE.id -> SPACE
                NEW_LINE.id -> NEW_LINE
                else -> null
            }
    }
}