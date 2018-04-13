package com.llc111minutes.gameday.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseActivity;
import com.llc111minutes.gameday.common.BaseFragment;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.activity.MainActivity;
import com.llc111minutes.gameday.util.BitmapUtils;
import com.llc111minutes.gameday.util.LogUtil;
import com.llc111minutes.gameday.util.TypefaceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainFragment extends BaseFragment {

    private final static String TAKE_PHOTO = "com.matchday.android.ui.fragment.take.photo";
    private final static String OPEN_GALLERY = "com.matchday.android.ui.fragment.take.gallery";
    private final static String PHOTO_PATH = "com.matchday.android.ui.fragment.photo.path";
    protected String mCurrentPhotoPath;
    private TextView mTextView;
    private ProgressDialog progressDialog;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxClick.addDelayOnPressedListener(this, R.id.img_settings);
        RxClick.addDelayOnPressedListener(this, R.id.tvTakePhoto);
        RxClick.addDelayOnPressedListener(this, R.id.tvPhotoFromGallery);
        mTextView = (TextView) view.findViewById(R.id.tv_brand_name);
        if (getAct().getPack() == null) {
            showDialogProgress();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        applySpanInText();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Const.INTENT_RESULT:
                    if (data != null) {
                        mCurrentPhotoPath = getPathFromContent(data.getData());

                        //If the photo is removed from the device but exists in the cloud
                        if (mCurrentPhotoPath == null) {
                            mCurrentPhotoPath = getPathFromContent(getImageUrlWithAuthority(getContext(), data.getData()));
                        }
                    }
                    break;
                case Const.INTENT_PHOTO_RESULT:
                    break;
            }

            BaseActivity.addFragment(getAct(), CropFragment.class, R.id.coordinator_layout,
                    CropFragment.getBundle(mCurrentPhotoPath), true, true, true);
        }
    }

    /**
     * Get the string with the path to the image file
     *
     * @param URI - uri content provider
     * @return string with the path
     */
    private String getPathFromContent(Uri URI) {
        String s;
        String[] FILE = {MediaStore.Images.Media.DATA};
        Cursor cursor = getAct().getContentResolver().query(URI,
                FILE, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(FILE[0]);
        s = cursor.getString(columnIndex);
        cursor.close();
        return s;
    }

    /**
     * Get the new uri content to the image file
     *
     * @param context - context application
     * @param uri     - uri content provider
     * @return uri content to the image
     */
    public Uri getImageUrlWithAuthority(Context context, Uri uri) {
        InputStream is = null;
        if (uri.getAuthority() != null) {
            try {
                is = context.getContentResolver().openInputStream(uri);
                Bitmap bmp = BitmapFactory.decodeStream(is);
                return BitmapUtils.writeToTempImageAndGetPathUri(context, bmp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null)
                        is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    protected void takePhoto() {
        LogUtil.info(this, "takePhoto");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int iteration = getArguments().getInt(TAKE_PHOTO, 0);
            if (iteration > 2) {
                Toast.makeText(getContext(), "Permission error!", Toast.LENGTH_SHORT).show();
                getArguments().putInt(TAKE_PHOTO, 0);
                return;
            }

            if (!getAct().isTakePhotoPermissionGranted()) {
                getArguments().putInt(TAKE_PHOTO, ++iteration);
                return;
            }

            getArguments().putInt(TAKE_PHOTO, 0);
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getAct().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                //String imageFileName = "JPEG_" + timeStamp + "_";
                String imageFileName = "first_screen";
                photoFile = createImageFile(imageFileName);
                // Save a file: path for use with ACTION_VIEW intents
                mCurrentPhotoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.llc111minutes.gameday.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                setTargetFragment(this, Const.INTENT_PHOTO_RESULT);
                startActivityForResult(takePictureIntent, Const.INTENT_PHOTO_RESULT);
            }
        }
    }

    protected void openGallery() {
        LogUtil.info(this, "openGallery");
        if (Build.VERSION.SDK_INT >= 23) {
            int iteration = getArguments().getInt(OPEN_GALLERY, 0);
            if (iteration > 2) {
                Toast.makeText(getContext(), "Permission error!", Toast.LENGTH_SHORT).show();
                getArguments().putInt(OPEN_GALLERY, 0);
                return;
            }

            if (!getAct().isStoragePermissionGranted()) {
                getArguments().putInt(OPEN_GALLERY, ++iteration);
                return;
            }

            getArguments().putInt(OPEN_GALLERY, 0);
        }

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        setTargetFragment(this, Const.INTENT_RESULT);
        startActivityForResult(galleryIntent, Const.INTENT_RESULT);
    }

    @Override
    public void onResume() {
        super.onResume();
        permissionResumed();
        getAct().setDrawerState(true);
    }

    private void permissionResumed() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getArguments().getInt(TAKE_PHOTO, 0) > 0) {
                takePhoto();
            } else if (getArguments().getInt(OPEN_GALLERY, 0) > 0) {
                openGallery();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_parent_menu;
    }

    @Override
    public MainActivity getAct() {
        return (MainActivity) getActivity();
    }

    @Override
    protected void saveValue(Bundle outState) {
        outState.putString(PHOTO_PATH, mCurrentPhotoPath);
    }

    @Override
    protected void restoreValue(Bundle outState) {
        mCurrentPhotoPath = outState.getString(PHOTO_PATH, "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_settings:
                getAct().getDrawer().openDrawer(GravityCompat.START);
                break;
            case R.id.tvTakePhoto:
                takePhoto();
                break;
            case R.id.tvPhotoFromGallery:
                openGallery();
        }
    }

    private void applySpanInText() {
        SpannableStringBuilder spannable = new SpannableStringBuilder(mTextView.getText());
        int color = ContextCompat.getColor(getContext(), R.color.red);
        spannable.setSpan(new ForegroundColorSpan(color), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextView.setTypeface(TypefaceUtils.getLatoBlackTypeface(getContext()));
        mTextView.setText(spannable);
    }

    public void showDialogProgress() {
        if (progressDialog != null && progressDialog.isShowing()) return;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Load data...");
        progressDialog.show();
    }

    public void offProgress() {
        progressDialog.dismiss();
    }
}
