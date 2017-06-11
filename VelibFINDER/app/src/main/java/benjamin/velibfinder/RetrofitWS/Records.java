package benjamin.velibfinder.RetrofitWS;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Benjamin on 21/05/2017.
 */

public class Records implements Parcelable{
    private final Fields fields;

    public Records(Parcel source){
        this.fields = source.readParcelable(Fields.class.getClassLoader());
    }


    public Fields getFields(){
        return fields;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeParcelable(fields,flags);
    }

    public static final Parcelable.Creator<Records> CREATOR = new Parcelable.Creator<Records>()
    {
        @Override
        public Records createFromParcel(Parcel source)
        {
            return new Records(source);
        }
        @Override
        public Records[] newArray(int size)
        {
            return new Records[size];
        }
    };

}
