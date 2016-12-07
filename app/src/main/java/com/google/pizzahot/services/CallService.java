package com.google.pizzahot.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.pizzahot.DB.DatabaseCommunication;
import com.google.pizzahot.MainActivity;
import com.google.pizzahot.model.FoursquareResponse;
import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class CallService extends IntentService {

    private static final String CLIENT_ID = "ZURIFJGOKBITYRPLBHZHRSXTHAFGYADA52RJRG5HHJPNVKXS";
    private static final String CLIENT_SECRET = "ZV21NT0DOHPKTKURHHOYNABHG14CLQ32J0WIQAADKZBQFNCH";
    private static final String VERSION = "20130815";
    private static final String LIMIT = "50";
    private static final String QUERY = "pizza";
    public final static int STATUS_FINISH_SUCCESS = 100;
    public final static int STATUS_FINISH_FAIL = 200;
    public final static int STATUS_END = 300;

    private static final String TAG = "myLogs";

    private static final Gson jsonMarshaller = new GsonBuilder().create();

    private int offset;
    private double latitude;
    private double longtitude;


    public CallService() {
        super("CallService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        latitude = intent.getDoubleExtra("lat", MainActivity.latitudeNY);
        longtitude = intent.getDoubleExtra("lon", MainActivity.longitudeNY);
        offset = intent.getIntExtra("offset", offset);
        //intent.getIntExtra("offset", 0); //offset doesn't supported by API https://developer.foursquare.com/docs/venues/search
        sendRequest();
    }

    private void sendRequest() {

        String url = "https://api.foursquare.com/v2/venues/explore?client_id="
                + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v=" + VERSION + "&limit=" + LIMIT + "&offset=" + offset + "&ll=" + latitude + "," + longtitude + "&query=" + QUERY + "&venuePhotos=1" + "&sortByDistance=1";

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
        Log.d(TAG, jsonBody);
        parseFoursquare(jsonBody);
    }


    public void sendBroadcast(int status) {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra(MainActivity.PARAM_RESULT, status);
        sendBroadcast(intent);
    }


    public void parseFoursquare(String response) {
        // Log.d(TAG, response);
        FoursquareResponse resp = jsonMarshaller.fromJson(response, FoursquareResponse.class);

        if (resp.getMeta().getCode() == 200) {
            if (resp.getMeta().getCode() == 200) {
                if (resp.getResponse() != null
                        && resp.getResponse().getVenueGroups().get(0).getGroupItems() != null
                        && resp.getResponse().getVenueGroups().get(0).getGroupItems().size() > 0) {

                    DatabaseCommunication.getInstance(this).addVenues(resp);

                    sendBroadcast(STATUS_FINISH_SUCCESS);
                } else {
                    sendBroadcast(STATUS_END);
                }
            } else {
                sendBroadcast(STATUS_FINISH_FAIL);
            }
        }

    }
}