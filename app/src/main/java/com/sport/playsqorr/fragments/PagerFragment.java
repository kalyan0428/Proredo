package com.sport.playsqorr.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sport.playsqorr.R;

public class PagerFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int pageNo=-1;
        if (getArguments() != null && getArguments().containsKey("pageNo")) {
              pageNo=getArguments().getInt("pageNo");
        }
        View rootView;
        if(pageNo==5){
            rootView = inflater.inflate(R.layout.pager_screen_layout1, container, false);
        }else {
            rootView = inflater.inflate(R.layout.pager_screen_layout, container, false);
            ImageView pager_image = rootView.findViewById(R.id.pager_image);
            TextView titleTv = rootView.findViewById(R.id.titleTv);
            TextView descTv = rootView.findViewById(R.id.descTv);

            if (getArguments() != null && getArguments().containsKey("title")) {
                titleTv.setText(getArguments().getString("title"));
            }
            if (getArguments() != null && getArguments().containsKey("pic")) {
                pager_image.setImageResource(getArguments().getInt("pic"));
                if (titleTv.getText().toString().equalsIgnoreCase("pick your prize")){
                    pager_image.setBackground(getResources().getDrawable(R.drawable.pick_prize_bg));
                }
            }

            if (getArguments() != null && getArguments().containsKey("desc")) {
                descTv.setText(getArguments().getString("desc"));
            }
        }

        return rootView;
    }
}



