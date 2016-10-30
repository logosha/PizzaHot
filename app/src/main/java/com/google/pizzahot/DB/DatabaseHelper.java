package com.google.pizzahot.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.pizzahot.DB.tables.LocationData;
import com.google.pizzahot.DB.tables.VenueData;
import com.google.pizzahot.model.Location;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by Алексей on 28.10.2016.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="foresquarePizza.db";

    private static final int DATABASE_VERSION = 1;

    private Dao<VenueData, Integer> venueDao = null;
    private Dao<LocationData, Integer> locationDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, VenueData.class);
            TableUtils.createTable(connectionSource, LocationData.class);
        }
        catch (Exception e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            TableUtils.dropTable(connectionSource, VenueData.class, true);
            TableUtils.dropTable(connectionSource, LocationData.class, true);
            onCreate(db, connectionSource);
        }
        catch (Exception e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    public Dao<VenueData, Integer> getVenueDAO() throws Exception{
        if(venueDao == null){
            venueDao = getDao(VenueData.class);
        }
        return venueDao;
    }
    public Dao<LocationData, Integer> getLocationDAO() throws Exception{
        if(locationDao == null){
            locationDao = getDao(LocationData.class);
        }
        return locationDao;
    }

    @Override
    public void close(){
        super.close();
        venueDao = null;
        locationDao = null;
    }
}