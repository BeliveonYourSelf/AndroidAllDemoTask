package topphotoeditor.bokehphotoeditor.stickersView2;


import android.content.Context;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

import topphotoeditor.bokehphotoeditor.R;


/**
 * extracting Typeface from Assets is a heavy operation,
 * we want to make sure that we cache the typefaces for reuse
 */
public class FontProvider {
    private static final String DEFAULT_FONT_NAME = "Text";
    private final ArrayList<String> fontList;

    public FontProvider() {
        // populate fonts
        fontList = new ArrayList<>();
        fontList.add("ABeeZee");
        fontList.add("Abel");
        fontList.add("Adamina");
        fontList.add("Alef");
        fontList.add("Alegreya");
        fontList.add("Amarante");
        fontList.add("Arbutus Slab");
        fontList.add("Arima Madurai");
        fontList.add("Assistant");
        fontList.add("Balthazar");
        fontList.add("Basic");
        fontList.add("Baumans");
        fontList.add("Belgrano");
        fontList.add("Bitter");
        fontList.add("Brawler");
        fontList.add("Bree Serif");
        fontList.add("Bowlby One SC");
        fontList.add("Bungee");
        fontList.add("Cagliostro");
        fontList.add("Candal");
        fontList.add("Cantarell");
        fontList.add("Capriola");
        fontList.add("Carrois Gothic SC");
        fontList.add("Chango");
        fontList.add("Cherry Swash");
        fontList.add("Cinzel");
        fontList.add("Comfortaa");
        fontList.add("Corben");
        fontList.add("Days One");
        fontList.add("Dosis");
        fontList.add("EB Garamond");
        fontList.add("El Messiri");
        fontList.add("Electrolize");
        fontList.add("Enriqueta");
        fontList.add("Fredoka One");
        fontList.add("Gabriela");
        fontList.add("Gafata");
        fontList.add("Galada");
        fontList.add("Graduate");
        fontList.add("Itim");
        fontList.add("Julee");
        fontList.add("Jura");
        fontList.add("Kreon");
        fontList.add("Kumar One");
        fontList.add("Mallanna");
        fontList.add("Mandali");
        fontList.add("Marcellus SC");
        fontList.add("Mate SC");
        fontList.add("Merienda One");
        fontList.add("Metamorphous");
        fontList.add("Oleo Script");
        fontList.add("Orbitron");
        fontList.add("Paprika");
        fontList.add("Righteous");
        fontList.add("Rochester");
        fontList.add("Squada One");
        fontList.add("Ubuntu");
        fontList.add("Varela");
        fontList.add("Varela Round");
        fontList.add("Voces");
    }

    public ArrayList<String> getfontThumbs() {
        return fontList;
    }

    public static ArrayList<Font> fontTypeFace(Context context) {
        ArrayList<Font> fontList = new ArrayList<>();
        fontList.add(new Font("style11", Typeface.createFromAsset(context.getAssets(), "style/style11.ttf")));
        fontList.add(new Font("style1", Typeface.createFromAsset(context.getAssets(), "style/style1.TTF")));
        fontList.add(new Font("style2", Typeface.createFromAsset(context.getAssets(), "style/style2.TTF")));
        fontList.add(new Font("style3", Typeface.createFromAsset(context.getAssets(), "style/style3.TTF")));
        fontList.add(new Font("style4", Typeface.createFromAsset(context.getAssets(), "style/style4.TTF")));
        fontList.add(new Font("style5", Typeface.createFromAsset(context.getAssets(), "style/style5.ttf")));
        fontList.add(new Font("style6", Typeface.createFromAsset(context.getAssets(), "style/style6.TTF")));
        fontList.add(new Font("style7", Typeface.createFromAsset(context.getAssets(), "style/style7.TTF")));
        fontList.add(new Font("style8", Typeface.createFromAsset(context.getAssets(), "style/style8.otf")));
        fontList.add(new Font("style9", Typeface.createFromAsset(context.getAssets(), "style/style9.TTF")));
        fontList.add(new Font("style10", Typeface.createFromAsset(context.getAssets(), "style/style10.TTF")));
        return fontList;
    }

}