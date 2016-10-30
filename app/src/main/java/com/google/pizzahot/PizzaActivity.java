package com.google.pizzahot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PizzaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        TextView tvName = (TextView) findViewById(R.id.venue_name);
        TextView tvDistance = (TextView) findViewById(R.id.venue_distance);
        TextView tvCity = (TextView) findViewById(R.id.venue_city);
        TextView tvCountry = (TextView) findViewById(R.id.venue_country);
        TextView tvPostalCode = (TextView) findViewById(R.id.venue_postal_code);

        tvName.setText("");
        tvDistance.setText("");
        tvCity.setText("");
        tvCountry.setText("");
        tvPostalCode.setText("");

    }
}
