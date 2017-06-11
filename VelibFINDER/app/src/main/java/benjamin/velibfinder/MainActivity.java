package benjamin.velibfinder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import benjamin.velibfinder.RetrofitWS.GithubService;
import benjamin.velibfinder.RetrofitWS.RepoData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set and Custom the toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("Velib Stations");
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);


        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler_view);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        params.setMargins(0, getActionBarHeight() + 25, 0, 0);
        mRecyclerView.setLayoutParams(params);

        mLayoutManager = new LinearLayoutManager(this);

        if (isNetworkStatusAvialable(getApplicationContext())) {

            // ------------- WS Retrofit 2 -------------------
            GithubService githubService = new Retrofit.Builder()
                    .baseUrl(GithubService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(GithubService.class);

            Call<RepoData> repoList = githubService.listRepos();
            repoList.enqueue(new Callback<RepoData>() {
                @Override
                public void onResponse(Call<RepoData> call, Response<RepoData> response) {
                    if (response.isSuccessful()) {
                        RepoData repoList = response.body();
                        myAdapter = new MyAdapter(repoList.getRecords(), MainActivity.this);
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setAdapter(myAdapter);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                    } else {
                        //erreur APi
                        System.out.println("erreur accès API");
                    }
                }

                @Override
                public void onFailure(Call<RepoData> call, Throwable t) {
                    System.out.println("ERROR : " + t.getMessage());
                }
            });
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);

            // set title
            alertDialogBuilder.setTitle("Pas de connexion internet");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Voulez-vous activer la connexion internet ?")
                    .setCancelable(false)
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setClassName("com.android.settings", "com.android.settings.Settings$DataUsageSummaryActivity");
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    //Attache la zone recherche à la toolbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        userResearch(searchView);
        return true;
    }

    //Méthode de filtrage en fonction de la recherche utilisateur
    private void userResearch(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String inputText) {
                if (myAdapter != null)
                    myAdapter.getFilter().filter(inputText);
                return true;
            }
        });
    }

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null)
                if (netInfos.isConnected())
                    return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent myIntent = new Intent(this, Members.class);
                this.startActivity(myIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        } else {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }
}
