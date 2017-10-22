package com.bilalekremharmansa.countdown.webapi;


import android.util.Log;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bilalekremharmansa on 4.8.2017.
 */

public class APIController {

    APIService apiInterface;

    public APIController() {

        apiInterface = APIClient.createService(APIService.class, "abc", "123");

    }

    private List<APINumberGame> helperList;
    private APINumberGame helperGame;

    private ControllerListener listener;

    public interface ControllerListener {
        void online(APINumberGame APINumberGame);

        void offline();
    }

    public void setListener(ControllerListener listener) {
        this.listener = listener;
    }

    private Callback<APINumberGame> callbackGet = new Callback<APINumberGame>() {
        @Override
        public void onResponse(Call<APINumberGame> call, Response<APINumberGame> response) {
            helperGame = response.body();
            Log.d("webapi", response.code() + "");
            if (listener != null) {
                listener.online(helperGame);
            }
        }

        @Override
        public void onFailure(Call<APINumberGame> call, Throwable t) {
            call.cancel();
            listener.offline();
        }
    };


    public APINumberGame get(int numberOfLargeNumbers, int lastGameID) {
        APINumberGame APINumberGame = null;

        Call<APINumberGame> call = apiInterface.get(numberOfLargeNumbers, lastGameID);
        call.enqueue(callbackGet);

        return APINumberGame;
    }

    private Callback<List<APINumberGame>> callbackGetByNumber = new Callback<List<APINumberGame>>() {
        @Override
        public void onResponse(Call<List<APINumberGame>> call, Response<List<APINumberGame>> response) {
            helperList = response.body();
        }

        @Override
        public void onFailure(Call<List<APINumberGame>> call, Throwable t) {

        }
    };


}
