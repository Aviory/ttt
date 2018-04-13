
package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MenuItemTemplate implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    @SerializedName("templates")
    @Expose
    private List<Template> templates = new ArrayList<Template>();

    public MenuItemTemplate(Integer id, String name, String updatedAt, String createdAt,
                            Preview preview, List<Template> templates) {
        this.id = id;
        this.name = name;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.preview = preview;
        this.templates = templates;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    @Override
    public String toString() {
        return "MenuItemTemplate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", preview=" + preview +
                ", templates=" + templates +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
        dest.writeParcelable(this.preview, flags);
        dest.writeList(this.templates);
    }

    protected MenuItemTemplate(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
        this.preview = in.readParcelable(Preview.class.getClassLoader());
        this.templates = new ArrayList<Template>();
        in.readList(this.templates, Template.class.getClassLoader());
    }

    public static final Parcelable.Creator<MenuItemTemplate> CREATOR = new Parcelable.Creator<MenuItemTemplate>() {
        @Override
        public MenuItemTemplate createFromParcel(Parcel source) {
            return new MenuItemTemplate(source);
        }

        @Override
        public MenuItemTemplate[] newArray(int size) {
            return new MenuItemTemplate[size];
        }
    };
}
