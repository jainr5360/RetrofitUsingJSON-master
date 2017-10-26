package com.hogo.rahul.retrofitusingjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static android.R.attr.name;

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
//                    for (int i = 0; i < mlist.size(); i++) {
//
//                        Userdatum details = new Userdatum();
//                        name = details.getName();
//                        String id = details.getId();
//                        details.setId(id);
//                        details.setName(name);
////                        details.setImg(image);
//                        detailsList.add(details);
//                        Log.d("Menu", "Menu details : " + id + name);
//                    }
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
}
