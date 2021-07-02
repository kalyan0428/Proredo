package com.sport.playsqorr.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.MyCardsPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.Utilities;
import com.sport.playsqorr.views.Dashboard;
import com.sport.playsqorr.views.MatchupScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.sport.playsqorr.utilities.Utilities.convertTParellelogram;

public class HomeCardsFragment extends Fragment {


    private List<MyCardsPojo> cardsList = new ArrayList<>();
    private RecyclerView rvTabList;
    private List<Object> recyclerViewItems = new ArrayList<>();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> recycleAdapter;
    private ImageView ivNoCards;
    private LinearLayout llNoCards;
    private int MY_CARDS_MAX = 3, myCardsCount = 0;
    private List<MyCardsPojo> myCardsData = new ArrayList<>();
    private List<MyCardsPojo> upcomingCardsData = new ArrayList<>();
    private String leagueId;
    private TextView tvNoCards;
    private ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_cards, container, false);

        llNoCards = v.findViewById(R.id.llNoCards);
        rvTabList = v.findViewById(R.id.rvTabList);

        tvNoCards = v.findViewById(R.id.tvNoCards);
        ivNoCards = v.findViewById(R.id.ivNoCards);
        progressBar = v.findViewById(R.id.progressBar);
        tvNoCards.setText("There are no cards right now");

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvTabList.setLayoutManager(llm);
        rvTabList.setItemAnimator(null);

        if (getArguments() != null) {
            leagueId = getArguments().getString("lid");
            progressBar.setVisibility(View.VISIBLE);
            getIndividualTabsData(leagueId);
        }

        return v;
    }


    private void getIndividualTabsData(String leagueId) {

        AndroidNetworking.get(APIs.GET_CARDS + "?leagueId=" + leagueId)
                .setPriority(Priority.HIGH)
                .addHeaders("sessionToken", Dashboard.SESSIONTOKEN)
                .addHeaders("Authorization", "bearer "+ Dashboard.NEWTOKEN)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // do anything with response
                        Log.e("TabsData:: ", response.toString());
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jb = response.getJSONObject(i);

                                MyCardsPojo mp = new MyCardsPojo();

                                JSONArray ja = jb.getJSONArray("playerImages");

                                String a1 = String.valueOf(ja.get(0));
                                String a2 = String.valueOf(ja.get(1));

                                mp.setCardId(jb.getString("cardId"));
                                mp.setCardTitle(jb.getString("cardTitle"));
                                mp.setMatchupType(jb.getString("matchupType"));
                                mp.setStartTime(jb.getString("startTime"));
                                mp.setLeagueId(jb.getString("leagueId"));
                                mp.setLeagueAbbrevation(jb.getString("leagueAbbrevation"));
                                mp.setPlayerImageLeft(a1);
                                mp.setPlayerImageRight(a2);

                                mp.setIsPurchased(jb.getString("isPurchased"));
                                mp.setIsLive(jb.getString("isLive"));

                                cardsList.add(mp);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData();
                        setPageData();
                        progressBar.setVisibility(View.GONE);
                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }


                    @Override
                    public void onError(ANError error) {
                        progressBar.setVisibility(View.GONE);
                        Utilities.showToast(getActivity(), "Response error from server ");
                        // handle error
                    }
                });

    }

    private void handleDiffData() {
        //If No response
        if (cardsList.size() <= 0) {
            rvTabList.setVisibility(View.GONE);
            llNoCards.setVisibility(View.VISIBLE);
            handleNoData();
        } else { // If we had cards in response
            rvTabList.setVisibility(View.VISIBLE);
            llNoCards.setVisibility(View.GONE);
            recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
            rvTabList.setAdapter(recycleAdapter);
            processResponseData();
        }
    }

    @SuppressLint("SetTextI18n")
    private void handleNoData() {

        ivNoCards.setColorFilter(ivNoCards.getContext().getResources().getColor(R.color.no_cards_color), PorterDuff.Mode.SRC_ATOP);
        if (leagueId.equals(getResources().getString(R.string.nfl_lg_id))) {
            tvNoCards.setText("There are no NFL cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_am_football);
        } else if (leagueId.equals(getResources().getString(R.string.nba_lg_id))) {
            tvNoCards.setText("There are no NBA cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_basketball);
        } else if (leagueId.equals(getResources().getString(R.string.nhl_lg_id))) {
            tvNoCards.setText("There are no NHL cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_hockey);
        } else if (leagueId.equals(getResources().getString(R.string.nascar_lg_id))) {
            tvNoCards.setText("There are no NASCAR cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_nascar_h);
        } else if (leagueId.equals(getResources().getString(R.string.mlb_lg_id))) {
            tvNoCards.setText("There are no MLB cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_baseball);
        } else if (leagueId.equals(getResources().getString(R.string.epl_lg_id))) {
            tvNoCards.setText("There are no EPL cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_tennis);
        } else if (leagueId.equals(getResources().getString(R.string.LALIGA_lg_id))) {
            tvNoCards.setText("There are no LA-LIGA cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_am_football);
        } else if (leagueId.equals(getResources().getString(R.string.mls_lg_id))) {
            tvNoCards.setText("There are no MLS cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_soccer);
        } else if (leagueId.equals(getResources().getString(R.string.NCAAMB_lg_id))) {
            tvNoCards.setText("There are no NCAAMB cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_basketball);
        } else if (leagueId.equals(getResources().getString(R.string.pga_lg_id))) {
            tvNoCards.setText("There are no PGA cards right now.");
            ivNoCards.setImageResource(R.drawable.ic_golf);
        }

    }

    //Process the cards fetched from server
    private void processResponseData() {
        for (int i = 0; i < cardsList.size(); i++) {
            if (cardsList.get(i).getIsPurchased().contains("true")) {
                if (myCardsCount < MY_CARDS_MAX) {
                    myCardsData.add(cardsList.get(i));
                }
                myCardsCount++;
            } else {
                upcomingCardsData.add(cardsList.get(i));
            }
        }
    }

    private void setPageData() {

        if (myCardsCount > 0) {
            recyclerViewItems.add("My cards");
            recyclerViewItems.addAll(myCardsData);
        }

        if (upcomingCardsData.size() > 0) {

            recyclerViewItems.add("Upcoming cards");
            recyclerViewItems.addAll(upcomingCardsData);
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    public class RecyclerViewAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        // A menu item view type.
        private static final int MENU_ITEM_VIEW_TYPE = 0;

        // The banner ad view type.
        private static final int BANNER_AD_VIEW_TYPE = 1;

        // An Activity's Context.
        private final Context context;

        // The list of banner ads and menu items.
        private final List<Object> recyclerViewItems;

        // FragmentActivity getActivity();

        RecyclerViewAdapterNew(List<Object> recyclerViewItems, Context context) {
            //    this.context = context;
            this.recyclerViewItems = recyclerViewItems;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return recyclerViewItems.size();
        }

        /**
         * Determines the view type for the given position.
         */
        @Override
        public int getItemViewType(int position) {
            int viewType = -1;

            if (recyclerViewItems.get(position) instanceof MyCardsPojo) {
                viewType = 0;
            } else {
                viewType = 1;
            }
            return viewType;
        }

        /**
         * Creates a new view for a menu item view or a banner ad view
         * based on the viewType. This method is invoked by the layout manager.
         */
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:
                    View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                            R.layout.fragment_mlb, viewGroup, false);
                    return new MenuItemViewHolder(menuItemLayoutView);
                case BANNER_AD_VIEW_TYPE:
                    // fall throughMat
                default:
                    View bannerLayoutView = LayoutInflater.from(
                            viewGroup.getContext()).inflate(R.layout.header_layout,
                            viewGroup, false);
                    return new AdViewHolder(bannerLayoutView);
            }
        }

        /**
         * Replaces the content in the views that make up the menu item view and the
         * banner ad view. This method is invoked by the layout manager.
         */
        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int ele_position) {

            int viewType = getItemViewType(ele_position);
            final int position = holder.getAdapterPosition();
            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:
                    final MenuItemViewHolder listingView = (MenuItemViewHolder) holder;
                    final MyCardsPojo all_home_cards = (MyCardsPojo) recyclerViewItems.get(position);

                    listingView.tvCardTitle.setText(all_home_cards.getCardTitle());
                    String cardType = all_home_cards.getMatchupType();
                    listingView.tvMatchUpType.setText(cardType);

                    if (cardType.equalsIgnoreCase("match-up")) {
                        //    listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_matchup, 0, 0, 0);
                        listingView.ivMatchUp.setVisibility(View.VISIBLE);
                        listingView.ivOverUnder.setVisibility(View.GONE);
                        listingView.tvPlus.setVisibility(View.GONE);
                    } else if (cardType.equalsIgnoreCase("mixed")) {
                        // listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_ptt, 0, 0, 0);
                        listingView.ivMatchUp.setVisibility(View.VISIBLE);
                        listingView.ivOverUnder.setVisibility(View.VISIBLE);
                        listingView.tvPlus.setVisibility(View.VISIBLE);
                    } else if (cardType.equalsIgnoreCase("OVER-UNDER")) {
                        //listingView.tvMatchUpType.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_overunder, 0, 0, 0);
                        listingView.ivMatchUp.setVisibility(View.GONE);
                        listingView.ivOverUnder.setVisibility(View.VISIBLE);
                        listingView.tvPlus.setVisibility(View.GONE);
                    }

                    if (all_home_cards.getIsLive().equals("true")) {
                        listingView.llLive.setVisibility(View.VISIBLE);
                        listingView.tvStartTime.setVisibility(View.GONE);
                    } else {
                        listingView.tvStartTime.setVisibility(View.VISIBLE);
                        listingView.llLive.setVisibility(View.GONE);
                    }


                    String leagueTime = all_home_cards.getStartTime();

                    if (leagueTime != null && !leagueTime.equals("")) {
                        String formatTimeDiff=getTimeDiff(leagueTime);
                        if(formatTimeDiff!=null&&!formatTimeDiff.equals("")) {
                            listingView.tvStartTime.setText(formatTimeDiff);
                            listingView.tvStartTime.setVisibility(View.VISIBLE);
                        }else{
                            listingView.tvStartTime.setVisibility(View.GONE);
                        }
                    } else {
                        listingView.tvStartTime.setVisibility(View.GONE);
                    }

                    if (all_home_cards.getPlayerImageLeft() != null && !all_home_cards.getPlayerImageLeft().equals("")) {
                        Picasso.with(getActivity())
                                .load(all_home_cards.getPlayerImageLeft())
                                .into(new Target() {

                                    @Override
                                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                        /* Save the bitmap or do something with it here */

                                        // Set it in the ImageView
                                        // listingView.player1Img.setImageBitmap(convertTParellelogram(bitmap, "xxx"));

                                        listingView.player1Img.setImageBitmap(convertTParellelogram(bitmap, "xxx", getActivity()));
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                        listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_basketball));
                                    }
                                });

                    } else {
                        listingView.player1Img.setImageDrawable(getResources().getDrawable(R.drawable.cl_basketball));
                    }

                    if (all_home_cards.getPlayerImageRight() != null && !all_home_cards.getPlayerImageRight().equals("")) {
                        Picasso.with(getActivity())
                                .load(all_home_cards.getPlayerImageRight())
                                .into(new Target() {

                                    @Override
                                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                                        /* Save the bitmap or do something with it here */

                                        // Set it in the ImageView
                                        listingView.player2Img.setImageBitmap(convertTParellelogram(bitmap, "pare", getActivity()));
                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {
                                        listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_basketball));
                                    }
                                });

                    } else {
                        listingView.player2Img.setImageDrawable(getResources().getDrawable(R.drawable.cr_basketball));
                    }

                    String leagueName = all_home_cards.getLeagueAbbrevation();
                    boolean isFailedtoLoadLHS_img = false;
                    boolean isFailedtoLoadRHS_img = false;
                    switch (leagueName) {
                        case "NFL":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.foot_ball_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getDrawable(R.drawable.cl_football));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getDrawable(R.drawable.cr_football));
                            }
                            break;

                        case "NBA":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.basket_ball_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_basketball));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_basketball));
                            }
                            break;

                        case "NHL":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.hockey_color));
                            if (isFailedtoLoadLHS_img) {

                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_hockey));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_hockey));
                            }
                            break;

                        case "NASCAR":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.car_race_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_nascar));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_nascar));
                            }
                            break;

                        case "MLB":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.base_ball_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_baseball));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_baseball));
                            }
                            break;

                        case "EPL":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.tennis_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_tennis));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_tennis));
                            }
                            break;

                        case "LA-LIGA":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.foot_ball_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_football));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_football));
                            }
                            break;

                        case "MLS":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.soccer_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_soccer));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_soccer));
                            }
                            break;

                        case "NCAAMB":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.basket_ball_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_basketball));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_basketball));
                            }
                            break;

                        case "PGA":
                            listingView.cardColor.setBackgroundColor(getActivity().getResources().getColor(R.color.golf_color));
                            if (isFailedtoLoadLHS_img) {
                                listingView.player1Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cl_golf));
                            }
                            if (isFailedtoLoadRHS_img) {
                                listingView.player2Img.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.cr_golf));
                            }
                            break;
                        default:

                            break;
                    }


                    listingView.llTotal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                Intent matchup = new Intent(getActivity(), MatchupScreen.class);
                                matchup.putExtra("cardid", all_home_cards.getCardId());
                                matchup.putExtra("cardid_title", all_home_cards.getCardTitle());
                                matchup.putExtra("cardid_color1", all_home_cards.getLeagueAbbrevation());
                                startActivity(matchup);

                        }
                    });

                    break;
                case BANNER_AD_VIEW_TYPE:
                    // fall through
                    final AdViewHolder adViewHolder = (AdViewHolder) holder;

                    //handle header data
                    String headerStr = "";
                    if (recyclerViewItems.get(ele_position) instanceof String) {
                        headerStr = (String) recyclerViewItems.get(ele_position);
                    }
                    adViewHolder.tvHeader.setText(headerStr);
                    adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);


                    if (headerStr != null && headerStr.equals("My cards")) {
                        if (myCardsCount > MY_CARDS_MAX)
                            adViewHolder.tvViewALL.setVisibility(View.VISIBLE);
                        else
                            adViewHolder.tvViewALL.setVisibility(View.GONE);
                    } else {
                        adViewHolder.tvViewALL.setVisibility(View.GONE);
                    }


                    adViewHolder.tvViewALL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            try {
                                Activity act = getActivity();
                                if (act instanceof Dashboard)
                                    ((Dashboard) act).navToMyCards();
                            } catch (Exception e) {
                                e.getStackTrace();
                            }

                        }
                    });
                default:


                    break;
            }
        }

        class MenuItemViewHolder extends RecyclerView.ViewHolder {

            ImageView player1Img, player2Img,ivMatchUp,ivOverUnder;
            LinearLayout llTotal, llLive;
            private TextView tvCardTitle, tvMatchUpType, tvStartTime,tvPlus;
            private View cardColor;


            MenuItemViewHolder(View convertView) {
                super(convertView);

                tvCardTitle = convertView.findViewById(R.id.tvCardTitle);
                player1Img = convertView.findViewById(R.id.player1Img);
                player2Img = convertView.findViewById(R.id.player2Img);
                llTotal = convertView.findViewById(R.id.llTotal);
                tvMatchUpType = convertView.findViewById(R.id.tvMatchUpType);
                tvStartTime = convertView.findViewById(R.id.tvStartTime);
                cardColor = convertView.findViewById(R.id.cardColor);
                llLive = convertView.findViewById(R.id.llLive);
                ivOverUnder=convertView.findViewById(R.id.ivOverUnder);
                ivMatchUp=convertView.findViewById(R.id.ivMatchUp);
                tvPlus=convertView.findViewById(R.id.tvPlus);
            }
        }

        class AdViewHolder extends RecyclerView.ViewHolder {
            private TextView tvHeader, tvViewALL;

            AdViewHolder(View adview) {
                super(adview);
                tvHeader = adview.findViewById(R.id.tvHeader);
                tvViewALL = adview.findViewById(R.id.tvViewALL);
            }
        }

    }

    private String getTimeDiff(String timeFromServer){
        String formattedTime="";
        String [] timeArray= timeFromServer.split("T");
        String  serverDate="";
        String sererTime="";

        if(timeArray.length>0){
            serverDate=timeArray[0];
            sererTime=timeArray[1].replace("Z","");
            timeFromServer=serverDate+" "+sererTime;
        }
        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String datenow = dateFormat.format(currentDate);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = spf.parse(datenow);
            date2 = spf.parse(timeFromServer);
            if(date2!=null&&date1!=null)
                formattedTime=printDifference(date1, date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedTime;
    }

    private String  printDifference(Date startDate, Date endDate) {

        String diffDate="";
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        if(elapsedDays>0&&elapsedHours>0&&elapsedMinutes>0){
            diffDate=elapsedDays+"d "+elapsedHours+"h";
        }else if(elapsedHours>0&&elapsedMinutes>0){
            diffDate=elapsedHours+"h "+elapsedMinutes+"m";
        }else if(elapsedMinutes>0){
            diffDate=elapsedMinutes+"m";
        }

        return  diffDate;
    }

}