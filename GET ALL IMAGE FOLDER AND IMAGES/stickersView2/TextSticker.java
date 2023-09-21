package topphotoeditor.bokehphotoeditor.stickersView2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import topphotoeditor.bokehphotoeditor.editor.util.SystemUtil;


/**
 * Customize your sticker with text and image background.
 * You can place some text into a given region, however,
 * you can also add a plain text sticker. To support text
 * auto resizing , I take most of the code from AutoResizeTextView.
 * See https://adilatwork.blogspot.com/2014/08/android-textview-which-resizes-its-text.html
 * Notice: It's not efficient to add long text due to too much of
 * StaticLayout object allocation.
 * Created by liutao on 30/11/2016.
 */

public class TextSticker extends Sticker {

    /**
     * Our ellipsis string.
     */
    private static final String mEllipsis = "\u2026";

    private final Context context;
    //private final Rect realBounds;
    //private final Rect textRect;
    private TextPaint textPaint, textPaint2;
    private Drawable drawable;
    private StaticLayout staticLayout, staticLayout2;
    private int textWidth;
    private int textHeight;
    private int textAlpha;
    private int textColor;
    private int paddingWidth;
    private int paddingHeight;
    private int backgroundColor;
    private int backgroundAlpha;
    private int backgroundBorder;
    private boolean isShowBackground, showBorder;
    private String text;
    private Layout.Alignment textAlign;
    private AddTextProperties addTextProperties;
    private int shadowRadius;
    private int shadowColor;
    private int shadowDX;
    private int shadowDY;
    private float textSpacing;
    private float strokeWidth;
    private int strokeColor;

    /**
     * Upper bounds for text size.
     * This acts as a starting point for resizing.
     */
    private float maxTextSizePixels;

    /**
     * Lower bounds for text size.
     */
    private float minTextSizePixels;

    private float lineSpacingMultiplier = 1.0f;
    private float lineSpacingExtra = 0.0f;

    public TextSticker(@NonNull Context context) {
        this.context = context;
    }

    public void refreshTextSticker(AddTextProperties addTextProperties) {
        this.addTextProperties = addTextProperties;
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        textPaint2 = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        setTextSize(addTextProperties.getTextSize())
                .setTextWidth(addTextProperties.getTextWidth())
                .setTextHeight(addTextProperties.getTextHeight())
                .setText(addTextProperties.getText())
                .setPaddingWidth(SystemUtil.dpToPx(context, addTextProperties.getPaddingWidth()))
                .setBackgroundBorder(SystemUtil.dpToPx(context, addTextProperties.getBackgroundBorder()))
                .setShadowColor(addTextProperties.getColorShadow())
                .setShadowRadius(addTextProperties.getRadius())
                .setShadowDX(addTextProperties.getDx())
                .setShadowDY(addTextProperties.getDy())
                .setTextColor(addTextProperties.getTextColor())
                .setTextAlpha(addTextProperties.getTextAlpha())
                .setBackgroundColor(addTextProperties.getBackgroundColor())
                .setBackgroundAlpha(addTextProperties.getBackgroundAlpha())
                .setShowBackground(addTextProperties.isShowBackground())
                .setTextColor(addTextProperties.getTextColor())
                .setTypeface(addTextProperties.getFontName())
                .setTextAlign(addTextProperties.getTextAlign())
                .setTextShare(addTextProperties.getTextShader())
                .setStrokeColor(addTextProperties.getColorBorder())
                .setStrokeWidth(addTextProperties.getBorderSize())
                .setShowBorder(addTextProperties.isShowBorder())
                .setLetterSpacing(addTextProperties.getTextSpacing())
                .setLineSpacing(addTextProperties.getLineSpacingAddition(), addTextProperties.getLineSpacingMultiply())
                .resizeText();
    }

    public TextSticker(@NonNull Context context, AddTextProperties addTextProperties) {
        this.context = context;
        refreshTextSticker(addTextProperties);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        Matrix matrix = getMatrix();
        canvas.save();
        canvas.concat(matrix);
        if (isShowBackground) {
            Paint paint = new Paint();
            paint.setARGB(backgroundAlpha, Color.red(backgroundColor), Color.green(backgroundColor), Color.blue(backgroundColor));
            canvas.drawRoundRect(0, 0, textWidth, textHeight, backgroundBorder, backgroundBorder, paint);
            canvas.restore();
            canvas.save();
            canvas.concat(matrix);
        }
        canvas.restore();
        canvas.save();
        canvas.concat(matrix);
        int dx = paddingWidth;
        int dy = textHeight / 2 - staticLayout.getHeight() / 2;
        // center vertical
        canvas.translate(dx, dy);
        if (showBorder) {
            textPaint2.setStrokeJoin(Paint.Join.ROUND);
            textPaint2.setStrokeWidth(strokeWidth);
            textPaint2.setColor(strokeColor);
            textPaint2.setAlpha(textAlpha);
            textPaint2.setStyle(Paint.Style.FILL_AND_STROKE);
            staticLayout2.draw(canvas);
        }
        textPaint.setColor(textColor);
        textPaint.setAlpha(textAlpha);
        textPaint.setStyle(Paint.Style.FILL);
        staticLayout.draw(canvas);

        canvas.restore();
        canvas.save();
        canvas.concat(matrix);
        canvas.restore();
    }


    public AddTextProperties getAddTextProperties() {
        return addTextProperties;
    }

    public TextSticker setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public TextSticker setShadowRadius(int shadowRadius) {
        this.shadowRadius = shadowRadius;
        return this;
    }

    public TextSticker setShadowDX(int shadowDx) {
        this.shadowDX = shadowDx;
        return this;
    }

    public TextSticker setShadowDY(int shadowDy) {
        this.shadowDY = shadowDy;
        return this;
    }

    public TextSticker setStrokeWidth(float width) {
        this.strokeWidth = width;
        return this;
    }

    public TextSticker setStrokeColor(int StrokeColor) {
        this.strokeColor = StrokeColor;
        return this;
    }

    public TextSticker setLetterSpacing(float value) {
        this.textSpacing = value;
        return this;
    }

    public TextSticker setBackgroundBorder(int backgroundBorder) {
        this.backgroundBorder = backgroundBorder;
        return this;
    }

    public TextSticker setShowBackground(boolean showBackground) {
        this.isShowBackground = showBackground;
        return this;
    }

    public TextSticker setShowBorder(boolean showBorder) {
        this.showBorder = showBorder;
        return this;
    }

    @Override
    public int getWidth() {
        return textWidth;
    }

    @Override
    public int getHeight() {
        return textHeight;
    }

    @Override
    public void release() {
        super.release();
        if (drawable != null) {
            drawable = null;
        }
    }

    public int getTextAlpha() {
        return textAlpha;
    }

    public TextSticker setTextAlpha(int textAlpha) {
        this.textAlpha = textAlpha;
        return this;
    }

    @NonNull
    @Override
    public TextSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        textPaint.setAlpha(alpha);
        textPaint2.setAlpha(alpha);
        return this;
    }

    @Override
    public int getAlpha() {
        return textPaint.getAlpha();
    }

    @NonNull
    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public TextSticker setDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        //realBounds.set(0, 0, getWidth(), getHeight());
        //textRect.set(0, 0, getWidth(), getHeight());
        return this;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public TextSticker setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public int getBackgroundAlpha() {
        return backgroundAlpha;
    }

    public TextSticker setBackgroundAlpha(int backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
        return this;
    }

    public TextSticker setTextWidth(int width) {
        this.textWidth = width;
        return this;
    }

    public TextSticker setTextHeight(int height) {
        this.textHeight = height;
        return this;
    }

    @NonNull
    public TextSticker setDrawable(@NonNull Drawable drawable, @Nullable Rect region) {
        this.drawable = drawable;
        //realBounds.set(0, 0, getWidth(), getHeight());
        if (region == null) {
            //textRect.set(0, 0, getWidth(), getHeight());
        } else {
            //textRect.set(region.left, region.top, region.right, region.bottom);
        }
        return this;
    }

    @NonNull
    public TextSticker setTypeface(@Nullable Typeface typeface) {
        textPaint.setTypeface(typeface);
        textPaint2.setTypeface(typeface);
        return this;
    }

    @NonNull
    public TextSticker setTextSize(int textSize) {
        this.textPaint.setTextSize(convertSpToPx(textSize));
        this.textPaint2.setTextSize(convertSpToPx(textSize));
        return this;
    }

    @NonNull
    public TextSticker setTextShare(@Nullable Shader share) {
        textPaint.setShader(share);
        textPaint2.setShader(share);
        return this;
    }

    @NonNull
    public TextSticker setTextColor(@ColorInt int color) {
        this.textColor = color;
        return this;
    }

    @NonNull
    public TextSticker setTextAlign(@NonNull int alignment) {
        switch (alignment) {
            case View.TEXT_ALIGNMENT_CENTER:
                textAlign = Layout.Alignment.ALIGN_CENTER;
                break;
            case View.TEXT_ALIGNMENT_TEXT_START:
                textAlign = Layout.Alignment.ALIGN_NORMAL;
                break;
            case View.TEXT_ALIGNMENT_TEXT_END:
                textAlign = Layout.Alignment.ALIGN_OPPOSITE;
                break;
        }
        return this;
    }

    public int getPaddingWidth() {
        return paddingWidth;
    }

    public TextSticker setPaddingWidth(int paddingWidth) {
        this.paddingWidth = paddingWidth;
        return this;
    }

    public int getPaddingHeight() {
        return paddingHeight;
    }

    public TextSticker setPaddingHeight(int paddingHeight) {
        this.paddingHeight = paddingHeight;
        return this;
    }

    @NonNull
    public TextSticker setMaxTextSize(@Dimension(unit = Dimension.SP) float size) {
        textPaint.setTextSize(convertSpToPx(size));
        maxTextSizePixels = textPaint.getTextSize();
        return this;
    }

    /**
     * Sets the lower text size limit
     *
     * @param minTextSizeScaledPixels the minimum size to use for text in this view,
     *                                in scaled pixels.
     */
    @NonNull
    public TextSticker setMinTextSize(float minTextSizeScaledPixels) {
        minTextSizePixels = convertSpToPx(minTextSizeScaledPixels);
        return this;
    }

    @NonNull
    public TextSticker setLineSpacing(float add, float multiplier) {
        lineSpacingMultiplier = multiplier;
        lineSpacingExtra = add;
        return this;
    }

    @NonNull
    public TextSticker setText(@Nullable String text) {
        this.text = text;
        return this;
    }

    @Nullable
    public String getText() {
        return text;
    }


    /**
     * Resize this view's text size with respect to its width and height
     * (minus padding). You should always call this method after the initialization.
     */
    @NonNull
    public TextSticker resizeText() {

        final CharSequence text = getText();


        if (text == null
                || text.length() <= 0
        ) {
            return this;
        }


        textPaint.setLetterSpacing(textSpacing);
        textPaint.setTextAlign(Paint.Align.LEFT);

        textPaint2.setLetterSpacing(textSpacing);
        textPaint2.setTextAlign(Paint.Align.LEFT);


        //textPaint.setARGB(textAlpha, Color.red(textColor), Color.green(textColor), Color.blue(textColor));
        if (showBorder) {
            if (addTextProperties.getIsShadow())
                textPaint2.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor);
        } else {
            if (addTextProperties.getIsShadow())
                textPaint.setShadowLayer(shadowRadius, shadowDX, shadowDY, shadowColor);
        }

        int width = textWidth - paddingWidth * 2;
        if (width <= 0)
            width = 100;

        staticLayout =
                new StaticLayout(this.text, textPaint, width, textAlign, lineSpacingMultiplier,
                        lineSpacingExtra, true);


        staticLayout2 =
                new StaticLayout(this.text, textPaint2, width, textAlign, lineSpacingMultiplier,
                        lineSpacingExtra, true);


        return this;
    }

    /**
     * @return lower text size limit, in pixels.
     */
    public float getMinTextSizePixels() {
        return minTextSizePixels;
    }

    /**
     * Sets the text size of a clone of the view's {@link TextPaint} object
     * and uses a {@link StaticLayout} instance to measure the height of the text.
     *
     * @return the height of the text when placed in a view
     * with the specified width
     * and when the text has the specified size.
     */
    protected int getTextHeightPixels(@NonNull CharSequence source, int availableWidthPixels,
                                      float textSizePixels) {
        textPaint.setTextSize(textSizePixels);
        // It's not efficient to create a StaticLayout instance
        // every time when measuring, we can use StaticLayout.Builder
        // since api 23.
        StaticLayout staticLayout =
                new StaticLayout(source, textPaint, availableWidthPixels, textAlign,
                        lineSpacingMultiplier, lineSpacingExtra, true);
        return staticLayout.getHeight();
    }

    /**
     * @return the number of pixels which scaledPixels corresponds to on the device.
     */
    private float convertSpToPx(float scaledPixels) {
        return scaledPixels * context.getResources().getDisplayMetrics().scaledDensity;
    }
}
