package com.google.pizzahot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.pizzahot.DB.tables.VenueData;

public class PizzaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        TextView tvName = (TextView) findViewById(R.id.venue_name);
        TextView tvDistance = (TextView) findViewById(R.id.venue_distance);
        TextView tvLatCity = (TextView) findViewById(R.id.venue_city);
        TextView tvCountry = (TextView) findViewById(R.id.venue_country);
        TextView tvPost = (TextView) findViewById(R.id.venue_post);



        Intent intent = this.getIntent();
        VenueData value = (VenueData)intent.getSerializableExtra("key");
       
        tvName.setText(value.getName());
        tvDistance.setText(""+ value.getDistance());
        tvLatCity.setText(value.getCity());
        tvCountry.setText(value.getCountry());
        tvPost.setText(value.getPostalCode());


    }
}
