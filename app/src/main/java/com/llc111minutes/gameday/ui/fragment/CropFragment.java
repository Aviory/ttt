package com.llc111minutes.gameday.ui.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.common.BaseActivity;
import com.llc111minutes.gameday.common.BaseFragment;
import com.llc111minutes.gameday.interfaces.BackEventInterface;
import com.llc111minutes.gameday.model.enums.GlobalState;
import com.llc111minutes.gameday.rx.RxClick;
import com.llc111minutes.gameday.ui.activity.MainActivity;
import com.llc111minutes.gameday.ui.widget.LatoBlackTextView;
import com.llc111minutes.gameday.util.LogUtil;
import com.llc111minutes.gameday.util.UiUtils;
import com.yalantis.ucrop.callback.BitmapCropCallback;
import com.yalantis.ucrop.view.TransformImageView;
import com.yalantis.ucrop.view.UCropView;

import java.io.File;

import butterknife.BindView;

public class CropFragment extends BaseFragment implements View.OnTouchListener, BackEventInterface {
    private final static String ROTATION = "com.matchday.android.ui.fragment.rotation";
    private final float MAX_ROTATE = 45;
    private final float MIN_ROTATE = -45;
    private final float ANCHOR_POINT_ROTATE = 2;


    private float x = -1;
    private float rotation;
    private float percent;
    private float change;
    private float difference;

    @BindView(R.id.cropImageView)
    protected UCropView cropImageView;
    @BindView(R.id.vTouch)
    protected View vTouch;
    @BindView(R.id.vWheel)
    protected View vWheel;
    @BindView(R.id.vFirstTouch)
    protected View vFirstTouch;

    @BindView(R.id.tvReset)
    protected LatoBlackTextView tvReset;

    protected void resetImage() {
        rotation = 0;
        vWheel.setRotation(rotation);
        tvReset.setVisibility(View.GONE);
        cropImageView.getCropImageView().postRotate(-cropImageView.getCropImageView().getCurrentAngle());
        cropImageView.getCropImageView().zoomOutImage(cropImageView.getCropImageView().getMinScale());
        cropImageView.getCropImageView().setImageToWrapCropBounds();
        vFirstTouch.setOnTouchListener(this);
    }

    protected void toNextScreen() {
        cropImageView.getCropImageView().cropAndSaveImage(Bitmap.CompressFormat.JPEG, 90, new BitmapCropCallback() {
            @Override
            public void onBitmapCropped(@NonNull Uri resultUri, int imageWidth, int imageHeight) {
                BaseActivity.addFragment(getAct(), EditFragment.class,
                        R.id.coordinator_layout,
                        EditFragment.getBundle(resultUri.getEncodedPath()),
                        true, true, true);
            }

            @Override
            public void onCropFailure(@NonNull Throwable t) {

            }
        });
    }

    protected void toBack() {
        getAct().onBackPressed();
    }

    public static Bundle getBundle(String path) {
        Bundle bundle = new Bundle();
        bundle.putString(Const.INTENT_EXTRA, path);
        return bundle;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_crop;
    }

    @Override
    public MainActivity getAct() {
        return (MainActivity) getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAct().setGlobalState(GlobalState.CROP);
        cropImageView.getCropImageView().setTargetAspectRatio(1);
        cropImageView.getOverlayView().setTargetAspectRatio(1);
        percent = UiUtils.getScreenWidth(getActivity()) / 180;

        vFirstTouch.setOnTouchListener(this);
        vTouch.setOnTouchListener(this);

        cropImageView.getCropImageView().setRotateEnabled(false);
        cropImageView.getCropImageView().setTransformImageListener(new TransformImageView.TransformImageListener() {
            @Override
            public void onLoadComplete() {
            }

            @Override
            public void onLoadFailure(@NonNull Exception e) {
            }

            @Override
            public void onRotate(float currentAngle) {
            }

            @Override
            public void onScale(float currentScale) {
            }
        });

        RxClick.addDelayOnPressedListener(this, R.id.tvBack);
        RxClick.addDelayOnPressedListener(this, R.id.tvNext);
        RxClick.addDelayOnPressedListener(this, tvReset);

    }

    private void placeImage() {
        if (getArguments() != null) {
            String path = getArguments().getString(Const.INTENT_EXTRA, "");
            LogUtil.info(this, "Path: " + path);
            Uri uri = Uri.fromFile(new File(path));
            try {
                cropImageView.getCropImageView().setImageUri(uri, Uri.fromFile(createImageFile("crop_screen")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.getId() == R.id.vFirstTouch) {
            tvReset.setVisibility(View.VISIBLE);
            view.setOnTouchListener(null);
            return false;
        }
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = motionEvent.getX();
                return true;
            case MotionEvent.ACTION_UP:
                //return true;
            case MotionEvent.ACTION_MOVE:
                difference = x - motionEvent.getX();
                change = difference / percent;
                rotation = rotation + change;
                if (rotation > MAX_ROTATE) {
                    LogUtil.info(">Max", "Change: " + change+" rotate: "+rotation);
                    rotation = MAX_ROTATE;
                    change = 0;
                } else if (rotation < MIN_ROTATE) {
                    LogUtil.info("<Max", "Change: "+change+" rotate: "+rotation);
                    rotation = MIN_ROTATE;
                    change = 0;
                }

                x = motionEvent.getX();
                if (vWheel.getRotation() == rotation) {
                    return true;
                }
                if (tvReset.getVisibility() != View.VISIBLE) {
                    tvReset.setVisibility(View.VISIBLE);
                }
                if (rotation > -ANCHOR_POINT_ROTATE && rotation < ANCHOR_POINT_ROTATE) {
                    cropImageView.getCropImageView().postRotate(-cropImageView.getCropImageView().getCurrentAngle());
                    //cropImageView.getCropImageView().postRotate(0);
                    vWheel.setRotation(0);
                } else {
                    cropImageView.getCropImageView().postRotate(change);
                    vWheel.setRotation(rotation);
                }
                cropImageView.getCropImageView().setImageToWrapCropBounds();

                return true;
        }
        return false;
    }

    @Override
    public void onBackEvent() {
        if (cropImageView != null)
            getAct().getSupportFragmentManager().popBackStack();
    }

    @Override
    public boolean isEndingPressed() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        getAct().setDrawerState(false);
        placeImage();
    }

    @Override
    protected void saveValue(Bundle outState) {
        outState.putFloat(ROTATION, rotation);
        super.saveValue(outState);
    }

    @Override
    protected void restoreValue(Bundle outState) {
        super.restoreValue(outState);
        rotation = outState.getFloat(ROTATION, 0);
        LogUtil.info(this, "Rotation: " + rotation);
    }

    @Override
    public void onClick(View view) {
        LogUtil.info(this, "OnClick: " + view);
        switch (view.getId()) {
            case R.id.tvNext:
                toNextScreen();
                break;
            case R.id.tvReset:
                resetImage();
                break;
            case R.id.tvBack:
                toBack();
        }
    }
}
