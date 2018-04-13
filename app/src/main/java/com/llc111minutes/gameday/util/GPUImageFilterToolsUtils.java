package com.llc111minutes.gameday.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import com.llc111minutes.gameday.R;

import java.util.LinkedList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * Created by marina on 22.09.16.
 */
public class GPUImageFilterToolsUtils {
    public static FilterList fillFilterList(final Context context) {
        final FilterList filters = new FilterList();
        filters.addFilter(context.getString(R.string.brightness), FilterType.BRIGHTNESS,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_brightness, null));
        filters.addFilter(context.getString(R.string.contrast), FilterType.CONTRAST,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_contrast, null));
        filters.addFilter(context.getString(R.string.sharpness), FilterType.SHARPEN,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_sharpness, null));
        filters.addFilter("CROP AND ROTATE", FilterType.TRANSFORM2D,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_crop, null));
        filters.addFilter("SATURATION", FilterType.SATURATION,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_blur, null));
        filters.addFilter("TEMPERATURE", FilterType.WHITE_BALANCE,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_temperature, null));
        filters.addFilter("SHADOWS", FilterType.HIGHLIGHT_SHADOW,
                ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_shadows, null));

        return filters;
    }

    GPUImageBrightnessFilter brightnessFilter = null;
    GPUImageContrastFilter contrastFilter = null;
    GPUImageSharpenFilter sharpness = null;
    GPUImageTransformFilter transformFilter = null;
    GPUImageSaturationFilter saturationFilter = null;
    GPUImageWhiteBalanceFilter whiteBalanceFilter = null;
    GPUImageHighlightShadowFilter highlightShadowFilter = null;

    public GPUImageFilterGroup createFilterGroupForType(final GPUImageFilterGroup group, final FilterType type) {
        switch (type) {
            case BRIGHTNESS:
                if (brightnessFilter == null) {
                    brightnessFilter = new GPUImageBrightnessFilter(0.0f);
                    group.addFilter(brightnessFilter);
                }
                return group;
            case CONTRAST:
                if (contrastFilter == null) {
                    contrastFilter = new GPUImageContrastFilter(1.0f);
                    group.addFilter(contrastFilter);
                }
                return group;
            case SHARPEN:
                if (sharpness == null) {
                    sharpness = new GPUImageSharpenFilter();
                    sharpness.setSharpness(0.0f);
                    group.addFilter(sharpness);
                }
                return group;
            case TRANSFORM2D:
                if (transformFilter == null) {
                    transformFilter = new GPUImageTransformFilter();
                    group.addFilter(transformFilter);
                }
                return group;
            case SATURATION:
                if (saturationFilter == null) {
                    saturationFilter = new GPUImageSaturationFilter(1.0f);
                    group.addFilter(saturationFilter);
                }
                return group;
            case WHITE_BALANCE:
                if (whiteBalanceFilter == null) {
                    whiteBalanceFilter = new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
                    group.addFilter(whiteBalanceFilter);
                }
                return group;
            case HIGHLIGHT_SHADOW:
                if (highlightShadowFilter == null) {
                    highlightShadowFilter = new GPUImageHighlightShadowFilter(0.0f, 1.0f);
                    group.addFilter(highlightShadowFilter);
                }
                return group;
            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    public GPUImageFilter getFilterForType(final FilterType type) {
        switch (type) {
            case BRIGHTNESS:
                return brightnessFilter;
            case CONTRAST:
                return contrastFilter;
            case SHARPEN:
                return sharpness;
            case TRANSFORM2D:
                return transformFilter;
            case SATURATION:
                return saturationFilter;
            case WHITE_BALANCE:
                return whiteBalanceFilter;
            case HIGHLIGHT_SHADOW:
                return highlightShadowFilter;
            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    float brightnessValue = 0.0f;
    float contrastValue = 1.0f;
    float sharpnessValue = 0.0f;
    float transformValue;
    float saturationValue = 1.0f;
    float whitebalanceValue = 5000.0f;
    float highlightshadowsValue = 0.0f;


    public void setCurrentValueForTyp(final FilterType type, float currentValue) {
        switch (type) {
            case BRIGHTNESS:
                this.brightnessValue = currentValue;
                break;
            case CONTRAST:
                contrastValue = currentValue;
                break;
            case SHARPEN:
                sharpnessValue = currentValue;
                break;
            case TRANSFORM2D:
                transformValue = currentValue;
                break;
            case SATURATION:
                saturationValue = currentValue;
                break;
            case WHITE_BALANCE:
                whitebalanceValue = currentValue;
                break;
            case HIGHLIGHT_SHADOW:
                highlightshadowsValue = currentValue;
                break;
            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    public float getCurrentValueForTyp(final FilterType type) {
        switch (type) {
            case BRIGHTNESS:
                return ((brightnessValue * 100) + 100) / 2;
            case CONTRAST:
                return (contrastValue * 100) / 2;
            case SHARPEN:
                return (float) ((sharpnessValue * 12.5) + 50);
            case TRANSFORM2D:
                return transformValue;
            case SATURATION:
                return (saturationValue * 100) / 2;
            case WHITE_BALANCE:
                return (float) Math.round(((whitebalanceValue / 6000) * 100) - 33.333333);
            case HIGHLIGHT_SHADOW:
                return highlightshadowsValue * 100;
            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    public float getCurrentValue(final FilterType type, float cancelValue) {
        switch (type) {
            case BRIGHTNESS:
                return ((cancelValue * 2) - 100) / 100;
            case CONTRAST:
                return ((cancelValue * 2) / 100);
            case SHARPEN:
                return (float) ((cancelValue - 50) / 12.5);
            case TRANSFORM2D:
                return transformValue;
            case SATURATION:
                return ((cancelValue * 2) / 100);
            case WHITE_BALANCE:
                return (float) (((cancelValue + 33.333333) / 100) * 6000);
            case HIGHLIGHT_SHADOW:
                return cancelValue / 100;
            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }

    public float getDefaultValue(final FilterType type) {
        switch (type) {
            case BRIGHTNESS:
                return 0.0f;
            case CONTRAST:
                return 1.0f;
            case SHARPEN:
                return 0.0f;
            case TRANSFORM2D:
                return 1.0f;
            case SATURATION:
                return 1.0f;
            case WHITE_BALANCE:
                return 5000.0f;
            case HIGHLIGHT_SHADOW:
                return 0.0f;
            default:
                throw new IllegalStateException("No defolt value of that type!");
        }

    }

    public float getStepValue(final FilterType type) {
        switch (type) {
            case BRIGHTNESS:
                return (1.0f - (-1.0f)) / 30;
            case CONTRAST:
                return (2.0f - 0.0f) / 30;
            case SHARPEN:
                return (4.0f - (-4.0f)) / 30;
            case TRANSFORM2D:
                return 1.0f;
            case SATURATION:
                return (2.0f - 0.0f) / 30;
            case WHITE_BALANCE:
                return (8000.0f - 2000.0f) / 30;
            case HIGHLIGHT_SHADOW:
                return (1.0f - 0.0f) / 30;
            default:
                throw new IllegalStateException("No defolt value of that type!");
        }

    }


    public interface OnGpuImageFilterChosenListener {
        void onGpuImageFilterChosenListener(GPUImageFilter filter);
    }

    public enum FilterType {
        CONTRAST, SHARPEN, BRIGHTNESS, SATURATION, HIGHLIGHT_SHADOW, WHITE_BALANCE, TRANSFORM2D
    }

    public static class FilterList {
        public List<String> names = new LinkedList<String>();
        public List<FilterType> filters = new LinkedList<FilterType>();
        public List<Drawable> images = new LinkedList<>();

        public void addFilter(final String name, final FilterType filter, Drawable drawable) {
            names.add(name);
            filters.add(filter);
            images.add(drawable);
        }
    }
}
