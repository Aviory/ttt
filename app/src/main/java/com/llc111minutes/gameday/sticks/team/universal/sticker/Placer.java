package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.llc111minutes.gameday.Const;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.Font;
import com.llc111minutes.gameday.model.Headline;
import com.llc111minutes.gameday.model.Logo;
import com.llc111minutes.gameday.model.Overlay;
import com.llc111minutes.gameday.model.Place;
import com.llc111minutes.gameday.model.Team;
import com.llc111minutes.gameday.model.Template;
import com.llc111minutes.gameday.model.enums.Anchor;
import com.llc111minutes.gameday.model.enums.ContentType;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ContainerInterface;
import com.llc111minutes.gameday.sticks.team.universal.sticker.interfaces.ParentInterface;
import com.llc111minutes.gameday.util.LogUtil;

import java.util.HashMap;
import java.util.List;

import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class Placer {
    private static HashMap<ContentType, String> mapSavingValues;

    public static void setMapSavingValues(HashMap<ContentType, String> mapSavingValues) {
        Placer.mapSavingValues = mapSavingValues;
    }

    public static void placeTemplate(Template template, ParentInterface parentView) {
        if (template != null && template.getMetainfo() != null) {
            placePlace(template, parentView, ContentType.PLACE);
            placeHeadline(template, parentView, ContentType.HEADLINE);
            placeVs(template, parentView, ContentType.SEPARATOR_TITLE);

            if (template.getMetainfo().getTeam() != null && template.getMetainfo().getTeam().getTitle() != null) {
                placeTeam(template.getMetainfo().getTeam(), parentView, ContentType.TEAM1);
            }

            if (template.getMetainfo().getTeam() != null && template.getMetainfo().getTeam().getLogo()!=null) {
                if(mapSavingValues !=null && mapSavingValues.get(ContentType.TEAM1LOGO)!=null){
                    placeLogo(template.getMetainfo().getTeam().getLogo(), parentView, ContentType.TEAM1LOGO, mapSavingValues);

                }else {
                    placeLogo(template.getMetainfo().getTeam().getLogo(), parentView, ContentType.TEAM1LOGO);
                }
            }

            if (template.getMetainfo().getTeam2() != null && template.getMetainfo().getTeam2().getTitle() != null) {
                placeTeam(template.getMetainfo().getTeam2(), parentView, ContentType.TEAM2);
            }

            if (template.getMetainfo().getTeam2() != null && template.getMetainfo().getTeam2().getLogo()!=null) {
                if(mapSavingValues !=null && mapSavingValues.get(ContentType.TEAM2LOGO)!=null){
                    placeLogo(template.getMetainfo().getTeam2().getLogo(), parentView, ContentType.TEAM2LOGO, mapSavingValues);

                }else {
                    placeLogo(template.getMetainfo().getTeam2().getLogo(), parentView, ContentType.TEAM2LOGO);
                }
            }

        }

        for (ContainerInterface container : parentView.getContainers()) {
            container.invalidate();
        }
    }

    private static void placeLogo(Logo logo, ParentInterface parentView, ContentType type) {
        //LogUtil.info("Logo: " + logo);
        float width = (float) (logo.getW() * parentView.getWidth());
        float height = (float) (logo.getW() * parentView.getHeight());
        float x = (float) (logo.getX() * parentView.getWidth());
        float y = (float) (logo.getY() * parentView.getHeight());
        Container container = new Container((int) width, (int) height);
        x = x - container.getWidth() / 2;
        container.setX(x);
        y = y - container.getHeight() / 2;
        container.setY(y);
        container.setX(x);
        container.setY(y);
        ImageContent imageContent = new ImageContent();
        imageContent.setType(type);
        container.addContent(imageContent);
        parentView.addContainer(container);
    }
    private static void placeLogo(Logo logo, ParentInterface parentView, ContentType type, HashMap<ContentType, String> mapSavingValues) {
        //LogUtil.info("Logo: " + logo);
        float width = (float) (logo.getW() * parentView.getWidth());
        float height = (float) (logo.getW() * parentView.getHeight());
        float x = (float) (logo.getX() * parentView.getWidth());
        float y = (float) (logo.getY() * parentView.getHeight());
        Container container = new Container((int) width, (int) height);
        x = x - container.getWidth() / 2;
        container.setX(x);
        y = y - container.getHeight() / 2;
        container.setY(y);
        container.setX(x);
        container.setY(y);
        ImageContent imageContent = new ImageContent();
        imageContent.setType(type);
        container.addContent(imageContent);

        container.getContent().setData(mapSavingValues.get(type));
        container.getContent().setDefault(false);

        container.invalidate();

        parentView.addContainer(container);
    }

    public static void placeTeam(Team team, ParentInterface parentView, ContentType type) {
        placeTextContent(parentView,
                team.getTitle().getDefaultText(),
                (float) team.getTitle().getX().doubleValue(),
                (float) team.getTitle().getY().doubleValue(),
                team.getTitle().getFont(),
                team.getTitle().getArchor(),
                type);
    }

    private static void placeVs(Template template, ParentInterface parentView, ContentType type) {
        if (template.getMetainfo().getSeparatorTitle() != null) {
            placeTextContent(parentView,
                    template.getMetainfo().getSeparatorTitle().getDefaultText(),
                    (float) template.getMetainfo().getSeparatorTitle().getX(),
                    (float) template.getMetainfo().getSeparatorTitle().getY(),
                    template.getMetainfo().getSeparatorTitle().getFont(),
                    null, type);
        }
    }

    private static void placeHeadline(Template template, ParentInterface parentView, ContentType type) {
        Headline headline = template.getMetainfo().getHeadline();
        if (headline != null) {
            placeTextContent(parentView,
                    template.getMetainfo().getHeadline().getDefaultText(),
                    (float) template.getMetainfo().getHeadline().getX().doubleValue(),
                    (float) template.getMetainfo().getHeadline().getY().doubleValue(),
                    template.getMetainfo().getHeadline().getFont(),
                    null, type);
        }
    }

    private static void placePlace(Template template, ParentInterface parentView, ContentType type) {
        if (template.getMetainfo().getPlace() != null) {
            Place place = template.getMetainfo().getPlace();
            placeTextContent(parentView,
                    place.getDefaultText(),
                    (float) place.getX().doubleValue(),
                    (float) place.getY().doubleValue(),
                    place.getFont(), null,
                    type);
        }
    }

    public static void placeContent(ParentInterface parentView, float x, float y,
                                    @Nullable Anchor anchor,
                                    Container container) {
        x = x * parentView.getWidth();
        y = y * parentView.getHeight();

        if (anchor == null) {
            x = x - container.getWidth() / 2;
        } else if (anchor.equals(Anchor.RIGHT)) {
            x = x - container.getWidth();
        }

        y = y - container.getHeight() / 2;
        container.setX(x);
        container.setY(y);
    }

    public static void placeTextContent(ParentInterface parentView, String text,
                                         float x, float y,
                                         Font font,
                                         @Nullable Anchor archor,
                                         @NonNull ContentType type) {
        /*Container container = new Container(
                parentView.getResources().getDimensionPixelSize(R.dimen.minWidthText),
                parentView.getResources().getDimensionPixelOffset(R.dimen.minHeightText));*/
        Container container = new Container(10, 10);
        TextContent textContent = new TextContent();
        textContent.setMutable(false);
        textContent.setData(text);
        textContent.setType(type);
        String fontName = font.getName();
        if (fontName != null) {
            textContent.setFont(fontName);
        }

        textContent.setTextSize(getFontSize(font.getSize(), parentView));
        int color = com.llc111minutes.gameday.model.Color.createColorInt(font.getColor());
        /*int color = Color.argb((int) (255 * Float.parseFloat(font.getColor().getAlpha())),
                Integer.parseInt(font.getColor().getRed()),
                Integer.parseInt(font.getColor().getGreen()),
                Integer.parseInt(font.getColor().getBlue()));*/
        textContent.setContentColor(color);

        container.addContent(textContent);

        parentView.addContainer(container);
        parentView.notifyView();

        x = x * parentView.getWidth();
        y = y * parentView.getHeight();

        if (archor == null) {
            x = x - container.getWidth() / 2;
        } else if (archor.equals(Anchor.RIGHT)) {
            x = x - container.getWidth();
        }

        y = y - container.getHeight() / 2;
        container.setX(x);
        container.setY(y);

        // If DemoParentView mutable = false always
        container.getContent().setMutable(true);

        // LogUtil.info("Content: " + textContent);
        // LogUtil.info("Container: " + container);
    }

    public static float getFontSize(float size, ParentInterface parentView) {
        return (float) (size * (parentView.getWidth()*0.85));
    }

    public static void placeStickerAtCenter(float x, float y,
                                            ContainerInterface containerInterface) {
        float cX = containerInterface.absoluteCenterX();
        float cY = containerInterface.absoluteCenterY();
        float dX = containerInterface.getWidth() - cX;
        float dY = containerInterface.getHeight() - cY;

        x = x - dX;
        y = y - dY;

        containerInterface.setX(x);
        containerInterface.setY(y);
        containerInterface.invalidate();
    }

    public static void clearPanel(ParentInterface parentView) {
        parentView.setBorderColor(Color.WHITE);
        parentView.setSelectedBorderColor(Color.RED);
        parentView.setPreview(false);
        //parentView.setBackgroundResource(android.R.color.transparent);
        parentView.clear();
    }

    public static void setOverlay(List<Overlay> allOverlays, int overlayId, ParentInterface parentView) {
        if (overlayId > 0) {
            Overlay overlay = null;
            for (Overlay o : allOverlays) {
                if (o.getId() == overlayId) {
                    overlay = o;
                    break;
                }
            }
            setOverlay(parentView, overlay);
        } else {
            parentView.setBackground(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    public static void setOverlay(ParentInterface parentView, Overlay overlay) {
        if (overlay == null) {
            parentView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            return;
        }
        String url = getUrlFromOverlay(parentView, overlay);
        //Overlay finalOverlay = overlay;
        com.llc111minutes.gameday.model.Color color = overlay.getColor();

        int c = 0;
        if (color!=null) {
            c = com.llc111minutes.gameday.model.Color.createColorInt(color);
        } else {
            c = ContextCompat.getColor(parentView.getContext(), android.R.color.transparent);
        }
        if (parentView.getHeight() > 0 && parentView.getWidth()>0) {
            Glide.with(parentView.getContext())
                    .load(url)
                    .asBitmap()
                    .transform(new ColorFilterTransformation(parentView.getContext(), c))
                    .into(new SimpleTarget<Bitmap>(parentView.getWidth(), parentView.getHeight()) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            if (parentView.getColorForFilter() > Integer.MIN_VALUE) {
                                resource = tintImage(resource, parentView.getColorForFilter());
                            }
                            Drawable drawable = new BitmapDrawable(parentView.getResources(), resource);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                parentView.setBackground(drawable);
                            } else {
                                parentView.setBackgroundDrawable(drawable);
                            }

                            if (parentView instanceof ParentView) {
                                ((ParentView) parentView).setOverlayBitmap(resource);
                            }

                            parentView.notifyView();
                        }
                    });
        } else {
            LogUtil.info(parentView, "************");
        }
    }

    public static Bitmap tintImage(Bitmap bitmap, int color) {
        Paint paint = new Paint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        Bitmap bitmapResult = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapResult);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bitmapResult;
    }

    @NonNull
    private static String getUrlFromOverlay(ParentInterface parentView, Overlay overlay) {
        String url = Const.BASE_URL;
        if (parentView instanceof SmallTouchView) {
            url = url + overlay.getSmall();
        } else {
            String ratio = parentView.getResources().getString(R.string.ratio);
            switch (ratio) {
                case "xxhdpi":
                    url = url + overlay.getMedium();
                    break;
                case "xxxhdpi":
                    url = url + overlay.getOriginal();
                    break;
                case "hdpi":
                case "xhdpi":
                default:
                    url = url + overlay.getSmall();
            }
        }
        LogUtil.info(parentView, "Overlay URL: " + url);
        return url;
    }

}
