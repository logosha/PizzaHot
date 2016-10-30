package com.google.pizzahot;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.content.BroadcastReceiver;

import com.google.pizzahot.DB.HelperFactory;
import com.google.pizzahot.services.CallService;



public class MainActivity extends ListActivity {

    ArrayAdapter<String> myAdapter;
    BroadcastReceiver broadcastReceiver;
    public final static String BROADCAST_ACTION = "com.google.pizzahot";
    public final static String PARAM_RESULT = "result";
    public final static int STATUS_FINISH_SUCCESS = 100;
    public final static int STATUS_FINISH_FAIL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HelperFactory.setHelper(getApplicationContext());
        setContentView(R.layout.activity_main);

        if (isOnline()){
           this.startService(new Intent(this, CallService.class));
        }else{
            Toast.makeText(this, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
        }

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
}
