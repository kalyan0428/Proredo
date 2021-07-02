package com.sport.playsqorr.fragments;


import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.adapters.MyCards_Adapter;

import com.sport.playsqorr.pojos.MyCardsPojo;

import java.util.ArrayList;
import java.util.List;


public class MyCardsLive extends Fragment {

    public static final String TAG = MyCardsLive.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    static List<MyCardsPojo> myCardsPojo_l = new ArrayList<>();

    static String data_title = "";

    LinearLayout ll_no;
    RecyclerView cards_cycle;
    TextView txt1;
    ImageView img1;
    MyCards_Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mycards_live, container, false);

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ll_no = getActivity().findViewById(R.id.no_ll_live);

        txt1 = getActivity().findViewById(R.id.no_txt_live);
        img1 = getActivity().findViewById(R.id.no_logo_live);
        txt1.setText("You have no live cards.");
        cards_cycle = getActivity().findViewById(R.id.list_mycards_live);
        cards_cycle.setLayoutManager(new LinearLayoutManager(getActivity()));
//        cards_cycle.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, true));
        cards_cycle.setItemAnimator(new DefaultItemAnimator());

        if (myCardsPojo_l.size() <= 0) {

            cards_cycle.setVisibility(View.GONE);
            ll_no.setVisibility(View.VISIBLE);
            img1.setColorFilter(img1.getContext().getResources().getColor(R.color.no_cards_color), PorterDuff.Mode.SRC_ATOP);
            if (data_title.equalsIgnoreCase("ALL")) {
                img1.setImageResource(R.drawable.ic_star);

            } else if (data_title.equalsIgnoreCase("EPL")) {
                img1.setImageResource(R.drawable.ic_tennis);

            } else if (data_title.equalsIgnoreCase("LA_LIGA")) {
                img1.setImageResource(R.drawable.ic_am_football);
            } else if (data_title.equalsIgnoreCase("MLB")) {
                img1.setImageResource(R.drawable.ic_baseball);
            } else if (data_title.equalsIgnoreCase("MLS")) {
                img1.setImageResource(R.drawable.ic_soccer);
            } else if (data_title.equalsIgnoreCase("NASCAR")) {
                img1.setImageResource(R.drawable.ic_nascar_h);
            } else if (data_title.equalsIgnoreCase("NBA")) {
                img1.setImageResource(R.drawable.ic_basketball);
            } else if (data_title.equalsIgnoreCase("NCAAMB")) {
                img1.setImageResource(R.drawable.ic_basketball);
            } else if (data_title.equalsIgnoreCase("NFL")) {
                img1.setImageResource(R.drawable.ic_am_football);
            } else if (data_title.equalsIgnoreCase("NHL")) {
                img1.setImageResource(R.drawable.ic_hockey);
            } else if (data_title.equalsIgnoreCase("PGA")) {

                img1.setImageResource(R.drawable.ic_golf);
            } else if (data_title.equalsIgnoreCase("NCAAFB")) {
                img1.setImageResource(R.drawable.ic_am_football);
            } else if (data_title.equalsIgnoreCase("WILD CARD")) {
                img1.setImageResource(R.drawable.ic_wildcard);
            }else if (data_title.equalsIgnoreCase("PRORODEO")) {
                img1.setImageResource(R.drawable.rodeo);
            }
        } else {

            cards_cycle.setVisibility(View.VISIBLE);
            ll_no.setVisibility(View.GONE);

            adapter = new MyCards_Adapter(myCardsPojo_l, getActivity());


            cards_cycle.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();

        }
    }


    public Fragment instantiate(String s, String fname, List<MyCardsPojo> kk) {
        Log.e("145--->", "145- live-->" + s);
        myCardsPojo_l = kk;
        data_title = s;
        return null;
    }


}
