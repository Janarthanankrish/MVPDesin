package com.mvpdesign.dashboard;

import android.os.Parcel;
import android.os.Parcelable;

public class DashboardPojo implements Parcelable {
    String title = "", description = "", date = "", image = "";

    protected DashboardPojo(Parcel in) {
        title = in.readString();
        description = in.readString();
        date = in.readString();
        image = in.readString();
    }

    public DashboardPojo(String title, String description, String date, String image) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DashboardPojo> CREATOR = new Creator<DashboardPojo>() {
        @Override
        public DashboardPojo createFromParcel(Parcel in) {
            return new DashboardPojo(in);
        }

        @Override
        public DashboardPojo[] newArray(int size) {
            return new DashboardPojo[size];
        }
    };
}
