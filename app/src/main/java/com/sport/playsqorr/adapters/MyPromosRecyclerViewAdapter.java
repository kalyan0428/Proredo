package com.sport.playsqorr.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sport.playsqorr.R;

import java.util.ArrayList;


public class MyPromosRecyclerViewAdapter extends RecyclerView.Adapter<MyPromosRecyclerViewAdapter.ViewHolder> {

   // private final List<DummyContent.DummyItem> mValues;
    private final ArrayList<String> listItems=new ArrayList<>();


    public MyPromosRecyclerViewAdapter(ArrayList<String> listItems) {
        listItems = listItems;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_promos_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return
                listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + "'";
        }
    }
}
