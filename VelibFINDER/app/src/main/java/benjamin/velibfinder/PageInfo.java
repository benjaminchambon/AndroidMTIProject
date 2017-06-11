package benjamin.velibfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import benjamin.velibfinder.RetrofitWS.Records;

/**
 * Created by Benjamin on 30/05/2017.
 */

public class PageInfo extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_info, container, false);
        Bundle bundle = getArguments();
        final Records mData = bundle.getParcelable("mData");

        //Récupération des champs de la page
        TextView stationName = (TextView) view.findViewById(R.id.StationName);
        TextView nbPlace = (TextView) view.findViewById(R.id.textNbPlace);
        TextView nbBike = (TextView) view.findViewById(R.id.textNbBike);
        TextView lastUpdate = (TextView) view.findViewById(R.id.textLastUpdate);
        TextView address = (TextView) view.findViewById(R.id.textAdress);
        ImageView status = (ImageView) view.findViewById(R.id.imageStatus);


        //Paramétrage de la page en fonction des stations
        stationName.setText(mData.getFields().getName());
        nbPlace.setText(mData.getFields().getAvailable_bike_stands());
        nbBike.setText(mData.getFields().getBike_stands());
        lastUpdate.setText("Dernière mise à jour  :  " +
                " " + mData.getFields().getLast_update().substring(0,10) + " " + mData.getFields().getLast_update().substring(11
                ,19));
        address.setText(mData.getFields().getAddress());
        if (mData.getFields().getStatus().equals("OPEN"))
            status.setImageResource(R.drawable.stationopen);
        else
            status.setImageResource(R.drawable.stationclose);


        //Lancement de Google maps au clic sur l'adresse
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:" + mData.getFields().getPosition().get(0) + "," + mData.getFields().getPosition().get(1) + "?q=" + mData.getFields().getPosition().get(0) + "," + mData.getFields().getPosition().get(1) + "(" + mData.getFields().getAddress() + ")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                else{
                    Toast.makeText(getActivity(), "Google maps not installed", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}
