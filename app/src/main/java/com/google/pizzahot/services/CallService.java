package com.google.pizzahot.services;

import android.Manifest;
import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.pizzahot.DB.HelperFactory;
import com.google.pizzahot.MainActivity;
import com.google.pizzahot.model.FoursquareResponse;
import com.google.pizzahot.model.FoursquareRestaurant;
import com.google.pizzahot.model.Venue;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CallService extends IntentService {

    private static final String CLIENT_ID = "ZURIFJGOKBITYRPLBHZHRSXTHAFGYADA52RJRG5HHJPNVKXS";
    private static final String CLIENT_SECRET = "ZV21NT0DOHPKTKURHHOYNABHG14CLQ32J0WIQAADKZBQFNCH";
    private static final String VERSION = "20130815";
    private static final String LIMIT = "10";
    private static final String QUERY = "pizza";

    private static final String TAG = "myLogs";


    private static final Gson jsonMarshaller = new GsonBuilder().create();
    ArrayList<FoursquareRestaurant> pizzaList;


    private double latitude;
    private double longtitude;


    public CallService() {
        super("CallService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        latitude = intent.getDoubleExtra("lat", MainActivity.latitudeNY);
        longtitude = intent.getDoubleExtra("lat", MainActivity.longitudeNY);
        sendRequest();
    }

    private void sendRequest() {
        String url = "https://api.foursquare.com/v2/venues/search?client_id="
                + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=" + VERSION + "&limit=" + LIMIT + "&ll=" + latitude + "," + longtitude + "&query=" + QUERY;

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

        parseFoursquare(jsonBody);
    }


    public void sendBroadcast() {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra(MainActivity.PARAM_RESULT, MainActivity.STATUS_FINISH_SUCCESS);
        sendBroadcast(intent);
    }

    public void parseFoursquare(String response) {

        FoursquareResponse resp = jsonMarshaller.fromJson(response, FoursquareResponse.class);

        if (resp.getMeta().getCode() == 200) {
            if (resp.getResponse() != null
                    && resp.getResponse().getVenues() != null
                    && resp.getResponse().getVenues().length > 0) {
                HelperFactory.getInstance(this).addVenues(resp);
                HelperFactory.getInstance(this).getLists();
                sendBroadcast();
            } else {
            // TODO сообщить что новы   х результатов нет
        }
    } else {
        // TODO убрать прогресс, сообщить об ошибке.
    }

    }

}