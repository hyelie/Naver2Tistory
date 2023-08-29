package convert;

public enum SupportType {
    TABLE,
        ROW,
        COLUMN,
    QUOTATION,
        QUOTE,
        CITE,
    TEXT,
        PARAGRAPH_DEFAULT,
        PARAGRAPH_LEFT,
        PARAGRAPH_RIGHT,
        PARAGRAPH_CENTER,
            LINK,
            BOLD,
            TILT,
            UNDERBAR,
            STRIKE,
    CODE,
    IMAGE,
        IMAGEBYTE,
        CAPTION,
    HORIZONTALLINE,
    NONE;
    // Append other types here to add other types of HTML formats.
}