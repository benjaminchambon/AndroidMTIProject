package benjamin.velibfinder.RetrofitWS;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by Benjamin on 20/05/2017.
 */

public class Fields implements Parcelable{

    private final String status;
    private final String name;
    private final String bike_stands;
    private final String number;
    private final String last_update;
    private final String available_bike_stands;
    private final String address;
    private final ArrayList<String> position;

    public Fields(Parcel source) {
        this.status = source.readString();
        this.name = source.readString();
        this.bike_stands = source.readString();
        this.number = source.readString();
        this.last_update = source.readString();
        this.available_bike_stands = source.readString();
        this.address = source.readString();
        this.position  = source.readArrayList(String.class.getClassLoader());
    }

    public String getLast_update() {
        return last_update;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public String getBike_stands() {
        return bike_stands;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getPosition() {
        return position;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(name);
        dest.writeString(bike_stands);
        dest.writeString(number);
        dest.writeString(last_update);
        dest.writeString(available_bike_stands);
        dest.writeString(address);
        dest.writeList(position);
    }

    public static final Parcelable.Creator<Fields> CREATOR = new Parcelable.Creator<Fields>()
    {
        @Override
        public Fields createFromParcel(Parcel source)
        {
            return new Fields(source);
        }
        @Override
        public Fields[] newArray(int size)
        {
            return new Fields[size];
        }
    };
}