package com.llc111minutes.gameday.ui.fragment;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseFragment;
import com.llc111minutes.gameday.interfaces.BackEventInterface;
import com.llc111minutes.gameday.model.enums.GlobalState;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.activity.MainActivity;
import com.llc111minutes.gameday.ui.widget.owner.CustomFontTextView;
import com.llc111minutes.gameday.util.LogUtil;

import java.io.File;

import butterknife.BindView;


public class ShareFragment extends BaseFragment implements BackEventInterface, View.OnClickListener {
    private static final String MY_BITMAP = "bitmap";
    @BindView(R.id.surfaceView)
    protected ImageView mImageToShare;
    @BindView(R.id.button_share_instagram)
    protected LinearLayout mButtonInstagram;
    @BindView(R.id.button_share_save)
    protected LinearLayout mButtonSave;
    @BindView(R.id.button_share_more)
    protected LinearLayout mButtonMore;
    @BindView(R.id.tv_new)
    protected CustomFontTextView mTextViewNew;
    @BindView(R.id.tvBack)
    protected CustomFontTextView mTextViewBack;

    private String mPathBitmap;

    public static Bundle getBundle(String path) {
        Bundle bundle = new Bundle();
        bundle.putString(MY_BITMAP, path);
        return bundle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAct().setGlobalState(GlobalState.SHARE);
        mPathBitmap = getArguments().getString(MY_BITMAP);
        initView();
        RxClick.addDelayOnPressedListener(this, mButtonInstagram);
        RxClick.addDelayOnPressedListener(this, mButtonSave);
        RxClick.addDelayOnPressedListener(this, mButtonMore);
        RxClick.addDelayOnPressedListener(this, mTextViewBack);
        RxClick.addDelayOnPressedListener(this, mTextViewNew);
    }

    private void initView() {
        mImageToShare.setImageURI(Uri.parse("file://" + mPathBitmap));
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_share;
    }

    @Override
    public MainActivity getAct() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case (R.id.button_share_instagram):
                startActivity(getInstagramIntent());
                break;
            case (R.id.button_share_save):
                requestFileNameAndSave();
                break;
            case (R.id.button_share_more):
                LogUtil.info(this, "Click button MORE");
                startActivity(getMoreIntent());
                break;
            case (R.id.tv_new):
                LogUtil.info(this, "Click button NEW");
                break;
            case (R.id.tvBack):
                onBackEvent();
                break;
        }
    }

    @NonNull
    private Intent getInstagramIntent() {
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        if (!isAppInstalled(this, Const.PACKAGE_INSTAGRAM)) {
            // bring user to the market to download the app.
            shareIntent = new Intent(Intent.ACTION_VIEW);
            shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.setData(Uri.parse("market://details?id=" + Const.PACKAGE_INSTAGRAM));
        } else {
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setPackage(Const.PACKAGE_INSTAGRAM);
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + mPathBitmap));
            shareIntent.setType("image/*");
        }
        return shareIntent;
    }

    private void requestFileNameAndSave() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_save_photo, null);
        alertDialog.setView(v);
        alertDialog.setPositiveButton("Save", (dialog, which) -> {
            String s = "";
            try {
                File oldFile = new File(mPathBitmap);
                if (oldFile.exists()) {
                    String c = String.valueOf(((EditText) v.findViewById(R.id.et_file_name)).getText());
                    File file = new File(oldFile.getParent(), c + ".jpg");
                    if (oldFile.renameTo(file)) {
                        oldFile = file;
                        galleryAddPic(oldFile);
                        mPathBitmap = oldFile.getPath();
                    }
                }
                s = "File save";
            } finally {
                dialog.dismiss();
                Snackbar.make(this.getView(), s, Snackbar.LENGTH_LONG).show();
            }
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        alertDialog.show();
    }

    @NonNull
    private Intent getMoreIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + mPathBitmap));
        intent.setType("image/*");
        String title = getResources().getString(R.string.title_select_app);
        Intent chooser = Intent.createChooser(intent, title);
        return chooser;
    }


    private boolean isAppInstalled(ShareFragment shareFragment, String s) {
        PackageManager pm = shareFragment.getContext().getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(s, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            LogUtil.info(this, "Instagram not find");
        }
        return pi != null;
    }

    private void galleryAddPic(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        getContext().sendBroadcast(mediaScanIntent);
    }

    @Override
    public void onBackEvent() {
        LogUtil.info(this, "OnBackPressed");
        getFragmentManager().popBackStack();
        /*ApiService service = ApiManager.getClient().create(ApiService.class);
        service.getAllTeams().enqueue(new Callback<List<TeamInfo>>() {
            /*//** @Override public void onResponse(Call<List<TeamInfo>> call, Response<List<TeamInfo>> response) {
        LogUtil.info(this, "Call<List<TeamInfo>> : " + response.body());
        for (TeamInfo teamInfo : response.body()) {
        LogUtil.info("TeamInfo", teamInfo.toString());
        }
        }

         @Override public void onFailure(Call<List<TeamInfo>> call, Throwable t) {
         LogUtil.info(this, "Call<List<TeamInfo>> : " + call);
         t.printStackTrace();
         }
         });*/
    }

    @Override
    public boolean isEndingPressed() {
        return false;
    }
}
