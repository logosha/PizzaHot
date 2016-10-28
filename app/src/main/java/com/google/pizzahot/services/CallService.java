package com.google.pizzahot.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.pizzahot.model.FoursquareResponse;
import com.google.pizzahot.model.FoursquareRestaurant;
import com.google.pizzahot.model.Meta;
import com.google.pizzahot.model.Venue;
import com.google.pizzahot.model.VenuesData;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CallService extends IntentService {

    private static final String CLIENT_ID = "ZURIFJGOKBITYRPLBHZHRSXTHAFGYADA52RJRG5HHJPNVKXS";
    private static final String CLIENT_SECRET = "ZV21NT0DOHPKTKURHHOYNABHG14CLQ32J0WIQAADKZBQFNCH";
    private static final String VERSION = "20130815";
    private static final String LIMIT = "10";
    private static final String QUERY = "pizza";
    private static final String latitude = "40.7463956";
    private static final String longtitude = "-73.9852992";
    private static final String TAG = "myLogs";

    private static final String URL = "https://api.foursquare.com/v2/venues/search?client_id="
            + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=" + VERSION + "&limit=" + LIMIT + "&ll=" + latitude + "," + longtitude + "&query=" + QUERY;

    private static final Gson jsonMarshaller = new GsonBuilder().create();


    ArrayList<FoursquareRestaurant> pizzaList;

    public CallService() {
        super("CallService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {


        OkHttpClient client = new OkHttpClient();
        String jsonBody = null;
        Response response;
        try {
            Request request = new Request.Builder()
                .url(URL)
                .build();

            response = client.newCall(request).execute();
            jsonBody = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pizzaList = parseFoursquare(jsonBody);

    }



    private static ArrayList<FoursquareRestaurant> parseFoursquare(String response) {

        ArrayList<FoursquareRestaurant> temp = new ArrayList<FoursquareRestaurant>();

        FoursquareResponse resp = jsonMarshaller.fromJson(response, FoursquareResponse.class);

        if(resp.getMeta().getCode()==200){
            if(resp.getResponse()!=null
                    && resp.getResponse().getVenues() !=null
                    && resp.getResponse().getVenues().length>0){
                    Venue [] venues = resp.getResponse().getVenues();
                    for (int i = 0; i < venues.length; i++) {
                        Log.d(TAG,"Distance: " + venues[i].getLocation().getDistance());
                    }
            }else{
                // TODO сообщить что новых результатов нет
            }
        } else {
            // TODO убрать прогресс, сообщить об ошибке.
        }



        return null;

    }


}
