package com.google.pizzahot.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.pizzahot.DB.tables.LocationTable;
import com.google.pizzahot.DB.tables.VenueTable;
import com.google.pizzahot.model.Venue;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Created by Алексей on 28.10.2016.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    //имя файла базы данных который будет храниться в /data/data/APPNAME/DATABASE_NAME.db
    private static final String DATABASE_NAME ="foresquarePizza.db";

    //с каждым увеличением версии, при нахождении в устройстве БД с предыдущей версией будет выполнен метод onUpgrade();
    private static final int DATABASE_VERSION = 1;

    //ссылки на DAO соответсвующие сущностям, хранимым в БД
    private VenueTable venueTable = null;
    private LocationTable locationTable = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Выполняется, когда файл с БД не найден на устройстве
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, VenueTable.class);
            TableUtils.createTable(connectionSource, LocationTable.class);
        }
        catch (Exception e){
                        Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    //Выполняется, когда БД имеет версию отличную от текущей
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            //Так делают ленивые, гораздо предпочтительнее не удаляя БД аккуратно вносить изменения
            TableUtils.dropTable(connectionSource, VenueTable.class, true);
            TableUtils.dropTable(connectionSource, LocationTable.class, true);
            onCreate(db, connectionSource);
        }
        catch (Exception e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    //синглтон для GoalDAO
    public VenueTable getVenueDAO() throws Exception{
        if(venueTable == null){
            venueTable = getDao(VenueTable.class);
        }
        return venueTable;
    }
    //синглтон для RoleDAO
    public LocationTable getLocationDAO() throws Exception{
        if(locationTable == null){
            locationTable = getDao(LocationTable.class);
        }
        return locationTable;
    }

    //выполняется при закрытии приложения
    @Override
    public void close(){
        super.close();
        venueTable = null;
        locationTable = null;
    }
}