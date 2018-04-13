package com.llc111minutes.gameday.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.api.Creator;
import com.llc111minutes.gameday.common.BaseActivity;
import com.llc111minutes.gameday.common.BaseDrawerActivity;
import com.llc111minutes.gameday.model.Pack;
import com.llc111minutes.gameday.model.enums.GlobalState;
import com.llc111minutes.gameday.ui.adapter.PagerView;
import com.llc111minutes.gameday.ui.fragment.EditFragment;
import com.llc111minutes.gameday.ui.fragment.MainFragment;
import com.llc111minutes.gameday.ui.fragment.TermsConditionsFragment;
import com.llc111minutes.gameday.ui.fragment.dialog.ConnectionAlertDialogFragment;
import com.llc111minutes.gameday.ui.widget.LatoBlackTextView;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.LogUtil;
import com.llc111minutes.gameday.util.Storage;

import static com.llc111minutes.gameday.Const.BASE_64_ENCODE_PUBLIC_KEY;
import static com.llc111minutes.gameday.Const.REQUEST_CODE;
import static com.llc111minutes.gameday.Const.SKU_PURCHASE_BUY;

public class MainActivity extends BaseDrawerActivity implements View.OnClickListener, Creator.Callback, ConnectionAlertDialogFragment.OnExitErrorConnection {

    private final static String STATE = "com.matchday.android.ui.activity.my_state";
    private final static String SAVE_PACK = "com.matchday.android.ui.activity.save_pack";
    private Button buttonPurchase;
    private GlobalState globalState = GlobalState.START;
    private Creator creator;
    private Pack pack;
    private Storage mStorage;
    IabHelper mHelper;

    @Override
    public int getLayout() {
        return R.layout.activity_drawer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttonPurchase = (Button) navigationView.findViewById(R.id.navBuy);
        buttonPurchase.setOnClickListener(this);
        mStorage = Storage.instance(getBaseContext());

        settingsMenu();
        if (savedInstanceState != null) {
            globalState = (GlobalState) savedInstanceState.getSerializable(STATE);
            if (globalState == null) {
                globalState = GlobalState.START;
            }
        }
        LogUtil.info(this, "Density: " + getResources().getString(R.string.ratio));


        mHelper = new IabHelper(this, BASE_64_ENCODE_PUBLIC_KEY);
        //TODO убрать дебагер
        mHelper.enableDebugLogging(true);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Произошла ошибка авторизации библиотеки, скрываем кнопку от пользователя
                    LogUtil.info(this, "ERROR autorithation Purchase library: " + result.getMessage());
                    return;
                }
                if (mHelper == null) return;
                try {
                    mHelper.queryInventoryAsync(mReceivedInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * здесь совершаю покупку
     */
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            if (result.isFailure()) {
                LogUtil.info(this, "ERROR Purchase: " + result.getMessage() + " Resp: " + result.getResponse());
                // Обработка произошедшей ошибки покупки
                switch (result.getResponse()) {
                    case 7:
                        mStorage.setPurchaseState(true);
                        break;
                }
                return;
            }
            if (purchase.getSku().equals(SKU_PURCHASE_BUY)) {
                LogUtil.info(this, "SUCCESS Purchase: " + result.getMessage());
                try {
                    mHelper.queryInventoryAsync(mReceivedInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    e.printStackTrace();
                }
                // Говорим пользователю спасибо за перечисление средств
                Toast.makeText(MainActivity.this, R.string.nav_purchase, Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * здесь проверка была ли покупка
     */
    IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        @Override
        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
            if (mHelper == null) return;
            if (result.isFailure()) {
                LogUtil.info(this, "Failure mReceivedInventoryListener:  " + result.getMessage());
                return;
            }
            if (mStorage != null) {
                mStorage.setPurchaseState(inv.hasPurchase(SKU_PURCHASE_BUY));
            }
            setStatPurchaseButton();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(EditFragment.class.getCanonicalName());
            if (fragment == null) return;
            if (!(fragment instanceof EditFragment)) return;
            ((EditFragment) fragment).applyPurchaseBrandLogo();
        }
    };

    private void setStatPurchaseButton() {
        if (mStorage.getPurchaseState()) {
            buttonPurchase.setClickable(false);
            buttonPurchase.setEnabled(false);
        } else {
            buttonPurchase.setClickable(true);
            buttonPurchase.setEnabled(true);
        }
    }

    /**
     * method for make purchase
     */
    public void makePurchase() {
        if (mHelper == null) return;
        try {
            mHelper.launchPurchaseFlow(this, SKU_PURCHASE_BUY, REQUEST_CODE, mPurchaseFinishedListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            LogUtil.info(this, "" + e);
        }
    }

    private void addMainFragment() {
        MainFragment mainFragment = (MainFragment)
                getSupportFragmentManager()
                        .findFragmentByTag(MainFragment.class.getCanonicalName());
        if (mainFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.coordinator_layout, new MainFragment(),
                            MainFragment.class.getCanonicalName())
                    .commitAllowingStateLoss();
        }
    }

    private void settingsMenu() {
        navigationView.findViewById(R.id.navigation_restore).setOnClickListener(this);
        navigationView.findViewById(R.id.navigation_share).setOnClickListener(this);
        navigationView.findViewById(R.id.navigation_rate).setOnClickListener(this);
        navigationView.findViewById(R.id.editing_tips).setOnClickListener(this);
        navigationView.findViewById(R.id.terms_and_conditions).setOnClickListener(this);
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i) {
            case R.id.navigation_restore:
                Toast.makeText(MainActivity.this, R.string.slogan, Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_share:
                Toast.makeText(MainActivity.this, R.string.slogan, Toast.LENGTH_SHORT).show();
                break;
            case R.id.navigation_rate:
                Toast.makeText(MainActivity.this, R.string.slogan, Toast.LENGTH_SHORT).show();
                break;
            case R.id.editing_tips:
                getDrawer().closeDrawer(GravityCompat.START);
                getEditingTipsDialog();
                break;
            case R.id.terms_and_conditions:
                BaseActivity.addFragment(this, TermsConditionsFragment.class, R.id.coordinator_layout, null, true, true, true);

                getDrawer().closeDrawer(GravityCompat.START);
                mFlagDrawableOpen = true;
                break;
            case R.id.navBuy:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogCustom)
                        .setCancelable(false)
                        .setMessage(R.string.dialog_alert_purchase)
                        .setNegativeButton(R.string.no, (dialogInterface, which) -> dialogInterface.dismiss())
                        .setPositiveButton(R.string.yes, (dialogInterface, which) -> {
                            makePurchase();
                            Toast.makeText(MainActivity.this, R.string.nav_purchase, Toast.LENGTH_SHORT).show();
                            dialogInterface.dismiss();
                        })
                        .create();
                alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                alertDialog.show();
                alertDialog.getWindow().getDecorView().setSystemUiVisibility(
                        getAct().getWindow().getDecorView().getSystemUiVisibility());
                alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.info(this, "My state: " + globalState);
        if (pack == null) {
            creator = new Creator();
            creator.setCallback(this);
            creator.onStart();
        }
        addMainFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (creator != null) {
            creator.onStop();
        }
        creator = null;
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getCanonicalName());
        if (fragment instanceof MainFragment) {
            ((MainFragment) fragment).offProgress();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtil.info(this, "onSaveInstanceState");
        outState.putSerializable(STATE, globalState);
        outState.putParcelable(SAVE_PACK, pack);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        globalState = (GlobalState) savedInstanceState.getSerializable(STATE);
        if (globalState == null) {
            globalState = GlobalState.START;
        }
        pack = savedInstanceState.getParcelable(SAVE_PACK);
        LogUtil.info(this, "onRestoreInstanceStat, iteration: " + globalState);
    }

    public void setGlobalState(GlobalState globalState) {
        this.globalState = globalState;
    }

    @Override
    public void onError(Throwable e) {
        DialogFragment errConn = ConnectionAlertDialogFragment.newInstance();
        ((ConnectionAlertDialogFragment) errConn).setReconnectListener(this);
        errConn.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialogStyle);
        errConn.show(getSupportFragmentManager(), errConn.getClass().getName());
        LogUtil.info(this, "Error: " + e.getMessage());
        e.printStackTrace();
    }

    @Override
    public void onCompleted(Pack pack) {
        this.pack = pack;
    }

    @Override
    public void onFinish() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getCanonicalName());
        if (fragment instanceof MainFragment) {
            ((MainFragment) fragment).offProgress();
        }
    }

    @Override
    public void reConnect() {
        if (pack == null) {
            if (creator == null) {
                creator = new Creator();
            } else {
                creator.onStop();
            }
            creator.setCallback(this);
            creator.onStart();
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getCanonicalName());
            if (fragment instanceof MainFragment) {
                ((MainFragment) fragment).showDialogProgress();
            }
        }
    }

    public Pack getPack() {
        return pack;
    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.info(this, "onConfigurationChanged: "+ (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO));
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        if (mHelper != null) {
            try {
                mHelper.dispose();
            } catch (IabHelper.IabAsyncInProgressException e) {
                e.printStackTrace();
            }
            mHelper = null;
        }
        super.onDestroy();
    }

    public void getEditingTipsDialog() {
        AlertDialog malertDialog = new AlertDialog.Builder(this, R.style.full_screen_dialog)
                .setView(R.layout.view_editing_tips)
                .setCancelable(false)
                .create();

        malertDialog.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == event.KEYCODE_BACK) malertDialog.dismiss();
            return true;
        });
        malertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        malertDialog.show();
        malertDialog.getWindow().getDecorView().setSystemUiVisibility(
                getAct().getWindow().getDecorView().getSystemUiVisibility());
        malertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        ViewPager pager = (ViewPager) malertDialog.findViewById(R.id.pager);
        LatoBlackTextView tvEnter_tips = (LatoBlackTextView) malertDialog.findViewById(R.id.tvEnter_tips);
        CustomFontTextView tvSkip_tips = (CustomFontTextView) malertDialog.findViewById(R.id.tvSkip_tips);
        LinearLayout llIndication_tips = (LinearLayout) malertDialog.findViewById(R.id.llIndication_tips);
        tvSkip_tips.setOnClickListener(v -> malertDialog.dismiss());
        tvEnter_tips.setOnClickListener(v -> malertDialog.dismiss());
        pager.setAdapter(new PagerView(MainActivity.this));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 2:
                        tvSkip_tips.setAlpha(1 - positionOffset);
                        tvEnter_tips.setAlpha(positionOffset);
                        break;
                }

                for (int i = 0; i < llIndication_tips.getChildCount(); i++) {
                    float offset = 1 - positionOffset;
                    if (offset < 0.4F) {
                        offset = 0.4F;
                    }
                    if (i == position) {
                        llIndication_tips.getChildAt(i).setAlpha(offset);
                    } else {
                        llIndication_tips.getChildAt(i).setAlpha(0.4f);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 3) {
                    tvSkip_tips.setVisibility(View.INVISIBLE);
                    tvEnter_tips.setVisibility(View.VISIBLE);
                } else {
                    tvSkip_tips.setVisibility(View.VISIBLE);
                    tvEnter_tips.setVisibility(View.INVISIBLE);
                }
               /* switch (position) {
                    case 2:
                        tvSkip_tips.setVisibility(View.VISIBLE);
                        tvEnter_tips.setVisibility(View.INVISIBLE);
                        break;
                    case 3:
                        tvSkip_tips.setVisibility(View.INVISIBLE);
                        tvEnter_tips.setVisibility(View.VISIBLE);
                }*/

                for (int i = 0; i < llIndication_tips.getChildCount(); i++) {
                    if (i == position) {
                        llIndication_tips.getChildAt(i).setAlpha(1F);
                    } else {
                        llIndication_tips.getChildAt(i).setAlpha(0.4F);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
