package com.llc111minutes.gameday.sticks.team.universal.sticker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.llc111minutes.gameday.R;
import com.llc111minutes.gameday.model.enums.ContentType;
import com.llc111minutes.gameday.util.LogUtil;

public class ImageContent extends Content implements Parcelable {
    private Bitmap icon;
    private String url = null;

    @Override
    public void init(Context context) {
        if (TextUtils.isEmpty(url)) {
            icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.logo_team);
        } else {
            int width = 100;
            int height = 100;
            if (getContainer() != null) {
                width = (int) getContainer().getWidth();
                height = (int) getContainer().getHeight();
            }
            Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>
                            (width, height) {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            icon = resource;
                            if (getContainer() != null && getContainer().getParent() != null) {
                                getContainer().invalidate();
                                getContainer().getParent().notifyView();
                            }

                        }
                    });
        }
    }

    public void setImage(Bitmap bitmap) {
        icon = bitmap;
    }

    public Bitmap getBitmap() {
        return icon;
    }

    @Override
    public void oneTap(Context context) {

    }

    @Override
    public void doubleTap(Context context) {

    }

    @Override
    public void changeParentSize(float width, float height) {
        /*if (width < getMinWidth()*//*minWidth*//*) {
            getContainer().setWidth(getMinWidth()*//*minWidth*//*);
        }

        if (height < getMinWidth()) {
            getContainer().setHeight(getMinWidth());
        }*/
    }

    @Override
    public void drawContent(Canvas canvas, Path path, RectF rectF) {
        if (container == null) return;
        canvas.save();
        canvas.rotate(container.getRotate(), rectF.centerX(), rectF.centerY());
        canvas.scale(container.getScale(), container.getScale(), rectF.centerX(), rectF.centerY());
        LogUtil.info(this, "icon: "+icon);
        if(icon!=null)
        canvas.drawBitmap(icon, null, rectF, null);
        canvas.restore();
    }

    @Override
    public void release() {

    }

    @Override
    public void setMutable(boolean mutable) {

    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    @Override
    public void setData(String data) {
        url = data;
    }

    @Override
    public String getData() {
        return url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
        dest.writeInt(this.contentType == null ? -1 : this.contentType.ordinal());
    }

    ImageContent() {
    }

    private ImageContent(Parcel in) {
        this.url = in.readString();
        this.isDefault = in.readByte() != 0;
        int tmpContentType = in.readInt();
        this.contentType = tmpContentType == -1 ? null : ContentType.values()[tmpContentType];
    }

    public static final Parcelable.Creator<ImageContent> CREATOR = new Parcelable.Creator<ImageContent>() {
        @Override
        public ImageContent createFromParcel(Parcel source) {
            return new ImageContent(source);
        }

        @Override
        public ImageContent[] newArray(int size) {
            return new ImageContent[size];
        }
    };

    @Override
    public void invalidate() {

    }

    @Override
    public void invalidate(boolean change) {

    }
}
