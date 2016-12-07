package com.google.pizzahot;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.pizzahot.DB.tables.VenueData;

public class PizzaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        TextView tvName = (TextView) findViewById(R.id.venue_name);
        TextView tvCityAddress = (TextView) findViewById(R.id.venue_city_and_address);
        TextView tvDistance = (TextView) findViewById(R.id.venue_distance);
        TextView tvPhone = (TextView) findViewById(R.id.venue_phone);
        TextView tvURL = (TextView) findViewById(R.id.venue_url);


        Intent intent = this.getIntent();
        final VenueData value = (VenueData) intent.getSerializableExtra("key");

        tvName.setText(value.getName());
        tvCityAddress.setText( value.getCity() + ", " + value.getAddress());
        tvDistance.setText("Distance: " + value.getDistance());
        tvPhone.setText(value.getPhone());
        tvURL.setText(value.getUrl());

        tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+value.getPhone()));
                startActivity(intent);
            }
        });

        tvURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(value.getUrl())));
            }
        });

    }
}
