package com.sport.playsqorr.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.VideoView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.sport.playsqorr.R;
import com.sport.playsqorr.views.PlayWithCash;
import com.sport.playsqorr.views.Referfriend;
import com.sport.playsqorr.views.TvFullScreenVideo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;

import static com.sport.playsqorr.fragments.HomeFrag.timer;

public class Login_pageviwer extends PagerAdapter {

    LayoutInflater layoutInflater;
    private boolean isAlreadyInitialaised = false;
    Context context;
    String video_id;
    YouTubePlayerTracker youTubePlayerTracker;
    int[] images;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer mYouTubePlayer;
    Timer timer1;

    public Login_pageviwer(Context login_home, int[] images, Timer timer) {
        this.context = login_home;
        this.images = images;
        this.timer1 = timer;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(View container, final int position) {


        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_home_viewpager, null);
        ImageView imageView = view.findViewById(R.id.imageview_image);
        TextView thumimage_load = view.findViewById(R.id.hpwtoplay);
        TextView toptext = view.findViewById(R.id.toptext);
        if (position == 0) {
            thumimage_load.setText("How to Play Video");


        } else if (position == 1) {
            thumimage_load.setText("Scoring MLB cards");

        }  else if (position == 2) {
            thumimage_load.setText("How to score Golf");

        }else if (position == 3) {
            thumimage_load.setText("Scoring NFL cards");

        } else if (position == 4) {
            thumimage_load.setText("Scoring NHL cards");

        } else if (position == 5) {
            thumimage_load.setText("How to score soccer");

        }else if (position == 6) {
            toptext.setText("REFER A FRIEND");
            toptext.setCompoundDrawables(null,null,null,null);
            thumimage_load.setText("Get a Match up to $20");
        }else if (position == 7) {
            thumimage_load.setText("Scoring NBA cards");
        }

            imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position == 0) {
                    promoRedeemDialog1("https://www.youtube.com/watch?v=HmljRSEHm5M","HmljRSEHm5M");
                    timer.cancel();
                } else if (position == 1) {
                    promoRedeemDialog1("https://www.youtube.com/watch?v=iwxAvA6vS90","iwxAvA6vS90");
                    timer.cancel();

                } else if (position == 2) {
                    promoRedeemDialog_mp4("https://imgstore.azureedge.net/videos/howtoplay-golf.mp4","");
                    timer.cancel();
                } else if (position == 3) {
                    promoRedeemDialog1("https://www.youtube.com/watch?v=NNZjpoT4z2Q","NNZjpoT4z2Q");
                    timer.cancel();
                } else if (position == 4){
                    promoRedeemDialog1("https://www.youtube.com/watch?v=y5yWwFbGfF0","y5yWwFbGfF0");
                    timer.cancel();

                }else if (position == 5){
                    promoRedeemDialog1("https://www.youtube.com/watch?v=EL-jpyKUH1o","EL-jpyKUH1o");
                    timer.cancel();

                }else if (position == 6){

                    Intent intent = new Intent(context, Referfriend.class);
//                    intent.putExtra("SIGN_UP_BONUS","SIGNUPBONUS5");
                    context.startActivity(intent);

                    timer.cancel();

                }else if (position == 7){
                    promoRedeemDialog_mp4("https://imgstore.azureedge.net/videos/HowtoScore_NBA.mp4","");
                    timer.cancel();

                }



//                Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
        try {
            imageView.setImageResource(images[position]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }
    int index = 0;
    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList("https://imgstore.azureedge.net/videos/HowtoScore_NBA.mp4"));

    private void promoRedeemDialog_mp4(String surl, final String scode) {

        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));
        timer.cancel();


        final Dialog dialogView = new Dialog(context);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.dialog_promo_redeem1);
        dialogView.setCancelable(false);

        Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

        Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
        ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);
        ToggleButton fullscreen_button_lol = dialogView.findViewById(R.id.fullscreen_button_lol);
        fullscreen_button_lol.setVisibility(View.GONE);

        dialogView.show();

        Window window = dialogView.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialogView.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                if (dialogView.isShowing()) {
                    dialogView.dismiss();
                }


                timer = new Timer();

               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                dialogView.dismiss();


            }
        });



        final VideoView videoView = dialogView.findViewById(R.id.videoView);

        final MediaController mediacontroller = new MediaController(context);
        mediacontroller.setAnchorView(videoView);


        videoView.setMediaController(mediacontroller);
        videoView.setVideoURI(Uri.parse(surl));
//        videoView.setVideoURI(Uri.parse(arrayList.get(index)));
        videoView.requestFocus();
        videoView.start();
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

        /*videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(context, "Video over", Toast.LENGTH_SHORT).show();
                if (index++ == arrayList.size()) {
                    index = 0;
                    mp.release();
                    Toast.makeText(context, "Videos completed", Toast.LENGTH_SHORT).show();
                } else {
                    videoView.setVideoURI(Uri.parse(arrayList.get(index)));
                    videoView.start();
                }


            }
        });
        */


        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("API123", "What " + what + " extra " + extra);
                return false;
            }
        });

//        final MediaController mediacontroller = new MediaController(context);
//        mediacontroller.setAnchorView(videoView);
//
//
//        Toast.makeText(context,""+Uri.parse(surl),Toast.LENGTH_LONG).show();
//        videoView.setMediaController(mediacontroller);
//        videoView.setVideoURI(Uri.parse(surl));
//        videoView.requestFocus();





//        fullscreen_button_lol.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, TvFullScreenVideo.class);
//                intent.putExtra("video_id", scode);
//                intent.putExtra("video_time", "3.00");
//                context.startActivity(intent);
//            }
//        });
//
        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PlayWithCash.class);
//                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                if (dialogView.isShowing()) {
                    dialogView.dismiss();
                }

                timer = new Timer();
                dialogView.dismiss();
                intent.putExtra("referredby", "");
                context.startActivity(intent);
            }
        });
        youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
        youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
        youTubePlayerView.getPlayerUiController().showMenuButton(false);

        youTubePlayerView.setVisibility(View.GONE);

//            context.addObserver(youTubePlayerView);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.isShowing()) {
                    dialogView.dismiss();
                }

                dialogView.dismiss();
                youTubePlayerView.release();
//                youTubePlayerView.clearAnimation();
              /*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*/


            }
        });


    }
    private void promoRedeemDialog1(String surl, final String scode) {

        // dialogView.setBackgroundColor(getResources().getColor(R.color.transparent));
        timer.cancel();


        final Dialog dialogView = new Dialog(context);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setContentView(R.layout.dialog_promo_redeem1);
        dialogView.setCancelable(false);

        Button btnViewAllPromos = dialogView.findViewById(R.id.buttonViewAllPromotions);

        Button btnRedeem = dialogView.findViewById(R.id.btnRedeem);
        ImageView cancel = dialogView.findViewById(R.id.imageViewcancel);
        ToggleButton fullscreen_button_lol = dialogView.findViewById(R.id.fullscreen_button_lol);

        final VideoView videoView = dialogView.findViewById(R.id.videoView);
        videoView.setVisibility(View.GONE);

        dialogView.show();

        Window window = dialogView.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialogView.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnViewAllPromos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView.dismiss();
                if (dialogView.isShowing()) {
                    dialogView.dismiss();
                }


                timer = new Timer();

               /* Fragment PromoFragment = new PromosFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();//getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, PromoFragment); // give your fragment container id in first parameter
                fragmentTransaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                fragmentTransaction.commit();*/
                dialogView.dismiss();


            }
        });



        fullscreen_button_lol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TvFullScreenVideo.class);
                intent.putExtra("video_id", scode);
                intent.putExtra("video_time", "3.00");
                context.startActivity(intent);
            }
        });
        btnRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PlayWithCash.class);
//                intent.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                if (dialogView.isShowing()) {
                    dialogView.dismiss();
                }

                timer = new Timer();
                dialogView.dismiss();
                intent.putExtra("referredby", "");
                context.startActivity(intent);
            }
        });
        youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view_all);
        youTubePlayerView.getPlayerUiController().showFullscreenButton(false);
        youTubePlayerView.getPlayerUiController().showBufferingProgress(true);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        youTubePlayerView.getPlayerUiController().showVideoTitle(false);
        youTubePlayerView.getPlayerUiController().showMenuButton(false);

//            context.addObserver(youTubePlayerView);
        playVideo("", surl, "dd", "dd", true);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialogView.isShowing()) {
                    dialogView.dismiss();
                }

                dialogView.dismiss();
                youTubePlayerView.release();
//                youTubePlayerView.clearAnimation();
              /*  Intent in = new Intent(getActivity(), PlayWithCash.class);
//                in.putExtra("SIGN_UP_BONUS", "SIGNUPBONUS5");
                startActivity(in);*/


            }
        });


    }

    private void playVideo(String view, final String url, String title, String description, final boolean getIsFeatured) {

        String currentString = url;
        final String[] separated = currentString.split("=");
        youTubePlayerView.setEnableAutomaticInitialization(false);
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                mYouTubePlayer = youTubePlayer;
                YouTubePlayerTracker youTubePlayerTracker = new YouTubePlayerTracker();
                youTubePlayer.addListener(youTubePlayerTracker);
                loadVideo(youTubePlayer, separated[1], getIsFeatured);
                isAlreadyInitialaised = true;
            }
        });
        if (!isAlreadyInitialaised) {
            youTubePlayerView.setEnableAutomaticInitialization(false);
            youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    mYouTubePlayer = youTubePlayer;
                    YouTubePlayerTracker youTubePlayerTracker = new YouTubePlayerTracker();
                    youTubePlayer.addListener(youTubePlayerTracker);
                    loadVideo(youTubePlayer, separated[1], getIsFeatured);
                    isAlreadyInitialaised = true;
                }
            });
        } else {
            loadVideo(mYouTubePlayer, separated[1], false);
        }
    }

    private void loadVideo(final YouTubePlayer youTubePlayer, String video_code, boolean getIsFeatured) {
        Log.v("VIDEO_CODE", video_code);
        video_id = video_code;
        if (getIsFeatured) {
            youTubePlayer.loadVideo(video_code, 0f);
        } else {
            youTubePlayer.loadVideo(video_code, 0f);
        }
       /* video_music_toggle_button.setClickable(true);
        fullscreen_button.setClickable(true);*/
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}