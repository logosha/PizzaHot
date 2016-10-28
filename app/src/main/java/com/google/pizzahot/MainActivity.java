package com.google.pizzahot;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.pizzahot.services.CallService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    ArrayAdapter<String> myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.google.pizzahot.R.layout.activity_main);

        if (isOnline()){
           this.startService(new Intent(this, CallService.class));
        }else{
            Toast.makeText(this, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
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
}
