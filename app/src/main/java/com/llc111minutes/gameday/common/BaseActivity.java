package com.llc111minutes.gameday.common;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.interfaces.BackEventInterface;
import com.llc111minutes.gameday.util.LogUtil;
import com.llc111minutes.gameday.util.Storage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;



public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = "" + getClass().getName();
    private boolean isActivityPaused = false;
    private Snackbar snackbar;
    private final static int PERMISSION_STORAGE_REQUEST = 1221;
    private final static int PERMISSION_PHOTO_REQUEST = 1222;

    private static int[] mResAnim;

    //@BindView(R.id.toolbar)
    //protected Toolbar toolbar;
    @BindView(R.id.coordinator_layout)
    protected CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        if (getLayout() > 0) {
            setContentView(getLayout());
            ButterKnife.bind(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public <T extends BaseActivity> T getAct() {
        return (T) this;
    }

    public abstract int getLayout();

    @Override
    public void onBackPressed() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isVisible() && fragment instanceof BackEventInterface) {
                ((BackEventInterface) fragment).onBackEvent();
                return;
            }
        }
        super.onBackPressed();
    }

    /*@Override
    public void onBack() {
        if (!returnBackStackImmediate(getSupportFragmentManager())) {
            super.onBack();
        }
    }*/

    // HACK: propagate back button press to child fragments.
    // This might not work properly when you have multiple fragments adding multiple children to the backstack.
    // (in our case, only one child fragments adds fragments to the backstack, so we're fine with this)
    private boolean returnBackStackImmediate(FragmentManager fm) {
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                if (fragment == null) continue;
                if (fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                    if (fragment.getChildFragmentManager().popBackStackImmediate()) {
                        Log.d("Base: ", "fragment.getChildFragmentManager().popBackStackImmediate()");
                        return true;
                    } else {
                        Log.d("Base: ", "!fragment.getChildFragmentManager().popBackStackImmediate()");
                        return returnBackStackImmediate(fragment.getChildFragmentManager());
                    }
                }
                //BackEventInterface checked
                if (fragment instanceof BackEventInterface && !((BackEventInterface) fragment).isEndingPressed()) {
                    ((BackEventInterface) fragment).onBackEvent();
                    return true;
                }
            }
        }
        return false;
    }

    public static <T> Fragment addFragment(AppCompatActivity activity
            , Class<T> clazz            // Class of fragment
            , int container             // layout
            , @Nullable Bundle args     // Arguments
            , boolean backstack         // Add to backstack
            , boolean replace           // false - add, true - replace
            , boolean animation        // animation on/off
            , int[] resAnim) {
        mResAnim = resAnim;
        return addFragment(activity, clazz, container, args, backstack, replace, animation);
    }

    public static <T> Fragment addFragment(AppCompatActivity activity
            , Class<T> clazz            // Class of fragment
            , int container             // layout
            , @Nullable Bundle args     // Arguments
            , boolean backstack         // Add to backstack
            , boolean replace           // false - add, true - replace
            , boolean animation)        // animation on/off
    {
        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(clazz.getCanonicalName());
        if (fragment == null) {
            try {
                Constructor<?> cons = clazz.getConstructor();
                fragment = (Fragment) cons.newInstance();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        } else if (fragment.isAdded()) {
            return fragment;
        }

        if (fragment == null) return null;

        if (args != null) {
            fragment.setArguments(args);
        }

        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction();
        if (backstack) {
            transaction.addToBackStack(clazz.getCanonicalName());
        }

        if (animation) {
            if (mResAnim == null) {
                transaction
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left
                                , R.anim.slide_in_left, R.anim.slide_out_right);
            } else if (mResAnim.length == 2) {
                transaction
                        .setCustomAnimations(mResAnim[0], mResAnim[1]);
            } else if (mResAnim.length == 4) {
                transaction
                        .setCustomAnimations(mResAnim[0], mResAnim[1], mResAnim[2], mResAnim[3]);
            }
        }

        if (replace) {
            transaction.replace(container, fragment, fragment.getClass().getCanonicalName());
        } else {
            transaction.add(container, fragment, fragment.getClass().getCanonicalName());
        }
        transaction.commitAllowingStateLoss();
        return fragment;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityPaused = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityPaused = true;
    }

    public boolean isActivityPaused() {
        return this.isActivityPaused;
    }

    public void snackBarAction(String message, int length, String action,
                               View.OnClickListener listener) {
        if (snackbar != null) {
            snackbar.dismiss();
            snackbar = null;
        }

        snackbar = Snackbar.make(coordinatorLayout, message, length);
        snackbar.setAction(action, listener);
        snackbar.show();
    }

    public void hideSnackBar() {
        if (snackbar != null) {
            snackbar.dismiss();
            snackbar = null;
        }
    }

    public Storage getStorage() {
        return Storage.instance(getBaseContext());
    }

    public boolean isTakePhotoPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.CAMERA}, PERMISSION_PHOTO_REQUEST);

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    // Checking if the user has granted permission of external storage
    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_STORAGE_REQUEST);

                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO
                    // Permission Granted
                    //resume tasks needing this permission
                    LogUtil.info(this, "Permission granded");
                } else {
                    //TODO
                    // Permission Denied
                    LogUtil.info(this, "Permission denied");
                }
                break;
            case PERMISSION_PHOTO_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //TODO
                    // Permission Granted
                    //resume tasks needing this permission
                    LogUtil.info(this, "Permission granded");
                } else {
                    //TODO
                    // Permission Denied
                    LogUtil.info(this, "Permission denied");
                }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        /*View decor_View = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT < 19) { //19 or above api
            decor_View.setSystemUiVisibility(View.GONE);
        } else {
            int ui_Options =
                    //View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            //| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            //| View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            decor_View.setSystemUiVisibility(ui_Options);
        }*/
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                getWindow().getDecorView().setSystemUiVisibility(
                        //View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
            }
        }
    }
}
