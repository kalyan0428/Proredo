package com.sport.playsqorr.fragments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sport.playsqorr.R;
import com.sport.playsqorr.database.DB_Constants;
import com.sport.playsqorr.database.DataBaseHelper;
import com.sport.playsqorr.views.AddFunds;
import com.sport.playsqorr.views.PlayWithCash;
import com.sport.playsqorr.views.Referfriend;

import java.util.ArrayList;

public class PromosFragment extends Fragment {
    public static final String TAG = PromosFragment.class.getSimpleName();
    private ArrayList<String> lisItems = new ArrayList<>();
    private RecyclerViewAdapterNew recycleAdapter;
    private RecyclerView rvPromoList;
    private DataBaseHelper mydb;
    Cursor cursor;
    private String ROLE;
    private LinearLayout llLoggedUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promos, container, false);
        llLoggedUser = view.findViewById(R.id.llLoggedUser);

        mydb = new DataBaseHelper(getActivity());

        lisItems.add("FIRST ELEMENT");
        rvPromoList = view.findViewById(R.id.rvPromoList);
        recycleAdapter = new RecyclerViewAdapterNew(lisItems, getActivity());
        rvPromoList.setAdapter(recycleAdapter);


        return view;
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
    public void onResume() {
        super.onResume();
        getUserInfoFromDB();
    }

    private void getUserInfoFromDB() {
        cursor = mydb.getAllUserInfo();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                ROLE = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_MODETYPE));
          /*  CASH_BAL = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_CASHBALANCE));
            AMOUNT_CASH = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOTALCASHBALANCE));
            AMOUNT_TOKEN = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_TOKENBALANCE));
            AVATAR = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_IMAGE));
            MYWiNS = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_WINS));
            ACCNAME = cursor.getString(cursor.getColumnIndex(DB_Constants.USER_NAME));
            USEREMAIL=cursor.getString(cursor.getColumnIndex(DB_Constants.USER_EMAIL));*/
            }
            cursor.close();
        }

        if (ROLE != null && (ROLE.equalsIgnoreCase("cash") || ROLE.equalsIgnoreCase("tokens"))) {
            rvPromoList.setVisibility(View.VISIBLE);
//        llLoggedUser.setVisibility(View.VISIBLE);
        } else {
            llLoggedUser.setVisibility(View.GONE);
            rvPromoList.setVisibility(View.VISIBLE);
            if (recycleAdapter != null) {
                recycleAdapter.notifyDataSetChanged();
            }
        }
    }

    public class RecyclerViewAdapterNew extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        // An Activity's Context.
        private final Context context;
        // The list of banner ads and menu items.
        private final ArrayList<String> lisItems;

        RecyclerViewAdapterNew(ArrayList<String> lisItems, Context context) {
            this.lisItems = lisItems;
            this.context = context;
        }

        class MenuItemViewHolder extends RecyclerView.ViewHolder {
            MenuItemViewHolder(View convertView) {
                super(convertView);
            }
        }

        @Override
        public int getItemCount() {
            return lisItems.size();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View menuItemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.fragment_promos_list, viewGroup, false);
            LinearLayout guest_l = menuItemLayoutView.findViewById(R.id.guest_1);
            LinearLayout guest_2 = menuItemLayoutView.findViewById(R.id.guest_2);
            LinearLayout user_login = menuItemLayoutView.findViewById(R.id.user_login);
            TextView textView2 = menuItemLayoutView.findViewById(R.id.tvpplaymore);
            TextView textView_add = menuItemLayoutView.findViewById(R.id.tvpplay);

            if (ROLE != null && (ROLE.equalsIgnoreCase("cash") || ROLE.equalsIgnoreCase("tokens"))) {
                guest_l.setVisibility(View.GONE);
                guest_2.setVisibility(View.VISIBLE);
                user_login.setVisibility(View.VISIBLE);

                TextView textView = menuItemLayoutView.findViewById(R.id.tvRefer);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, Referfriend.class);
//                    intent.putExtra("SIGN_UP_BONUS","SIGNUPBONUS5");
                        startActivity(intent);
                    }
                });

                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {




                        String url = "https://promos.playsqorr.com/NFLVETS";//9666070070
//                        String url = "https://games.playsqorr.com/nflvets";//9666070070
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });


                textView_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        ContentValues cv = new ContentValues();
                        cv.put(DB_Constants.USER_DEEPLINK_CODE,"NFLVETS");
                        mydb.insertDeepInfo(cv);

                        Intent in = new Intent(getActivity(), AddFunds.class);
                        in.putExtra("ppcode", "NFLVETS");
                        startActivity(in);
                    }
                });

            } else {
                guest_l.setVisibility(View.VISIBLE);
                guest_2.setVisibility(View.VISIBLE);
                user_login.setVisibility(View.GONE);
                TextView textView = menuItemLayoutView.findViewById(R.id.tvRedeem);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, PlayWithCash.class);
                    intent.putExtra("SIGN_UP_BONUS","SIGNUPBONUS5");
                        startActivity(intent);
                    }
                });




                textView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        String url = "https://promos.playsqorr.com/NFLVETS";//9666070070
//                        String url = "https://games.playsqorr.com/nflvets";//9666070070
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                textView_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentValues cv = new ContentValues();
                        cv.put(DB_Constants.USER_DEEPLINK_CODE,"NFLVETS");
                        mydb.insertDeepInfo(cv);

                        Intent in = new Intent(getActivity(), PlayWithCash.class);
                        in.putExtra("ppcode", "NFLVETS");
                        startActivity(in);
                    }
                });

            }


            return new RecyclerViewAdapterNew.MenuItemViewHolder(menuItemLayoutView);
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

        }
    }

}
