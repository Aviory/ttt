package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TeamInfo extends StickChangeData implements Parcelable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("imported_logo")
    @Expose
    private Boolean importedLogo;
    @SerializedName("imported_stadium")
    @Expose
    private Boolean importedStadium;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Boolean getImportedLogo() {
        return importedLogo;
    }

    public void setImportedLogo(Boolean importedLogo) {
        this.importedLogo = importedLogo;
    }

    public Boolean getImportedStadium() {
        return importedStadium;
    }

    public void setImportedStadium(Boolean importedStadium) {
        this.importedStadium = importedStadium;
    }

    @Override
    public String toString() {
        return "TeamInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", image=" + image +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", countryName='" + countryName + '\'' +
                ", importedLogo=" + importedLogo +
                ", importedStadium=" + importedStadium +
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
        dest.writeString(this.url);
        dest.writeParcelable(this.image, flags);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.countryName);
        dest.writeValue(this.importedLogo);
        dest.writeValue(this.importedStadium);
    }

    public TeamInfo() {
    }

    protected TeamInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.url = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.countryName = in.readString();
        this.importedLogo = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.importedStadium = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Parcelable.Creator<TeamInfo> CREATOR = new Parcelable.Creator<TeamInfo>() {
        @Override
        public TeamInfo createFromParcel(Parcel source) {
            return new TeamInfo(source);
        }

        @Override
        public TeamInfo[] newArray(int size) {
            return new TeamInfo[size];
        }
    };
}