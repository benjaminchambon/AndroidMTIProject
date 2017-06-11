package benjamin.velibfinder.RetrofitWS;

import java.util.ArrayList;

/**
 * Created by Benjamin on 21/05/2017.
 */

public class RepoData {
    private final ArrayList<Records> records;

    public RepoData(ArrayList<Records> records){
        this.records = records;
    }

    public ArrayList<Records> getRecords() {
        return records;
    }
}
