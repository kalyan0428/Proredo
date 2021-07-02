package com.sport.playsqorr.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.model.NewPlayerStatistics;
import com.sport.playsqorr.model.StatsPlayerStatistics;
import com.sport.playsqorr.pojos.Matchup;
import com.sport.playsqorr.pojos.PlayerB;
import com.sport.playsqorr.views.CustomLinearLayoutManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


public class WinPlayShowNewPlayerListAdapter extends RecyclerView.Adapter<WinPlayShowNewPlayerListAdapter.PlayerHolder> {

    Context mContext;
    //    List<NewPlayerStatistics> mPlayersStatistics = new ArrayList<>();
    List<StatsPlayerStatistics> stats_ps = new ArrayList<>();

    boolean IsPurchased;
    private List<Matchup> matchups = new ArrayList<>();
    private HashMap<String, String> new_pick_index = new HashMap<>();
    private OnItemClick mCallback;
    PlayerB playerB;
    String values;
    private RecyclerView playerGridView;

    public WinPlayShowNewPlayerListAdapter(List<Matchup> matchups, PlayerB playerB, List<NewPlayerStatistics> mPlayersStatistics, List<StatsPlayerStatistics> stats_ps, boolean IsPurchased, Context mContext, RecyclerView playerGridView, OnItemClick mCallback, String values) {
//        this.mPlayersStatistics = mPlayersStatistics;
        this.stats_ps = stats_ps;
        this.mContext = mContext;
        this.IsPurchased = IsPurchased;
        this.matchups = matchups;
        this.mCallback = mCallback;
        this.playerB = playerB;
        this.values = values;
        this.playerGridView = playerGridView;
    }


    @NonNull
    @Override
    public PlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.winplayshowsingle_player_card, viewGroup, false);
        PlayerHolder viewHolder = new PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerHolder playerHolder, final int position) {

//        //Filling the playA details
//
//        if (matchups.get(position).getPlayerC() != null) {
//            playerHolder.player_price_start.setVisibility(View.GONE);
//            playerHolder.playerCard_start.setClickable(false);
//            playerHolder.playerCard_start.setEnabled(false);
//            Log.e("sssss--", "yes");
//
//
//            playerHolder.heads_s.setVisibility(View.VISIBLE);
//
//
//        }

        playerHolder.player_2.setText(matchups.get(position).getPlayerA().getFirstName());
        playerHolder.player_3.setText(matchups.get(position).getPlayerB().getFirstName());
        playerHolder.player_4.setText(matchups.get(position).getPlayerC().getFirstName());

//        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
//            playerHolder.playerCard_start.setClickable(false);
//            playerHolder.playerCard_start.setEnabled(false);
//
//        }

//        Log.e("ccccc", matchups.get(position).getPlayerC().getFirstName() + "---");


        try {
            if (values.equalsIgnoreCase("1")) {


// hoh

                if (matchups.get(position).getPlayerA().getPointSpread() != null) {
                    playerHolder.player_price_start.setText(matchups.get(position).getPlayerA().getPointSpread() + "");
                }
                if (matchups.get(position).getPlayerB().getPointSpread() != null) {
                    playerHolder.player_price_middle.setText(matchups.get(position).getPlayerB().getPointSpread() + "");
                }
                if (matchups.get(position).getPlayerC().getPointSpread() != null) {
                    playerHolder.player_price_last.setText(matchups.get(position).getPlayerC().getPointSpread() + "");
                }


                if (matchups.get(position).getPlayerA().getVenue() != null) {
                    playerHolder.player_Match_start.setVisibility(View.VISIBLE);
                    playerHolder.player_Match_start.setText(matchups.get(position).getPlayerA().getVenue());
                } else {
                    playerHolder.player_Match_start.setVisibility(View.INVISIBLE);
                }
                if (matchups.get(position).getPlayerA().getPlayerImage() != null) {
                    Picasso.with(mContext).load(matchups.get(position).getPlayerA().getPlayerImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(playerHolder.player_img_start);
                }
                if (matchups.get(position).getPlayerA().getGameDate() != null) {
                    String live = matchups.get(position).getPlayerA().getIsLive();
                    if (live.equalsIgnoreCase("true")) {
                        playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.live));
                        playerHolder.player_time1_start.setVisibility(View.VISIBLE);
                        playerHolder.player_time1_start.setText("Live");
                        playerHolder.player_time1_start.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                        playerHolder.player_time1_start.setTextColor(mContext.getResources().getColor(R.color.white));

                        playerHolder.player_time_start.setVisibility(View.GONE);
                        // playerHolder.play.setVisibility(View.VISIBLE);
                        playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                    } else {
                        playerHolder.player_time1_start.setVisibility(View.GONE);
                        playerHolder.player_time_start.setVisibility(View.VISIBLE);
                        //  playerHolder.play.setVisibility(View.GONE);
                        playerHolder.player_time_start.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                    }

                }
                //Filling the PlayerB details

//                    if (matchups.get(position).getPlayerC() != null) {
//                } else if (matchups.get(position).getPlayerstatus_C().equalsIgnoreCase("0")) {
                    playerHolder.playerCard_start.setClickable(false);
                    playerHolder.playerCard_middle.setClickable(false);
                    playerHolder.player_Name_last.setText(matchups.get(position).getPlayerC().getFirstName() + " " + matchups.get(position).getPlayerC().getLastName());

                    if (matchups.get(position).getPlayerC().getPlayerImage() != null) {
                        Picasso.with(mContext).load(matchups.get(position).getPlayerC().getPlayerImage())
                                .placeholder(R.drawable.game_inactive_placeholder)
                                .error(R.drawable.game_inactive_placeholder)
                                .transform(new Transformation() {
                                    @Override
                                    public Bitmap transform(Bitmap source) {
                                        return transformImg(source, R.color.hint);
                                    }

                                    @Override
                                    public String key() {
                                        return "circle";
                                    }
                                })
                                .into(playerHolder.player_img_last);

                    }

                    if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                        Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                .placeholder(R.drawable.game_inactive_placeholder)
                                .error(R.drawable.game_inactive_placeholder)
                                .transform(new Transformation() {
                                    @Override
                                    public Bitmap transform(Bitmap source) {
                                        return transformImg(source, R.color.hint);
                                    }

                                    @Override
                                    public String key() {
                                        return "circle";
                                    }
                                })
                                .into(playerHolder.player_img_middle);

                    }
                    if (matchups.get(position).getPlayerB().getPositonName() != null) {
                        playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                    }
                    if (matchups.get(position).getPlayerB().getPointSpread() != null) {
                        playerHolder.player_price_middle.setText(matchups.get(position).getPlayerB().getPointSpread() + "");
                    }
                    if (matchups.get(position).getPlayerB().getVenue() != null) {
                        playerHolder.player_Match_middle.setVisibility(View.VISIBLE);
                        playerHolder.player_Match_middle.setText(matchups.get(position).getPlayerB().getVenue());
                    } else {
                        playerHolder.player_Match_middle.setVisibility(View.INVISIBLE);
                    }
                    if (matchups.get(position).getPlayerC().getPositonName() != null) {
                        playerHolder.player_position_last.setText(matchups.get(position).getPlayerC().getPositonName());
                    }
                    if (matchups.get(position).getPlayerC().getPointSpread() != null) {
                        playerHolder.player_price_last.setText(matchups.get(position).getPlayerC().getPointSpread() + "");
                    }
                    if (matchups.get(position).getPlayerC().getVenue() != null) {
                        playerHolder.player_Match_last.setVisibility(View.VISIBLE);
                        playerHolder.player_Match_last.setText(matchups.get(position).getPlayerC().getVenue());
                    } else {
                        playerHolder.player_Match_last.setVisibility(View.INVISIBLE);
                    }

                    if (matchups.get(position).getPlayerB().getGameDate() != null) {
//                    playerHolder.player_time_start.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                        String live = matchups.get(position).getPlayerB().getIsLive();
                        if (live.equalsIgnoreCase("true")) {
                            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.live));
                            playerHolder.player_time_middle.setText("Live");
                            playerHolder.player_time_middle.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                            playerHolder.player_time_middle.setTextColor(mContext.getResources().getColor(R.color.white));


                            //playerHolder.play.setVisibility(View.VISIBLE);
                            playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                        } else {
                            //   playerHolder.play.setVisibility(View.GONE);
                            playerHolder.player_time_middle.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                        }
                    }

                    if (matchups.get(position).getPlayerC().getGameDate() != null) {
//                    playerHolder.player_time_start.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                        String live = matchups.get(position).getPlayerC().getIsLive();
                        if (live.equalsIgnoreCase("true")) {
                            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor(R.color.live));
                            playerHolder.player_time_last.setVisibility(View.VISIBLE);
                            playerHolder.player_time_last.setText("Live");
                            playerHolder.player_time_last.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                            playerHolder.player_time_last.setTextColor(mContext.getResources().getColor(R.color.white));

                            playerHolder.player_time_last.setVisibility(View.GONE);

                            //playerHolder.play.setVisibility(View.VISIBLE);
                            playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                        } else {
                            //   playerHolder.play.setVisibility(View.GONE);
                            playerHolder.player_time_last.setText(getTime(matchups.get(position).getPlayerC().getGameDate()));
                        }
                    }
                    //  }

                // else if ! c
                {
//                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
                  //      playerHolder.playerCard_middle.setVisibility(View.GONE);
                  //      playerHolder.playerCard_start.setClickable(false);
//                        playerHolder.playerCard_start.setEnabled(false);
                        if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null && !matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null)) {
                            String Name_A = matchups.get(position).getPlayerA().getFirstName();

                            if (Name_A.contains("(")) {
                                String[] separated = Name_A.split("\\(");
                                String fn = separated[0]; // this will contain "Fruit"
                                String vs = separated[1];

                                String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                                Log.e("314-1---aa", "" + fn + "--" + vs + "===" + separated_two);
                                if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.player_Name_start.setText(fn);
                                } else {
                                    playerHolder.player_Name_start.setText(fn + " " + matchups.get(position).getPlayerA().getLastName());
                                }

//                            playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.player_position_start.setText(separated_two);
                            } else {
                                Log.e("314--12--402aa", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.player_Name_start.setText( matchups.get(position).getPlayerA().getFirstName());
                                    playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
                                } else {
                                    playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
                                    playerHolder.player_Name_start.setText( matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());
                                }

                                playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.player_position_middle.setText(separated_two);
                            }


                        } else if (matchups.get(position).getPlayerA().getFirstName() != null) {
                            String Name_A = matchups.get(position).getPlayerA().getFirstName();

                            if (Name_A.contains("(")) {
                                String[] separated = Name_A.split("\\(");
                                String fn = separated[0]; // this will contain "Fruit"
                                String vs = separated[1];

                                String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                                Log.e("314--21- 426a-", "" + fn + "--" + vs + "===" + separated_two);

                                playerHolder.player_Name_start.setText(fn);
//                            playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.player_position_start.setText(separated_two);
                            } else {
                                Log.e("314--22-432 a-", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                playerHolder.player_Name_start.setText( matchups.get(position).getPlayerA().getFirstName());
                                playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.player_position_middle.setText(separated_two);
                            }
                        } else {

                            Log.e("314----", "0999999999999999");

                            playerHolder.player_Name_start.setText(matchups.get(position).getPlayerA().getLastName());

                        }

                     //   playerHolder.tvPlayPoints_.setText(matchups.get(position).getPlayerA().getPointSpread() + " ");

                 //   }

                    // else b 0
                    {
//                    } else if (matchups.get(position).getPlayerC() == null) {
                     //   playerHolder.playerCard_middle.setVisibility(View.VISIBLE);
                     //   playerHolder.playerCard_start.setClickable(true);

                        if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null && !matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null)) {
                            String Name_A = matchups.get(position).getPlayerA().getFirstName();

                            if (Name_A.contains("(")) {
                                String[] separated = Name_A.split("\\(");
                                String fn = separated[0]; // this will contain "Fruit"
                                String vs = separated[1];

                                String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                                Log.e("314-1---aa", "" + fn + "--" + vs + "===" + separated_two);
                                if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.player_Name_start.setText(fn);
                                } else {
                                    playerHolder.player_Name_start.setText(fn + " " + matchups.get(position).getPlayerA().getLastName());
                                }

//                            playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.player_position_start.setText(separated_two);
                            } else {
                                Log.e("314--12--402aa", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.player_Name_start.setText( matchups.get(position).getPlayerA().getFirstName());
                                    playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
                                } else {
                                    playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
                                    playerHolder.player_Name_start.setText( matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());
                                }

                                playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.player_position_middle.setText(separated_two);
                            }


                        } else if (matchups.get(position).getPlayerA().getFirstName() != null) {
                            String Name_A = matchups.get(position).getPlayerA().getFirstName();

                            if (Name_A.contains("(")) {
                                String[] separated = Name_A.split("\\(");
                                String fn = separated[0]; // this will contain "Fruit"
                                String vs = separated[1];

                                String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                                Log.e("314--21- 426a-", "" + fn + "--" + vs + "===" + separated_two);

                                playerHolder.player_Name_start.setText(fn);
//                            playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.player_position_start.setText(separated_two);
                            } else {
                                Log.e("314--22-432 a-", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                playerHolder.player_Name_start.setText( matchups.get(position).getPlayerA().getFirstName());
                                playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.player_position_middle.setText(separated_two);
                            }
                        } else {

                            Log.e("314----", "0999999999999999");

                            playerHolder.player_Name_start.setText(matchups.get(position).getPlayerA().getLastName());

                        }



                        if (matchups.get(position).getPlayerB().getFirstName() != null && matchups.get(position).getPlayerB().getLastName() != null && !matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase(null)) {
                            String Name_B = matchups.get(position).getPlayerB().getFirstName();

                            if (Name_B.contains("(")) {
                                String[] separated = Name_B.split("\\(");
                                String fn = separated[0]; // this will contain "Fruit"
                                String vs = separated[1];

                                String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                                Log.e("314-1--468B-", "" + fn + "--" + vs + "===" + separated_two);
                                if (matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.player_Name_middle.setText(fn);
                                } else {
                                    playerHolder.player_Name_middle.setText(fn + " " + matchups.get(position).getPlayerB().getLastName());
                                }

//                            playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.player_position_middle.setText(separated_two);
                            } else {
                                Log.e("314--12- 468b-", "" + Name_B + "-else-" + matchups.get(position).getPlayerB().getLastName());
                                if (matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.player_Name_middle.setText(matchups.get(position).getPlayerB().getFirstName());
                                } else {
                                    playerHolder.player_Name_middle.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                                }

                                playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
//                                playerHolder.player_position_middle.setText(separated_two);
                            }


                        } else if (matchups.get(position).getPlayerB().getFirstName() != null) {
                            String Name_B = matchups.get(position).getPlayerB().getFirstName();

                            if (Name_B.contains("(")) {
                                String[] separated = Name_B.split("\\(");
                                String fn = separated[0]; // this will contain "Fruit"
                                String vs = separated[1];

                                String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                                Log.e("314--21-b-", "" + fn + "--" + vs + "===" + separated_two);

                                playerHolder.player_Name_middle.setText(fn);
//                            playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.player_position_middle.setText(separated_two);
                            } else {
                                Log.e("314--22b--", "" + Name_B + "-else-" + matchups.get(position).getPlayerB().getLastName());
                                playerHolder.player_Name_middle.setText(Name_B);
                                playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
//                                playerHolder.player_position_middle.setText(separated_two);
                            }
                        } else {

                            Log.e("314--b--", "0999999999999999");

                            playerHolder.player_Name_middle.setText(matchups.get(position).getPlayerB().getLastName());

                        }

                        playerHolder.player_price_middle.setText(matchups.get(position).getPlayerB().getPointSpread() + "");
                        if (matchups.get(position).getPlayerB().getVenue() != null) {
                            playerHolder.player_Match_middle.setVisibility(View.VISIBLE);
                            playerHolder.player_Match_middle.setText(matchups.get(position).getPlayerB().getVenue());
                        } else {
                            playerHolder.player_Match_middle.setVisibility(View.INVISIBLE);
                        }

                        if (matchups.get(position).getPlayerB().getGameDate() != null) {
                            String live = matchups.get(position).getPlayerB().getIsLive();
                            if (live.equalsIgnoreCase("true")) {
                                playerHolder.player_time1_middle.setVisibility(View.VISIBLE);
                                //    playerHolder.play.setVisibility(View.VISIBLE);
                                playerHolder.player_time1_middle.setText("Live");
                                playerHolder.player_time1_middle.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                                playerHolder.player_time1_middle.setTextColor(mContext.getResources().getColor(R.color.white));


                                playerHolder.player_time_middle.setVisibility(View.GONE);
                                playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                            } else {
                                //     playerHolder.play.setVisibility(View.GONE);
                                playerHolder.player_time_middle.setVisibility(View.VISIBLE);
                                playerHolder.player_time_middle.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                            }
//                        playerHolder.player_time_middle.setText("Live");
                        }
                        if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .error(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(playerHolder.player_img_middle);

                        }
                    }
                }



            } else {

                // Values 2


                if (matchups.get(position).getPlayerA().getIsLive().equalsIgnoreCase("true"))//|| matchups.get(position).getPlayerB().getIsLive().equalsIgnoreCase("true"))
                {
                    // initApiCall();
                    System.out.println("outes");
                    System.out.println("fristnAME" + matchups.get(position).getPlayerA().getIsLive());

                    matchups.get(position).setDisplayStats(matchups.get(position).getDisplayStats());
                    //  matchups.get(position).getPlayerA().setLastName(matchups.get(position).getPlayerA().getFirstName());

                }
//                if (matchups.get(position).getPlayerB() != null) {
                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                    if (matchups.get(position).getPlayerB().getIsLive().equalsIgnoreCase("true")) {
                        //  initApiCall();
                        System.out.println("outes");
                        System.out.println("fristnAME" + matchups.get(position).getPlayerA().getIsLive());

                        matchups.get(position).setDisplayStats(matchups.get(position).getDisplayStats());
                        //   matchups.get(position).getPlayerB().setLastName(matchups.get(position).getPlayerB().getFirstName());

                    }
                }

            Log.d("fristname",matchups.get(position).getPlayerA()+matchups.get(position).getPlayerA().getFirstName()+matchups.get(position).getPlayerA().getPointSpread());
                if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.player_Name_start.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());

                } else if (matchups.get(position).getPlayerA().getFirstName() != null) {

                    playerHolder.player_Name_start.setText(matchups.get(position).getPlayerA().getFirstName());
                } else if (matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.player_Name_start.setText(matchups.get(position).getPlayerA().getLastName());
                }


                if (matchups.get(position).getPlayerA().getPositonName() != null) {
                    playerHolder.player_position_start.setText(matchups.get(position).getPlayerA().getPositonName());
                }


                if (matchups.get(position).getPlayerA().getPointSpread() != null) {
                    playerHolder.player_price_start.setText(matchups.get(position).getPlayerA().getPointSpread() + "");
                }
                if (matchups.get(position).getPlayerA().getVenue() != null) {
                    playerHolder.player_Match_start.setVisibility(View.VISIBLE);
                    playerHolder.player_Match_start.setText(matchups.get(position).getPlayerA().getVenue());
                } else {
                    playerHolder.player_Match_start.setVisibility(View.INVISIBLE);
                }
                if (matchups.get(position).getPlayerA().getPlayerImage() != null) {
                    Picasso.with(mContext).load(matchups.get(position).getPlayerA().getPlayerImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
                            .transform(new Transformation() {
                                @Override
                                public Bitmap transform(Bitmap source) {
                                    return transformImg(source, R.color.hint);
                                }

                                @Override
                                public String key() {
                                    return "circle";
                                }
                            })
                            .into(playerHolder.player_img_start);
                }


                ///
                if (matchups.get(position).getPlayerA().getGameDate() != null) {
//                    playerHolder.player_time_start.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                    String live = matchups.get(position).getPlayerA().getIsLive();
                    if (live.equalsIgnoreCase("true")) {
                        playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.live));
                        playerHolder.player_time1_start.setVisibility(View.VISIBLE);
                        playerHolder.player_time1_start.setText("Live");
                        playerHolder.player_time1_start.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                        playerHolder.player_time1_start.setTextColor(mContext.getResources().getColor(R.color.white));

                        playerHolder.player_time_start.setVisibility(View.GONE);

                        //playerHolder.play.setVisibility(View.VISIBLE);
                        playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                    } else {
                        playerHolder.player_time1_start.setVisibility(View.GONE);
                        playerHolder.player_time_start.setVisibility(View.VISIBLE);
                        //   playerHolder.play.setVisibility(View.GONE);
                        playerHolder.player_time_start.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                    }
                }
                playerHolder.player_time_start.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                //Filling the PlayerB details
// action this


                if (matchups.get(position).getPlayerC() != null) {
                    {
                        playerHolder.playerCard_start.setClickable(false);
                        playerHolder.player_Name_last.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());

                        if (matchups.get(position).getPlayerC().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerC().getPlayerImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .error(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(playerHolder.player_img_last);

                        }

                        if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .error(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(playerHolder.player_img_middle);

                        }


                    }
                } else {
                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
                       playerHolder.playerCard_start.setClickable(false);



                    } else {

                        playerHolder.playerCard_start.setClickable(true);
                        playerHolder.playerCard_start.setEnabled(true);
                        playerHolder.player_Name_middle.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                        playerHolder.player_position_middle.setText(matchups.get(position).getPlayerB().getPositonName());
                        playerHolder.player_price_middle.setText(matchups.get(position).getPlayerB().getPointSpread() + "");
                        if (matchups.get(position).getPlayerB().getVenue() != null) {
                            playerHolder.player_Match_middle.setText(matchups.get(position).getPlayerB().getVenue());
                        } else {
                        }
                        if (matchups.get(position).getPlayerB().getGameDate() != null) {
                            String live = matchups.get(position).getPlayerB().getIsLive();
                            if (live.equalsIgnoreCase("true")) {
                                playerHolder.player_time1_middle.setVisibility(View.VISIBLE);
                                //   playerHolder.play.setVisibility(View.VISIBLE);
                                playerHolder.player_time1_middle.setText("Live");
                                playerHolder.player_time1_middle.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                                playerHolder.player_time1_middle.setTextColor(mContext.getResources().getColor(R.color.white));


                                playerHolder.player_time_middle.setVisibility(View.GONE);
                                playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                            } else {
                                //         playerHolder.play.setVisibility(View.GONE);
                                playerHolder.player_time_middle.setVisibility(View.VISIBLE);
                                playerHolder.player_time_middle.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                            }
//                        playerHolder.player_time_middle.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                        }
                        if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                    .placeholder(R.drawable.game_inactive_placeholder)
                                    .error(R.drawable.game_inactive_placeholder)
                                    .transform(new Transformation() {
                                        @Override
                                        public Bitmap transform(Bitmap source) {
                                            return transformImg(source, R.color.hint);
                                        }

                                        @Override
                                        public String key() {
                                            return "circle";
                                        }
                                    })
                                    .into(playerHolder.player_img_middle);

                        }
                    }
                }


            }
        } catch (Exception e) {

        }



/*
        Checking IsPurchased or not, if purchase player cound't clickble else can clickble
*/
        //if IsPurchased true
        /*
Checking picindex and win index of player A and PLayer B  DONE
*/
        Log.e("771--test:: ",IsPurchased + "");
        if (IsPurchased) {





            try {


                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
                if (matchups.get(position).getIsFinished()) {
/*
                    // if winindex=0,pickindex=0 playerA checked Green
                    if (matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == 0) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        playerHolder.statusTxt.setText("WIN");

//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_b, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
//                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                playerHolder.player_img_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.playerCheck_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));


                            } else {
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            }
                        }

                    }
                    //  if winindex=1,pickindex=1 playerB checked Green
                      if (matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == 1) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        playerHolder.statusTxt.setText("WIN");
//                        if (matchups.get(position).getPlayerB() != null) {

                        if (matchups.get(position).getPlayerC() != null) {
                            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.playerCheck_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));


                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.playerCheck_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                                playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            }
                        }

                    }
                    // if winindex=0,pickindex=1 or winindex=0,pickindex=-1   playerA checked gray
                    if ((matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == 1)) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        playerHolder.statusTxt.setText("LOST");
//                        if (matchups.get(position).getPlayerB() != null) {

                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_b, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
//                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                Log.e("bnull", "not null---");
                                playerHolder.player_img_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.playerCheck_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));


                            } else {
                                Log.e("bnullnot", "null---");
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                            }
                        }

                    }
                    if ((matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == -1) && matchups.get(position).getIsCancelled()) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        if (matchups.get(position).getCancelledReason() != null) {
                            playerHolder.statusTxt.setText(matchups.get(position).getCancelledReason());
                        } else {
                            playerHolder.statusTxt.setText("CANCELLED");
                        }


                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                        } else {
//                            if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                playerHolder.player_img_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.playerCheck_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            }
                        }
//

                    }
                    if ((matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == -1) && matchups.get(position).getIsCancelled()) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        if (matchups.get(position).getCancelledReason() != null) {
                            playerHolder.statusTxt.setText(matchups.get(position).getCancelledReason());
                        } else {
                            playerHolder.statusTxt.setText("CANCELLED");
                        }


                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                        } else {
//                        if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.playerCheck_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            } else {
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));

                            }
                        }

                    }


                  //    if winindex=1,pickindex=0 or winindex=1,pickindex=-1   playerB checked gray
                    if (matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == 0) {


                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        playerHolder.statusTxt.setText("LOST");

                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.playerCheck_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
//                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
//                        if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.playerCheck_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));

                            }
                        }


                    }

                   */
                } else {

                    Log.e("277---", "277----not played kalyan played");
                    //    if played false,pickindex=0 playerA checked


                    if (matchups.get(position).getPickIndex() == 0) {

//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerC() != null) {
//                        if (matchups.get(position).getPlayerstatus_C().equalsIgnoreCase("1")) {
//                        } else if (matchups.get(position).getPlayerC() != null){
                            // playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.playerCheck_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            playerHolder.playerCard_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            setTextViewDrawableColor(playerHolder.player_Name_middle, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//                                playerHolder.player_img_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                playerHolder.playerCheck_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
                                playerHolder.playerCard_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
//                            } else {
//                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            }
                        }

                    }

                    /*
                    //  if played =flase,pickindex=1 playerB checked
                    if (matchups.get(position).getPickIndex() == 1) {

                        Log.e("277---", "0101");

                        if (matchups.get(position).getPlayerC() != null) {
//                        if (matchups.get(position).getPlayerstatus_C().equalsIgnoreCase("1")) {
                            Log.e("277---", "0102");
//                        } else if (matchups.get(position).getPlayerC() != null){
//                            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.playerCard_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            playerHolder.playerCheck_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
                            setTextViewDrawableColor(playerHolder.player_Name_last, mContext.getResources().getColor(R.color.text_color_new));
                        } else {
                            Log.e("277---", "0103");
//                        if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                Log.e("277---", "0104");
//                                playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                playerHolder.playerCard_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                                playerHolder.playerCheck_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                Log.e("277---", "0105");
//                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//                                playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                                playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            }
                        }
                    }

                    if (matchups.size() > 0) {

                        Log.e("277---", matchups.get(position).getPlayerA().getFirstName() + "277----not played" + matchups.get(position).getPlayerB().getFirstName());
                        //   if played false,pickindex=0 playerA checked
                        if (matchups.get(position).getPickIndex() == 0) {

//                        Toast.makeText(mContext,"533--"+matchups.get(position).getPickIndex(),Toast.LENGTH_LONG).show();
                            if (matchups.get(position).getPlayerB() != null) {
                                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            Toast.makeText(mContext,"535--",Toast.LENGTH_LONG).show();
                                    playerHolder.player_img_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.playerCheck_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                    playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
                                } else {
//                            Toast.makeText(mContext,"541-null-",Toast.LENGTH_LONG).show();
                                    setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                                    playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                }
                            }
                        }

                        //  if played =flase,pickindex=1 playerB checked
                        if (matchups.get(position).getPickIndex() == 1) {
//                        Toast.makeText(mContext,"551--"+position,Toast.LENGTH_LONG).show();
                            if (matchups.get(position).getPlayerB() != null) {
                                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                          Toast.makeText(mContext,"557--",Toast.LENGTH_LONG).show();
                                    playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.playerCheck_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                    playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
                                } else {
//                            Toast.makeText(mContext,"559--",Toast.LENGTH_LONG).show();
                                    setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                    playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                }
                            }
                        }
                    }
*/


                }

            } catch (Exception e) {
            }
        } else {
            playerHolder.win_loss_statusView_ly.setVisibility(View.GONE);

            playerHolder.playerCard_start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // if (playerHolder.no_player.getVisibility() == View.GONE || playerHolder.no_player_abc.getVisibility() == View.GONE) {
                        mHilightViews(playerHolder, 1, position);
                 //   }

//                    if (playerHolder.no_player_abc.getVisibility() == View.GONE) {
//                        mHilightViews(playerHolder, 1, position);
//                    }
                }
            });

            playerHolder.playerCard_middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 2, position);
                }
            });

            playerHolder.playerCard_last.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 3, position);
                }
            });

            /*playerHolder.over_playercard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 3, position);
                }
            });

            playerHolder.under_playercard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 4, position);
                }
            });


            playerHolder.over_playercard_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 5, position);
                }
            });

            playerHolder.over_playercard_c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 6, position);
                }
            });
*/
        }

//            playerHolder.playerstatsViewSpinner.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    playerHolder.playerStatisticsView.setVisibility(playerHolder.playerStatisticsView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//                    if (playerHolder.playerStatisticsView.getVisibility() == View.VISIBLE) {
//                        playerHolder.arrow.setRotation(180);
//                    } else {
//                        playerHolder.arrow.setRotation(360);
//                    }
//                }
//            });

        playerHolder.playerstatsViewSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerHolder.playerStatisticsView.setVisibility(playerHolder.playerStatisticsView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                if (playerHolder.playerStatisticsView.getVisibility() == View.VISIBLE) {
                    playerHolder.arrow.setRotation(180);
                } else {
                    playerHolder.arrow.setRotation(360);
                }
                int size = matchups.size();
                System.out.println("size" + size + " " + position);
                int values = position + 1;
                if (size == values) {
                    System.out.println("size" + size + position);
//                        int values=position+1;
                    playerGridView.scrollToPosition(values);
                    playerGridView.scrollTo(position, values);
//                    playerGridView.setSelection(scorll);
                    playerGridView.setVerticalScrollbarPosition(values);
                    playerGridView.smoothScrollToPosition(values);
                }

            }
        });

//             NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext);
////        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
//            CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
//            playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);
//
//            /// ststsus
//            playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);

//        if (matchups.get(position).getPlayerB() != null) {

        if (matchups.get(position).getPlayerC() != null) {
            NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext, "3");
//        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
            CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
            playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);

            /// ststsus
            playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);

        } else {
            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext, "1");
//        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
                CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
                playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);

                /// ststsus
                playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);
            } else {
                NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(matchups.get(position).getDisplayStats(), mContext, "2");
//        NewPLayerStatisticsAdapter pLayerStatisticsAdapter = new NewPLayerStatisticsAdapter(stats_ps, mContext);
                CustomLinearLayoutManager gridLayoutManager = new CustomLinearLayoutManager(mContext);
                playerHolder.playerStatsRcView_child.setLayoutManager(gridLayoutManager);

                /// ststsus
                playerHolder.playerStatsRcView_child.setAdapter(pLayerStatisticsAdapter);
            }
        }


    }

    private String getTime(String leagueTime) {
        String time = null;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        f.setTimeZone(TimeZone.getTimeZone("EST"));
        String currentDate = f.format(new Date()).toString();
        String[] currentTime = currentDate.split(" ");
        String[] hoursMIns = currentTime[1].split(":");
        int curHour = Integer.parseInt(hoursMIns[0]);
        int curMins = Integer.parseInt(hoursMIns[1]);
        if (leagueTime != null && !leagueTime.equals("")) {
            String[] timeArray = leagueTime.split("T");
            if (timeArray.length > 0) {
                String lg_time = timeArray[1];
                String[] hourArray = lg_time.split("\\.");
                if (hourArray.length > 0) {
                    String[] final_time = hourArray[0].split(":");

                    if (final_time.length > 0) {
                        int lg_hour = Integer.parseInt(final_time[0]);
                        int lg_mins = Integer.parseInt(final_time[1]);

                        int diffHours = lg_hour - curHour;
                        int diffMins = lg_mins - curMins;
                        if (diffHours > 0) {
                            if (diffMins > 0)
                                time = diffHours + "h " + diffMins + "m";
                            else
                                time = diffHours + "h";
                        } else {
                            if (diffMins > 0)
                                time = diffMins + "m";
                        }
                    }
                }
            }
        } else {
            time = "";
        }

        return time;
    }


    private void mHilightViews(PlayerHolder playerHolder, int from, int position) {
        if (from == 1) {
            if (playerHolder.playerCheck_start.isChecked()) {
                // PLAYER A REMOVED
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.playerCheck_start.setChecked(false);
                setFirstPlayerTextEnableDisable(playerHolder, false);
                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {
                if (playerHolder.playerCheck_middle.isChecked()  ) {
                    //PLAYER B REMOVED
                    playerHolder.playerCheck_middle.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setSecondPlayerTextDisableEnableMode(playerHolder, false);
                }else if (playerHolder.playerCheck_last.isChecked()  ) {
                    //PLAYER C REMOVED
                    playerHolder.playerCheck_last.setChecked(false);

                    new_pick_index.remove(matchups.get(position).get_id());
                    setThirdPlayerTextDisableEnableMode(playerHolder, false);

                }
                //PLAYER A ADDED
                playerHolder.playerCheck_start.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setFirstPlayerTextEnableDisable(playerHolder, true);
            }
        } else if (from == 2) {
            if (playerHolder.playerCheck_middle.isChecked()) {
                //PLAYER B REMOVED
                playerHolder.playerCheck_middle.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setSecondPlayerTextDisableEnableMode(playerHolder, false);
                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {

                if (playerHolder.playerCheck_start.isChecked()) {
                    // PLAYER A REMOVED
                    playerHolder.playerCheck_start.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setFirstPlayerTextEnableDisable(playerHolder, false);
                }else if (playerHolder.playerCheck_last.isChecked()) {
                    // PLAYER C REMOVED
                    playerHolder.playerCheck_last.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setThirdPlayerTextDisableEnableMode(playerHolder, false);
                }
                //PLAYER B ADDED
                playerHolder.playerCheck_middle.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setSecondPlayerTextDisableEnableMode(playerHolder, true);
            }
        } else if (from == 3) {
            if (playerHolder.playerCheck_last.isChecked()) {
                //PLAYER B REMOVED
                playerHolder.playerCheck_last.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setThirdPlayerTextDisableEnableMode(playerHolder, false);
                playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {
                if (playerHolder.playerCheck_start.isChecked()) {
                    // PLAYER A REMOVED
                    playerHolder.playerCheck_start.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setFirstPlayerTextEnableDisable(playerHolder, false);
                }else if (playerHolder.playerCheck_middle.isChecked()) {
                    // PLAYER A REMOVED
                    playerHolder.playerCheck_middle.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setSecondPlayerTextDisableEnableMode(playerHolder, false);
                }
                //PLAYER B ADDED
                playerHolder.playerCheck_last.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "2");
                setThirdPlayerTextDisableEnableMode(playerHolder, true);
//                if (playerHolder.playerCheck_start.isChecked()) {
//                    // PLAYER A REMOVED
//                    playerHolder.playerCheck_start.setChecked(false);
//                    new_pick_index.remove(matchups.get(position).get_id());
//                    setFirstPlayerTextEnableDisable(playerHolder, false);
//                }
                //PLAYER B ADDED
//                playerHolder.playerCheck_last.setChecked(true);
//                new_pick_index.put(matchups.get(position).get_id(), "1");
//                setThirdPlayerTextDisableEnableMode(playerHolder, true);
            }


            /*
            if (playerHolder.overCheckbox.isChecked()) {
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.overCheckbox.setChecked(false);
                setAPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                if (playerHolder.underCheckbox.isChecked()) {
                    new_pick_index.remove(matchups.get(position).get_id());
                    playerHolder.underCheckbox.setChecked(false);
                    setBPlayerTextDisableEnableMode(playerHolder, false);
                }
                playerHolder.overCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setAPlayerTextDisableEnableMode(playerHolder, true);
            }*/
        } else if (from == 4) {
            /*if (playerHolder.underCheckbox.isChecked()) {

                playerHolder.underCheckbox.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setBPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                if (playerHolder.overCheckbox.isChecked()) {
                    playerHolder.overCheckbox.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setAPlayerTextDisableEnableMode(playerHolder, false);
                }

                playerHolder.underCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setBPlayerTextDisableEnableMode(playerHolder, true);
            }*/
        } else if (from == 5) {
            /*if (playerHolder.playerCheck_b.isChecked()) {
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.playerCheck_b.setChecked(false);
                setABPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                if (playerHolder.playerCheck_c.isChecked()) {
                    new_pick_index.remove(matchups.get(position).get_id());
                    playerHolder.playerCheck_c.setChecked(false);
                    setACPlayerTextDisableEnableMode(playerHolder, false);
                }
                playerHolder.playerCheck_b.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setABPlayerTextDisableEnableMode(playerHolder, true);
            }*/
        } else if (from == 6) {
          /*  if (playerHolder.playerCheck_c.isChecked()) {

                playerHolder.playerCheck_c.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setACPlayerTextDisableEnableMode(playerHolder, false);
            } else {
                if (playerHolder.playerCheck_b.isChecked()) {
                    playerHolder.playerCheck_b.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setABPlayerTextDisableEnableMode(playerHolder, false);
                }

                playerHolder.playerCheck_c.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setACPlayerTextDisableEnableMode(playerHolder, true);
            }*/
        }
    }

    private void setABPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
//        if (enable) {
//            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));
//        } else {
//            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
//            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
//        }
//        mCallback.onClick(new_pick_index);

        if (enable) {
            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.player_Name_middle, mContext.getResources().getColor(R.color.text_color_new));

        } else {
            playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);
    }


    private void setACPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
       /* if (enable) {
            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));

        }
        mCallback.onClick(new_pick_index);*/

        if (enable) {
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.player_img_last.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.player_Name_last, mContext.getResources().getColor(R.color.text_color_new));
        } else {
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.player_img_last.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));

        }
        mCallback.onClick(new_pick_index);


    }

    //bb

    /*private void setAPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);
    }


    private void setBPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));
        } else {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));

        }
        mCallback.onClick(new_pick_index);


    }*/

    private void setFirstPlayerTextEnableDisable(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            //Kalyan  s
            playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.rr_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.player_Name_start, mContext.getResources().getColor(R.color.text_color_new));

        } else {
            playerHolder.player_img_start.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
//            playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor(R.color.hint));
            playerHolder.rr_start.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
        mCallback.onClick(new_pick_index);

    }

    private void setSecondPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor((R.color.hint)));
//            playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.player_Name_middle, mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.rr_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
        } else {
//            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.rr_middle.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }
        mCallback.onClick(new_pick_index);


    }

    private void setThirdPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable) {
        if (enable) {
            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.player_Name_start.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.player_Name_middle.setTextColor(mContext.getResources().getColor((R.color.hint)));
//            playerHolder.player_img_middle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.player_Name_middle, mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.rr_last.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
        } else {
//            playerHolder.player_Name_last.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.player_img_last.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.rr_last.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }
        mCallback.onClick(new_pick_index);


    }


    @Override
    public int getItemCount() {
        return matchups.size();
    }


    @SuppressLint("NewApi")
    public Bitmap transformImg(Bitmap source, int color) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r1 = size / 2f;
        canvas.drawCircle(r1, r1, r1, paint);
        //border code
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(mContext.getColor(color));
        paint.setStrokeWidth(1f);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @SuppressLint("NewApi")
    private void setTextViewDrawableColor(TextView textView, int color) {
        textView.setCompoundDrawableTintList(ColorStateList.valueOf(color));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public interface OnItemClick {
        void onClick(HashMap<String, String> matchup_selections);
    }

    public class PlayerHolder extends RecyclerView.ViewHolder {

        //1St PLayer
        ImageView player_img_start;
        TextView player_Name_start;
        TextView player_time_start;
        TextView player_position_start;
        TextView player_Match_start;
        Button player_price_start;
        CardView playerCard_start;
        CheckBox playerCheck_start;
        TextView player_time1_start;
        RelativeLayout rr_start;

        //2nd player
        ImageView player_img_middle;
        TextView player_Name_middle;
        TextView player_time_middle;
        TextView player_position_middle;
        TextView player_Match_middle;
        Button player_price_middle;
        CardView playerCard_middle;
        CheckBox playerCheck_middle;
        TextView player_time1_middle;
        RelativeLayout rr_middle;


        //3rd player
        ImageView player_img_last;
        TextView player_Name_last;
        TextView player_time_last;
        TextView player_position_last;
        TextView player_Match_last;
        Button player_price_last;
        CardView playerCard_last;
        CheckBox playerCheck_last;
        TextView player_time1_last;
        RelativeLayout rr_last;

        //Player statistics
        CardView playerstatsViewSpinner;
        LinearLayout playerStatisticsView;
        ImageView arrow;
        RecyclerView playerStatsRcView_child;

//        //B player off
//        CheckBox overCheckbox;
//        CheckBox underCheckbox;
//        RelativeLayout under_playercard;
//        RelativeLayout over_playercard;
//        TextView overview_txt;
//        TextView underView_txt;
//        LinearLayout overView;
//        LinearLayout underView;
//        CardView no_player, no_player_abc;
//        TextView tvPlayPoints;

        /*win loss status View*/
        FrameLayout win_loss_statusView_ly;
        CardView win_loss_statusView;
        TextView statusTxt;
        //  TextView play;

        //        AB
//        TextView player_Name_b, player_position_b, player_Match_b, player_time_b, player_price_b;
//        CheckBox playerCheck_b;
//        RelativeLayout over_playercard_b;
//        ImageView player_img_b;


        //        AC
//        ImageView player_img_c;
//        TextView player_Name_c, player_position_c, player_Match_c, player_time_c, player_price_c;
//        CheckBox playerCheck_c;
//        RelativeLayout over_playercard_c;


        LinearLayout heads_s;
        TextView player_4, player_2, player_3;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            // PLAYER A start
            this.player_time1_start = (TextView) itemView.findViewById(R.id.player_time1_start);
            this.player_img_start = (ImageView) itemView.findViewById(R.id.player_img_start);
            this.player_Name_start = (TextView) itemView.findViewById(R.id.player_Name_start);
            this.player_position_start = (TextView) itemView.findViewById(R.id.player_position_start);
            this.player_Match_start = (TextView) itemView.findViewById(R.id.player_Match_start);
            this.player_time_start = (TextView) itemView.findViewById(R.id.player_time_start);
            this.player_price_start = (Button) itemView.findViewById(R.id.player_price_start);
            this.playerCard_start = (CardView) itemView.findViewById(R.id.playerCard_start);
            this.rr_start = (RelativeLayout) itemView.findViewById(R.id.rr_start);
            this.playerCheck_start = (CheckBox) itemView.findViewById(R.id.playerCheck_start);

            // PLAYER B Middle
            this.player_img_middle = (ImageView) itemView.findViewById(R.id.player_img_middle);
            this.player_Name_middle = (TextView) itemView.findViewById(R.id.player_Name_middle);
            this.player_position_middle = (TextView) itemView.findViewById(R.id.player_position_middle);
            this.player_Match_middle = (TextView) itemView.findViewById(R.id.player_Match_middle);
            this.player_time_middle = (TextView) itemView.findViewById(R.id.player_time_middle);
            this.player_price_middle = (Button) itemView.findViewById(R.id.player_price_middle);
            this.playerCard_middle = (CardView) itemView.findViewById(R.id.playerCard_middle);
            this.playerCheck_middle = (CheckBox) itemView.findViewById(R.id.playerCheck_middle);
            this.player_time1_middle = (TextView) itemView.findViewById(R.id.player_time1_middle);
            this.rr_middle = (RelativeLayout) itemView.findViewById(R.id.rr_middle);



            // PLAYER C Last
            this.player_img_last = (ImageView) itemView.findViewById(R.id.player_img_last);
            this.player_Name_last = (TextView) itemView.findViewById(R.id.player_Name_last);
            this.player_position_last = (TextView) itemView.findViewById(R.id.player_position_last);
            this.player_Match_last = (TextView) itemView.findViewById(R.id.player_Match_last);
            this.player_time_last = (TextView) itemView.findViewById(R.id.player_time_last);
            this.player_price_last = (Button) itemView.findViewById(R.id.player_price_last);
            this.playerCard_last = (CardView) itemView.findViewById(R.id.playerCard_last);
            this.playerCheck_last = (CheckBox) itemView.findViewById(R.id.playerCheck_last);
            this.player_time1_last = (TextView) itemView.findViewById(R.id.player_time1_last);
            this.rr_last = (RelativeLayout) itemView.findViewById(R.id.rr_last);



            //Players ststistics
            this.playerstatsViewSpinner = (CardView) itemView.findViewById(R.id.playerstatsViewSpinner);
            this.playerStatisticsView = (LinearLayout) itemView.findViewById(R.id.playerStatisticsView_m);
            this.arrow = (ImageView) itemView.findViewById(R.id.arrow);
            this.playerStatsRcView_child = (RecyclerView) itemView.findViewById(R.id.playerStatsRcView_child);

            //PlayerB OFF



            this.win_loss_statusView_ly = (FrameLayout) itemView.findViewById(R.id.win_loss_statusView_ly);
            this.win_loss_statusView = (CardView) itemView.findViewById(R.id.win_loss_statusView);
            this.statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            //  this.play=(TextView) itemView.findViewById(R.id.play);


       //     this.no_player_abc = (CardView) itemView.findViewById(R.id.no_player_abc);




            this.heads_s = itemView.findViewById(R.id.heads_s);
            this.player_2 = itemView.findViewById(R.id.player_2);
            this.player_3 = itemView.findViewById(R.id.player_3);
            this.player_4 = itemView.findViewById(R.id.player_4);

        }

    }

}
