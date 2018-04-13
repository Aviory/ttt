package com.llc111minutes.gameday.util;


import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Hashtable;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.BOLD_ITALIC;
import static android.graphics.Typeface.ITALIC;
import static android.graphics.Typeface.NORMAL;

public class FontsUtil {
    private static final String TAG = FontsUtil.class.getSimpleName();
    private static final Hashtable<Font, Typeface> cache = new Hashtable<>();
    private static HashMap<Font,Boolean> enableFonts = new HashMap<>();

    public static HashMap<Font, Boolean> getEnableFonts() {
        return enableFonts;
    }

    public static void setEnableFonts(HashMap<Font, Boolean> enableFonts) {
        FontsUtil.enableFonts = enableFonts;
    }

    /* @"Audiowide-Regular",
         @"Coustard-Regular",
         @"Coustard-Black",
         @"FjallaOne-Regular",
         @"JockeyOne-Regular",
         @"Judson-Bold",
         @"Lato-Regular",
         @"Monoton-Regular",
         @"MontserratAlternates-Regular",
         @"Orbitron-Medium",
         @"Oswald-Regular",
         @"Quantico-Regular",
         @"Raleway-Bold",
         @"Raleway-BoldItalic",
         @"Righteous-Regular",
         @"Rubik-Black",
         @"RussoOne-Regular",
         @"OstrichSans-Bold",
         @"OstrichSans-Medium"*/
    public enum Font {
        /*         AudiowideRegular(1, "AudiowideRegular", "Audiowide-Regular"),
           CoustardRegular(2, "CoustardRegular", "Coustard-Regular"),
         CoustardBlack(3, "CoustardBlack", "Coustard-Black"),
         FjallaOneRegular(4, "FjallaOneRegular", "FjallaOne-Regular"),
         JockeyOneRegular(5,"JockeyOneRegular", "JockeyOne-Regular"),
         JudsonBold(6, "JudsonBold", "Judson-Bold"),
         LatoRegular(7, "LatoRegular", "Lato-Regular"),
         MonotonRegular(8, "MonotonRegular", "Monoton-Regular"),
         MontserratAlternatesRegular(9, "MontserratAlternatesRegular", "MontserratAlternates-Regular"),
         OrbitronMedium(10, "OrbitronMedium", "Orbitron-Medium"),
         OswaldRegular(11, "OswaldRegular", "Oswald-Regular"),
         QuanticoRegular(12, "QuanticoRegular", "Quantico-Regular"),
         RalewayBold(13, "RalewayBold", "Raleway-Bold"),
         RalewayBoldItalic(14, "RalewayBoldItalic", "Raleway-BoldItalic"),
         RalewaySemiBold(15, "RalewaySemiBold","Raleway-SemiBold"),
         RighteousRegular(16, "RighteousRegular", "Righteous-Regular"),
         OstrichSansBold(17, "OstrichSansBold", "OstrichSans-Bold"),
         OstrichSansMedium(18, "OstrichSansMedium", "OstrichSans-Medium"),
         RubikRegular(19, "RubikRegular", "Rubik-Regular"),
         RubikBold(20, "RubikBold", "Rubik-Bold"),
         Wallpoet(21, "Wallpoet", "Wallpoet"),
         RajdhaniRegular(22, "RajdhaniRegular", "Rajdhani-Regular"),
         QuanticoBoldItalic(23, "QuanticoBoldItalic", "Quantico-BoldItalic"),
         RalewayMedium(24, "RalewayMedium", "Raleway-Medium"),
         LatoBlack(1000, "LatoBlack", "Lato-Black");
 ------------------------------------------------------------------------------------next avi*/
        Awery(1, "Awery", "awery.regular", null, null, null),
        DecalotypeRegular(2, "Decalotype", "decalotype.regular", "decalotype.bold", "decalotype.bold-italic", "decalotype.italic"),
        MonospaceRegular(3, "MonospaceRegular", "nk57-monospace.regular", "nk57-monospace.bold", "nk57-monospace.bold-italic", "nk57-monospace.italic"),
        AthabascaRegular(4, "AthabascaRegular", "athabasca.regular", "athabasca.bold", "athabasca.bold-italic", "athabasca.italic"),
        SFUITextRegular(5, "SFUITextRegular", "SF-UI-Text-Regular", "SF-UI-Text-Bold", "SF-UI-Text-BoldItalic", "SF-UI-Text-Italic"),
        AudiowideRegular(6, "AudiowideRegular", "Audiowide-Regular", null, null, null),
        Coustard(7, "Coustard", "Coustard-Regular", "Coustard-Black", null, null),
        LatoRegular(8, "LatoRegular", "Lato-Regular", "Lato-Bold", "Lato-BoldItalic", "Lato-Italic"),
        JockeyOneRegular(9, "JockeyOneRegular", "JockeyOne-Regular", null, null, null),
        MonotonRegular(10, "MonotonRegular", "Monoton-Regular", null, null, null),
        MontserratAlternates(11, "MontserratAlternatesRegular", "MontserratAlternates-Regular", "MontserratAlternates-Bold", null, null),
        OrbitronRegular(12, "OrbitronRegular", "Orbitron-Regular", "Orbitron-bold", null, null),
        Oswald(13, "OswaldRegular", "Oswald-Regular", "Oswald-Bold", null, null),
        RajdhaniRegular(14, "RajdhaniRegular", "Rajdhani-Regular", "Rajdhani-Bold", null, null),
        QuanticoRegular(15, "QuanticoRegular", "Quantico-Regular", "Quantico-Bold", "Quantico-BoldItalic", "Quantico-Italic"),
        RalewayRegular(16, "RalewayRegular", "Raleway-Regular", "Raleway-Bold", "Raleway-BoldItalic", "Raleway-Italic"),
        RighteousRegular(17, "RighteousRegular", "Righteous-Regular", null, null, null),
        RubikRegular(18, "RubikRegular", "Rubik-Regular", "Rubik-Bold", "Rubik-BoldItalic", "Rubik-Italic"),
        OstrichSans(19, "OstrichSansMedium", "OstrichSans-Medium", null, null, null),
        Wallpoet(20, "Wallpoet", "Wallpoet", null, null, null),

        LatoBlack(1000, "LatoBlack", "Lato-Black", null, null, null);

        Font(int id, String fontName, String fontRegularAssets, String fontBoldAssets, String fontBoldItalicAssets, String fontItalicAssets) {
            this.id = id;
            this.fontName = fontName;
            this.fontRegularAssets = fontRegularAssets;
            this.fontBoldAssets = fontBoldAssets;
            this.fontBoldItalicAssets = fontBoldItalicAssets;
            this.fontItaliAssets = fontItalicAssets;
        }

        private int id;
        private String fontName;
        private String fontRegularAssets;
        private String fontBoldAssets;
        private String fontBoldItalicAssets;
        private String fontItaliAssets;

        public String getFontName() {
            return fontName;
        }

        public String getFontRegularAssets() {
            return fontRegularAssets;
        }

        public String getFontBoldAssets() {
            return fontBoldAssets;
        }

        public String getFontBoldItalicAssets() {
            return fontBoldItalicAssets;
        }

        public String getFontItaliAssets() {
            return fontItaliAssets;
        }

        public int getId() {
            return id;
        }

        public static Font getFontById(int id) {
            for (Font currFont : values()) {
                if (currFont.getId() == id) {
                    return currFont;
                }
            }
            return AudiowideRegular;
        }

        public static Font getFontFromName(String fontName) {
            for (Font currFont : values()) {
                if (currFont.getFontName().equalsIgnoreCase(fontName)) {
                    return currFont;
                }
            }
            return AudiowideRegular;
        }

        public static Font getFontFromFileName(String fileName) {
            for (Font currFont : values()) {
                if (currFont.getFontRegularAssets().equalsIgnoreCase(fileName.trim())) {
                    return currFont;
                }
            }
            return LatoBlack;
        }
    }

    public static Typeface getDefaultFont(Context c) {
        return get(c, Font.AudiowideRegular);
    }

    public static Typeface get(Context c, Font font) {
        return get(c, font.getId());
    }

    public static Typeface get(Context c, int id) {
        return get(c, id, NORMAL);
    }

    public static Typeface get(Context c, int id, int typeStyle) {
        Font font = Font.getFontById(id);
        synchronized (cache) {
            if (!cache.containsKey(font)||cache.get(font).getStyle()!=typeStyle) {
                try {
                    Typeface t = null;
                    switch (typeStyle) {
                        case NORMAL:
                            t = Typeface.createFromAsset(c.getAssets(), "font/" + font.getFontRegularAssets());
                            break;
                        case BOLD:
                            t = Typeface.createFromAsset(c.getAssets(), "font/" + font.getFontBoldAssets());
                            break;
                        case ITALIC:
                            t = Typeface.createFromAsset(c.getAssets(), "font/" + font.getFontItaliAssets());
                            break;
                        case BOLD_ITALIC:
                            t = Typeface.createFromAsset(c.getAssets(), "font/" + font.getFontBoldItalicAssets());
                            break;
                    }
                    Typeface tt = Typeface.create(t, typeStyle);
                    cache.put(font, tt);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
            return cache.get(font);
        }
    }
}
