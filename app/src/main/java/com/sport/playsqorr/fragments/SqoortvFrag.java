package com.sport.playsqorr.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.TvPojo;
import com.sport.playsqorr.utilities.APIs;
import com.sport.playsqorr.utilities.CustomVideoPlayer;
import com.sport.playsqorr.utilities.PresetValueButton;
import com.sport.playsqorr.utilities.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SqoortvFrag extends Fragment implements CustomVideoPlayer.PlaybackListener, View.OnClickListener {

    private RecyclerView rvTv;

    private List<Object> recyclerViewItems = new ArrayList<>();
    private List<String> cardTypes = new ArrayList<>();
    private List<TvPojo> tvPojoList = new ArrayList<>();
    private RecyclerViewAdapterNew recycleAdapter;

    TextView tv_t, tv_des;
    CustomVideoPlayer customVideoPlayer;

    YouTubePlayerView youTubePlayerView;
    PresetValueButton all_pvb, EPL_pvb, LA_LIGA_pvb, mlb_pvb, mls_pvb, NASCAR_pvb, NBA_pvb, NCAAMB_pvb, NFL_pvb, NHL_pvb, PGA_pvb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragthree, container, false);
        rvTv = rootView.findViewById(R.id.rvTv);
        tv_t = rootView.findViewById(R.id.tv_t);
        tv_des = rootView.findViewById(R.id.tv_des);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvTv.setLayoutManager(llm);
        rvTv.setItemAnimator(null);
        recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
        rvTv.setAdapter(recycleAdapter);

        init();



       /* VideoView simpleVideoView = rootView.findViewById(R.id.simpleVideoView);
        simpleVideoView.setVideoURI(uri);
        simpleVideoView.start();
        simpleVideoView.setMediaController(mediaController);


        // get id from XML
         youtubePlayerView =  rootView.findViewById(R.id.youtubePlayerView);

        // Control values
        // see more # https://developers.google.com/youtube/player_parameters?hl=en
        YTParams params = new YTParams();

        // make auto height of youtube. if you want to use 'wrap_content'
        youtubePlayerView.setAutoPlayerHeight(getActivity());
        // initialize YoutubePlayerCallBackListener and VideoID
        youtubePlayerView.initialize("xpx5L6z7Ctw", new YoutubePlayerView.YouTubeListener() {

            @Override
            public void onReady() {
                // when player is ready.
                // play video when it's ready
                youtubePlayerView.play();
            }

            @Override
            public void onStateChange(YoutubePlayerView.STATE state) {
                /**
                 * YoutubePlayerView.STATE
                 *
                 * UNSTARTED, ENDED, PLAYING, PAUSED, BUFFERING, CUED, NONE
                 *

            }

            @Override
            public void onPlaybackQualityChange(String arg) {
            }

            @Override
            public void onPlaybackRateChange(String arg) {
            }

            @Override
            public void onError(String error) {
            }

            @Override
            public void onApiChange(String arg) {
            }

            @Override
            public void onCurrentSecond(double second) {
                // currentTime callback
            }

            @Override
            public void onDuration(double duration) {
                // total duration
            }

            @Override
            public void logs(String log) {
                // javascript debug log. you don't need to use it.
            }
        });


        // psuse video
        youtubePlayerView.pause();
    */

        getSqorrTvData();

        return rootView;
    }

    private void init() {
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

        youTubePlayerView = getActivity().findViewById(R.id.youtube_player_view);

        /*all_pvb.setOnClickListener(this);
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
        */

        /*getActivity().getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = vidoe_code;
                youTubePlayer.loadVideo(videoId, 0);
            }
        });*/

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all /*2131230804*/:
                getActivity().findViewById(R.id.all).setSelected(true);
                return;
            case R.id.EPL /*2131230804*/:
                getActivity().findViewById(R.id.EPL);//.setSelected(true);

                return;
            case R.id.LA_LIGA /*2131230804*/:
                getActivity().findViewById(R.id.LA_LIGA);//.setSelected(true);

                return;
            case R.id.mlb /*2131230804*/:
                getActivity().findViewById(R.id.mlb);//.setSelected(true);

                return;
            case R.id.mls /*2131230804*/:
                getActivity().findViewById(R.id.mls);

                return;
            case R.id.NASCAR /*2131230804*/:
                getActivity().findViewById(R.id.NASCAR);//.setSelected(true);

                return;
            case R.id.NBA /*2131230804*/:
                getActivity().findViewById(R.id.NBA);//.setSelected(true);

                return;
            case R.id.NCAAMB /*2131230804*/:
                getActivity().findViewById(R.id.NCAAMB);//.setSelected(true);

                return;
            case R.id.NFL /*2131230804*/:
                getActivity().findViewById(R.id.NFL);//.setSelected(true);

                return;
            case R.id.NHL /*2131230804*/:
                getActivity().findViewById(R.id.NHL);//.setSelected(true);

                return;
            case R.id.PGA /*2131230804*/:
                getActivity().findViewById(R.id.PGA);

                return;

            default:
                return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // pause video when on the background mode.
//        youTubePlayerView.pause();
        if(customVideoPlayer!=null)
        customVideoPlayer.pause();
    }

    @Override
    public void onStop() {
        super.onStop();
        // pause video when on the background mode.
//        youTubePlayerView.pause();
        if(customVideoPlayer!=null)
        customVideoPlayer.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // this is optional but you need.
        //youTubePlayerView.destroy();
        if(customVideoPlayer!=null)
        customVideoPlayer.stop();

    }

    //To sqorr tv data from server
    private void getSqorrTvData() {

        AndroidNetworking.get(APIs.SQORRTV_URL)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        recyclerViewItems.clear();
                        tvPojoList.clear();

                        Log.e("tv",response.toString());
                        // do anything with response
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                TvPojo tvPojo = new TvPojo();
                                tvPojo.setUrl(jsonObject.getString("url"));
                                tvPojo.setTitle(jsonObject.getString("title"));
                                tvPojo.setDescription(jsonObject.getString("description"));
                                tvPojo.setLeagueId(jsonObject.getString("leagueId"));
                                tvPojo.setDurationInSeconds(jsonObject.getString("durationInSeconds"));
//                                tvPojo.setIsFeatured(jsonObject.getString("isFeatured"));

                                tvPojoList.add(tvPojo);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        handleDiffData();
                        //    recyclerViewItems.addAll(tvPojoList);

                        if (recycleAdapter != null)
                            recycleAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utilities.showToast(getActivity(), error.getErrorDetail());
                    }
                });

    }


    private void handleDiffData() {
        for (int i = 0; i < tvPojoList.size(); i++) {

            String leagueId = tvPojoList.get(i).getLeagueId();
            if (!cardTypes.contains(leagueId)) {
                cardTypes.add(leagueId);
            }

            switch (leagueId) {
                case "551351dc37b279523d469be2":

                    //  MLBData.add(tvPojoList.get(i));
                    recyclerViewItems.add("MLB");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "547e6e1e57489582581c7d8b":
                    recyclerViewItems.add("NBA");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "588312f280e08f06fb4e5338":
                    recyclerViewItems.add("EPL");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "588d147080e08f06fb4ecbd7":
                    recyclerViewItems.add("LA-LIGA");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "54b47ce6c85e70081a1637a2":
                    recyclerViewItems.add("NHL");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "54e634250e03fdfe2d8569cd":
                    recyclerViewItems.add("NASCAR");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "5c78ffacba8cfd62d4060620":
                    recyclerViewItems.add("NCAAMB");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "547e6d5057489582581c7d83":
                    recyclerViewItems.add("NFL");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "58e564ea80e08f06fb5234f2":
                    recyclerViewItems.add("MLS");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;

                case "5ca4dd12ec0c61b7f5d822ef":
                    recyclerViewItems.add("PGA");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;
                default:
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;
            }
        }


    }

    @Override
    public void onVolumeChange(boolean volumeOn) {

    }

    @Override
    public void onPlayEvent() {
        customVideoPlayer.play();

    }

    @Override
    public void onPauseEvent() {
        customVideoPlayer.pause();

    }

    @Override
    public void onCompletedEvent() {
        customVideoPlayer.stop();

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

        // FragmentActivity activity_t;

        RecyclerViewAdapterNew(List<Object> recyclerViewItems, Context context) {
            this.recyclerViewItems = recyclerViewItems;
            this.context = context;
        }


        class MenuItemViewHolder extends RecyclerView.ViewHolder {

            private TextView tvVideoName, tvDuration;
            private View cardColor;

            ImageView ivThumbnail;
            LinearLayout llTotal;


            MenuItemViewHolder(View convertView) {
                super(convertView);

                tvVideoName = convertView.findViewById(R.id.tvVideoName);
                ivThumbnail = convertView.findViewById(R.id.ivThumbnail);
                llTotal = convertView.findViewById(R.id.llTotal);
                tvDuration = convertView.findViewById(R.id.tvDuration);
                cardColor = convertView.findViewById(R.id.cardColor);

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

            if (recyclerViewItems.get(position) instanceof TvPojo) {
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
                            R.layout.fragment_tv_cell, viewGroup, false);
                    return new RecyclerViewAdapterNew.MenuItemViewHolder(menuItemLayoutView);
                case BANNER_AD_VIEW_TYPE:
                    // fall through
                default:
                    View bannerLayoutView = LayoutInflater.from(
                            viewGroup.getContext()).inflate(R.layout.header_layout,
                            viewGroup, false);
                    return new RecyclerViewAdapterNew.AdViewHolder(bannerLayoutView);
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
                    final RecyclerViewAdapterNew.MenuItemViewHolder listingView = (RecyclerViewAdapterNew.MenuItemViewHolder) holder;
                    final TvPojo tvCards = (TvPojo) recyclerViewItems.get(position);

                    listingView.tvVideoName.setText(tvCards.getTitle());

                    int duration = 0, mins = 0, sec = 0;
                    String timeFormat = "";
                    if (tvCards.getDurationInSeconds() != null && !tvCards.getDurationInSeconds().equals("")) {
                        duration = Integer.parseInt(tvCards.getDurationInSeconds());

                    }

                    sec = duration % 60;
                    mins = (duration / 60) % 60;

                    if (mins > 0) {
                        if (sec < 10)
                            timeFormat = mins + ":0" + sec;
                        else
                            timeFormat = mins + ":" + sec;
                    } else {
                        if (sec < 10)
                            timeFormat = "0:0" + sec;
                        else
                            timeFormat = "0:" + sec;
                    }


                    listingView.tvDuration.setText(timeFormat);
                    String leagueType = tvCards.getTitle();


                    if (leagueType.contains("NFL")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_football));
                    } else if (leagueType.contains("NBA")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_basketball));
                    } else if (leagueType.contains("NHL")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.hockey_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_hockey));
                    } else if (leagueType.contains("NASCAR")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.car_race_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_nascar));
                    } else if (leagueType.contains("MLB")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.base_ball_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_baseball));
                    } else if (leagueType.contains("EPL")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.tennis_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_tennis));

                    } else if (leagueType.contains("LA-LIGA")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_football));
                    } else if (leagueType.contains("MLS")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.soccer_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_soccer));
                    } else if (leagueType.contains("NCAAMB")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_basketball));

                    } else if (leagueType.contains("PGA")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.golf_color));
                        listingView.ivThumbnail.setBackground(getResources().getDrawable(R.drawable.vm_golf));
                    }


                    //      listingView.tvVideoName.setText(""+min + " : "+ sec);

                    listingView.llTotal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Utilities.showToast(getActivity(), "Play Video");
                            getUrl(tvCards.getUrl(), tvCards.getTitle(), tvCards.getDescription());
                        }
                    });

                    break;
                case BANNER_AD_VIEW_TYPE:
                    // fall through
                    final RecyclerViewAdapterNew.AdViewHolder adViewHolder = (RecyclerViewAdapterNew.AdViewHolder) holder;

                    //handle header data
                    String headerStr = "Header";
                    if (recyclerViewItems.get(ele_position) instanceof String) {
                        headerStr = (String) recyclerViewItems.get(ele_position);
                    }
                    adViewHolder.tvHeader.setText(headerStr);
                    adViewHolder.tvViewALL.setVisibility(View.GONE);

                    //handle view all

                    boolean showViewAll = false;
                    switch (headerStr) {
                        case "NFL":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_am_football, 0, 0, 0);
                            break;

                        case "NBA":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball, 0, 0, 0);
                            break;

                        case "NHL":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hockey, 0, 0, 0);
                            break;

                        case "NASCAR":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_nascar_h, 0, 0, 0);
                            break;

                        case "MLB":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseball, 0, 0, 0);
                            break;

                        case "EPL":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tennis, 0, 0, 0);
                            break;

                        case "LA-LIGA":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_am_football, 0, 0, 0);
                            break;

                        case "MLS":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_soccer, 0, 0, 0);
                            break;

                        case "NCAAMB":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_basketball, 0, 0, 0);
                            break;

                        case "PGA":
                            adViewHolder.tvHeader.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_golf, 0, 0, 0);
                            break;
                        default:
                            break;
                    }


            }
        }

    }
     String vidoe_code;
    private void getUrl(String url, String title, String desc) {


        String currentString = url;
        String[] separated = currentString.split("=");
        String url_ = separated[0]; // this will contain "Fruit"
         vidoe_code = separated[1];


        init();

//        customVideoPlayer = getActivity().findViewById(R.id.customVideoPlayer);
//
//        customVideoPlayer.setMediaUrl(url);
//        customVideoPlayer.enableAutoMute(false)
//                .enableAutoPlay(true)
//                .hideControllers(true)
//                .setOnPlaybackListener(this)
//                .build();

        tv_t.setText(title);
        tv_des.setText(desc);
    }
}
