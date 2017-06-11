package benjamin.velibfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import benjamin.velibfinder.RetrofitWS.Fields;
import benjamin.velibfinder.RetrofitWS.Records;

/**
 * Created by mateos on 20/05/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements Filterable {
    private ArrayList<Records> mDataset;
    private ArrayList<Records> mDatasetFiltered;
    private Context ctx;

    public MyAdapter(ArrayList<Records> myDataset, Context ctx) {
        mDatasetFiltered = myDataset;
        mDataset = myDataset;
        this.ctx = ctx;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.velib_point, parent, false);
        return new ViewHolder(v, ctx, mDataset);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fields curData = mDatasetFiltered.get(position).getFields();
        holder.mTextView.setText(curData.getName());
        holder.setRecord(mDatasetFiltered.get(position));
        if (curData.getStatus().equals("OPEN"))
            holder.mImageView.setImageResource(R.drawable.open);
        else
            holder.mImageView.setImageResource(R.drawable.closed);
    }

    @Override
    public int getItemCount() {
        return mDatasetFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty())
                    mDatasetFiltered = mDataset;
                else {
                    ArrayList<Records> filteredList = new ArrayList<>();
                    for (Records androidVersion : mDataset) {
                        if (androidVersion.getFields().getName().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    mDatasetFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mDatasetFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDatasetFiltered = (ArrayList<Records>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextView;
        private Context ctx;
        private Records record;
        private ImageView mImageView;
        private ArrayList<Records> mDataset;
        public static final String STATION_LIST = "STATION_NAME";
        public static final String INDEX = "INDEX";

        public ViewHolder(View v, Context ctx, ArrayList<Records> mDataset) {
            super(v);
            this.ctx = ctx;
            this.mDataset = mDataset;
            v.setOnClickListener(this);
            mTextView = (TextView) v.findViewById(R.id.list_text_item);
            mImageView = (ImageView) v.findViewById(R.id.imageView_item);
        }


        public void setRecord(Records record) {
            this.record = record;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ctx, InfoActivity.class);
            intent.putExtra(STATION_LIST, (ArrayList<? extends Parcelable>) mDataset);
            intent.putExtra(INDEX, mDataset.indexOf(record));
            ctx.startActivity(intent);

        }
    }
}
