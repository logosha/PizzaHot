package com.google.pizzahot.DB;

import android.content.Context;
import android.util.Log;

import com.google.pizzahot.DB.tables.VenueData;
import com.google.pizzahot.model.FoursquareResponse;
import com.google.pizzahot.model.Venue;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Алексей on 28.10.2016.
 */

public class DatabaseCommunication {

    private static DatabaseCommunication instance;
    private DatabaseHelper databaseHelper;
    private static final String TAG = "myLogs";


    private DatabaseHelper getHelper(){
        return databaseHelper;
    }

    public static DatabaseCommunication getInstance(Context context){
        if(instance == null){
            instance = new DatabaseCommunication();
        }
        if(instance.databaseHelper == null) {
            instance.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        }
        return instance;
    }
    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        instance.databaseHelper = null;
    }

    public void addVenues(FoursquareResponse foursquareResponse) {
        getHelper();
        List venues = foursquareResponse.getResponse().getVenues();
        Collections.sort(venues, Venue.VenueDistanceComparator);

        for(Venue venue:foursquareResponse.getResponse().getVenues()) {
            addVenueData(venue);
        }
    }

    private VenueData addVenueData(Venue venue) {
        try {
            Dao<VenueData, Integer> dao = databaseHelper.getVenueDAO();
            VenueData vData = new VenueData(venue);

            dao.create(vData);
            return vData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<VenueData> getOffsetLimitLists(long offset, long limit) {
        List<VenueData> listResult = new ArrayList<VenueData>();
        try {
            Dao<VenueData, Integer> dao = databaseHelper.getVenueDAO();
            listResult = dao.queryBuilder().offset(offset).limit(limit).query();
            logList(listResult);
            return listResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listResult;
    }

    public void clearTable(){
        try {
            TableUtils.clearTable(databaseHelper.getConnectionSource(), VenueData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<VenueData> getLists() {
       try {
            Dao<VenueData, Integer> dao = databaseHelper.getVenueDAO();
            List<VenueData> listResult = dao.queryForAll();
            logList(listResult);
            return listResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void logList(List<VenueData> lists) {
        if (lists != null && !lists.isEmpty()) {
            for (VenueData qData : lists) {
                Log.i(TAG,qData.toString());
            }
        } else {
            Log.e(TAG,"lists isEmpty or Null");
        }
    }


}