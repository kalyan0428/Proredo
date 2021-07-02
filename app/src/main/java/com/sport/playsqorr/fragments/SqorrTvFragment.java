package com.sport.playsqorr.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.TvPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.PresetValueButton;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.sport.playsqorr.views.Dashboard.ROLE;

public class SqorrTvFragment extends Fragment implements View.OnClickListener {
    PresetValueButton all_pvb, EPL_pvb, LA_LIGA_pvb, mlb_pvb, mls_pvb, NASCAR_pvb, NBA_pvb, NCAAMB_pvb, NFL_pvb, NHL_pvb, PGA_pvb,NCAAFB_pvb, WILD_pvb;
    View vv;
    private HorizontalScrollView horizontal;
    private List<TvPojo> tvPojoList = new ArrayList<>();
    private List<TvPojo> tvPojoList_epl = new ArrayList<>();
    private List<TvPojo> tvPojoList_nba = new ArrayList<>();
    private List<TvPojo> tvPojoList_liga = new ArrayList<>();
    private List<TvPojo> tvPojoList_mlb = new ArrayList<>();
    private List<TvPojo> tvPojoList_mls = new ArrayList<>();
    private LinkedList<TvPojo> tvPojoList_pga = new LinkedList<>();
    private LinkedList<TvPojo> tvPojoList_nascar = new LinkedList<>();
    private LinkedList<TvPojo> tvPojoList_naccamb = new LinkedList<>();
    private LinkedList<TvPojo> tvPojoList_nfl = new LinkedList<>();
    private LinkedList<TvPojo> tvPojoList_nhl = new LinkedList<>();
    private LinkedList<TvPojo> tvPojoList_ncaafb = new LinkedList<>();
    private LinkedList<TvPojo> tvPojoList_wild = new LinkedList<>();
    public  static ProgressDialog progressDialog;




//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState( SqorrTvFragment.getInstance( getFragmentManager() ).popData() );
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        super.onCreate(SqorrTvFragment.getInstance(getFragmentManager()).popData());

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sqorrtv, container, false);
        vv = v.findViewById(R.id.banner_c);
        if (ROLE.equalsIgnoreCase("cash") || ROLE.equalsIgnoreCase("tokens")) {
            vv.setVisibility(View.GONE);
        }
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        all_pvb = getActivity().findViewById(R.id.all);
        EPL_pvb = getActivity().findViewById(R.id.EPL);
        LA_LIGA_pvb = getActivity().findViewById(R.id.LA_LIGA);
        mlb_pvb = getActivity().findViewById(R.id.mlb);
        mls_pvb = getActivity().findViewById(R.id.mls);
        NASCAR_pvb = getActivity().findViewById(R.id.NASCAR);
        NBA_pvb = getActivity().findViewById(R.id.NBA);
        NCAAMB_pvb = getActivity().findViewById(R.id.NCAAMB);
        NFL_pvb = getActivity().findViewById(R.id.NFL);
        NHL_pvb = getActivity().findViewById(R.id.NHL);
        PGA_pvb = getActivity().findViewById(R.id.PGA);
        PGA_pvb = getActivity().findViewById(R.id.PGA);
        PGA_pvb = getActivity().findViewById(R.id.PGA);
        NCAAFB_pvb = getActivity().findViewById(R.id.NCAAFB);
        WILD_pvb = getActivity().findViewById(R.id.WILD);
        horizontal = getActivity().findViewById(R.id.horizontal);

        all_pvb.setOnClickListener(this);
        EPL_pvb.setOnClickListener(this);
        LA_LIGA_pvb.setOnClickListener(this);
        mlb_pvb.setOnClickListener(this);
        mls_pvb.setOnClickListener(this);
        NASCAR_pvb.setOnClickListener(this);
        NBA_pvb.setOnClickListener(this);
        NCAAMB_pvb.setOnClickListener(this);
        NFL_pvb.setOnClickListener(this);
        NHL_pvb.setOnClickListener(this);
        PGA_pvb.setOnClickListener(this);
        NCAAFB_pvb.setOnClickListener(this);
        WILD_pvb.setOnClickListener(this);
        if (Utilities.isNetworkAvailable(getActivity())) {
            getSqorrTvData();
        } else {
            Utilities.showNoInternetAlert(getActivity());
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all:
                getActivity().findViewById(R.id.all).setSelected(true);
                setSqorrTvAll(tvPojoList);
                return;
            case R.id.EPL:
                getActivity().findViewById(R.id.EPL);
                setSqorrTvOther(tvPojoList_epl, "EPL");
                return;
            case R.id.LA_LIGA:
                getActivity().findViewById(R.id.LA_LIGA);
                setSqorrTvOther(tvPojoList_liga, "LA_LIGA");
                return;
            case R.id.mlb:
                getActivity().findViewById(R.id.mlb);
                setSqorrTvOther(tvPojoList_mlb, "MLB");
                return;
            case R.id.mls:
                getActivity().findViewById(R.id.mls);
                setSqorrTvOther(tvPojoList_mls, "MLS");
                return;
            case R.id.NASCAR:
                getActivity().findViewById(R.id.NASCAR);
                setSqorrTvOther(tvPojoList_nascar, "NASCAR");
                return;
            case R.id.NBA:
                getActivity().findViewById(R.id.NBA);
                setSqorrTvOther(tvPojoList_nba, "NBA");
                return;
            case R.id.NCAAMB:
                getActivity().findViewById(R.id.NCAAMB);
                setSqorrTvOther(tvPojoList_naccamb, "NCAAMB");
                return;
            case R.id.NFL:
                getActivity().findViewById(R.id.NFL);
                setSqorrTvOther(tvPojoList_nfl, "NFL");
                return;
            case R.id.NHL:
                getActivity().findViewById(R.id.NHL);
                setSqorrTvOther(tvPojoList_nhl, "EPL");
                return;
            case R.id.PGA:
                getActivity().findViewById(R.id.PGA);
                setSqorrTvOther(tvPojoList_pga, "NHL");
                return;
            case R.id.NCAAFB:
                getActivity().findViewById(R.id.NCAAFB);
                setSqorrTvOther(tvPojoList_ncaafb, "NCAAFB");
                return;
            case R.id.WILD://"WILD CARD":
                getActivity().findViewById(R.id.WILD);
                setSqorrTvOther(tvPojoList_wild, "WILD CARD");
                break;


            default:
                return;
        }
    }

    private void getSqorrTvData() {
        if (progressDialog != null)
            progressDialog.show();

        AndroidNetworking.get(APIs.SQORRTV_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        tvPojoList.clear();
//                        if (progressDialog != null)
//                            progressDialog.dismiss();
                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                String leagueAbbreviation = "";
                                JSONObject jsonObject = response.getJSONObject(i);

                                if (jsonObject.getString("leagueAbbreviation") != null) {
                                    leagueAbbreviation = jsonObject.getString("leagueAbbreviation");
                                }

                                if (leagueAbbreviation.equals("EPL")) {
                                    TvPojo tvPojo_epl = new TvPojo();
                                    tvPojo_epl.setUrl(jsonObject.getString("url"));
                                    tvPojo_epl.setTitle(jsonObject.getString("title"));
                                    tvPojo_epl.setDescription(jsonObject.getString("description"));
                                    tvPojo_epl.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_epl.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_epl.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_epl.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_epl.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_epl.setUpdatedAt(jsonObject.getString("updatedAt"));
                                    tvPojoList_epl.add(tvPojo_epl);
                                } else if (leagueAbbreviation.equals("NBA")) {
                                    TvPojo tvPojo_nba = new TvPojo();
                                    tvPojo_nba.setUrl(jsonObject.getString("url"));
                                    tvPojo_nba.setTitle(jsonObject.getString("title"));
                                    tvPojo_nba.setDescription(jsonObject.getString("description"));
                                    tvPojo_nba.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_nba.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_nba.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_nba.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_nba.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_nba.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_nba.add(tvPojo_nba);
                                } else if (leagueAbbreviation.equals("LA-LIGA")) {
                                    TvPojo tvPojo_liga = new TvPojo();
                                    tvPojo_liga.setUrl(jsonObject.getString("url"));
                                    tvPojo_liga.setTitle(jsonObject.getString("title"));
                                    tvPojo_liga.setDescription(jsonObject.getString("description"));
                                    tvPojo_liga.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_liga.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_liga.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_liga.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_liga.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_liga.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_liga.add(tvPojo_liga);
                                } else if (leagueAbbreviation.equals("MLB")) {
                                    TvPojo tvPojo_mlb = new TvPojo();
                                    tvPojo_mlb.setUrl(jsonObject.getString("url"));
                                    tvPojo_mlb.setTitle(jsonObject.getString("title"));
                                    tvPojo_mlb.setDescription(jsonObject.getString("description"));
                                    tvPojo_mlb.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_mlb.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_mlb.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_mlb.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_mlb.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_mlb.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_mlb.add(tvPojo_mlb);
                                } else if (leagueAbbreviation.equals("MLS")) {
                                    TvPojo tvPojo_mls = new TvPojo();
                                    tvPojo_mls.setUrl(jsonObject.getString("url"));
                                    tvPojo_mls.setTitle(jsonObject.getString("title"));
                                    tvPojo_mls.setDescription(jsonObject.getString("description"));
                                    tvPojo_mls.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_mls.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_mls.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_mls.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_mls.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_mls.setUpdatedAt(jsonObject.getString("updatedAt"));
                                    tvPojoList_mls.add(tvPojo_mls);
                                } else if (leagueAbbreviation.equals("NASCAR")) {
                                    TvPojo tvPojo_nascar = new TvPojo();
                                    tvPojo_nascar.setUrl(jsonObject.getString("url"));
                                    tvPojo_nascar.setTitle(jsonObject.getString("title"));
                                    tvPojo_nascar.setDescription(jsonObject.getString("description"));
                                    tvPojo_nascar.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_nascar.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_nascar.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_nascar.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_nascar.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_nascar.setUpdatedAt(jsonObject.getString("updatedAt"));
                                    tvPojoList_nascar.add(tvPojo_nascar);
                                } else if (leagueAbbreviation.equals("NCAAMB")) {
                                    TvPojo tvPojo_ncaamb = new TvPojo();
                                    tvPojo_ncaamb.setUrl(jsonObject.getString("url"));
                                    tvPojo_ncaamb.setTitle(jsonObject.getString("title"));
                                    tvPojo_ncaamb.setDescription(jsonObject.getString("description"));
                                    tvPojo_ncaamb.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo_ncaamb.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo_ncaamb.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo_ncaamb.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo_ncaamb.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo_ncaamb.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_naccamb.add(tvPojo_ncaamb);
                                } else if (leagueAbbreviation.equals("NFL")) {
                                    TvPojo tvPojo = new TvPojo();
                                    tvPojo.setUrl(jsonObject.getString("url"));
                                    tvPojo.setTitle(jsonObject.getString("title"));
                                    tvPojo.setDescription(jsonObject.getString("description"));
                                    tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_nfl.add(tvPojo);
                                } else if (leagueAbbreviation.equals("NHL")) {
                                    TvPojo tvPojo = new TvPojo();
                                    tvPojo.setUrl(jsonObject.getString("url"));
                                    tvPojo.setTitle(jsonObject.getString("title"));
                                    tvPojo.setDescription(jsonObject.getString("description"));
                                    tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_nhl.add(tvPojo);
                                } else if (leagueAbbreviation.equals("PGA")) {
                                    TvPojo tvPojo = new TvPojo();
                                    tvPojo.setUrl(jsonObject.getString("url"));
                                    tvPojo.setTitle(jsonObject.getString("title"));
                                    tvPojo.setDescription(jsonObject.getString("description"));
                                    tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_pga.add(tvPojo);
                                }else if (leagueAbbreviation.equals("NCAAFB")) {
                                    TvPojo tvPojo = new TvPojo();
                                    tvPojo.setUrl(jsonObject.getString("url"));
                                    tvPojo.setTitle(jsonObject.getString("title"));
                                    tvPojo.setDescription(jsonObject.getString("description"));
                                    tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_ncaafb.add(tvPojo);
                                }else if (leagueAbbreviation.equals("WILD CARD")) {
                                    TvPojo tvPojo = new TvPojo();
                                    tvPojo.setUrl(jsonObject.getString("url"));
                                    tvPojo.setTitle(jsonObject.getString("title"));
                                    tvPojo.setDescription(jsonObject.getString("description"));
                                    tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                    tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                    tvPojo.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                    tvPojo.setThumbnail(jsonObject.getString("thumbnail"));
                                    tvPojo.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                    tvPojo.setUpdatedAt(jsonObject.getString("updatedAt"));

                                    tvPojoList_wild.add(tvPojo);
                                }
                                TvPojo tvPojo = new TvPojo();
                                tvPojo.setUrl(jsonObject.getString("url"));
                                tvPojo.setTitle(jsonObject.getString("title"));
                                tvPojo.setDescription(jsonObject.getString("description"));
                                tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
                                tvPojo.setIsFeatured(jsonObject.getBoolean("isFeatured"));
                                tvPojo.setThumbnail(jsonObject.getString("thumbnail"));
                                tvPojo.setLeagueAbbreviation(jsonObject.getString("leagueAbbreviation"));
                                tvPojo.setUpdatedAt(jsonObject.getString("updatedAt"));

                                tvPojoList.add(tvPojo);

                                setSqorrTvAll(tvPojoList);

//                                setSqorrTvOther(tvPojoList_nba, "NBA");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                            }
                        }
                        all_pvb.setSelected(true);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Utilities.showToast(getActivity(), error.getErrorDetail());
                    }
                });
    }


    private void setSqorrTvAll(List<TvPojo> tvPojoList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("tvPojoList", (Serializable) tvPojoList);
        Fragment someFragment2 = new SqoortvAllFrag();
        someFragment2.setArguments(bundle);
        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
        transaction2.replace(R.id.home_frame_layout, someFragment2); // give your fragment container id in first parameter
        transaction2.addToBackStack(null);
        transaction2.commit();

//        if (progressDialog != null)
//            progressDialog.dismiss();
    }


    private void setSqorrTvOther(List<TvPojo> tvPojoList, String type) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("tvPojoList", (Serializable) tvPojoList);
        bundle.putString("type", type);
        Fragment someFragment2 = new SqorrtvOtherFrag();
        someFragment2.setArguments(bundle);
        FragmentTransaction transaction2 = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
        transaction2.replace(R.id.home_frame_layout, someFragment2); // give your fragment container id in first parameter
        transaction2.addToBackStack(null);
        transaction2.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
          tvPojoList.clear();
          tvPojoList_epl.clear();
          tvPojoList_nba.clear();
          tvPojoList_liga.clear();
          tvPojoList_mlb.clear();
          tvPojoList_mls.clear();
          tvPojoList_pga.clear();
          tvPojoList_nascar.clear();
          tvPojoList_naccamb.clear();
          tvPojoList_nfl.clear();
          tvPojoList_nhl.clear();
          tvPojoList_ncaafb.clear();
          tvPojoList_wild.clear();

    }
}
