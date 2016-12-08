package com.google.pizzahot;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.pizzahot.DB.tables.VenueData;
import com.squareup.picasso.Picasso;

public class PizzaActivity extends AppCompatActivity {

    String urlPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza);

        TextView tvName = (TextView) findViewById(R.id.venue_name);
        TextView tvCityAddress = (TextView) findViewById(R.id.venue_city_and_address);
        TextView tvDistance = (TextView) findViewById(R.id.venue_distance);
        TextView tvPhone = (TextView) findViewById(R.id.venue_phone);
        TextView tvURL = (TextView) findViewById(R.id.venue_url);
        ImageView image = (ImageView) findViewById(R.id.venue_photo);



        Intent intent = this.getIntent();
        final VenueData value = (VenueData) intent.getSerializableExtra("key");

        tvName.setText(value.getName());
        tvCityAddress.setText( value.getCity() + ", " + value.getAddress());
        tvDistance.setText("Distance: " + value.getDistance());
        tvPhone.setText(value.getPhone());
        tvURL.setText(value.getUrl());

        Picasso.with(this)
                .load(pictureRequest(value))
                .into(image);

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

    private String pictureRequest(VenueData val) {
        if (val != null) {
            StringBuilder builder = new StringBuilder();
            builder.append(val.getPrefix())
            //        .append(val.getWidth())
                    .append("1024")
            //        .append(val.getHeight())
                    .append(val.getSuffix());
            return builder.toString();
        }
        return "nothing";
    }
}
