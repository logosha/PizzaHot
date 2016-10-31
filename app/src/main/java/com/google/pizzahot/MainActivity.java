package com.google.pizzahot;

import android.Manifest;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.content.BroadcastReceiver;

import com.google.pizzahot.DB.DatabaseHelper;
import com.google.pizzahot.DB.HelperFactory;
import com.google.pizzahot.services.CallService;


public class MainActivity extends ListActivity implements LocationListener {

    ArrayAdapter<String> myAdapter;
    BroadcastReceiver broadcastReceiver;
    public final static String BROADCAST_ACTION = "com.google.pizzahot";
    public final static String PARAM_RESULT = "result";
    public final static int STATUS_FINISH_SUCCESS = 100;
    public final static int STATUS_FINISH_FAIL = 200;

    public static final double latitudeNY = 40.7463956;
    public static final double longitudeNY = -73.9852992;

    private static final String TAG = "myLogs";


    private LocationManager locationManager;
    private Location location;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperFactory.getInstance(this);
        setContentView(com.google.pizzahot.R.layout.activity_main);

        broadcastReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {

                int result = intent.getIntExtra(PARAM_RESULT, 0);
                switch (result) {
                    case STATUS_FINISH_SUCCESS:
                        Toast.makeText(MainActivity.this, "Information added to the database", Toast.LENGTH_LONG).show();
                        break;
                    case STATUS_FINISH_FAIL:
                        Toast.makeText(MainActivity.this, "Information not added to the database", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intFilt);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


    }




    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }



 /*   List<String> listTitle = new ArrayList<String>();

    for (int i = 0; i < pizzaList.size(); i++) {
        listTitle.add(i, pizzaList.get(i).getName() + ", " + pizzaList.get(i).getDistance());
    }

    myAdapter = new ArrayAdapter<>(this, com.google.pizzahot.R.layout.row_layout, com.google.pizzahot.R.id.listText, listTitle);
    setListAdapter(myAdapter);
*/

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onDestroy() {
        HelperFactory.releaseHelper();
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.location = location;
        } else {
            this.location = new Location("dummyprovider");
            this.location.setLongitude(longitudeNY);
            this.location.setLatitude(latitudeNY);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.removeUpdates(this);
        }

        Intent intent = new Intent(this, CallService.class);
        intent.putExtra("lat", this.location.getLatitude());
        intent.putExtra("lon", this.location.getLongitude());
        if (isOnline()){
            this.startService(new Intent(this, CallService.class));
        }else{
            Toast.makeText(this, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}