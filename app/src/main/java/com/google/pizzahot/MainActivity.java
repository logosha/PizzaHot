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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.BroadcastReceiver;

import com.google.pizzahot.DB.DatabaseCommunication;
import com.google.pizzahot.DB.tables.VenueData;
import com.google.pizzahot.adapters.MyPagingAdaper;
import com.google.pizzahot.services.CallService;
import com.paging.listview.PagingListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements LocationListener {

    private PagingListView listView;
    private TextView textView;
    private Button defaultButton;
    private MyPagingAdaper adapter;
    BroadcastReceiver broadcastReceiver;
    public final static String BROADCAST_ACTION = "com.google.pizzahot";
    public final static String PARAM_RESULT = "result";
    public final static int STATUS_FINISH_SUCCESS = 100;
    public final static int STATUS_FINISH_FAIL = 200;
    public final static int STATUS_END = 300;

    public static final double latitudeNY = 40.7463956;
    public static final double longitudeNY = -73.9852992;
    public static final int LIMIT = 10;

    private static final String TAG = "myLogs";


    private LocationManager locationManager;
    private Location location;
    List<VenueData> pizzaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseCommunication.getInstance(this);
        setContentView(R.layout.activity_main);
        listView = (PagingListView) findViewById(R.id.paging_list_view);
        textView = (TextView) findViewById(R.id.default_text);
        defaultButton = (Button) findViewById(R.id.default_button);

        adapter = new MyPagingAdaper();

        listView.setAdapter(adapter);
        listView.setHasMoreItems(true);

        listView.setPagingableListener(new PagingListView.Pagingable() {
            @Override
            public void onLoadMoreItems() {
                if (location != null) {
                    getNextPage();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PizzaActivity.class);
                VenueData vd = pizzaList.get(i);
                intent.putExtra("key", vd);
                startActivity(intent);
            }
        });

        if (!isOnline()) {
            pizzaList = DatabaseCommunication.getInstance(this).getOffsetLimitLists(0, LIMIT);
            if (pizzaList.size() > 0) {
                listView.onFinishLoading(true, pizzaList);
                location = new Location("dummyprovider");
                location.setLatitude(pizzaList.get(0).getLatitude());
                location.setLongitude(pizzaList.get(0).getLongitude());
            } else {
                listView.setHasMoreItems(false);
                textView.setVisibility(View.VISIBLE);
            }
        } else {
            pizzaList = new ArrayList<>();
        }

        getLocation();

        broadcastReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {

                int result = intent.getIntExtra(PARAM_RESULT, 0);
                switch (result) {
                    case STATUS_FINISH_SUCCESS:
                        Toast.makeText(MainActivity.this, "Information added to the database", Toast.LENGTH_LONG).show();
                        List<VenueData> list;
                        list = DatabaseCommunication.getInstance(MainActivity.this).getOffsetLimitLists(pizzaList.size(), LIMIT);
                        pizzaList.addAll(list);
                        listView.onFinishLoading(true, list);
                        break;
                    case STATUS_FINISH_FAIL:
                        Toast.makeText(MainActivity.this, "Information not added to the database", Toast.LENGTH_LONG).show();
                        listView.onFinishLoading(false, null);
                        break;
                    case STATUS_END:
                        listView.onFinishLoading(false, null);
                        break;
                }
            }
        };

        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intFilt);

        defaultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    DatabaseCommunication.getInstance(MainActivity.this).clearTable();
                    adapter.removeAllItems();
                    onLocationChanged(null);
                }
            }
        });


    }

    private void getNextPage() {
        List list = DatabaseCommunication.getInstance(this).getOffsetLimitLists(pizzaList.size(), LIMIT);
        if (list.isEmpty()) {
            listView.onFinishLoading(false, null);
        } else {
            pizzaList.addAll(list);
            listView.onFinishLoading(true, list);
        }
    }


    private void getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    onLocationChanged(null);
                }
                break;
            }
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

        if (isOnline()) {
            DatabaseCommunication.getInstance(this).clearTable();
            Intent intent = new Intent(this, CallService.class);
            intent.putExtra("lat", this.location.getLatitude());
            intent.putExtra("lon", this.location.getLongitude());
            intent.putExtra("offset", pizzaList.size());
            this.startService(intent);
        } else {
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