package com.hogo.rahul.retrofitusingjson.Retrofit2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface WebService {

    @GET("api/app_controller/bh_menu_item/{itemId}")
    Call<MyResponse> getData(@Path("itemId") int itemId);

}
