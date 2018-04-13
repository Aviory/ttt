package com.llc111minutes.gameday.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marina on 19.08.16.
 */
public class TypefaceUtils {
    private static Typeface mLatoBlack;
    private static Typeface mLatoBold;
    private static Typeface mLatoHairline;
    private static Typeface mLatoHeavy;
    private static Typeface mLatoLight;
    private static Typeface mLatoMedium;
    private static Typeface mLatoRegular;
    private static Typeface mLatoSemibold;
    private static Typeface mLatoThin;

    private static final String LATO_BLACK = "font/Lato-Black";
    private static final String LATO_BOLD = "font/Lato-Bold";
    private static final String LATO_HAIRLINE = "font/Lato-Hairline";
    private static final String LATO_HEAVY = "font/Lato-Heavy";
    private static final String LATO_LIGHT = "font/Lato-Light";
    private static final String LATO_MEDIUM = "font/Lato-Medium";
    private static final String LATO_REGULAR = "font/Lato-Regular";
    private static final String LATO_SEMIBOLD = "font/Lato-Semibold";
    private static final String LATO_THIN = "font/Lato-Thin";

    private static final String ARCHIVOBLACK_REGULAR = "font/ArchivoBlack-Regular";
    private static final String AUDIOWIDE_REGULAR = "font/Audiowide-Regular";
    private static final String CLEANVEL = "font/CLEANVEL";
    private static final String CLEANVERTISING_BLACK = "font/Cleanvertising-Black";
    private static final String COUSTARD_BLACK = "font/Coustard-Black";
    private static final String COUSTARD_REGULAR = "font/Coustard-Regular";
    private static final String FJALLAONE_REGULAR = "font/FjallaOne-Regular";
    private static final String GRUPPO_REGULAR = "font/Gruppo-Regular";
    private static final String HKGROTESK_BOLD = "font/HKGrotesk-Bold";
    private static final String HKGROTESK_DOLDITALYC = "font/HKGrotesk-BoldItalic";
    private static final String HKGROTESK_ITALIC = "font/HKGrotesk-Italic";
    private static final String HKGROTESK_LIGHT = "font/HKGrotesk-Light";
    private static final String HKGROTESK_LIGHTITALIC = "font/HKGrotesk-LightItalic";
    private static final String HKGROTESK_MEDIUM = "font/HKGrotesk-Medium";
    private static final String HKGROTESK_MEDIUMITALIC = "font/HKGrotesk-MediumItalic";
    private static final String HKGROTESK_REGULAR = "font/HKGrotesk-Regular";
    private static final String JOCKEYONE_REGULAR = "font/JockeyOne-Regular";
    private static final String JUDSON_BOLD = "font/Judson-Bold";
    private static final String JUDSON_ITALIC = "font/Judson-Italic";
    private static final String JUDSON_REGULAR = "font/Judson-Regular";
    private static final String KRONAONE_REGULAR = "font/KronaOne-Regular";
    private static final String LATO_BLACKITALIC = "font/Lato-BlackItalic";
    private static final String LATO_BOLDITALIC = "font/Lato-BoldItalic";
    private static final String LATO_HAIRLINEITALIC = "font/Lato-HairlineItalic";
    private static final String LATO_HEAVYITALIC = "font/Lato-HeavyItalic";
    private static final String LATO_ITALIC = "font/Lato-Italic";
    private static final String LATO_LIGHTITALIC = "font/Lato-LightItalic";
    private static final String LATO_MEDIUMITALIC = "font/Lato-MediumItalic";
    private static final String LATO_SEMIBOLDITALIC = "font/Lato-SemiboldItalic";
    private static final String LATO_THINITALIC = "font/Lato-ThinItalic";
    private static final String LEAGUEGOTHIC_CONDENSEDITALIC = "font/LeagueGothic-CondensedItalic";
    private static final String LEAGUEGOTHIC_CONDENSEDREGULAR = "font/LeagueGothic-CondensedRegular";
    private static final String LEAGUEGOTHIC__ITALIC = "font/LeagueGothic-Italic";
    private static final String LEAGUEGOTHIC__REGULAR = "font/LeagueGothic-Regular";
    private static final String MONTON_REGULAR = "font/Monoton-Regular";
    private static final String MONTSERRATALTERNATES_BOLD = "font/MontserratAlternates-Bold";
    private static final String MONTSERRATALTERNATES_REGULAR = "font/MontserratAlternates-Regular";
    private static final String NOW_BLACK = "font/Now-Black";
    private static final String NOW_BOLD = "font/Now-Bold";
    private static final String NOW_LIGHT = "font/Now-Light";
    private static final String NOW_MEDIUM = "font/Now-Medium";
    private static final String NOW_REGULAR = "font/Now-Regular";
    private static final String NOW_THIN = "font/Now-Thin";
    private static final String ORBITRON_LIGHT = "font/Orbitron-Light";
    private static final String ORBITRON_BLACK = "font/Orbitron-Black";
    private static final String ORBITRON_BOLD = "font/Orbitron-Bold";
    private static final String ORBITRON_MEDIUM = "font/Orbitron-Medium";
    private static final String ORBITRON_REGULAR = "font/Orbitron-Regular";
    private static final String OSTRICHSANS_BLACK = "font/OstrichSans-Black";
    private static final String OSTRICHSANS_BOLD = "font/OstrichSans-Bold";
    private static final String OSTRICHSANS_HEAVY = "font/OstrichSans-Heavy";
    private static final String OSTRICHSANS_LIGHT = "font/OstrichSans-Light";
    private static final String OSTRICHSANS_MEDIUM = "font/OstrichSans-Medium";
    private static final String OSTRICHSANSDASHED_MEDIUM = "font/OstrichSansDashed-Medium";
    private static final String OSTRICHSANSINLINE_ITALIC = "font/OstrichSansInline-Italic";
    private static final String OSTRICHSANSINLINE_REGULAR = "font/OstrichSansInline-Regular";
    private static final String OSTRICHSANSROUNDED_MEDIUM = "font/OstrichSansRounded-Medium";
    private static final String OSWALD_BOLD = "font/Oswald-Bold";
    private static final String OSWALD_LIGHT = "font/Oswald-Light";
    private static final String OSWALD_REGULAR = "font/Oswald-Regular";
    private static final String PASSIONONE_BLACK = "font/PassionOne-Black";
    private static final String PASSIONONE_BOLD = "font/PassionOne-Bold";
    private static final String PASSIONONE_REGULAR = "font/PassionOne-Regular";
    private static final String PATUAONE_REGULAR = "font/PatuaOne-Regular";
    private static final String POPPINS_BOLD = "font/Poppins-Bold";
    private static final String POPPINS_LIGHT = "font/Poppins-Light";
    private static final String POPPINS_MEDIUM = "font/Poppins-Medium";
    private static final String POPPINS_REGULAR = "font/Poppins-Regular";
    private static final String POPPINS_SEMIBOLD = "font/Poppins-SemiBold";
    private static final String QUANTICO_BOLD = "font/Quantico-Bold";
    private static final String QUANTICO_BOLDITALIC = "font/Quantico-BoldItalic";
    private static final String QUANTICO_ITALIC = "font/Quantico-Italic";
    private static final String QUANTICO_REGULAR = "font/Quantico-Regular";
    private static final String RAJDHANI_BOLD = "font/Rajdhani-Bold";
    private static final String RAJDHANI_LIGHT = "font/Rajdhani-Light";
    private static final String RAJDHANI_MEDIUM = "font/Rajdhani-Medium";
    private static final String RAJDHANI_REGULAR = "font/Rajdhani-Regular";
    private static final String RAJDHANI_SEMIBOLD = "font/Rajdhani-SemiBold";
    private static final String RALEWAY_BLACK = "font/Raleway-Black";
    private static final String RALEWAY_BLACKITALIC = "font/Raleway-BlackItalic";
    private static final String RALEWAY_BOLD = "font/Raleway-Bold";
    private static final String RALEWAY_BOLDITALIC = "font/Raleway-BoldItalic";
    private static final String RALEWAY_EXTRABOLD = "font/Raleway-ExtraBold";
    private static final String RALEWAY_EXTRABOLDITALIC = "font/Raleway-ExtraBoldItalic";
    private static final String RALEWAY_EXTRALIGHT = "font/Raleway-ExtraLight";
    private static final String RALEWAY_EXTRALIGHTITALIc = "font/Raleway-ExtraLightItalic";
    private static final String RALEWAY_ITALIC = "font/Raleway-Italic";
    private static final String RALEWAY_LIGHT = "font/Raleway-Light";
    private static final String RALEWAY_LIGHTITALIC = "font/Raleway-LightItalic";
    private static final String RALEWAY_MEDIUM = "font/Raleway-Medium";
    private static final String RALEWAY_MEDIUMITALIC = "font/Raleway-MediumItalic";
    private static final String RALEWAY_REGULAR = "font/Raleway-Regular";
    private static final String RALEWAY_SEMIBOLD = "font/Raleway-SemiBold";
    private static final String RALEWAY_SEMIBOLDITALIC = "font/Raleway-SemiBoldItalic";
    private static final String RALEWAY_THIN = "font/Raleway-Thin";
    private static final String RALEWAY_THINITALIC = "font/Raleway-ThinItalic";
    private static final String RIGHTEOUS_REGULAR = "font/Righteous-Regular";
    private static final String RUBIK_BLACK = "font/Rubik-Black";
    private static final String RUBIK_BLACKITALIC = "font/Rubik-BlackItalic";
    private static final String RUBIK_BOLD = "font/Rubik-Bold";
    private static final String RUBIK_BOLDITALIC = "font/Rubik-BoldItalic";
    private static final String RUBIK_ITALIC = "font/Rubik-Italic";
    private static final String RUBIK_LIGHT = "font/Rubik-Light";
    private static final String RUBIK_LIGHTITALIC = "font/Rubik-LightItalic";
    private static final String RUBIK_MEDIUM = "font/Rubik-Medium";
    private static final String RUBIK_MEDIUMITALIC = "font/Rubik-MediumItalic";
    private static final String RUBIK_REGULAR = "font/Rubik-Regular";
    private static final String RUSSOONE_REGULAR = "font/RussoOne-Regular";
    private static final String SINTONY_BOLD = "font/Sintony-Bold";
    private static final String SINTONY_REGULAR = "font/Sintony-Regular";
    private static final String WALLPOET_REGULAR = "font/Wallpoet";
    private static final String WORKSANS_BLACK = "font/WorkSans-Black";
    private static final String WORKSANS_BOLD = "font/WorkSans-Bold";
    private static final String WORKSANS_EXTRABOLD = "font/WorkSans-ExtraBold";
    private static final String WORKSANS_EXTRALIGHT = "font/WorkSans-ExtraLight";
    private static final String WORKSANS_LIGHT = "font/WorkSans-Light";
    private static final String WORKSANS_MEDIUM = "font/WorkSans-Medium";
    private static final String WORKSANS_REGULAR = "font/WorkSans-Regular";
    private static final String WORKSANS_SEMIBOLD = "font/WorkSans-SemiBold";
    private static final String WORKSANS_THIN = "font/WorkSans-Thin";

    public static List<String> allFonts(){
        List<String> list = new ArrayList<>();
        list.add("Lato-Black");
        list.add("Lato-Bold");
        list.add("Lato-Hairline");
        list.add("Lato-Heavy");
        list.add("Lato-Light");
        list.add("Lato-Medium");
        list.add("Lato-Regular");
        list.add("Lato-Semibold");
        list.add("Lato-Thin");
        list.add("ArchivoBlack-Regular");
        list.add("Audiowide-Regular");
        list.add("CLEANVEL");
        list.add("Cleanvertising-Black");
        list.add("Coustard-Black");
        list.add("Coustard-Regular");
        list.add("FjallaOne-Regular");
        list.add("Gruppo-Regular");
        list.add("HKGrotesk-Bold");
        list.add("HKGrotesk-BoldItalic");
        list.add("HKGrotesk-Italic");
        list.add("HKGrotesk-Light");
        list.add("HKGrotesk-LightItalic");
        list.add("HKGrotesk-Medium");
        list.add("HKGrotesk-MediumItalic");
        list.add("HKGrotesk-Regular");
        list.add("JockeyOne-Regular");
        list.add("Judson-Bold");
        list.add("Judson-Italic");
        list.add("Judson-Regular");
        list.add("KronaOne-Regular");
        list.add("Lato-BlackItalic");
        list.add("Lato-BoldItalic");
        list.add("Lato-HairlineItalic");
        list.add("Lato-HeavyItalic");
        list.add("Lato-Italic");
        list.add("Lato-LightItalic");
        list.add("Lato-MediumItalic");
        list.add("Lato-SemiboldItalic");
        list.add("Lato-ThinItalic");
        list.add("LeagueGothic-CondensedItalic");
        list.add("LeagueGothic-CondensedRegular");
        list.add("LeagueGothic-Italic");
        list.add("LeagueGothic-Regular");
        list.add("Monoton-Regular");
        list.add("font/MontserratAlternates-Bold");
//        private static final String MONTSERRATALTERNATES_REGULAR = "font/MontserratAlternates-Regular";
//        private static final String NOW_BLACK = "font/Now-Black";
//        private static final String NOW_BOLD = "font/Now-Bold";
//        private static final String NOW_LIGHT = "font/Now-Light";
//        private static final String NOW_MEDIUM = "font/Now-Medium";
//        private static final String NOW_REGULAR = "font/Now-Regular";
//        private static final String NOW_THIN = "font/Now-Thin";
//        private static final String ORBITRON_LIGHT = "font/Orbitron-Light";
//        private static final String ORBITRON_BLACK = "font/Orbitron-Black";
//        private static final String ORBITRON_BOLD = "font/Orbitron-Bold";
//        private static final String ORBITRON_MEDIUM = "font/Orbitron-Medium";
//        private static final String ORBITRON_REGULAR = "font/Orbitron-Regular";
//        private static final String OSTRICHSANS_BLACK = "font/OstrichSans-Black";
//        private static final String OSTRICHSANS_BOLD = "font/OstrichSans-Bold";
//        private static final String OSTRICHSANS_HEAVY = "font/OstrichSans-Heavy";
//        private static final String OSTRICHSANS_LIGHT = "font/OstrichSans-Light";
//        private static final String OSTRICHSANS_MEDIUM = "font/OstrichSans-Medium";
//        private static final String OSTRICHSANSDASHED_MEDIUM = "font/OstrichSansDashed-Medium";
//        private static final String OSTRICHSANSINLINE_ITALIC = "font/OstrichSansInline-Italic";
//        private static final String OSTRICHSANSINLINE_REGULAR = "font/OstrichSansInline-Regular";
//        private static final String OSTRICHSANSROUNDED_MEDIUM = "font/OstrichSansRounded-Medium";
//        private static final String OSWALD_BOLD = "font/Oswald-Bold";
//        private static final String OSWALD_LIGHT = "font/Oswald-Light";
//        private static final String OSWALD_REGULAR = "font/Oswald-Regular";
//        private static final String PASSIONONE_BLACK = "font/PassionOne-Black";
//        private static final String PASSIONONE_BOLD = "font/PassionOne-Bold";
//        private static final String PASSIONONE_REGULAR = "font/PassionOne-Regular";
//        private static final String PATUAONE_REGULAR = "font/PatuaOne-Regular";
//        private static final String POPPINS_BOLD = "font/Poppins-Bold";
//        private static final String POPPINS_LIGHT = "font/Poppins-Light";
//        private static final String POPPINS_MEDIUM = "font/Poppins-Medium";
//        private static final String POPPINS_REGULAR = "font/Poppins-Regular";
//        private static final String POPPINS_SEMIBOLD = "font/Poppins-SemiBold";
//        private static final String QUANTICO_BOLD = "font/Quantico-Bold";
//        private static final String QUANTICO_BOLDITALIC = "font/Quantico-BoldItalic";
//        private static final String QUANTICO_ITALIC = "font/Quantico-Italic";
//        private static final String QUANTICO_REGULAR = "font/Quantico-Regular";
//        private static final String RAJDHANI_BOLD = "font/Rajdhani-Bold";
//        private static final String RAJDHANI_LIGHT = "font/Rajdhani-Light";
//        private static final String RAJDHANI_MEDIUM = "font/Rajdhani-Medium";
//        private static final String RAJDHANI_REGULAR = "font/Rajdhani-Regular";
//        private static final String RAJDHANI_SEMIBOLD = "font/Rajdhani-SemiBold";
//        private static final String RALEWAY_BLACK = "font/Raleway-Black";
//        private static final String RALEWAY_BLACKITALIC = "font/Raleway-BlackItalic";
//        private static final String RALEWAY_BOLD = "font/Raleway-Bold";
//        private static final String RALEWAY_BOLDITALIC = "font/Raleway-BoldItalic";
//        private static final String RALEWAY_EXTRABOLD = "font/Raleway-ExtraBold";
//        private static final String RALEWAY_EXTRABOLDITALIC = "font/Raleway-ExtraBoldItalic";
//        private static final String RALEWAY_EXTRALIGHT = "font/Raleway-ExtraLight";
//        private static final String RALEWAY_EXTRALIGHTITALIc = "font/Raleway-ExtraLightItalic";
//        private static final String RALEWAY_ITALIC = "font/Raleway-Italic";
//        private static final String RALEWAY_LIGHT = "font/Raleway-Light";
//        private static final String RALEWAY_LIGHTITALIC = "font/Raleway-LightItalic";
//        private static final String RALEWAY_MEDIUM = "font/Raleway-Medium";
//        private static final String RALEWAY_MEDIUMITALIC = "font/Raleway-MediumItalic";
//        private static final String RALEWAY_REGULAR = "font/Raleway-Regular";
//        private static final String RALEWAY_SEMIBOLD = "font/Raleway-SemiBold";
//        private static final String RALEWAY_SEMIBOLDITALIC = "font/Raleway-SemiBoldItalic";
//        private static final String RALEWAY_THIN = "font/Raleway-Thin";
//        private static final String RALEWAY_THINITALIC = "font/Raleway-ThinItalic";
//        private static final String RIGHTEOUS_REGULAR = "font/Righteous-Regular";
//        private static final String RUBIK_BLACK = "font/Rubik-Black";
//        private static final String RUBIK_BLACKITALIC = "font/Rubik-BlackItalic";
//        private static final String RUBIK_BOLD = "font/Rubik-Bold";
//        private static final String RUBIK_BOLDITALIC = "font/Rubik-BoldItalic";
//        private static final String RUBIK_ITALIC = "font/Rubik-Italic";
//        private static final String RUBIK_LIGHT = "font/Rubik-Light";
//        private static final String RUBIK_LIGHTITALIC = "font/Rubik-LightItalic";
//        private static final String RUBIK_MEDIUM = "font/Rubik-Medium";
//        private static final String RUBIK_MEDIUMITALIC = "font/Rubik-MediumItalic";
//        private static final String RUBIK_REGULAR = "font/Rubik-Regular";
//        private static final String RUSSOONE_REGULAR = "font/RussoOne-Regular";
//        private static final String SINTONY_BOLD = "font/Sintony-Bold";
//        private static final String SINTONY_REGULAR = "font/Sintony-Regular";
//        private static final String WALLPOET_REGULAR = "font/Wallpoet";
//        private static final String WORKSANS_BLACK = "font/WorkSans-Black";
//        private static final String WORKSANS_BOLD = "font/WorkSans-Bold";
//        private static final String WORKSANS_EXTRABOLD = "font/WorkSans-ExtraBold";
//        private static final String WORKSANS_EXTRALIGHT = "font/WorkSans-ExtraLight";
//        private static final String WORKSANS_LIGHT = "font/WorkSans-Light";
//        private static final String WORKSANS_MEDIUM = "font/WorkSans-Medium";
//        private static final String WORKSANS_REGULAR = "font/WorkSans-Regular";
//        private static final String WORKSANS_SEMIBOLD = "font/WorkSans-SemiBold";
//        private static final String WORKSANS_THIN = "font/WorkSans-Thin";
        return list;
    }


    public static Typeface getLatoBlackTypeface(Context context) {
        if (mLatoBlack == null) {
            mLatoBlack = Typeface.createFromAsset(context.getAssets(), LATO_BLACK);
        }
        return mLatoBlack;
    }

    public static Typeface getLatoBoldTypeface(Context context) {
        if (mLatoBold == null) {
            mLatoBold = Typeface.createFromAsset(context.getAssets(), LATO_BOLD);
        }
        return mLatoBold;
    }

    public static Typeface getLatoHairlineTypeface(Context context) {
        if (mLatoHairline == null) {
            mLatoHairline = Typeface.createFromAsset(context.getAssets(), LATO_HAIRLINE);
        }
        return mLatoHairline;
    }

    public static Typeface getLatoHeavyTypeface(Context context) {
        if (mLatoHeavy == null) {
            mLatoHeavy = Typeface.createFromAsset(context.getAssets(), LATO_HEAVY);
        }
        return mLatoHeavy;
    }

    public static Typeface getLatoLightTypeface(Context context) {
        if (mLatoLight == null) {
            mLatoLight = Typeface.createFromAsset(context.getAssets(), LATO_LIGHT);
        }
        return mLatoLight;
    }

    public static Typeface getLatoMediumTypeface(Context context) {
        if (mLatoMedium == null) {
            mLatoMedium = Typeface.createFromAsset(context.getAssets(), LATO_MEDIUM);
        }
        return mLatoMedium;
    }

    public static Typeface getLatoRegularTypeface(Context context) {
        if (mLatoRegular == null) {
            mLatoRegular = Typeface.createFromAsset(context.getAssets(), LATO_REGULAR);
        }
        return mLatoRegular;
    }

    public static Typeface getLatoSemiboldTypeface(Context context) {
        if (mLatoSemibold == null) {
            mLatoSemibold = Typeface.createFromAsset(context.getAssets(), LATO_SEMIBOLD);
        }
        return mLatoSemibold;
    }

    public static Typeface getLatoThinTypeface(Context context) {
        if (mLatoThin == null) {
            mLatoThin = Typeface.createFromAsset(context.getAssets(), LATO_THIN);
        }
        return mLatoThin;
    }
}
