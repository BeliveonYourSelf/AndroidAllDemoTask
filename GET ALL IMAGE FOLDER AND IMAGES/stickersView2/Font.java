package topphotoeditor.bokehphotoeditor.stickersView2;

import android.graphics.Typeface;

public class Font {
    String fontName;
    Typeface fontType;

    public Font(String fontName, Typeface fontType) {
        this.fontName = fontName;
        this.fontType = fontType;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Typeface getFontType() {
        return fontType;
    }

    public void setFontType(Typeface fontType) {
        this.fontType = fontType;
    }
}