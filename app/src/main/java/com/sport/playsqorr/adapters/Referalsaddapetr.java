package com.sport.playsqorr.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.views.ReferUsedetails;

import java.util.ArrayList;

public class Referalsaddapetr extends RecyclerView.Adapter<Referalsaddapetr.CategoryHolder>  {

    Context context;
    ArrayList<ReferUsedetails> playerModelArrayList, mfilteredList;


    public Referalsaddapetr(Context activity, ArrayList<ReferUsedetails> proposer_models) {
        this.context = activity;
        this.playerModelArrayList = proposer_models;


    }


    @Override
    public Referalsaddapetr.CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contactsdetails, parent, false);
        return new Referalsaddapetr.CategoryHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final Referalsaddapetr.CategoryHolder holder, final int position)
    {

//        holder.releconta.setVisibility(View.VISIBLE);
        holder.faqtxt.setText(playerModelArrayList.get(position).getName());
        holder.rsindia.setText(" $ "+playerModelArrayList.get(position).getBenefitAmount());




    }



    @Override
    public int getItemCount()
    {
        return playerModelArrayList.size();

    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        TextView faqtxt,rsindia;
        RelativeLayout releconta;
        ImageView add,cancel;

        public CategoryHolder(View itemView) {
            super(itemView);

            faqtxt=itemView.findViewById(R.id.faqtxt);
            rsindia=itemView.findViewById(R.id.amount);

        }
    }


}