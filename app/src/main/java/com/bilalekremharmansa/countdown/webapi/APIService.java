package com.bilalekremharmansa.countdown.webapi;

import java.util.List;


import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bilalekremharmansa on 4.8.2017.
 */

public interface APIService {

    @GET("game")
    Call<APINumberGame> get(@Query("numberOfLargeNumbers") int numberOfLargeNumbers, @Query("lastGameID") int lastGameID);

    @GET("gamePack")
    Call<List<APINumberGame>> getPack();

    @POST("game")
    Call<APINumberGame> insert(@Body APINumberGame APINumberGame);

    @PUT("game")
    Call<APINumberGame> update(@Body APINumberGame APINumberGame);

    @DELETE("game/{ID}")
    Call<APINumberGame> delete(@Path("ID") int ID);


}
