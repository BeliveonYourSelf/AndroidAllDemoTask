package topphotoeditor.bokehphotoeditor.stickersView2;

import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.view.View;

import topphotoeditor.bokehphotoeditor.editor.util.MyApp;


public class AddTextProperties {
    private int textShadowIndex;
    private int textSize;
    private int textColor;
    private int textColorIndex;
    private int textAlpha;
    private Shader textShader;
    private int textShaderIndex;
    private String text;
    private int textAlign;
    private Typeface fontName;
    private int fontIndex;
    private int backgroundColor;
    private int backgroundColorIndex;
    private int backgroundAlpha;
    private int backgroundBorder;
    private boolean isFullScreen;
    private boolean isShowBackground;
    private int paddingWidth;
    private int paddingHeight;
    private int textWidth;
    private int textHeight;
    private int radius;
    private int dx;
    private int dy;
    private int colorShadow;
    private boolean isShadow;
    private float textSpacing;
    private boolean showBorder;
    private int borderColorIndex;
    private int borderSize;
    private int colorBorder;
    private int textLineGaping;
    private float lineSpacingAddition;
    private float lineSpacingMultiply;


    public static AddTextProperties getDefaultProperties() {
        AddTextProperties addTextProperties = new AddTextProperties();
        addTextProperties.setTextSize(30);
        addTextProperties.setTextAlign(View.TEXT_ALIGNMENT_CENTER);
        addTextProperties.setFontName(MyApp.defaultTypeFace);
        addTextProperties.setTextColor(Color.WHITE);
        addTextProperties.setTextAlpha(255);
        addTextProperties.setBackgroundAlpha(255);
        addTextProperties.setPaddingWidth(12);
        addTextProperties.setTextShaderIndex(7);
        addTextProperties.setBackgroundColorIndex(21);
        addTextProperties.setTextColorIndex(0);
        addTextProperties.setFontIndex(0);
        addTextProperties.setShowBackground(false);
        addTextProperties.setBackgroundBorder(8);
        addTextProperties.setTextShadowIndex(0);
        addTextProperties.setColorShadow(Color.TRANSPARENT);
        addTextProperties.setRadius(1);
        addTextProperties.setDx(0);
        addTextProperties.setDy(0);
        addTextProperties.setTextSpacing(0);
        addTextProperties.setBorderColorIndex(0);
        addTextProperties.setBorderSize(0);
        addTextProperties.setColorBorder(Color.TRANSPARENT);
        addTextProperties.setShowBorder(false);
        addTextProperties.setTextLineGaping(0);
        addTextProperties.setLineSpacingAddition((float) (double) 0);
        addTextProperties.setLineSpacingMultiply((float) 1.0);
        addTextProperties.setTextAlign(View.TEXT_ALIGNMENT_CENTER);
        return addTextProperties;
    }

    public float getLineSpacingAddition() {
        return lineSpacingAddition;
    }

    public void setLineSpacingAddition(float lineSpacingAddition) {
        this.lineSpacingAddition = lineSpacingAddition;
    }

    public float getLineSpacingMultiply() {
        return lineSpacingMultiply;
    }

    public void setLineSpacingMultiply(float lineSpacingMultiply) {
        this.lineSpacingMultiply = lineSpacingMultiply;
    }

    public int getBorderColorIndex() {
        return borderColorIndex;
    }

    public void setBorderColorIndex(int borderColorIndex) {
        this.borderColorIndex = borderColorIndex;
    }

    public boolean isShowBorder() {
        return showBorder;
    }

    public void setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
    }

    public int getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(int colorBorder) {
        this.colorBorder = colorBorder;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }


    public int getTextLineGaping() {
        return textLineGaping;
    }

    public void setTextLineGaping(int textLineGaping) {
        this.textLineGaping = textLineGaping;
    }

    public float getTextSpacing() {
        return textSpacing;
    }

    public void setTextSpacing(float textSpacing) {
        this.textSpacing = textSpacing;
    }

    public boolean getIsShadow() {
        return isShadow;
    }

    public void setIsShadow(boolean isShadow) {
        this.isShadow = isShadow;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getColorShadow() {
        return colorShadow;
    }

    public void setColorShadow(int colorShadow) {
        this.colorShadow = colorShadow;
    }

    public int getTextColorIndex() {
        return textColorIndex;
    }

    public void setTextColorIndex(int textColorIndex) {
        this.textColorIndex = textColorIndex;
    }

    int getTextShaderIndex() {
        return textShaderIndex;
    }

    public void setTextShaderIndex(int textShaderIndex) {
        this.textShaderIndex = textShaderIndex;
    }

    public int getBackgroundColorIndex() {
        return backgroundColorIndex;
    }

    public void setBackgroundColorIndex(int backgroundColorIndex) {
        this.backgroundColorIndex = backgroundColorIndex;
    }

    public int getFontIndex() {
        return fontIndex;
    }

    public void setFontIndex(int fontIndex) {
        this.fontIndex = fontIndex;
    }

    public int getTextShadowIndex() {
        return textShadowIndex;
    }

    public void setTextShadowIndex(int textShadowIndex) {
        this.textShadowIndex = textShadowIndex;
    }


    public int getBackgroundBorder() {
        return backgroundBorder;
    }

    public void setBackgroundBorder(int backgroundBorder) {
        this.backgroundBorder = backgroundBorder;
    }

    public int getTextHeight() {
        return textHeight;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }

    public int getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }

    public int getPaddingWidth() {
        return paddingWidth;
    }

    public void setPaddingWidth(int paddingWidth) {
        this.paddingWidth = paddingWidth;
    }

    public int getPaddingHeight() {
        return paddingHeight;
    }

    public void setPaddingHeight(int paddingHeight) {
        this.paddingHeight = paddingHeight;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextAlpha() {
        return textAlpha;
    }

    public void setTextAlpha(int textAlpha) {
        this.textAlpha = textAlpha;
    }

    public Shader getTextShader() {
        return textShader;
    }

    public void setTextShader(Shader textShader) {
        this.textShader = textShader;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(int textAlign) {
        this.textAlign = textAlign;
    }

    public Typeface getFontName() {
        return fontName;
    }

    public void setFontName(Typeface fontName) {
        this.fontName = fontName;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isShowBackground() {
        return isShowBackground;
    }

    public void setShowBackground(boolean showBackground) {
        isShowBackground = showBackground;
    }

    public int getBackgroundAlpha() {
        return backgroundAlpha;
    }

    public void setBackgroundAlpha(int backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }
}
