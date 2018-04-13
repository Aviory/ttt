package com.llc111minutes.gameday.util;

import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageChromaKeyBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLightenBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOverlayBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelThresholdFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSoftLightBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageThresholdEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWeakPixelInclusionFilter;

/*[[MDEffect alloc] initWithTitle:NSLocalizedString(@"Normal", @"Effect name")
        filter:nil],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Polka Dot", @"Effect name")
        filter:[GPUImagePolkaDotFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Pixellate", @"Effect name")
        filter:[GPUImagePixellateFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Kuwahara", @"Effect name")
        filter:[GPUImageKuwaharaFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Sepia", @"Effect name")
        filter:[GPUImageSepiaFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Tone", @"Effect name")
        filter:[GPUImageHalftoneFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Amatorka", @"Effect name")
        filter:[GPUImageAmatorkaFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Miss Etikate", @"Effect name")
        filter:[GPUImageMissEtikateFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Soft Elegance", @"Effect name")
        filter:[GPUImageSoftEleganceFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Color invert", @"Effect name")
        filter:[GPUImageColorInvertFilter class]],
        [[MDEffect alloc] initWithTitle:NSLocalizedString(@"Toon", @"Effect name")
        filter:[GPUImageToonFilter class]],*/

public class FilterUtil {
    public enum FILTER {
//        Normal                  (0, "Normal"),
//        Bilateral               (1, "Bilateral"),
//        BoxBlur                 (2, "Box Blur"),
//        Brightness              (3, "Brightness"),
//        BulgeDistortion         (4, "Bulge Distortion"),
//        CGAColorSpace           (5, "CGA Color Space"),
//        ChromaKeyBlend          (6, "Chroma Key Blend"),
//        ColorBalance            (7, "Color Balance"),
//        ColorBlend              (8, "Color Blend"),
//        ColorInvert             (9, "Color Invert"),//10
//        Contrast                (10, "Contrast"),//9
//        Crosshatch              (11, "Crosshatch"),
//        Dilation                (12, "Dilation"),
//        DirectionalSobel        (13, "Dir Sobel Edge"),
//        DissolveBlend           (14, "Dissolve Blend"),//7
//        Emboss                  (15, "Emboss"),
//        Exposure                (16, "Exposure"),//8
//        FalseColor              (17, "False Color"),
//        Gamma                   (18, "Gamma"),
//        GaussianBlur            (19, "Gaussian Blur"),
//        GlassSphere             (20, "Glass Sphere"),
//        GrayScale               (21, "Gray Scale"),
//        Halftone                (22, "Halftone"),//6
//        Haze                    (23, "Haze"),
//        HighlightShadow         (24, "Highlight Shadow"),
//        HueBlend                (25, "Hue Blend"),
//        HueFilter               (26, "Hue Filter"),
//        Kuwahara                (27, "Kuwahara"),//4
//        Laplacian               (28, "Laplacian"),
//        LightenBlend            (29, "Lighten Blend"),
//        Lookup                  (30, "Lookup"),
//        Monochrome              (31, "Image Monochrome"),
//        NonMaximumSupp          (32, "Non Maximum Supp"),
//        Opacity                 (33, "Opacity"),
//        OverlayBlend            (34, "Overlay Blend"),
//        Pixelation              (35, "Pixelation"),//3
//        Posterize               (36, "Posterize"),
//        RGBDilation             (37, "RGB Dilation"),
//        SaturationBlend         (38, "Saturation Blend"),
//        Saturation              (39, "Saturation"),
//        Sepia                   (40, "Sepia"),//5
//        Sketch                  (41, "Sketch"),
//        SmoothToon              (42, "Smooth Toon"),
//        SobelEdge               (43, "Sobel Edge Detection"),
//        SobelThres              (44, "Sobel Thres Hold"),
//        SoftLightBlend          (45, "Soft Light Blend"),
//        SphereRefraction        (46, "Sphere Refraction"),
//        Swirl                   (47, "Swirl"),
//        Threshold               (48, "Thres Hold"),
//        Toon                    (49, "Toon"),//11
//        Vignette                (50, "Vignette"),
//        WeakPixelInclusion      (51, "Weak Pixel Inclusion");

        Normal                  (0, "Normal"),
        Sketch                  (1, "Sketch"),
        Pixelation              (2, "Pixelation"),
        Kuwahara                (3, "Kuwahara"),
        Sepia                   (4, "Sepia"),
        Halftone                (5, "Halftone"),
        DissolveBlend           (6, "Dissolve Blend"),
        Exposure                (7, "Exposure"),
        Contrast                (8, "Contrast"),
        ColorInvert             (9, "Color Invert"),
        Toon                    (10, "Toon");


        private final int id;
        private final String filterName;

        FILTER(int id, String filterName) {
            this.id = id;
            this.filterName = filterName;
        }

        public static FILTER getById(int id) {
            for (FILTER curr : values()) {
                if (curr.getId() == id) {
                    return curr;
                }
            }
            return Normal;
        }

        public int getId() {
            return id;
        }

        public String getFilterName() {
            return filterName;
        }
    }

    public static GPUImageFilter getFilter(FILTER filter) {
        switch (filter) {

            case Normal:
                break;
//            case Bilateral:
//                GPUImageBilateralFilter gpuImageBilateralFilter =
//                        new GPUImageBilateralFilter();
//                gpuImageBilateralFilter.setDistanceNormalizationFactor(0.1f);
//                gpuImageBilateralFilter.onInitialized();
//                return gpuImageBilateralFilter;
//            case BoxBlur:
//                return new GPUImageBoxBlurFilter();
//            case Brightness:
//                GPUImageBrightnessFilter gpuImageBrightnessFilter =
//                        new GPUImageBrightnessFilter();
//                gpuImageBrightnessFilter.setBrightness(0.4f);
//                gpuImageBrightnessFilter.onInitialized();
//                return gpuImageBrightnessFilter;
//            case BulgeDistortion:
//                GPUImageBulgeDistortionFilter gpuImageBulgeDistortionFilter =
//                        new GPUImageBulgeDistortionFilter();
//                gpuImageBulgeDistortionFilter.setRadius(0.4f);
//                gpuImageBulgeDistortionFilter.onInitialized();
//                return gpuImageBulgeDistortionFilter;
//            case CGAColorSpace:
//                return new GPUImageCGAColorspaceFilter();
//            case ChromaKeyBlend:
//                GPUImageChromaKeyBlendFilter gpuImageChromaKeyBlendFilter =
//                        new GPUImageChromaKeyBlendFilter();
//                gpuImageChromaKeyBlendFilter.setSmoothing(0.4f);
//                gpuImageChromaKeyBlendFilter.onInitialized();
//                return gpuImageChromaKeyBlendFilter;
//            case ColorBalance:
//                GPUImageColorBalanceFilter gpuImageColorBalanceFilter =
//                        new GPUImageColorBalanceFilter();
//                float[]shadows = {1.0f, 0.9f, 0.8f, 0.7f, 0.6f, 0.5f, 0.4f, 0.3f, 0.2f, 0.1f};
//                gpuImageColorBalanceFilter.setShowdows(shadows);
//                gpuImageColorBalanceFilter.onInitialized();
//                return gpuImageColorBalanceFilter;
//            case ColorBlend:
                //return new GPUImageColorBlendFilter();
            case ColorInvert:
                return new GPUImageColorInvertFilter();
            case Contrast:
                return new GPUImageContrastFilter();
//            case Crosshatch:
//                return new GPUImageCrosshatchFilter();
//            case Dilation:
//                return new GPUImageDilationFilter();
//            case DirectionalSobel:
//                return new GPUImageDirectionalSobelEdgeDetectionFilter();
            case DissolveBlend:
                return new GPUImageDissolveBlendFilter();
//            case Emboss:
//                return new GPUImageEmbossFilter();
            case Exposure:
                return new GPUImageExposureFilter();
//            case FalseColor:
//                return new GPUImageFalseColorFilter();
//            case Gamma:
//                return new GPUImageGammaFilter();
//            case GaussianBlur:
//                return new GPUImageGaussianBlurFilter();
//            case GlassSphere:
//                return new GPUImageGlassSphereFilter();
//            case GrayScale:
//                return new GPUImageGrayscaleFilter();
            case Halftone:
                return new GPUImageHalftoneFilter();
//            case Haze:
//                return new GPUImageHazeFilter();
//            case HighlightShadow:
//                return new GPUImageHighlightShadowFilter();
//            case HueBlend:
//                return new GPUImageHueBlendFilter();
//            case HueFilter:
//                return new GPUImageHueFilter();
            case Kuwahara:
                return new GPUImageKuwaharaFilter();
//            case Laplacian:
//                return new GPUImageLaplacianFilter();
//            case LightenBlend:
//                return new GPUImageLightenBlendFilter();
//            case Lookup:
//                GPUImageLookupFilter gpuImageLookupFilter =
//                        new GPUImageLookupFilter();
//                gpuImageLookupFilter.setIntensity(0.5f);
//                gpuImageLookupFilter.onInitialized();
//                return gpuImageLookupFilter;
//            case Monochrome:
//                return new GPUImageMonochromeFilter();
//            case NonMaximumSupp:
//                return new GPUImageNonMaximumSuppressionFilter();
//            case Opacity:
//                GPUImageOpacityFilter gpuImageOpacityFilter =
//                        new GPUImageOpacityFilter();
//                gpuImageOpacityFilter.setOpacity(0.5f);
//                gpuImageOpacityFilter.onInitialized();
//                return gpuImageOpacityFilter;
//            case OverlayBlend:
//                return new GPUImageOverlayBlendFilter();
            case Pixelation:
                return new GPUImagePixelationFilter();
//            case Posterize:
//                return new GPUImagePosterizeFilter();
//            case RGBDilation:
//                return new GPUImageRGBDilationFilter();
//            case SaturationBlend:
//                return new GPUImageSaturationBlendFilter();
//            case Saturation:
//                GPUImageSaturationFilter gpuImageSaturationFilter =
//                        new GPUImageSaturationFilter();
//                gpuImageSaturationFilter.setSaturation(0.5f);
//                gpuImageSaturationFilter.onInitialized();
//                return gpuImageSaturationFilter;
            case Sepia:
                return new GPUImageSepiaFilter();
            case Sketch:
                return new GPUImageSketchFilter();
//            case SmoothToon:
//                return new GPUImageSmoothToonFilter();
//            case SobelEdge:
//                return new GPUImageSobelEdgeDetection();
//            case SobelThres:
//                return new GPUImageSobelThresholdFilter();
//            case SoftLightBlend:
//                return new GPUImageSoftLightBlendFilter();
//            case SphereRefraction:
//                return new GPUImageSphereRefractionFilter();
//            case Swirl:
//                return new GPUImageSwirlFilter();
//            case Threshold:
//                return new GPUImageThresholdEdgeDetection();
            case Toon:
                return new GPUImageToonFilter();
//            case Vignette:
//                return new GPUImageVignetteFilter();
//            case WeakPixelInclusion:
//                return new GPUImageWeakPixelInclusionFilter();
        }
        return null;
    }
}
