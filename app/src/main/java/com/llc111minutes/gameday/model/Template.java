
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Template implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("template_group_id")
    @Expose
    private Integer templateGroupId;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;
    @SerializedName("metainfo")
    @Expose
    private Metainfo metainfo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("preview")
    @Expose
    private Preview preview;
    @SerializedName("overlay_id")
    @Expose
    //private Overlay overlay;
    private int overlayId = -1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateGroupId() {
        return templateGroupId;
    }

    public void setTemplateGroupId(Integer templateGroupId) {
        this.templateGroupId = templateGroupId;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Metainfo getMetainfo() {
        return metainfo;
    }

    public void setMetainfo(Metainfo metainfo) {
        this.metainfo = metainfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public int getOverlayId() {
        return overlayId;
    }

    public void setOverlayId(int overlayId) {
        this.overlayId = overlayId;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    @Override
    public String toString() {
        return "Template{" +
                "id=" + id +
                ", templateGroupId=" + templateGroupId +
                ", isPaid=" + isPaid +
                ", metainfo=" + metainfo +
                ", name='" + name + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", preview=" + preview +
                ", overlayId=" + overlayId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.templateGroupId);
        dest.writeValue(this.isPaid);
        dest.writeParcelable(this.metainfo, flags);
        dest.writeString(this.name);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
        dest.writeParcelable(this.preview, flags);
        dest.writeInt(this.overlayId);
    }

    public Template() {
    }

    protected Template(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.templateGroupId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isPaid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.metainfo = in.readParcelable(Metainfo.class.getClassLoader());
        this.name = in.readString();
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
        this.preview = in.readParcelable(Preview.class.getClassLoader());
        this.overlayId = in.readInt();
    }

    public static final Parcelable.Creator<Template> CREATOR = new Parcelable.Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel source) {
            return new Template(source);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
        }
    };
}
