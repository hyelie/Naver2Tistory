package convert.blogPost;

public enum StyleType {
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
            CONTENT,
                LINK,
                BOLD,
                TILT,
                UNDERBAR,
                STRIKE,
    CODE,
    IMAGE,
        IMAGEBASE64,
        CAPTION,
    HORIZONTALLINE,
    NONE;
    // Append other types here to add other types of HTML formats.
}