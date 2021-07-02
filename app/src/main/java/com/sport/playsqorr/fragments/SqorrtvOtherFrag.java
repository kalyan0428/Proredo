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
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.potyvideo.library.AndExoPlayerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.sport.playsqorr.R;
import com.sport.playsqorr.pojos.TvPojo;
import com.sport.playsqorr.utilities.BitmapUtils;
import com.sport.playsqorr.utilities.PathParser;
import com.sport.playsqorr.views.TvFullScreenVideo;

import java.util.ArrayList;
import java.util.List;

public class SqorrtvOtherFrag extends Fragment {

    private final ArrayList<Integer> selected = new ArrayList<>();
    TextView tv_t, tv_des, tvNoCards;
    ToggleButton video_music_toggle_button;
    ToggleButton fullscreen_button;
    TextView novideos_txt;
    LinearLayout llNoCards, playerView_ly;
    FrameLayout tv_frame;
    private RecyclerView rvTv;
    private List<Object> recyclerViewItems = new ArrayList<>();
    private List<String> cardTypes = new ArrayList<>();
    private List<TvPojo> tvPojoList = new ArrayList<>();
    private RecyclerViewAdapterNew recycleAdapter;
 //   private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    private String video_id;
    AndExoPlayerView andExoPlayerView;
    VideoView videoView;
    MediaController mediacontroller;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_other_sqorrtv, container, false);
        rvTv = rootView.findViewById(R.id.rvTv);
        tv_t = rootView.findViewById(R.id.tv_t);
        tv_frame = rootView.findViewById(R.id.tv_frame);
        tvNoCards = rootView.findViewById(R.id.tvNoCards);
        tv_des = rootView.findViewById(R.id.tv_des);
        andExoPlayerView = rootView.findViewById(R.id.andExoPlayerView);

        videoView = rootView.findViewById(R.id.videoView_tv);
        mediacontroller = new MediaController(getActivity());
        mediacontroller.setAnchorView(videoView);
        videoView.setMediaController(mediacontroller);
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("API123", "What " + what + " extra " + extra);
                return false;
            }
        });

   //     youTubePlayerView = rootView.findViewById(R.id.youtube_player_view_other);
        video_music_toggle_button = rootView.findViewById(R.id.video_music_toggle_button);
        fullscreen_button = rootView.findViewById(R.id.fullscreen_button_lol);
        llNoCards = rootView.findViewById(R.id.llNoCards);
        playerView_ly = rootView.findViewById(R.id.playerView_ly);
//        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
//        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
//        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
//        youTubePlayerView.getPlayerUiController().showMenuButton(false);
//        youTubePlayerView.getPlayerUiController().showBufferingProgress(false);
//
//
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
                    startActivity(intent);
                }
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvTv.setLayoutManager(llm);
        rvTv.setItemAnimator(null);

        getSqorrTvData();
        return rootView;
    }

    private void getSqorrTvData() {
        recyclerViewItems.clear();
        tvPojoList.clear();
        if (getArguments() != null && getArguments().getSerializable("tvPojoList") != null) {
            tvPojoList = (List<TvPojo>) getArguments().getSerializable("tvPojoList");
            for (int i = 0; i < tvPojoList.size(); i++) {
                recyclerViewItems.add(tvPojoList.get(i));
            }

            if (recyclerViewItems.size() > 0) {
                llNoCards.setVisibility(View.GONE);
                playerView_ly.setVisibility(View.VISIBLE);
                recycleAdapter = new RecyclerViewAdapterNew(recyclerViewItems, getActivity());
                rvTv.setAdapter(recycleAdapter);
            } else {
                llNoCards.setVisibility(View.VISIBLE);
                playerView_ly.setVisibility(View.GONE);
                tvNoCards.setText("There are no " + getArguments().getString("type") + " videos right now");
            }
        } else {
            tvNoCards.setText("There are no " + getArguments().getString("type") + " videos right now");
            llNoCards.setVisibility(View.VISIBLE);
            playerView_ly.setVisibility(View.GONE);
        }

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
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    private void loadVideo(final YouTubePlayer youTubePlayer, String video_code, boolean getIsFeatured) {
        Log.v("VIDEO_CODE", video_code);
        video_id = video_code;
        youTubePlayer.loadVideo(video_code, 0f);
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


            switch (viewType) {
                case MENU_ITEM_VIEW_TYPE:
                    final MenuItemViewHolder listingView = (MenuItemViewHolder) holder;
                    final TvPojo tvCards = (TvPojo) recyclerViewItems.get(position);

                    listingView.tvVideoName.setText(tvCards.getTitle());


                    String currentString = tvCards.getUpdatedAt();
                    String[] separated = currentString.split("T");
//                    separated[0]; // this will contain "Fruit"
                    listingView.tvDate.setText(separated[0]);

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
                        if (isImageloaded) {
                            listingView.frame_playing.setVisibility(View.VISIBLE);
                            final Bitmap frame_bitmap = BitmapFactory.decodeResource(getContext().getResources(),
                                    R.drawable.frame_tv);
                            listingView.frame_playing.setBackground(convertTParellelogram(frame_bitmap, "xxx", getContext()));
                            setMargins(listingView.frame_playing, -60, 0, 0, 0);
                            listingView.is_playing_text.setVisibility(View.VISIBLE);
                        }
                        playVideo(listingView, tvCards.getUrl(), tvCards.getTitle(), tvCards.getDescription(), true);
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
                    } else {
                        listingView.frame_playing.setVisibility(View.GONE);
                        listingView.is_playing_text.setVisibility(View.GONE);
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
            }

        }


        private void playVideo(MenuItemViewHolder view, final String url, String title, String description, final boolean getIsFeatured) {
            tv_t.setText(title);
            tv_des.setText(description);
            String currentString = url;
            final String[] separated = currentString.split("=");
            tv_frame.setVisibility(View.VISIBLE);
            if (getIsFeatured) {
//                youTubePlayerView.setEnableAutomaticInitialization(false);
//                youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
//                    @Override
//                    public void onReady(YouTubePlayer youTubePlayer) {
//                        super.onReady(youTubePlayer);
//                        mYouTubePlayer = youTubePlayer;
//                        loadVideo(youTubePlayer, separated[1], getIsFeatured);
//                        isAlreadyInitialaised = true;
//                    }
//                });

                andExoPlayerView.setSource(currentString);
              /*  videoView.setVideoURI(Uri.parse(currentString));
                videoView.requestFocus();
                videoView.start();
//                isAlreadyInitialaised = true;
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                videoView.setMediaController(mediacontroller);
                                mediacontroller.setAnchorView(videoView);

                            }
                        });
                    }
                });
                */
            } else {
                andExoPlayerView.setSource(currentString);

                /*
                videoView.setVideoURI(Uri.parse(currentString));
                videoView.requestFocus();
                videoView.start();
//                isAlreadyInitialaised = true;
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                            @Override
                            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                                videoView.setMediaController(mediacontroller);
                                mediacontroller.setAnchorView(videoView);

                            }
                        });
                    }
                });*/

//                loadVideo(mYouTubePlayer, separated[1], false);
            }
        }

        class MenuItemViewHolder extends RecyclerView.ViewHolder {
            ImageView ivThumbnail;
            FrameLayout ivThumbnail_lay;
            ImageView frame_playing;
            LinearLayout llTotal;
            private TextView tvVideoName, tvDuration, is_playing_text,tvDate;
            private View cardColor;


            MenuItemViewHolder(View convertView) {
                super(convertView);
                tvVideoName = convertView.findViewById(R.id.tvVideoName);
                is_playing_text = convertView.findViewById(R.id.is_playing_text);
                ivThumbnail = convertView.findViewById(R.id.ivThumbnail);
                frame_playing = convertView.findViewById(R.id.frame_playing);
                llTotal = convertView.findViewById(R.id.llTotal);
                tvDuration = convertView.findViewById(R.id.tvDuration);
                cardColor = convertView.findViewById(R.id.cardColor);
                ivThumbnail_lay = convertView.findViewById(R.id.ivThumbnail_lay);
                tvDate = convertView.findViewById(R.id.tvDate);
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

}
