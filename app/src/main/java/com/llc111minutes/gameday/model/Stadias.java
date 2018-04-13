package com.llc111minutes.gameday.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Stadias extends StickChangeData implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("country_name")
    @Expose
    private String country;

    @SerializedName("name")
    @Expose
    private String name;

    protected Stadias(Parcel in) {
        id = in.readInt();
        city = in.readString();
        country = in.readString();
        name = in.readString();
    }

    public static final Creator<Stadias> CREATOR = new Creator<Stadias>() {
        @Override
        public Stadias createFromParcel(Parcel in) {
            return new Stadias(in);
        }

        @Override
        public Stadias[] newArray(int size) {
            return new Stadias[size];
        }
    };

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Stadias{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.name);
    }
}
