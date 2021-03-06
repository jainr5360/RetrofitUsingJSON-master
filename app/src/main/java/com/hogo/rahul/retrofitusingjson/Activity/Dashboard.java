package com.hogo.rahul.retrofitusingjson.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.hogo.rahul.retrofitusingjson.Adapter.MenuHomeAdapter;
import com.hogo.rahul.retrofitusingjson.R;
import com.hogo.rahul.retrofitusingjson.Retrofit2.MyResponse;
import com.hogo.rahul.retrofitusingjson.Retrofit2.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity {

    ArrayList<Userdatum> mlist = new ArrayList<>();
    List<Userdatum> detailsList = new ArrayList<>();
    MenuHomeAdapter adapter;
    RecyclerView rvMenu;
    String name;
    String image;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        rvMenu = (RecyclerView) findViewById(R.id.rv_menu);
        getData();
//        getDependentList(1);

        rvMenu.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvMenu, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), SubItemActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void getData() {

        retrofit2.Call<PostGsonModel> postGsonModelCall = Menuapi.getService().getPostList();
        postGsonModelCall.enqueue(new Callback<PostGsonModel>() {
            @Override
            public void onResponse(retrofit2.Call<PostGsonModel> call, Response<PostGsonModel> response) {

                PostGsonModel gsonModel = response.body();
                Toast.makeText(Dashboard.this, "sucess" + gsonModel, Toast.LENGTH_SHORT).show();
                mlist = (ArrayList<Userdatum>) gsonModel.getUserdata();
                try {
                    if (!mlist.isEmpty()) {
                        adapter = new MenuHomeAdapter(getApplicationContext(), mlist);
                        rvMenu.setAdapter(adapter);
                        rvMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    } else {
                        Toast.makeText(getApplicationContext(), "List is empty", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<PostGsonModel> call, Throwable t) {
                Toast.makeText(Dashboard.this, "error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //    private void getDependentList(int PrimaryID) {
//
//        retrofit2.Call<MyResponse> call = null;
//        call = Utils.getWebService().getData(1);
//        Log.e("115 ", ": :" + call.request().url().toString());
//        call.enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<MyResponse> call, Response<MyResponse> response) {
//                Log.e("DependentList", " : " + new GsonBuilder().create().toJson(response.body()));
//                Log.e("DependentList", " : " + response.code());
//
//                MyResponse myResponse = response.body();
//
//                adapter = new MenuHomeAdapter(getApplicationContext(), myResponse.getUserdata());
//                rvMenu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                rvMenu.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }
    public static interface ClickListener {

        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    private class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        GestureDetector detector;
        Dashboard.ClickListener clickListener;

        public RecyclerTouchListener(Context context, RecyclerView view, final ClickListener listener) {

            this.clickListener = listener;
            detector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);

                    View child = rvMenu.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {

                        clickListener.onLongClick(child, rvMenu.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && detector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
