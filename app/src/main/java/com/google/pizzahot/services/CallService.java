package com.google.pizzahot.services;

import android.app.IntentService;
import android.content.Intent;

import com.google.pizzahot.model.FoursquareRestaurant;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CallService extends IntentService {

    final String CLIENT_ID = "ZURIFJGOKBITYRPLBHZHRSXTHAFGYADA52RJRG5HHJPNVKXS";
    final String CLIENT_SECRET = "ZV21NT0DOHPKTKURHHOYNABHG14CLQ32J0WIQAADKZBQFNCH";

    final String latitude = "40.7463956";
    final String longtitude = "-73.9852992";

    ArrayList<FoursquareRestaurant> pizzaList;

    public CallService() {
        super("CallService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {

        String url = "https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET +
                "&v=20130815&ll=" + latitude + "," + longtitude + "&query=pizza&offset=0&limit=10";
        OkHttpClient client = new OkHttpClient();
        String jsonBody = null;
        Response response;
        try {
            Request request = new Request.Builder()
                .url(url)
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
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("response")) {
                if (jsonObject.getJSONObject("response").has("venues")) {
                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareRestaurant poi = new FoursquareRestaurant();
                        if (jsonArray.getJSONObject(i).has("name")) {
                            poi.setName(jsonArray.getJSONObject(i).getString("name"));

                            if (jsonArray.getJSONObject(i).has("location")) {
                                if (jsonArray.getJSONObject(i).getJSONObject("location").has("distance")) {
                                     poi.setDistance(jsonArray.getJSONObject(i).getJSONObject("location").getInt("distance"));

                                    temp.add(poi);
                                }
                            }
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<FoursquareRestaurant>();
        }
        return temp;

    }


}
