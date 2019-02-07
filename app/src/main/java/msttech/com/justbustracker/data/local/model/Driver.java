package msttech.com.justbustracker.data.local.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Driver implements Parcelable {
    private String driverName;
    private String driverId;
    private String busName;
    private double latitude;
    private double longitude;

    protected Driver(Parcel in) {
        driverName = in.readString();
        driverId = in.readString();
        busName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public Driver(){}

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(driverName);
        parcel.writeString(driverId);
        parcel.writeString(busName);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
    }
}
