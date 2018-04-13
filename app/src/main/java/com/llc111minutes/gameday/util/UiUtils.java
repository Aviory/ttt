package com.llc111minutes.gameday.util;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.Overlay;
import com.llc111minutes.gameday.model.Preview;

/**
 * Created by marina on 29.08.16.
 */
public class UiUtils {

    private static final String TAG = "my";
    private static Toast sToast;

    public static int getScreenWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Log.d(TAG, "size.x: ;"+size.x);
        return size.x;

    }

    public static int getScreenHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getNavigationBarHeight(Context context) {
        int navigationBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }

    public static String getUriDependingDensityDpi(Object o, Context context) {
        String uri = null;
        if (o instanceof Preview) {
            Preview preview = (Preview) o;
            uri = getPreviewDisplayDensityDpi(context, preview);
        } else if (o instanceof Overlay) {
            Overlay overlay = (Overlay) o;
            uri = (String) getOverlayDisplayDensityDpi(context, overlay);
        }
        return uri;
    }

    private static String getPreviewDisplayDensityDpi(Context context, Preview preview) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        String uri = null;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                uri = preview.getSmall();
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                uri = preview.getSmall();
                break;
            case DisplayMetrics.DENSITY_HIGH:
                uri = preview.getMedium();
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                uri = preview.getMedium();
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                uri = preview.getOriginal();
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                uri = preview.getOriginal();
                break;
            default:
                uri = preview.getOriginal();
                break;
        }
        return uri;
    }

    private static Object getOverlayDisplayDensityDpi(Context context, Overlay overlay) {
        int density = context.getResources().getDisplayMetrics().densityDpi;
        Object uri = null;
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                uri = overlay.getSmall();
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                uri = overlay.getSmall();
                break;
            case DisplayMetrics.DENSITY_HIGH:
                uri = overlay.getMedium();
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                uri = overlay.getMedium();
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                uri = overlay.getOriginal();
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                uri = overlay.getOriginal();
                break;
            default:
                uri = overlay.getOriginal();
                break;
        }
        return uri;
    }

    public static void showConnectionErrorToast(Context context) {
        if (sToast == null || !sToast.getView().isShown()) {
            sToast = Toast.makeText(context, R.string.error_connection, Toast.LENGTH_SHORT);
            sToast.show();
        }
    }


    private static void showView(final View view, final boolean show) {
        view.animate().alpha(show ? 1 : 0).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        }).start();
    }

    public static void hideView(View view) {
        showView(view, false);
    }

    public static void showView(View view) {
        showView(view, true);
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public static float getPixelFromDpResource(Context context, int thumb_size) {
        return context.getResources().getDimensionPixelSize(thumb_size);
        //float screenDensity = context.getResources().getDisplayMetrics().density;
        //float dipDimension = context.getResources().getDimension(thumb_size);
        //return dipDimension/screenDensity;
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm =(InputMethodManager)
                view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
