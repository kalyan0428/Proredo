package com.sport.playsqorr.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.potyvideo.library.AndExoPlayerView;
import com.sport.playsqorr.utilities.CustomVideoPlayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.TvPojo;
import com.sport.playsqorr.utilities.BitmapUtils;
import com.sport.playsqorr.utilities.PathParser;
import com.sport.playsqorr.views.TvFullScreenVideo;

import java.util.ArrayList;
import java.util.List;


public class SqoortvAllFrag extends Fragment {

    private final ArrayList<Integer> selected = new ArrayList<>();
    TextView tv_t, tv_des;
    ToggleButton video_music_toggle_button;
    ToggleButton fullscreen_button;
    YouTubePlayerTracker youTubePlayerTracker;
    private RecyclerView rvTv;
    private List<Object> recyclerViewItems = new ArrayList<>();
    private List<String> cardTypes = new ArrayList<>();
    private List<TvPojo> tvPojoList = new ArrayList<>();
    private RecyclerViewAdapterNew recycleAdapter;
 //   private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    private String video_id;
    CustomVideoPlayer customVideoPlayer;
    VideoView videoView;
    MediaController mediacontroller;

    //    ProgressDialog progressDialog;

//    SimpleExoPlayerView simpleExoPlayerView;
//    SimpleExoPlayer player;
    AndExoPlayerView andExoPlayerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.sqorrtv_all, container, false);
        rvTv = rootView.findViewById(R.id.rvTv);
        tv_t = rootView.findViewById(R.id.tv_t);
        tv_des = rootView.findViewById(R.id.tv_des);

        videoView = rootView.findViewById(R.id.videoView_tv);
        mediacontroller = new MediaController(getActivity());
        mediacontroller.setAnchorView(videoView);

//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading..");
//
//        if (progressDialog != null)
//            progressDialog.show();
//
//
        videoView.setMediaController(mediacontroller);
//        videoView.setVideoURI(Uri.parse("https://imgstore.azureedge.net/videos/HowtoScore_NBA.mp4"));
//        videoView.requestFocus();
//        videoView.start();
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//                        videoView.setMediaController(mediacontroller);
//                        mediacontroller.setAnchorView(videoView);
//
//                    }
//                });
//            }
//        });

//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mp) {
//                Toast.makeText(context, "Video over", Toast.LENGTH_SHORT).show();
//                if (index++ == arrayList.size()) {
//                    index = 0;
//                    mp.release();
//                    Toast.makeText(context, "Videos completed", Toast.LENGTH_SHORT).show();
//                } else {
//                    videoView.setVideoURI(Uri.parse(arrayList.get(index)));
//                    videoView.start();
//                }
//
//
//            }
//        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.e("API123", "What " + what + " extra " + extra);
                return false;
            }
        });

//        simpleExoPlayerView = rootView.findViewById(R.id.simple_exoplayer_view);
        andExoPlayerView = rootView.findViewById(R.id.andExoPlayerView);

    //    customVideoPlayer = rootView.findViewById(R.id.customVideoPlayer);
        //customVideoPlayer.setMediaUrl("https://imgstore.azureedge.net/videos/HowtoScore_NBA.mp4");

    //    youTubePlayerView = rootView.findViewById(R.id.youtube_player_view_all);
        video_music_toggle_button = rootView.findViewById(R.id.video_music_toggle_button);
        fullscreen_button = rootView.findViewById(R.id.fullscreen_button_lol);
//        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
//        youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
//        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
//        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
//        youTubePlayerView.getPlayerUiController().showMenuButton(false);
//        getLifecycle().addObserver(youTubePlayerView);

        video_music_toggle_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mYouTubePlayer.setVolume(0);
                } else {
                    mYouTubePlayer.setVolume(90);
                }
            }
        });
        fullscreen_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Intent intent = new Intent(getActivity(), TvFullScreenVideo.class);
                    intent.putExtra("video_id", video_id);
                    intent.putExtra("video_time", youTubePlayerTracker.getCurrentSecond());
                    startActivity(intent);
                }
            }
        });



        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvTv.setLayoutManager(llm);
        rvTv.setItemAnimator(null);


        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
     //   setupPlayer();

    }

    /*void setupPlayer(Uri videoUri){
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        //initialize the player with default configurations
        player = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);
//        SimpleExoPlayer player = new SimpleExoPlayer.Builder(getActivity()).build();

        //Assign simpleExoPlayerView
        simpleExoPlayerView.setPlayer(player);

        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getActivity(), Util.getUserAgent(getActivity(), "CloudinaryExoplayer"));

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        // Prepare the player with the source.
        player.prepare(videoSource);

    }*/

    @Override
    public void onPause() {
        super.onPause();
        // pause video when on the background mode.
//        youTubePlayerView.pause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // this is optional but you need.
        //youTubePlayerView.destroy();
    }

    private void getSqorrTvData() {


        recyclerViewItems.clear();
        tvPojoList.clear();
        tvPojoList = (List<TvPojo>) getArguments().getSerializable("tvPojoList");
        for (int i = 0; i < getArguments().getParcelableArrayList("tvPojoList").size(); i++) {
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
                case "5d9f56eeb762fc5fcb8660cf":
                    recyclerViewItems.add("NCAAFB");
                    recyclerViewItems.add(tvPojoList.get(i));
                    break;
                case "5d6eb800c54b8b41d010626b":
                    recyclerViewItems.add("WILD CARD");
                    recyclerViewItems.add(tvPojoList.get(i));

                    break;
                default:
                    recyclerViewItems.add(tvPojoList.get(i));


                    break;
            }
        }


//        if (SqorrTvFragment.progressDialog != null)
//            SqorrTvFragment.progressDialog.dismiss();
    }


    public Drawable convertTParellelogram(Bitmap src, String type, Context context) {
        Bitmap typex;
        typex = BitmapUtils.newGetCroppedBitmap(src, getParellelogramPath(src, type));
        Drawable d = new BitmapDrawable(getResources(), typex);
        return d;
    }

    private Path getParellelogramPath(Bitmap src, String type) {
        Path path = null;
        path = resizePath(PathParser.createPathFromPathData(getContext().getString(R.string.square)),
                src.getWidth(), src.getHeight());
        return path;
    }

    public Path resizePath(Path path, float width, float height) {
        RectF bounds = new RectF(0, 0, width, height);
        Path resizedPath = new Path(path);
        RectF src = new RectF();
        resizedPath.computeBounds(src, true);

        Matrix resizeMatrix = new Matrix();
        resizeMatrix.setRectToRect(src, bounds, Matrix.ScaleToFit.CENTER);
        resizedPath.transform(resizeMatrix);

        return resizedPath;
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(-60, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public void onResume() {
        super.onResume();


        getSqorrTvData();

        recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
        rvTv.setAdapter(recycleAdapter);

        if (recycleAdapter != null)
            recycleAdapter.notifyDataSetChanged();
//        if (SqorrTvFragment.progressDialog != null)
//            SqorrTvFragment.progressDialog.dismiss();


    }

    private void loadVideo(final YouTubePlayer youTubePlayer, String video_code, boolean getIsFeatured) {
        Log.v("VIDEO_CODE", video_code);
        video_id = video_code;
        if (getIsFeatured) {
            youTubePlayer.loadVideo(video_code, 0f);
        } else {
            youTubePlayer.loadVideo(video_code, 0f);
        }
        video_music_toggle_button.setClickable(true);
        fullscreen_button.setClickable(true);
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

        int selectedPosition = -1;
        private boolean playautomatic = true;
        private boolean isAlreadyInitialaised = false;
        private boolean isImageloaded = false;

        RecyclerViewAdapterNew(List<Object> recyclerViewItems, Context context) {
            this.recyclerViewItems = recyclerViewItems;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return recyclerViewItems.size();
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
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
                    return new MenuItemViewHolder(menuItemLayoutView);
                case BANNER_AD_VIEW_TYPE:
                    // fall through

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
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int ele_position) {

            int viewType = getItemViewType(ele_position);
            final int position = holder.getAdapterPosition();

//            if (SqorrTvFragment.progressDialog != null)
//            SqorrTvFragment.progressDialog.dismiss();

            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:
                    final MenuItemViewHolder listingView = (MenuItemViewHolder) holder;
                    final TvPojo tvCards = (TvPojo) recyclerViewItems.get(position);

                    listingView.tvVideoName.setText(tvCards.getTitle());

                    if (SqorrTvFragment.progressDialog != null)
                        SqorrTvFragment.progressDialog.dismiss();

                    String strDate= tvCards.getUpdatedAt();
                    String currentString = tvCards.getUpdatedAt();
                    String[] separated = currentString.split("T");
//                    separated[0]; // this will contain "Fruit"
                    listingView.tvDate.setText(separated[0]);

                    /*
                    String strDate = tvCards.getUpdatedAt();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

                    Date convertedDate = new Date();
                    try {
                        convertedDate = dateFormat.parse(strDate);
                        SimpleDateFormat sdfmonth = new SimpleDateFormat("MM/dd");
                        String monthday = sdfmonth.format(convertedDate);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
*/
//                    listingView.tvDate.setText(tvCards.getUpdatedAt());

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
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color_org));
/*                        final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_football);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_football));

                    } else if (leagueType.contains("NBA")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.basket_ball_color));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_basketball);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_basketball));

                    } else if (leagueType.contains("NHL")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.hockey_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_hockey);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
                */
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_hockey));

                    } else if (leagueType.contains("NASCAR")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.car_race_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_nascar);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_nascar));
                    } else if (leagueType.contains("MLB")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.base_ball_color_org));
                         /*  final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_baseball);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_baseball));


                    } else if (leagueType.contains("EPL")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.tennis_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_tennis);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_tennis));

                    } else if (leagueType.contains("LA-LIGA")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.foot_ball_color_org));
                      /*    final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_football);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_football));

                    } else if (leagueType.contains("MLS")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.soccer_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_soccer);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_soccer));

                    } else if (leagueType.contains("NCAAMB")) {

                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.basket_ball_color_org));
                      /*  final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_basketball);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
*/
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_basketball));

                    } else if (leagueType.contains("PGA")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.golf_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_golf);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
          */
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_golf));
                    } else if (leagueType.contains("NCAAFB")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.golf_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_golf);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
          */
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_football));
                    } else if (leagueType.contains("WILD CARD")) {
                        listingView.cardColor.setBackgroundColor(getResources().getColor(R.color.golf_color_org));
                       /* final Bitmap frame_bitmap = BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.vm_golf);
                        listingView.ivThumbnail.setBackground(convertTParellelogram(frame_bitmap, "xxx", context));
          */
                        listingView.ivThumbnail.setBackground(getContext().getDrawable(R.drawable.vm_basketball));
                    }

                    setMargins(listingView.ivThumbnail, -60, 0, 0, 0);

                    if (tvCards.getThumbnail() != null && !tvCards.getThumbnail().equals("")) {
                        Picasso.with(context).load(tvCards.getThumbnail()).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                isImageloaded = true;
                                listingView.ivThumbnail.setBackground(convertTParellelogram(bitmap, "xxx", context));
                                setMargins(listingView.ivThumbnail, -60, 0, 0, 0);
                                Log.v("PROCESS", "SUCCESS" + position);
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Log.v("PROCESS", "onBitmapFailed..." + position);

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Log.v("PROCESS", "loading..." + position);
                            }
                        });
                    }
                    if (tvCards.getIsFeatured() && playautomatic) {
                        playautomatic = false;
                        listingView.frame_playing.setVisibility(View.VISIBLE);
                        final Bitmap frame_bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                                R.drawable.frame_tv);
                        listingView.frame_playing.setBackground(convertTParellelogram(frame_bitmap, "xxx", getContext()));
                        setMargins(listingView.frame_playing, -60, 0, 0, 0);
                        listingView.is_playing_text.setVisibility(View.VISIBLE);
                        Log.d("valusvideo", tvCards.getUrl());

                        {

                            tv_t.setText(tvCards.getTitle());
                            tv_des.setText(tvCards.getDescription());
                            andExoPlayerView.setSource(tvCards.getUrl());
                            /*
                            videoView.setVideoURI(Uri.parse(tvCards.getUrl()));
                            videoView.requestFocus();
                            videoView.start();
//                isAlreadyInitialaised = true;
                            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.setLooping(true);
                                    videoView.start();
                                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                                        @Override
                                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                            videoView.setMediaController(mediacontroller);
                                            mediacontroller.setAnchorView(videoView);

                                        }
                                    });
                                }
                            });*/

//                youTubePlayerView.setEnableAutomaticInitialization(false);
//                youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady(YouTubePlayer youTubePlayer) {
//                        super.onReady(youTubePlayer);
//                        mYouTubePlayer = youTubePlayer;
//                        youTubePlayerTracker = new YouTubePlayerTracker();
//                        youTubePlayer.addListener(youTubePlayerTracker);
//                        loadVideo(youTubePlayer, separated[1], getIsFeatured);
//                        isAlreadyInitialaised = true;
//                    }
//                });
                        }

                        // playVideo(listingView, tvCards.getUrl(), tvCards.getTitle(), tvCards.getDescription(), true);
                    } else {
                        listingView.frame_playing.setVisibility(View.GONE);
                        listingView.is_playing_text.setVisibility(View.GONE);
                    }

                    if (selectedPosition == position) {
                        if (isImageloaded) {
                            listingView.frame_playing.setVisibility(View.VISIBLE);
                            final Bitmap frame_bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                                    R.drawable.frame_tv);
                            listingView.frame_playing.setBackground(convertTParellelogram(frame_bitmap, "xxx", getContext()));
                            setMargins(listingView.frame_playing, -60, 0, 0, 0);
                            listingView.is_playing_text.setVisibility(View.VISIBLE);
                        }
                    }
                    final int finalPosition = position;

                    listingView.llTotal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedPosition = finalPosition;
                            playVideo(listingView, tvCards.getUrl(), tvCards.getTitle(), tvCards.getDescription(), false);
                            notifyDataSetChanged();
                        }
                    });

                    break;
                case BANNER_AD_VIEW_TYPE:
                    // fall through
                    final AdViewHolder adViewHolder = (AdViewHolder) holder;

                    //handle header data
                    String headerStr = "Header";
                    if (recyclerViewItems.get(ele_position) instanceof String) {
                        headerStr = (String) recyclerViewItems.get(ele_position);
                    }
                    adViewHolder.tvHeader.setText(headerStr);
                    adViewHolder.tvHeader.setVisibility(View.GONE);
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

        private void playVideo(MenuItemViewHolder view, final String url, String title, String description, boolean getIsFeatured) {
            tv_t.setText(title);
            tv_des.setText(description);
            String currentString = url;
            final String[] separated = currentString.split("=");
            if (getIsFeatured)
//            if (!isAlreadyInitialaised)
            {


//                customVideoPlayer.setMediaUrl(url);
                andExoPlayerView.setSource(url);

//              Uri  videoUri_1 = Uri.parse(url);
//                setupPlayer(videoUri_1);
            /*    videoView.setVideoURI(Uri.parse(currentString));
                videoView.requestFocus();
                videoView.start();
//                isAlreadyInitialaised = true;
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setLooping(true);
                        videoView.start();
//
//                        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
//                            @Override
//                            public void onBufferingUpdate(MediaPlayer mp, int percent) {
//                                if(percent == 100){
//                                    //video have completed buffering
//                                    Toast.makeText(getActivity(),"Loading",Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });

                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                videoView.setMediaController(mediacontroller);
                                mediacontroller.setAnchorView(videoView);

                            }
                        });
                    }
                });*/

//                youTubePlayerView.setEnableAutomaticInitialization(false);
//                youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady(YouTubePlayer youTubePlayer) {
//                        super.onReady(youTubePlayer);
//                        mYouTubePlayer = youTubePlayer;
//                        youTubePlayerTracker = new YouTubePlayerTracker();
//                        youTubePlayer.addListener(youTubePlayerTracker);
//                        loadVideo(youTubePlayer, separated[1], getIsFeatured);
//                        isAlreadyInitialaised = true;
//                    }
//                });
            } else {
//                loadVideo(mYouTubePlayer, separated[1], false);

//                customVideoPlayer.setMediaUrl(url);
//                Uri  videoUri_2 = Uri.parse(url);
//                setupPlayer(videoUri_2);
                andExoPlayerView.setSource(url);

                /*
                videoView.setVideoURI(Uri.parse(currentString));
                videoView.requestFocus();
                videoView.start();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                    @Override
                    public void onPrepared(MediaPlayer mp) {

                        mp.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                            @Override
                            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                                if(percent == 100){
                                    //video have completed buffering
                                    Toast.makeText(getActivity(),"Loading",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                videoView.setMediaController(mediacontroller);
                                mediacontroller.setAnchorView(videoView);

                            }
                        });
                    }
                });*/
                isAlreadyInitialaised = false;
            }
        }

        class MenuItemViewHolder extends RecyclerView.ViewHolder {
            ImageView ivThumbnail;
            FrameLayout ivThumbnail_lay;
            ImageView frame_playing;
            LinearLayout llTotal;
            private TextView tvVideoName, tvDuration, is_playing_text, tvDate;
            private View cardColor;


            MenuItemViewHolder(View convertView) {
                super(convertView);
                tvVideoName = convertView.findViewById(R.id.tvVideoName);
                tvDate = convertView.findViewById(R.id.tvDate);
                is_playing_text = convertView.findViewById(R.id.is_playing_text);
                ivThumbnail = convertView.findViewById(R.id.ivThumbnail);
                frame_playing = convertView.findViewById(R.id.frame_playing);
                llTotal = convertView.findViewById(R.id.llTotal);
                tvDuration = convertView.findViewById(R.id.tvDuration);
                cardColor = convertView.findViewById(R.id.cardColor);
                ivThumbnail_lay = convertView.findViewById(R.id.ivThumbnail_lay);
            }

        }

        class AdViewHolder extends RecyclerView.ViewHolder {
            private TextView tvHeader, tvViewALL;

            AdViewHolder(View adview) {
                super(adview);
                tvHeader = adview.findViewById(R.id.tvHeader);
                tvHeader.setVisibility(View.GONE);
                tvViewALL = adview.findViewById(R.id.tvViewALL);

            }
        }

    }

}
