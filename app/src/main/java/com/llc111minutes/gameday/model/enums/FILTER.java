package com.llc111minutes.gameday.model.enums;

import com.llc111minutes.gameday.R;

import java.io.Serializable;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

public enum FILTER implements Serializable{
    BRIGHTNESS          (0, R.drawable.ic_brightness, R.string.brightness, 0f, -0.29f, 0.29f, Float.MIN_VALUE),
    CONTRAST            (1, R.drawable.ic_contrast, R.string.contrast, 1.1f, 0.6f, 1.6f, Float.MIN_VALUE),
    SHARPEN             (2, R.drawable.ic_sharpness, R.string.sharpness, 0f, -4.0f, 4.0f, Float.MIN_VALUE),
    SATURATION          (3, R.drawable.ic_saturation, R.string.saturation, 1.0f, 0.0f, 2.0f, Float.MIN_VALUE),
    WHITE_BALANCE       (4, R.drawable.ic_temperatures, R.string.white_balance,
            6000.0f, 0.0f, 12000.0f, Float.MIN_VALUE),
    HIGHLIGHT_SHADOW    (5, R.drawable.ic_shadows, R.string.highlight_shadow, 0.5f, 0f, 1.0f, Float.MIN_VALUE),
    GAUSSIAN_BLUR       (6, R.drawable.ic_blur, R.string.gaussian_blur, 0.5f, 0f, 1.0f, Float.MIN_VALUE);

    private int id;
    private int iconId;
    private int nameId;
    private float start;
    private float min;
    private float max;
    private float current = Float.MIN_VALUE;

    FILTER(int id, int iconId, int nameId, float start, float min, float max, float current) {
        this.id = id;
        this.iconId = iconId;
        this.nameId = nameId;
        this.start = start;
        this.min = min;
        this.max = max;
        this.current = current;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public static FILTER getFilterById(int id) {
        for (FILTER current : values()) {
            if (current.getId() == id) {
                return current;
            }
        }
        return CONTRAST;
    }

    public int getId() {
        return id;
    }

    public int getIconId() {
        return iconId;
    }

    public int getNameId() {
        return nameId;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public static GPUImage apply(FILTER filter, GPUImage gpuImage) {
        switch (filter) {
            case CONTRAST:
                gpuImage.setFilter(new GPUImageContrastFilter(filter.getCurrent()));
                break;
            case BRIGHTNESS:
                gpuImage.setFilter(new GPUImageBrightnessFilter(filter.getCurrent()));
                break;
            case SHARPEN:
                gpuImage.setFilter(new GPUImageSharpenFilter(filter.getCurrent()));
                break;
            case SATURATION:
                gpuImage.setFilter(new GPUImageSaturationFilter(filter.getCurrent()));
                break;
            case HIGHLIGHT_SHADOW:
                GPUImageHighlightShadowFilter gpuImageHighlightShadowFilter =
                        new GPUImageHighlightShadowFilter(filter.getMin(),filter.getMax());
                gpuImageHighlightShadowFilter.setHighlights(0f);
                gpuImageHighlightShadowFilter.setShadows(filter.getCurrent());
                gpuImage.setFilter(gpuImageHighlightShadowFilter);
                break;
            case WHITE_BALANCE:
                GPUImageWhiteBalanceFilter gpuImageWhiteBalanceFilter =
                        new GPUImageWhiteBalanceFilter(filter.getMin(),filter.getMax());
                gpuImageWhiteBalanceFilter.setTint(0f);
                gpuImageWhiteBalanceFilter.setTemperature(filter.getCurrent());
                gpuImage.setFilter(gpuImageWhiteBalanceFilter);
                break;
            case GAUSSIAN_BLUR:
                GPUImageGaussianBlurFilter gpuImageGaussianBlurFilter =
                        new GPUImageGaussianBlurFilter();
                gpuImageGaussianBlurFilter.setBlurSize(filter.getCurrent());
                gpuImage.setFilter(gpuImageGaussianBlurFilter);
                break;
        }
        return gpuImage;
    }
}
