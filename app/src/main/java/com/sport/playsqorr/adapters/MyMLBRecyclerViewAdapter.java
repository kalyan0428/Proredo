package com.sport.playsqorr.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Path;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.DummyContent;
import com.sport.playsqorr.utilities.PathParser;
import com.sport.playsqorr.views.MatchupScreen;

import java.util.ArrayList;
import java.util.List;

import static com.sport.playsqorr.utilities.Utilities.resizePath;


public class MyMLBRecyclerViewAdapter extends RecyclerView.Adapter<MyMLBRecyclerViewAdapter.ViewHolder> {



    private final List<DummyContent.DummyItem> mValues
            ;
    List<Object> recyclerViewItems = new ArrayList<>();

    static FragmentActivity activity_t;

    public MyMLBRecyclerViewAdapter(List<DummyContent.DummyItem> items, FragmentActivity activity) {
        mValues = items;
        this.activity_t = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mlb, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.mItem = mValues.get(position);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    Toast.makeText(activity_t, position+"Kalyan", Toast.LENGTH_LONG).show();
                    Intent matchup = new Intent(activity_t, MatchupScreen.class);
                    activity_t.startActivity(matchup);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        // public final TextView mIdView;
        // public final TextView mContentView;
        final ImageView player1Img,player2Img ;

        public DummyContent.DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.item_number);
            //mContentView = (TextView) view.findViewById(R.id.content);
             player1Img = (ImageView) view.findViewById(R.id.player1Img);
             player2Img = (ImageView) view.findViewById(R.id.player2Img);
        }

        @Override
        public String toString() {
            return super.toString() + " '" /*+ mContentView.getText()*/ + "'";
        }
    }

    private static Path getParellelogramPath(Bitmap src, String type) {
        Path path = null;
        if (type.equals("pare")) {
            path = resizePath(PathParser.createPathFromPathData(activity_t.getString(R.string.pare)),
                    src.getWidth(), src.getHeight());
        } else {
            path = resizePath(PathParser.createPathFromPathData(activity_t.getString(R.string.square)),
                    src.getWidth(), src.getHeight());
        }

        return path;
    }

}
