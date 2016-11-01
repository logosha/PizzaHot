package com.google.pizzahot;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.BroadcastReceiver;

import com.google.pizzahot.DB.DatabaseCommunication;
import com.google.pizzahot.DB.tables.VenueData;
import com.google.pizzahot.adapters.MyPagingAdaper;
import com.google.pizzahot.services.CallService;
import com.paging.listview.PagingListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends Activity implements LocationListener {

    private PagingListView listView;
    private MyPagingAdaper adapter;
    BroadcastReceiver broadcastReceiver;
    public final static String BROADCAST_ACTION = "com.google.pizzahot";
    public final static String PARAM_RESULT = "result";
    public final static int STATUS_FINISH_SUCCESS = 100;
    public final static int STATUS_FINISH_FAIL = 200;

    public static final double latitudeNY = 40.7463956;
    public static final double longitudeNY = -73.9852992;

    private static final String TAG = "myLogs";

    int offset, limit;

    private LocationManager locationManager;
    private Location location;
    List<VenueData> pizzaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseCommunication.getInstance(this);
        setContentView(R.layout.activity_main);
        listView = (PagingListView) findViewById(R.id.paging_list_view);
        pizzaList = DatabaseCommunication.getInstance(this).getLists();

        adapter = new MyPagingAdaper(pizzaList);


        listView.setAdapter(adapter);
            listView.setHasMoreItems(true);
        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                getNextPage();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PizzaActivity.class);
                VenueData vd = pizzaList.get(i);
                intent.putExtra("key", vd);
            }
        });

        getLocation();

        broadcastReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {

                int result = intent.getIntExtra(PARAM_RESULT, 0);
                switch (result) {
                    case STATUS_FINISH_SUCCESS:
                        Toast.makeText(MainActivity.this, "Information added to the database", Toast.LENGTH_LONG).show();
                        listView.onFinishLoading(false, null);
                        break;
                    case STATUS_FINISH_FAIL:
                        Toast.makeText(MainActivity.this, "Information not added to the database", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intFilt);

      /*
        DatabaseCommunication.getInstance(this).getLists();

        pizzaList = DatabaseCommunication.getInstance(this).getLists();
          Collections.sort(pizzaList, VenueData.VenueDistanceComparator);

        List listTitle = new ArrayList();
        for (int i = 0; i < pizzaList.size(); i++) {
            listTitle.add(i, pizzaList.get(i).getName() + ", " +  pizzaList.get(i).getDistance() +"m");
        }


         myAdapter = new ArrayAdapter(this, R.layout.row_layout, R.id.listText, listTitle);
         listView.setAdapter(myAdapter);

*/
    }

    private void getNextPage() {
        List list = DatabaseCommunication.getInstance(this).getOffsetLimitLists(offset, limit);
        if (list.isEmpty()){
            if (isOnline()){
                this.startService(new Intent(this, CallService.class));
            }else{
                Toast.makeText(this, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
            }
        } else {


        }

    }


    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        }
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onDestroy() {
        DatabaseCommunication.releaseHelper();
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