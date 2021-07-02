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

//import static com.sport.playsqorr.views.MatchupScreen.initApiCall;

public class PlayAPickNewPlayerListAdapter extends RecyclerView.Adapter<PlayAPickNewPlayerListAdapter.PlayerHolder> {

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

    public PlayAPickNewPlayerListAdapter(List<Matchup> matchups, PlayerB playerB, List<NewPlayerStatistics> mPlayersStatistics, List<StatsPlayerStatistics> stats_ps, boolean IsPurchased, Context mContext, RecyclerView playerGridView, OnItemClick mCallback, String values) {
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
        View listItem = layoutInflater.inflate(R.layout.playapick_single_player_card, viewGroup, false);
        PlayerHolder viewHolder = new PlayerHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerHolder playerHolder, final int position) {

        //Filling the playA details

        if (matchups.get(position).getPlayerC() != null) {
            playerHolder.first_player_price.setVisibility(View.GONE);
            playerHolder.first_playerCard.setClickable(false);
            playerHolder.first_playerCard.setEnabled(false);
            Log.e("sssss--", "yes");


            playerHolder.heads_s.setVisibility(View.VISIBLE);
            playerHolder.player_2.setText(matchups.get(position).getPlayerA().getFirstName());
            playerHolder.player_3.setText(matchups.get(position).getPlayerB().getFirstName());
            playerHolder.player_4.setText(matchups.get(position).getPlayerC().getFirstName());


        }


        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
            playerHolder.first_playerCard.setClickable(false);
            playerHolder.first_playerCard.setEnabled(false);

            playerHolder.first_player_price.setVisibility(View.GONE);

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }

//        Log.e("ccccc", matchups.get(position).getPlayerC().getFirstName() + "---");
        try {
            if (values.equalsIgnoreCase("1")) {


//                if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null) {
//                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());
//
//                } else if (matchups.get(position).getPlayerA().getFirstName() != null) {
//
//                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
//                } else if (matchups.get(position).getPlayerA().getLastName() != null) {
//                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());
//                }
//
//                if (matchups.get(position).getPlayerA().getPositonName() != null) {
//                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                }


              /*  if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null && !matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null)) {
                    String Name_A = matchups.get(position).getPlayerA().getFirstName();

                    if (Name_A.contains("(")) {
                        String[] separated = Name_A.split("\\(");
                        String fn = separated[0]; // this will contain "Fruit"
                        String vs = separated[1];

                        String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                        Log.e("314-1---", "" + fn + "--" + vs + "===" + separated_two);
                        if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                            playerHolder.first_player_Name.setText(fn);
                        } else {
                            playerHolder.first_player_Name.setText(fn + " " + matchups.get(position).getPlayerA().getLastName());
                        }

//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                        playerHolder.first_player_position.setText(separated_two);
                    } else {
                        Log.e("314--12--", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                        if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                            playerHolder.first_player_Name.setText(Name_A);
                            playerHolder.first_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                        } else {
                            playerHolder.first_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                            playerHolder.first_player_Name.setText(Name_A + " " + matchups.get(position).getPlayerB().getLastName());
                        }

                        playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
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
                        Log.e("314--21--", "" + fn + "--" + vs + "===" + separated_two);

                        playerHolder.first_player_Name.setText(fn);
//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                        playerHolder.first_player_position.setText(separated_two);
                    } else {
                        Log.e("314--22--", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                        playerHolder.first_player_Name.setText(Name_A);
                        playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
                    }
                } else {

                    Log.e("314----", "0999999999999999");

                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());

                }*/


// hoh
              /*  if (position == 0) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
                }

                if (position == 1) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
                }

                if (position == 2) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
                }

                if (position == 3) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p3));

                    playerHolder.first_player_price.setTextColor(mContext.getResources().getColor(R.color.black));
                    playerHolder.second_player_price.setTextColor(mContext.getResources().getColor(R.color.black));
                    playerHolder.tvPlayPoints.setTextColor(mContext.getResources().getColor(R.color.black));

                }

                if (position == 4) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
                }

                if (position == 5) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
                }

                if (position == 6) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
                }

                if (position == 7) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
                }

                if (position == 8) {
                    playerHolder.first_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                    playerHolder.second_player_price.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                    playerHolder.tvPlayPoints.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
                }
*/
                if (matchups.get(position).getPlayerA().getPointSpread() != null) {


                    playerHolder.first_player_price.setText(((matchups.get(position).getHandicap()) * (-1)) + "");

                }
                if (matchups.get(position).getPlayerA().getUniformNumber() != null) {
                    playerHolder.first_player_place.setText(" # " + ((matchups.get(position).getPlayerA().getUniformNumber())) + "");
                }

                if (matchups.get(position).getPlayerA().getVenue() != null) {
                    playerHolder.first_player_Match.setVisibility(View.VISIBLE);
                    playerHolder.first_player_Match.setText(matchups.get(position).getPlayerA().getVenue());
                } else {
                    playerHolder.first_player_Match.setVisibility(View.INVISIBLE);
                }
                if (matchups.get(position).getPlayerA().getPlayerImage() != null) {
                    Picasso.with(mContext).load(matchups.get(position).getPlayerA().getPlayerImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
//                            .transform(new Transformation() {
//                                @Override
//                                public Bitmap transform(Bitmap source) {
//                                    return transformImg(source, R.color.hint);
//                                }
//
//                                @Override
//                                public String key() {
//                                    return "circle";
//                                }
//                            })
                            .into(playerHolder.first_player_img);
                }
                if (matchups.get(position).getPlayerA().getGameDate() != null) {
                    String live = matchups.get(position).getPlayerA().getIsLive();
                    if (live.equalsIgnoreCase("true")) {
                        playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.live));
                        playerHolder.first_platytime.setVisibility(View.VISIBLE);
                        playerHolder.first_platytime.setText("Live");
                        playerHolder.first_platytime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                        playerHolder.first_platytime.setTextColor(mContext.getResources().getColor(R.color.white));

                        playerHolder.first_player_Time.setVisibility(View.GONE);
                        // playerHolder.play.setVisibility(View.VISIBLE);
                        playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                    } else {
                        playerHolder.first_platytime.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setVisibility(View.VISIBLE);
                        //  playerHolder.play.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                    }

                }
                //Filling the PlayerB details

                if (matchups.get(position).getPlayerC() != null) {
//                    if (matchups.get(position).getPlayerC() != null) {
//                } else if (matchups.get(position).getPlayerstatus_C().equalsIgnoreCase("0")) {
                    playerHolder.second_playerCard.setVisibility(View.GONE);
                    playerHolder.no_player.setVisibility(View.GONE);
                    playerHolder.no_player_abc.setVisibility(View.VISIBLE);
                    playerHolder.first_playerCard.setClickable(false);
                    playerHolder.player_Name_b.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                    playerHolder.player_Name_c.setText(matchups.get(position).getPlayerC().getFirstName() + " " + matchups.get(position).getPlayerC().getLastName());

                    if (matchups.get(position).getPlayerC().getPlayerImage() != null) {
                        Picasso.with(mContext).load(matchups.get(position).getPlayerC().getPlayerImage())
                                .placeholder(R.drawable.profile_placeholder)
//                                .error(R.drawable.game_inactive_placeholder)
//                                .transform(new Transformation() {
//                                    @Override
//                                    public Bitmap transform(Bitmap source) {
//                                        return transformImg(source, R.color.hint);
//                                    }
//
//                                    @Override
//                                    public String key() {
//                                        return "circle";
//                                    }
//                                })
                                .into(playerHolder.player_img_c);

                    }

                    if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                        Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                .placeholder(R.drawable.profile_placeholder)
//                                .error(R.drawable.game_inactive_placeholder)
//                                .transform(new Transformation() {
//                                    @Override
//                                    public Bitmap transform(Bitmap source) {
//                                        return transformImg(source, R.color.hint);
//                                    }
//
//                                    @Override
//                                    public String key() {
//                                        return "circle";
//                                    }
//                                })
                                .into(playerHolder.player_img_b);

                    }
                    if (matchups.get(position).getPlayerB().getPositonName() != null) {
                        playerHolder.player_position_b.setText(matchups.get(position).getPlayerB().getPositonName());
                    }
                    if (matchups.get(position).getPlayerB().getPointSpread() != null) {
                        playerHolder.player_price_b.setText(matchups.get(position).getHandicapAvsB() + "");
                    }
                    if (matchups.get(position).getPlayerB().getVenue() != null) {
                        playerHolder.player_Match_b.setVisibility(View.VISIBLE);
                        playerHolder.player_Match_b.setText(matchups.get(position).getPlayerB().getVenue());
                    } else {
                        playerHolder.player_Match_b.setVisibility(View.INVISIBLE);
                    }
                    if (matchups.get(position).getPlayerC().getPositonName() != null) {
                        playerHolder.player_position_c.setText(matchups.get(position).getPlayerC().getPositonName());
                    }
                    if (matchups.get(position).getPlayerC().getPointSpread() != null) {
                        playerHolder.player_price_c.setText(matchups.get(position).getHandicapAvsC() + "");
                    }
                    if (matchups.get(position).getPlayerC().getVenue() != null) {
                        playerHolder.player_Match_c.setVisibility(View.VISIBLE);
                        playerHolder.player_Match_c.setText(matchups.get(position).getPlayerC().getVenue());
                    } else {
                        playerHolder.player_Match_c.setVisibility(View.INVISIBLE);
                    }

                    if (matchups.get(position).getPlayerB().getGameDate() != null) {
//                    playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                        String live = matchups.get(position).getPlayerB().getIsLive();
                        if (live.equalsIgnoreCase("true")) {
                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.live));
                            playerHolder.player_time_b.setVisibility(View.VISIBLE);
                            playerHolder.player_time_b.setText("Live");
                            playerHolder.player_time_b.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                            playerHolder.player_time_b.setTextColor(mContext.getResources().getColor(R.color.white));

                            playerHolder.player_time_b.setVisibility(View.GONE);

                            //playerHolder.play.setVisibility(View.VISIBLE);
                            playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                        } else {
                            playerHolder.player_time_b.setVisibility(View.GONE);
                            playerHolder.player_time_b.setVisibility(View.VISIBLE);
                            //   playerHolder.play.setVisibility(View.GONE);
                            playerHolder.player_time_b.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                        }
                    }

                    if (matchups.get(position).getPlayerC().getGameDate() != null) {
//                    playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                        String live = matchups.get(position).getPlayerC().getIsLive();
                        if (live.equalsIgnoreCase("true")) {
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.live));
                            playerHolder.player_time_c.setVisibility(View.VISIBLE);
                            playerHolder.player_time_c.setText("Live");
                            playerHolder.player_time_c.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                            playerHolder.player_time_c.setTextColor(mContext.getResources().getColor(R.color.white));

                            playerHolder.player_time_c.setVisibility(View.GONE);

                            //playerHolder.play.setVisibility(View.VISIBLE);
                            playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                        } else {
                            playerHolder.player_time_c.setVisibility(View.GONE);
                            playerHolder.player_time_c.setVisibility(View.VISIBLE);
                            //   playerHolder.play.setVisibility(View.GONE);
                            playerHolder.player_time_c.setText(getTime(matchups.get(position).getPlayerC().getGameDate()));
                        }
                    }
                    //  }
                } else {
                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
                        playerHolder.second_playerCard.setVisibility(View.GONE);
                        playerHolder.no_player_abc.setVisibility(View.GONE);
                        playerHolder.no_player.setVisibility(View.VISIBLE);
                        playerHolder.first_playerCard.setClickable(false);
//                        playerHolder.first_playerCard.setEnabled(false);
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
                                    playerHolder.first_player_Name.setText(fn);
                                } else {
                                    playerHolder.first_player_Name.setText(fn + " " + matchups.get(position).getPlayerA().getLastName());
                                }

//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.first_player_position.setText(separated_two);
                            } else {
                                Log.e("314--12--402aa", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                                } else {
                                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());
                                }

                                playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
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

                                playerHolder.first_player_Name.setText(fn);
//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.first_player_position.setText(separated_two);
                            } else {
                                Log.e("314--22-432 a-", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                                playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
                            }
                        } else {

                            Log.e("314----", "0999999999999999");

                            playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());

                        }

//                        playerHolder.tvPlayPoints.setText(matchups.get(position).getPlayerA().getPointSpread() + " ");
                        playerHolder.tvPlayPoints.setText(matchups.get(position).getHandicap() + " ");

                    } else {
//                    } else if (matchups.get(position).getPlayerC() == null) {
                        playerHolder.second_playerCard.setVisibility(View.VISIBLE);
                        playerHolder.first_playerCard.setClickable(true);
                        playerHolder.no_player.setVisibility(View.GONE);
                        playerHolder.no_player_abc.setVisibility(View.GONE);

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
                                    playerHolder.first_player_Name.setText(fn);
                                } else {
                                    playerHolder.first_player_Name.setText(fn + " " + matchups.get(position).getPlayerA().getLastName());
                                }

//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.first_player_position.setText(separated_two);
                            } else {
                                Log.e("314--12--402aa", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                                } else {
                                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());
                                }

                                playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
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

                                playerHolder.first_player_Name.setText(fn);
//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.first_player_position.setText(separated_two);
                            } else {
                                Log.e("314--22-432 a-", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                                playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                                playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
                            }
                        } else {

                            Log.e("314----", "0999999999999999");

                            playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());

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
                                    playerHolder.second_player_Name.setText(fn);
                                } else {
                                    playerHolder.second_player_Name.setText(fn + " " + matchups.get(position).getPlayerB().getLastName());
                                }

//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.second_player_position.setText(separated_two);
                            } else {
                                Log.e("314--12- 468b-", "" + Name_B + "-else-" + matchups.get(position).getPlayerB().getLastName());
                                if (matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerB().getLastName().equalsIgnoreCase("null")) {
                                    playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getFirstName());
                                } else {
                                    playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                                }

                                playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
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

                                playerHolder.second_player_Name.setText(fn);
//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                                playerHolder.second_player_position.setText(separated_two);
                            } else {
                                Log.e("314--22b--", "" + Name_B + "-else-" + matchups.get(position).getPlayerB().getLastName());
                                playerHolder.second_player_Name.setText(Name_B);
                                playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
                            }
                        } else {

                            Log.e("314--b--", "0999999999999999");

                            playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getLastName());

                        }

                        playerHolder.second_player_price.setText((matchups.get(position).getHandicap()) * 1 + "");
                        if (matchups.get(position).getPlayerB().getVenue() != null) {
                            playerHolder.second_player_Match.setVisibility(View.VISIBLE);
                            playerHolder.second_player_Match.setText(matchups.get(position).getPlayerB().getVenue());
                        } else {
                            playerHolder.second_player_Match.setVisibility(View.INVISIBLE);
                        }

                        if (matchups.get(position).getPlayerB().getGameDate() != null) {
                            String live = matchups.get(position).getPlayerB().getIsLive();
                            if (live.equalsIgnoreCase("true")) {
                                playerHolder.secondtime.setVisibility(View.VISIBLE);
                                //    playerHolder.play.setVisibility(View.VISIBLE);
                                playerHolder.secondtime.setText("Live");
                                playerHolder.secondtime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                                playerHolder.secondtime.setTextColor(mContext.getResources().getColor(R.color.white));


                                playerHolder.second_player_Time.setVisibility(View.GONE);
                                playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                            } else {
                                //     playerHolder.play.setVisibility(View.GONE);
                                playerHolder.second_player_Time.setVisibility(View.VISIBLE);
                                playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                            }
//                        playerHolder.second_player_Time.setText("Live");
                        }
                        if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                    .placeholder(R.drawable.profile_placeholder)
                                    .error(R.drawable.profile_placeholder)
//                                    .transform(new Transformation() {
//                                        @Override
//                                        public Bitmap transform(Bitmap source) {
//                                            return transformImg(source, R.color.hint);
//                                        }
//
//                                        @Override
//                                        public String key() {
//                                            return "circle";
//                                        }
//                                    })
                                    .into(playerHolder.second_player_img);

                        }
                    }
                }


            } else {


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

                Log.d("fristname", matchups.get(position).getPlayerA() + matchups.get(position).getPlayerA().getFirstName() + matchups.get(position).getPlayerA().getPointSpread());
                if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName() + " " + matchups.get(position).getPlayerA().getLastName());

                } else if (matchups.get(position).getPlayerA().getFirstName() != null) {

                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getFirstName());
                } else if (matchups.get(position).getPlayerA().getLastName() != null) {
                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());
                }


                if (matchups.get(position).getPlayerA().getPositonName() != null) {
                    playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
                }

               /* if (matchups.get(position).getPlayerA().getFirstName() != null && matchups.get(position).getPlayerA().getLastName() != null && !matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null)) {
                    String Name_A = matchups.get(position).getPlayerA().getFirstName();

                    if (Name_A.contains("(")) {
                        String[] separated = Name_A.split("\\(");
                        String fn = separated[0]; // this will contain "Fruit"
                        String vs = separated[1];

                        String separated_two = vs.replaceAll("\\)", "");
//                            String fn2 = separated_two[0]; // this will contain "Fruit"
//                            String vs2 = separated_two[1];
                        Log.e("314-1---", "" + fn + "--" + vs + "===" + separated_two);
                        if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                            playerHolder.first_player_Name.setText(fn);
                        } else {
                            playerHolder.first_player_Name.setText(fn + " " + matchups.get(position).getPlayerA().getLastName());
                        }

//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                        playerHolder.first_player_position.setText(separated_two);
                    } else {
                        Log.e("314--12--", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                        if (matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase(null) || matchups.get(position).getPlayerA().getLastName().equalsIgnoreCase("null")) {
                            playerHolder.first_player_Name.setText(Name_A);
                        } else {
                            playerHolder.first_player_Name.setText(Name_A + " " + matchups.get(position).getPlayerA().getLastName());
                        }

                        playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
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
                        Log.e("314--21--", "" + fn + "--" + vs + "===" + separated_two);

                        playerHolder.first_player_Name.setText(fn);
//                            playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                        playerHolder.first_player_position.setText(separated_two);
                    } else {
                        Log.e("314--22--", "" + Name_A + "-else-" + matchups.get(position).getPlayerA().getLastName());
                        playerHolder.first_player_Name.setText(Name_A);
                        playerHolder.first_player_position.setText(matchups.get(position).getPlayerA().getPositonName());
//                                playerHolder.second_player_position.setText(separated_two);
                    }
                } else {

                    Log.e("314----", "0999999999999999");

                    playerHolder.first_player_Name.setText(matchups.get(position).getPlayerA().getLastName());

                }*/
                if (matchups.get(position).getPlayerA().getPointSpread() != null) {
                    playerHolder.first_player_price.setText(((matchups.get(position).getHandicap()) * (-1)) + "");
                }
                if (matchups.get(position).getPlayerA().getVenue() != null) {
                    playerHolder.first_player_Match.setVisibility(View.VISIBLE);
                    playerHolder.first_player_Match.setText(matchups.get(position).getPlayerA().getVenue());
                } else {
                    playerHolder.first_player_Match.setVisibility(View.INVISIBLE);
                }
                if (matchups.get(position).getPlayerA().getPlayerImage() != null) {
                    Picasso.with(mContext).load(matchups.get(position).getPlayerA().getPlayerImage())
                            .placeholder(R.drawable.game_inactive_placeholder)
//                            .transform(new Transformation() {
//                                @Override
//                                public Bitmap transform(Bitmap source) {
//                                    return transformImg(source, R.color.hint);
//                                }
//
//                                @Override
//                                public String key() {
//                                    return "circle";
//                                }
//                            })
                            .into(playerHolder.first_player_img);
                }


                ///
                if (matchups.get(position).getPlayerA().getGameDate() != null) {
//                    playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));

                    String live = matchups.get(position).getPlayerA().getIsLive();
                    if (live.equalsIgnoreCase("true")) {
                        playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.live));
                        playerHolder.first_platytime.setVisibility(View.VISIBLE);
                        playerHolder.first_platytime.setText("Live");
                        playerHolder.first_platytime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                        playerHolder.first_platytime.setTextColor(mContext.getResources().getColor(R.color.white));

                        playerHolder.first_player_Time.setVisibility(View.GONE);

                        //playerHolder.play.setVisibility(View.VISIBLE);
                        playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                    } else {
                        playerHolder.first_platytime.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setVisibility(View.VISIBLE);
                        //   playerHolder.play.setVisibility(View.GONE);
                        playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                    }
                }
                playerHolder.first_player_Time.setText(getTime(matchups.get(position).getPlayerA().getGameDate()));
                //Filling the PlayerB details
// action this


                if (matchups.get(position).getPlayerC() != null) {
                    {
                        playerHolder.first_player_price.setVisibility(View.GONE);
                        playerHolder.second_playerCard.setVisibility(View.GONE);
                        playerHolder.no_player.setVisibility(View.GONE);
                        playerHolder.no_player_abc.setVisibility(View.VISIBLE);
                        playerHolder.first_playerCard.setClickable(false);
                        playerHolder.player_Name_b.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                        playerHolder.player_Name_c.setText(matchups.get(position).getPlayerC().getFirstName() + " " + matchups.get(position).getPlayerC().getLastName());

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
                                    .into(playerHolder.player_img_c);

                        }

                        if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                    .placeholder(R.drawable.profile_placeholder)
                                    .error(R.drawable.profile_placeholder)
//                                    .transform(new Transformation() {
//                                        @Override
//                                        public Bitmap transform(Bitmap source) {
//                                            return transformImg(source, R.color.hint);
//                                        }
//
//                                        @Override
//                                        public String key() {
//                                            return "circle";
//                                        }
//                                    })
                                    .into(playerHolder.player_img_b);

                        }


                    }
                } else {
                    if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("0")) {
                        playerHolder.second_playerCard.setVisibility(View.GONE);
                        playerHolder.no_player_abc.setVisibility(View.GONE);
                        playerHolder.no_player.setVisibility(View.VISIBLE);
                        playerHolder.first_playerCard.setClickable(false);
                        playerHolder.tvPlayPoints.setText(matchups.get(position).getHandicap() + " ");
//                        playerHolder.tvPlayPoints.setText(matchups.get(position).getPlayerA().getPointSpread() + " ");


                    } else {

                        playerHolder.second_playerCard.setVisibility(View.VISIBLE);
                        playerHolder.no_player.setVisibility(View.GONE);
                        playerHolder.no_player_abc.setVisibility(View.GONE);
                        playerHolder.first_playerCard.setClickable(true);
                        playerHolder.first_playerCard.setEnabled(true);
                        playerHolder.second_player_Name.setText(matchups.get(position).getPlayerB().getFirstName() + " " + matchups.get(position).getPlayerB().getLastName());
                        playerHolder.second_player_position.setText(matchups.get(position).getPlayerB().getPositonName());
                        playerHolder.second_player_price.setText(matchups.get(position).getHandicap() * 1 + "");
                        if (matchups.get(position).getPlayerB().getVenue() != null) {
                            playerHolder.second_player_Match.setVisibility(View.VISIBLE);
                            playerHolder.second_player_Match.setText(matchups.get(position).getPlayerB().getVenue());
                        } else {
                            playerHolder.second_player_Match.setVisibility(View.INVISIBLE);
                        }
                        if (matchups.get(position).getPlayerB().getGameDate() != null) {
                            String live = matchups.get(position).getPlayerB().getIsLive();
                            if (live.equalsIgnoreCase("true")) {
                                playerHolder.secondtime.setVisibility(View.VISIBLE);
                                //   playerHolder.play.setVisibility(View.VISIBLE);
                                playerHolder.secondtime.setText("Live");
                                playerHolder.secondtime.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                                playerHolder.secondtime.setTextColor(mContext.getResources().getColor(R.color.white));


                                playerHolder.second_player_Time.setVisibility(View.GONE);
                                playerHolder.playerStatisticsView.setVisibility(View.VISIBLE);
                            } else {
                                //         playerHolder.play.setVisibility(View.GONE);
                                playerHolder.second_player_Time.setVisibility(View.VISIBLE);
                                playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                            }
//                        playerHolder.second_player_Time.setText(getTime(matchups.get(position).getPlayerB().getGameDate()));
                        }
                        if (matchups.get(position).getPlayerB().getPlayerImage() != null) {
                            Picasso.with(mContext).load(matchups.get(position).getPlayerB().getPlayerImage())
                                    .placeholder(R.drawable.profile_placeholder)
                                    .error(R.drawable.profile_placeholder)
//                                    .transform(new Transformation() {
//                                        @Override
//                                        public Bitmap transform(Bitmap source) {
//                                            return transformImg(source, R.color.hint);
//                                        }
//
//                                        @Override
//                                        public String key() {
//                                            return "circle";
//                                        }
//                                    })
                                    .into(playerHolder.second_player_img);

                        }
                    }
                }


            }
        } catch (Exception e) {
Log.e("00--",e+"");
        }

/*
        Checking IsPurchased or not, if purchase player cound't clickble else can clickble
*/
        //if IsPurchased true
        /*
Checking picindex and win index of player A and PLayer B  DONE
*/


        if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

            playerHolder.sq_cc_over.setVisibility(View.GONE);
            playerHolder.sq_cc.setVisibility(View.VISIBLE);
        } else {

            playerHolder.sq_cc_over.setVisibility(View.VISIBLE);
            playerHolder.sq_cc.setVisibility(View.GONE);
        }

        if (IsPurchased) {


            try {


                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                if (matchups.get(position).getIsFinished()) {

                    /*  if winindex=0,pickindex=0 playerA checked Green*/
                    if (matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == 0) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        playerHolder.statusTxt.setText("WIN");

//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_b, mContext.getResources().getColor(R.color.text_color_new));
                            //  playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
//                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                                playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.left_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                                playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));


                            } else {
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            }
                        }

                    }
                    /*  if winindex=1,pickindex=1 playerB checked Green*/
                    if (matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == 1) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                        playerHolder.statusTxt.setText("WIN");
//                        if (matchups.get(position).getPlayerB() != null) {

                        if (matchups.get(position).getPlayerC() != null) {
//                            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                            playerHolder.over_playercard_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                            playerHolder.playerCheck_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));


                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
                                playerHolder.right_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
//                                playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win_bg));
                                playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_green));
                                playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_win));
                                playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            }
                        }

                    }
                    /*  if winindex=0,pickindex=1 or winindex=0,pickindex=-1   playerA checked gray*/
                    if ((matchups.get(position).getPickIndex() == 0 && matchups.get(position).getWinIndex() == 1)) {
                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        playerHolder.statusTxt.setText("LOST");
//                        if (matchups.get(position).getPlayerB() != null) {

                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_b, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.over_playercard_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
//                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                Log.e("bnull", "not null---");
                                playerHolder.left_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
//                                playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));


                            } else {
                                Log.e("bnullnot", "null---");
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
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
//                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.over_playercard_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                        } else {
//                            if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                                playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.left_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                                playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
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
//                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.over_playercard_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                        } else {
//                        if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                                playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.right_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));

                                playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            } else {
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));

                            }
                        }

                    }

                    /*  if winindex=1,pickindex=0 or winindex=1,pickindex=-1   playerB checked gray*/
                    if (matchups.get(position).getPickIndex() == 1 && matchups.get(position).getWinIndex() == 0) {


                        playerHolder.win_loss_statusView_ly.setVisibility(View.VISIBLE);
                        playerHolder.win_loss_statusView.setBackgroundColor(mContext.getResources().getColor(R.color.text_color_new));
                        playerHolder.statusTxt.setText("LOST");

                        if (matchups.get(position).getPlayerC() != null) {
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));
//                            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                            playerHolder.playerCheck_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                            playerHolder.over_playercard_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
//                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
//                        if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                                playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.right_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));

                                playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_lost_bg));
                                playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_gray));
                                playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_loss));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));

                            }
                        }


                    }
                } else {

                    Log.e("277---", "277----not played");
                    /*  if played false,pickindex=0 playerA checked */
                    if (matchups.get(position).getPickIndex() == 0) {

//                        if (matchups.get(position).getPlayerB() != null) {
                        if (matchups.get(position).getPlayerC() != null) {
//                        if (matchups.get(position).getPlayerstatus_C().equalsIgnoreCase("1")) {
//                        } else if (matchups.get(position).getPlayerC() != null){
//                            playerHolder.player_img_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.over_playercard_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
//                            playerHolder.left_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            playerHolder.playerCheck_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            setTextViewDrawableColor(playerHolder.player_Name_b, mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
                        } else {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {
//                                playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                playerHolder.left_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                                playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
//                                playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                                playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                            }
                        }

                    }

                    /*  if played =flase,pickindex=1 playerB checked */
                    if (matchups.get(position).getPickIndex() == 1) {

                        Log.e("277---", "0101");

                        if (matchups.get(position).getPlayerC() != null) {
//                        if (matchups.get(position).getPlayerstatus_C().equalsIgnoreCase("1")) {
                            Log.e("277---", "0102");
//                        } else if (matchups.get(position).getPlayerC() != null){
//                            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                            playerHolder.over_playercard_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                            playerHolder.playerCheck_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
//                            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
                            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));
                        } else {
                            Log.e("277---", "0103");
//                        if (matchups.get(position).getPlayerB() != null) {
                            if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

                                Log.e("277---", "0104");
//                                playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                playerHolder.right_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                                playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                            } else {
                                Log.e("277---", "0105");
                                setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
//                                playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
                                playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                            }
                        }
                    }

                  /*  if (matchups.size() > 0) {

                        Log.e("277---", matchups.get(position).getPlayerA().getFirstName() + "277----not played" + matchups.get(position).getPlayerB().getFirstName());
                        *//*  if played false,pickindex=0 playerA checked *//*
                        if (matchups.get(position).getPickIndex() == 0) {

//                        Toast.makeText(mContext,"533--"+matchups.get(position).getPickIndex(),Toast.LENGTH_LONG).show();
                            if (matchups.get(position).getPlayerB() != null) {
                                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                            Toast.makeText(mContext,"535--",Toast.LENGTH_LONG).show();
                                    playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.first_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                    playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                                } else {
//                            Toast.makeText(mContext,"541-null-",Toast.LENGTH_LONG).show();
                                    setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.hint));
                                    playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.overCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                }
                            }
                        }

                        *//*  if played =flase,pickindex=1 playerB checked *//*
                        if (matchups.get(position).getPickIndex() == 1) {
//                        Toast.makeText(mContext,"551--"+position,Toast.LENGTH_LONG).show();
                            if (matchups.get(position).getPlayerB() != null) {
                                if (matchups.get(position).getPlayerstatus().equalsIgnoreCase("1")) {

//                          Toast.makeText(mContext,"557--",Toast.LENGTH_LONG).show();
                                    playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.second_playerCheck.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                    playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
                                } else {
//                            Toast.makeText(mContext,"559--",Toast.LENGTH_LONG).show();
                                    setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.text_color_new));
                                    playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
                                    playerHolder.underCheckbox.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.game_badge_selected));
                                    playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
                                }
                            }
                        }
                    }*/

                }

            } catch (Exception e) {
            }
        } else {


            // guest/ not purchage
            playerHolder.win_loss_statusView_ly.setVisibility(View.GONE);

            playerHolder.first_playerCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (playerHolder.no_player.getVisibility() == View.GONE || playerHolder.no_player_abc.getVisibility() == View.GONE) {
                        mHilightViews(playerHolder, 1, position);
                    }

//                    if (playerHolder.no_player_abc.getVisibility() == View.GONE) {
//                        mHilightViews(playerHolder, 1, position);
//                    }
                }
            });

            playerHolder.second_playerCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHilightViews(playerHolder, 2, position);
                }
            });

            playerHolder.over_playercard.setOnClickListener(new View.OnClickListener() {
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

        playerHolder.sq_cc_over.setClickable(true);
        playerHolder.sq_cc.setClickable(true);

        playerHolder.sq_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("1425----", "1112");
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
        playerHolder.sq_cc_over.setOnClickListener(new View.OnClickListener() {
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
            if (playerHolder.first_playerCheck.isChecked()) {
                // PLAYER A REMOVED
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.first_playerCheck.setChecked(false);
                setFirstPlayerTextEnableDisable(playerHolder, false, position);
                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));

//                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
//                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            } else {
                if (playerHolder.second_playerCheck.isChecked()) {


                    //PLAYER B REMOVED
                    playerHolder.second_playerCheck.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setSecondPlayerTextDisableEnableMode(playerHolder, false, position);
                }
//                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
//                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));
                //PLAYER A ADDED
                playerHolder.first_playerCheck.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setFirstPlayerTextEnableDisable(playerHolder, true, position);
            }
        } else if (from == 2) {
            if (playerHolder.second_playerCheck.isChecked()) {
                //PLAYER B REMOVED
                playerHolder.second_playerCheck.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setSecondPlayerTextDisableEnableMode(playerHolder, false, position);
                playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
                playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.black));
            } else {

                if (playerHolder.first_playerCheck.isChecked()) {
                    // PLAYER A REMOVED
                    playerHolder.first_playerCheck.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setFirstPlayerTextEnableDisable(playerHolder, false, position);
                }
                //PLAYER B ADDED
                playerHolder.second_playerCheck.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setSecondPlayerTextDisableEnableMode(playerHolder, true, position);
            }
        } else if (from == 3) {


            if (playerHolder.overCheckbox.isChecked()) {
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.overCheckbox.setChecked(false);
                setAPlayerTextDisableEnableMode(playerHolder, false, position);
            } else {
                if (playerHolder.underCheckbox.isChecked()) {
                    new_pick_index.remove(matchups.get(position).get_id());
                    playerHolder.underCheckbox.setChecked(false);
                    setBPlayerTextDisableEnableMode(playerHolder, false, position);
                }
                playerHolder.overCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setAPlayerTextDisableEnableMode(playerHolder, true, position);
            }
        } else if (from == 4) {
            if (playerHolder.underCheckbox.isChecked()) {

                playerHolder.underCheckbox.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setBPlayerTextDisableEnableMode(playerHolder, false, position);
            } else {
                if (playerHolder.overCheckbox.isChecked()) {
                    playerHolder.overCheckbox.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setAPlayerTextDisableEnableMode(playerHolder, false, position);
                }

                playerHolder.underCheckbox.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setBPlayerTextDisableEnableMode(playerHolder, true, position);
            }
        } else if (from == 5) {
            if (playerHolder.playerCheck_b.isChecked()) {
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                playerHolder.playerCheck_b.setChecked(false);
                setABPlayerTextDisableEnableMode(playerHolder, false,position);
            } else {
                if (playerHolder.playerCheck_c.isChecked()) {
                    new_pick_index.remove(matchups.get(position).get_id());
                    playerHolder.playerCheck_c.setChecked(false);
                    setACPlayerTextDisableEnableMode(playerHolder, false,position);
                }
                playerHolder.playerCheck_b.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "0");
                setABPlayerTextDisableEnableMode(playerHolder, true,position);
            }
        } else if (from == 6) {
            if (playerHolder.playerCheck_c.isChecked()) {

                playerHolder.playerCheck_c.setChecked(false);
                if (new_pick_index.size() > 0) {
                    new_pick_index.remove(matchups.get(position).get_id());
                }
                setACPlayerTextDisableEnableMode(playerHolder, false,position);
            } else {
                if (playerHolder.playerCheck_b.isChecked()) {
                    playerHolder.playerCheck_b.setChecked(false);
                    new_pick_index.remove(matchups.get(position).get_id());
                    setABPlayerTextDisableEnableMode(playerHolder, false,position);
                }

                playerHolder.playerCheck_c.setChecked(true);
                new_pick_index.put(matchups.get(position).get_id(), "1");
                setACPlayerTextDisableEnableMode(playerHolder, true,position);
            }
        }
    }

    private void setABPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable,int position) {
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
            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor(R.color.hint));
//            playerHolder.over_playercard_b.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.player_Name_b, mContext.getResources().getColor(R.color.text_color_new));
            if (position == 0) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
            }

            if (position == 2) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
            }

            if (position == 3) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
            }

            if (position == 4) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
            }

            if (position == 6) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }

        } else {
            playerHolder.over_playercard_b.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);
    }


    private void setACPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable,int position) {
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
            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.player_Name_b.setTextColor(mContext.getResources().getColor((R.color.hint)));
//            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
         //   playerHolder.over_playercard_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            if (position == 0) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
            }

            if (position == 2) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
            }

            if (position == 3) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
            }

            if (position == 4) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
            }

            if (position == 6) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }

            setTextViewDrawableColor(playerHolder.player_Name_c, mContext.getResources().getColor(R.color.text_color_new));
        } else {
            playerHolder.player_Name_c.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.over_playercard_c.setBackgroundColor(mContext.getResources().getColor(R.color.white));
//            playerHolder.player_img_c.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));

        }
        mCallback.onClick(new_pick_index);


    }

    //bb

    private void setAPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable, int position) {
        if (enable) {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
//            playerHolder.over_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));

            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.black));

            playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));

            if (position == 0) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
            }

            if (position == 2) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
            }

            if (position == 3) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
            }

            if (position == 4) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
            }

            if (position == 6) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }


        } else {
            playerHolder.overview_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.overView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            setTextViewDrawableColor(playerHolder.overview_txt, mContext.getResources().getColor(R.color.hint));
            playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
        }
        mCallback.onClick(new_pick_index);
    }


    private void setBPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable, int position) {
        if (enable) {
            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
//            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            //    playerHolder.under_playercard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.black));

//            playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));

            if (position == 0) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
            }

            if (position == 2) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
            }

            if (position == 3) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
            }

            if (position == 4) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
            }

            if (position == 6) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }


        } else {
            playerHolder.over_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));


            playerHolder.underView_txt.setTextColor(mContext.getResources().getColor(R.color.black));
            setTextViewDrawableColor(playerHolder.underView_txt, mContext.getResources().getColor(R.color.hint));
//            playerHolder.underView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_unchecked));
            playerHolder.under_playercard.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        }
        mCallback.onClick(new_pick_index);


    }

    private void setFirstPlayerTextEnableDisable(PlayerHolder playerHolder, boolean enable, int position) {
        if (enable) {
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.text_color_new));
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
//            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            playerHolder.left_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
            setTextViewDrawableColor(playerHolder.first_player_Name, mContext.getResources().getColor(R.color.text_color_new));

            Log.e("11976-----------Pos", position + "");


            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));


            if (position == 0) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
            }

            if (position == 2) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
            }

            if (position == 3) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
            }

            if (position == 4) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
            }

            if (position == 6) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }


        } else {

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));

//            playerHolder.first_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.left_matchcard.setBackgroundColor(mContext.getResources().getColor(R.color.white));

            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor(R.color.hint));
        }
        mCallback.onClick(new_pick_index);

    }

    private void setSecondPlayerTextDisableEnableMode(PlayerHolder playerHolder, boolean enable, int position) {
        if (enable) {
            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.text_color_new)));
            playerHolder.first_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
            playerHolder.right_matchcard.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rectangle_new_win));
//            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_select_bg_circle1));
            setTextViewDrawableColor(playerHolder.second_player_Name, mContext.getResources().getColor(R.color.text_color_new));

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));


            if (position == 0) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p0));
            }

            if (position == 1) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p1));
            }

            if (position == 2) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p2));
            }

            if (position == 3) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p3));
            }

            if (position == 4) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p4));
            }

            if (position == 5) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p5));
            }

            if (position == 6) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p6));
            }

            if (position == 7) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p7));
            }

            if (position == 8) {
                playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.p8));
            }

//            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.sqorr_red));

        } else {

            playerHolder.first_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));
            playerHolder.second_playerCard.setBackgroundColor(mContext.getResources().getColor(R.color.gray_line));

            playerHolder.second_player_Name.setTextColor(mContext.getResources().getColor((R.color.hint)));
//            playerHolder.second_player_img.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.player_checked));
            playerHolder.right_matchcard.setBackgroundColor(mContext.getResources().getColor(R.color.white));

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
        ImageView first_player_img;
        TextView first_player_Name;
        TextView first_player_Time;
        TextView first_player_position;
        TextView first_player_place;
        TextView first_player_Match;
        Button first_player_price;
        CardView first_playerCard;
        CheckBox first_playerCheck;
        TextView first_platytime;

        RelativeLayout left_matchcard;


        //2nd player
        ImageView second_player_img;
        TextView second_player_Name;
        TextView second_player_Time;
        TextView second_player_position;
        TextView second_player_Match;
        Button second_player_price;
        CardView second_playerCard;
        CheckBox second_playerCheck;
        TextView secondtime;
        RelativeLayout right_matchcard;

        //Player statistics
        CardView playerstatsViewSpinner;
        LinearLayout playerStatisticsView;
        ImageView arrow;
        RecyclerView playerStatsRcView_child;
        //B player off
        CheckBox overCheckbox;
        CheckBox underCheckbox;
        RelativeLayout under_playercard;
        RelativeLayout over_playercard;
        TextView overview_txt;
        TextView underView_txt;
        LinearLayout overView;
        LinearLayout underView;
        CardView no_player, no_player_abc;
        TextView tvPlayPoints;

        /*win loss status View*/
        FrameLayout win_loss_statusView_ly;
        CardView win_loss_statusView;
        TextView statusTxt;
        //  TextView play;

        //        AB
        TextView player_Name_b, player_position_b, player_Match_b, player_time_b, player_price_b;
        CheckBox playerCheck_b;
        RelativeLayout over_playercard_b;
        ImageView player_img_b;


        //        AC
        ImageView player_img_c;
        TextView player_Name_c, player_position_c, player_Match_c, player_time_c, player_price_c;
        CheckBox playerCheck_c;
        RelativeLayout over_playercard_c;


        LinearLayout heads_s;
        TextView player_4, player_2, player_3;

        LinearLayout sq_cc, sq_cc_over;

        public PlayerHolder(@NonNull View itemView) {
            super(itemView);
            // PLAYER A
            this.first_platytime = (TextView) itemView.findViewById(R.id.player_time1);
            this.first_player_img = (ImageView) itemView.findViewById(R.id.player_img);
            this.first_player_Name = (TextView) itemView.findViewById(R.id.player_Name);
            this.first_player_position = (TextView) itemView.findViewById(R.id.player_position);
            this.first_player_place = (TextView) itemView.findViewById(R.id.player_place);
            this.first_player_Match = (TextView) itemView.findViewById(R.id.player_Match);
            this.first_player_Time = (TextView) itemView.findViewById(R.id.player_time);
            this.first_player_price = (Button) itemView.findViewById(R.id.player_price);
            this.first_playerCard = (CardView) itemView.findViewById(R.id.playerCard);
            this.first_playerCheck = (CheckBox) itemView.findViewById(R.id.playerCheck);
            this.left_matchcard = itemView.findViewById(R.id.left_matchcard);

            // PLAYER B
            this.second_player_img = (ImageView) itemView.findViewById(R.id.second_player_img);
            this.second_player_Name = (TextView) itemView.findViewById(R.id.second_player_Name);
            this.second_player_position = (TextView) itemView.findViewById(R.id.second_player_position);
            this.second_player_Match = (TextView) itemView.findViewById(R.id.second_player_Match);
            this.second_player_Time = (TextView) itemView.findViewById(R.id.second_player_time);
            this.second_player_price = (Button) itemView.findViewById(R.id.second_player_price);
            this.second_playerCard = (CardView) itemView.findViewById(R.id.second_playerCard);
            this.second_playerCheck = (CheckBox) itemView.findViewById(R.id.second_playerCheck);
            this.secondtime = (TextView) itemView.findViewById(R.id.second_player_time1);
            //Players ststistics
            this.playerstatsViewSpinner = (CardView) itemView.findViewById(R.id.playerstatsViewSpinner);
            this.playerStatisticsView = (LinearLayout) itemView.findViewById(R.id.playerStatisticsView);
            this.arrow = (ImageView) itemView.findViewById(R.id.arrow);
            this.playerStatsRcView_child = (RecyclerView) itemView.findViewById(R.id.playerStatsRcView_child);
            this.right_matchcard = itemView.findViewById(R.id.right_matchcard);

            //PlayerB OFF
            this.overCheckbox = (CheckBox) itemView.findViewById(R.id.overCheckbox);
            this.underCheckbox = (CheckBox) itemView.findViewById(R.id.underCheckbox);
            this.over_playercard = (RelativeLayout) itemView.findViewById(R.id.over_playercard);
            this.under_playercard = (RelativeLayout) itemView.findViewById(R.id.under_playercard);
            this.overview_txt = (TextView) itemView.findViewById(R.id.overview_txt);
            this.underView_txt = (TextView) itemView.findViewById(R.id.underView_txt);
            this.overView = (LinearLayout) itemView.findViewById(R.id.overView);
            this.underView = (LinearLayout) itemView.findViewById(R.id.underView);
            this.no_player = (CardView) itemView.findViewById(R.id.no_player);
            this.tvPlayPoints = (TextView) itemView.findViewById(R.id.tvPlayPoints);


            this.win_loss_statusView_ly = (FrameLayout) itemView.findViewById(R.id.win_loss_statusView_ly);
            this.win_loss_statusView = (CardView) itemView.findViewById(R.id.win_loss_statusView);
            this.statusTxt = (TextView) itemView.findViewById(R.id.statusTxt);
            //  this.play=(TextView) itemView.findViewById(R.id.play);


            this.no_player_abc = (CardView) itemView.findViewById(R.id.no_player_abc);

            // PLAYER A vs B
            this.player_img_b = (ImageView) itemView.findViewById(R.id.player_img_b);
            this.player_Name_b = (TextView) itemView.findViewById(R.id.player_Name_b);
            this.player_position_b = (TextView) itemView.findViewById(R.id.player_position_b);
            this.player_Match_b = (TextView) itemView.findViewById(R.id.player_Match_b);
            this.player_time_b = (TextView) itemView.findViewById(R.id.player_time_b);
            this.player_price_b = (Button) itemView.findViewById(R.id.player_price_b);
//            this.second_playerCard = (CardView) itemView.findViewById(R.id.second_playerCard);
//            this.second_playerCheck = (CheckBox) itemView.findViewById(R.id.second_playerCheck);
//            this.secondtime = (TextView) itemView.findViewById(R.id.second_player_time1);
            this.playerCheck_b = (CheckBox) itemView.findViewById(R.id.playerCheck_b);
            this.over_playercard_b = (RelativeLayout) itemView.findViewById(R.id.over_playercard_b);
            // PLAYER A vs C
            this.player_img_c = (ImageView) itemView.findViewById(R.id.player_img_c);
            this.player_Name_c = (TextView) itemView.findViewById(R.id.player_Name_c);
            this.player_position_c = (TextView) itemView.findViewById(R.id.player_position_c);
            this.player_Match_c = (TextView) itemView.findViewById(R.id.player_Match_c);
            this.player_time_c = (TextView) itemView.findViewById(R.id.player_time_c);
            this.player_price_c = (Button) itemView.findViewById(R.id.player_price_c);
//            this.second_playerCard = (CardView) itemView.findViewById(R.id.second_playerCard);
//            this.secondtime = (TextView) itemView.findViewById(R.id.second_player_time1);
            this.playerCheck_c = (CheckBox) itemView.findViewById(R.id.playerCheck_c);
            this.over_playercard_c = (RelativeLayout) itemView.findViewById(R.id.over_playercard_c);


            this.heads_s = itemView.findViewById(R.id.heads_s);
            this.player_2 = itemView.findViewById(R.id.player_2);
            this.player_3 = itemView.findViewById(R.id.player_3);
            this.player_4 = itemView.findViewById(R.id.player_4);


            this.sq_cc = itemView.findViewById(R.id.sq_cc);
            this.sq_cc_over = itemView.findViewById(R.id.sq_cc_over);


        }

    }

}
