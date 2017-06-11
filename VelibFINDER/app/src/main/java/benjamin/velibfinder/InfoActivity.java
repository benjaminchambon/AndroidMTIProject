package benjamin.velibfinder;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import benjamin.velibfinder.RetrofitWS.Records;
import static benjamin.velibfinder.MyAdapter.ViewHolder.INDEX;
import static benjamin.velibfinder.MyAdapter.ViewHolder.STATION_LIST;

/**
 * Created by mateos on 21/05/2017.
 */

public class InfoActivity extends AppCompatActivity {
    private ArrayList<Records> mDataset;
    private int index;
    ViewPager viewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_layout);

        //Set and Custom the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_pager);
        myToolbar .setTitle("Station details");
        myToolbar .setTitleTextColor(Color.LTGRAY);
        setSupportActionBar(myToolbar);

        final Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(STATION_LIST)) {
                mDataset = intent.getParcelableArrayListExtra(STATION_LIST);
            }
            if (intent.hasExtra(INDEX)) {
                index = intent.getIntExtra(INDEX, -1);
            }
        }
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        SwipeAdapter swipeAdapter = new SwipeAdapter(getSupportFragmentManager(), mDataset);
        viewpager.setAdapter(swipeAdapter);
        viewpager.setCurrentItem(index);
    }


    //Attache les boutons à la toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share, menu);
        return true;
    }


    //Bouton qui affiche la liste des membres du groupe
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_shared:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mDataset.get(index).getFields().getAddress());
                startActivity(intent);
                return true;
            case R.id.action_info:
                Intent myIntent = new Intent(this, Members.class);
                this.startActivity(myIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
