package benjamin.velibfinder;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import benjamin.velibfinder.RetrofitWS.Records;

/**
 * Created by Benjamin on 30/05/2017.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {
    private  ArrayList<Records> mDataset;


    public SwipeAdapter(FragmentManager fm,  ArrayList<Records> mDataset){
        super(fm);
        this.mDataset = mDataset;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment pageInfo = new PageInfo();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mData", mDataset.get(position));
        pageInfo.setArguments(bundle);
        return pageInfo;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }
}
